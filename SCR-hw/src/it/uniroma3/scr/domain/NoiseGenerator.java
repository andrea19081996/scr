package it.uniroma3.scr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoiseGenerator {
	private double noisePower;
	
	public NoiseGenerator(double noisePower){
		this.noisePower=noisePower;
	}
	
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
	
	public List<DigitalSignal> generateNoiseSequences(int numberOfNoiseSequences, int noiseSequenceLenght){
		List<DigitalSignal> noiseSequences=new ArrayList<>(numberOfNoiseSequences); 
		for(int i=0;i<numberOfNoiseSequences;i++){
			DigitalSignal noise= this.generateNoiseSequence(noiseSequenceLenght);
			noiseSequences.add(noise);
		}
		return noiseSequences;
	}
}
