package it.uniroma3.scr.domain;		

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
//valutare se distribuire le responsabilita di CognitiveTerminal fra altre nuove classi
//valutare la presenza di un noise generator che prende come parametro il numero di noise sequences da generare
//valutare se mettere le costanti come attributi semplici
//commentare con javadoc le classi e spiegare il significato delle costanti 

public class CognitiveTerminal {
	private static final int BLOCK_SIZE = 1000;	 
	private static final int NUMBER_OF_NOISE_SEQUENCES=1000;
	private final int signalLenght;
	private static final double PFA=0.01;
	private Map<Double,Double> snr2Threshold;

	public CognitiveTerminal(List<Double> snratios, int sequenceSize){
		this.signalLenght=sequenceSize;
		initialize(snratios); 
	}

	private void initialize(List<Double> snratios) {
		if(snratios==null || snratios.isEmpty())
			throw new IllegalArgumentException("Lista di SNR nulla o vuota");
		this.snr2Threshold=new HashMap<>();
		double threshold;
		for(Double snr: snratios){
			threshold=calculateThreshold(snr);
			this.snr2Threshold.put(snr, threshold);
		}
	}
	//valutare l'introduzione di un oggetto Calculator
	//sostituire numberOfSequences con numberOfNoiseSequences
	private double calculateThreshold(double snr) {
		List<DigitalSignal> noiseSequences=new ArrayList<>(NUMBER_OF_NOISE_SEQUENCES); 
		Double linearSNR=Math.pow(10,snr/10);
		Double noisePower=1/linearSNR;
		Double threshold;
		for(int i=0;i<NUMBER_OF_NOISE_SEQUENCES;i++){
			DigitalSignal noise=generateNoiseSequence(noisePower);
			noiseSequences.add(noise);
		}
		List<Double> noiseSequencesPowerValues=new ArrayList<>(NUMBER_OF_NOISE_SEQUENCES);	
		for(DigitalSignal noise: noiseSequences){
			double power=noise.getPowerValue();
			noiseSequencesPowerValues.add(power);
		}
		Collections.sort(noiseSequencesPowerValues);
		int thresholdIndex=(int) (NUMBER_OF_NOISE_SEQUENCES-NUMBER_OF_NOISE_SEQUENCES*PFA-1);
		threshold=noiseSequencesPowerValues.get(thresholdIndex);
		return threshold;
	}
	
	private DigitalSignal generateNoiseSequence(Double noisePower) {
		DigitalSignal noise=new DigitalSignal();
		Sample s;
		Random random=new Random();
		for(int i=0;i<this.signalLenght/NUMBER_OF_NOISE_SEQUENCES;i++){
			Double realPart=random.nextGaussian()*Math.sqrt(noisePower/2);	
			Double imaginaryPart=random.nextGaussian()*Math.sqrt(noisePower/2); 
			s=new Sample(realPart,imaginaryPart);
			noise.addSample(s);
		}
		return noise;
	}

	//c'è un nome migliore?
	//valutare se lasciare il tipo di ritorno a pd o mettere un boolean
	public Double findPrimaryUser(DigitalSignal signal, Double snr){
		if(signal== null || signal.getSamples().isEmpty())
			throw new IllegalArgumentException("Segnale NULL o privo di campioni");
		if(!this.snr2Threshold.containsKey(snr))
			throw new IllegalArgumentException("Valore di SNR non valido");
		double threshold=this.snr2Threshold.get(snr);
		List<DigitalSignal> signalFragments=signal.getFragments(this.signalLenght/BLOCK_SIZE);
		Double pDetection;
		Double cont=0.0;
		for(DigitalSignal fragment: signalFragments){
			Double power=fragment.getPowerValue();
			if(power>threshold)
				cont++;
		}
		pDetection=(cont/signalFragments.size())*100;
		return pDetection;
	}
	public Double getThreshold(Double snr){
		return this.snr2Threshold.get(snr);
	}

}
