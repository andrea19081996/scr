package it.uniroma3.scr.domain;		

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Classe che modella il terminale cognitivo, che dispone di una 
 * mappa SNR->soglia, i metodi per il calcolo della soglia per un dato 
 * SNR e per l'individuazione dell'utente primario 
 * secondo la tecnica dell'energy detector.
 * @author Jerin George Mathew
 */
public class CognitiveTerminal {
	private static final int BLOCK_SIZE = 1000;	 
	private static final int NUMBER_OF_NOISE_SEQUENCES=1000;
	private static final int SIGNAL_POWER=1;
	private static final int SIGNAL_LENGHT=1000000;
	private static final double PFA=0.01;
	private Map<Double,Double> snr2Threshold;

	public CognitiveTerminal(List<Double> snratios){
		initialize(snratios); 
	}

	/**
	 * Metodo che consente l'inizializzazione della mappa SNR->soglie
	 * del terminale cognitivo
	 * @param snratios la lista di SNR
	 */
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

	/**
	 * Calcola la soglia per un dato SNR e una data
	 * probabilita di falso allarme
	 * @param snr il valore di SNR per cui si vuole calcolare la soglia
	 * @return il valore di soglia corrispondente al valore di SNR passato come parametro
	 */
	private double calculateThreshold(double snr) {
		double linearSNR=Math.pow(10,snr/10);
		double noisePower=SIGNAL_POWER/linearSNR;
		double threshold;
		NoiseGenerator noiseGenerator=new NoiseGenerator(noisePower);
		List<DigitalSignal> noiseSequences=noiseGenerator.generateNoiseSequences(NUMBER_OF_NOISE_SEQUENCES, SIGNAL_LENGHT/NUMBER_OF_NOISE_SEQUENCES);; 
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
	
	/**
	 * Restituisce il valore di soglia corrispondente ad un 
	 * particolare valore di SNR
	 * @param snr il valore di SNR 
	 * @return il valore di soglia corrispondente al valore di SNR passato come parametro
	 */
	public double getThreshold(Double snr){
		return this.snr2Threshold.get(snr);
	}
	
	/**
	 * Restituisce la probabilita di detection per un dato segnale, noto il valore
	 * di SNR a cui il segnale è stato osservato
	 * @param signal il segnale in input usato per calcolare la probabilita di detection
	 * @param snr il valore di SNR a cui è stato osservato il segnale
	 * @return la probabilita di detection relativa a signal per quel valore di snr
	 */
	public Double findPrimaryUser(DigitalSignal signal, Double snr){
		if(signal== null || signal.getSamples().isEmpty())
			throw new IllegalArgumentException("Segnale NULL o privo di campioni");
		if(!this.snr2Threshold.containsKey(snr))
			throw new IllegalArgumentException("Valore di SNR non valido");
		double threshold=this.snr2Threshold.get(snr);
		List<DigitalSignal> signalFragments=signal.getFragments(SIGNAL_LENGHT/BLOCK_SIZE);
		double pDetection;
		double cont=0.0;
		for(DigitalSignal fragment: signalFragments){
			Double power=fragment.getPowerValue();
			if(power>threshold)
				cont++;
		}
		pDetection=(cont/signalFragments.size())*100;
		return pDetection;
	}


}
