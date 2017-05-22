package it.uniroma3.scr.domain;		//ridenominare il package

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
//valutare se distribuire le responsabilita di CognitiveTerminal fra altre nuove classi
//valutare la presenza di un noise generator che prende come parametro il numero di noise sequences da generare
//valutare se mettere le costanti come attributi semplici

public class CognitiveTerminal {
	private static final int BLOCK_SIZE = 1000;
	private final int numberOfSequences; //cambiare il nome
	private static final Double PFA=0.01;
	private Map<Double,Double> snr2Threshold;

	public CognitiveTerminal(List<Double> snratios, int sequenceSize){
		this.numberOfSequences=sequenceSize/BLOCK_SIZE;
		initialize(snratios); 
	}

	private void initialize(List<Double> snratios) {
		if(snratios==null || snratios.isEmpty())
			throw new IllegalArgumentException("Lista di SNR nulla o vuota");
		this.snr2Threshold=new HashMap<>();
		Double threshold;
		for(Double snr: snratios){
			if(snr!=null){
			threshold=calculateThreshold(snr);
			this.snr2Threshold.put(snr, threshold);
			}
			else
				throw new IllegalArgumentException("valore NULL di SNR");
		}
	}
//cambiarlo poi in private e mettere un metodo getSoglie()
	//valutare l'introduzione di un oggetto Calculator
	private double calculateThreshold(double snr) {
		List<DiscreteSignal> noiseSequences=new ArrayList<>(this.numberOfSequences); 
		Double linearSNR=Math.pow(10,snr/10);
		Double noisePower=1/linearSNR;
		Double threshold;
		for(int i=0;i<this.numberOfSequences;i++){
			DiscreteSignal noise=generateNoiseSequence(noisePower);
			noiseSequences.add(noise);
		}
		List<Double> noiseSequencesPowerValues=new ArrayList<>(this.numberOfSequences);	
		for(DiscreteSignal noise: noiseSequences){
			double power=noise.getPowerValue();
			noiseSequencesPowerValues.add(power);
		}
		Collections.sort(noiseSequencesPowerValues);
		int thresholdIndex=(int) (this.numberOfSequences-this.numberOfSequences*PFA-1);
		threshold=noiseSequencesPowerValues.get(thresholdIndex);
		return threshold;
	}

	private DiscreteSignal generateNoiseSequence(Double noisePower) {
		DiscreteSignal noise=new DiscreteSignal();
		Sample s;
		Random random=new Random();
		for(int i=0;i<BLOCK_SIZE;i++){
			Double realPart=random.nextGaussian()*Math.sqrt(noisePower/2);	
			Double imaginaryPart=random.nextGaussian()*Math.sqrt(noisePower/2); 
			s=new Sample(realPart,imaginaryPart);
			noise.addSample(s);
		}
		return noise;
	}

	//c'è un nome migliore?
	//gestire eventuali eccezioni
	//valutare se lasciare il tipo di ritorno a pd o mettere un boolean
	public Double checkPUSignalPresence(DiscreteSignal signal, Double snr){
		if(signal== null || signal.getSamples().isEmpty())
			throw new IllegalArgumentException("Segnale NULL o privo di campioni");
		if(!this.snr2Threshold.containsKey(snr))
			throw new IllegalArgumentException("Valore di SNR non valido");
		double threshold=this.snr2Threshold.get(snr);
		List<DiscreteSignal> signalFragments=signal.getFragments(this.numberOfSequences);
		Double pDetection;
		Double cont=0.0;
		for(DiscreteSignal fragment: signalFragments){
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
