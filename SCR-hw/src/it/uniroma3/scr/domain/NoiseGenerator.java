package it.uniroma3.scr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe che modella un blocco del terminale cognitivo
 * che si occupa di generare sequenze rumorose nota la 
 * potenza di rumore
 * @author Jerin George Mathew
 *
 */
public class NoiseGenerator {
	private double noisePower;
	
	public NoiseGenerator(double noisePower){
		this.noisePower=noisePower;
	}
	
	/**
	 * Genera una sequenza rumorosa di noiseSequenceLenght campioni di rumore
	 * @param noiseSequenceLenght il numero di campioni di rumore di cui deve essere 
	 * composta la sequenza rumorosa in output
	 * @return una sequenza rumorosa
	 */
	private DigitalSignal generateNoiseSequence(int noiseSequenceLenght) {
		DigitalSignal noise=new DigitalSignal();
		Sample s;
		Random random=new Random();
		for(int i=0;i<noiseSequenceLenght;i++){
			Double realPart=random.nextGaussian()*Math.sqrt(this.noisePower/2);	
			Double imaginaryPart=random.nextGaussian()*Math.sqrt(this.noisePower/2); 
			s=new Sample(realPart,imaginaryPart);
			noise.addSample(s);
		}
		return noise;
	}
	
	/**
	 * Genera una lista di numberOfNoiseSequences sequenze rumorose di 
	 * noiseSequenceLenght campioni di rumore
	 * @param numberOfNoiseSequences il numero di sequenze rumorose che si vuole generare
	 * @param noiseSequenceLenght la lunghezza delle sequenze rumorose generate
	 * @return una lista di sequenze rumorose 
	 */
	public List<DigitalSignal> generateNoiseSequences(int numberOfNoiseSequences, int noiseSequenceLenght){
		List<DigitalSignal> noiseSequences=new ArrayList<>(numberOfNoiseSequences); 
		for(int i=0;i<numberOfNoiseSequences;i++){
			DigitalSignal noise= this.generateNoiseSequence(noiseSequenceLenght);
			noiseSequences.add(noise);
		}
		return noiseSequences;
	}
}
