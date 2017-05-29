package it.uniroma3.scr.domain;

import java.util.ArrayList;
import java.util.List;

public class DigitalSignal {
	private List<Sample> samples;

	public DigitalSignal(){
		samples=new ArrayList<>();
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	public void addSample(Sample s){
		samples.add(s);
	}

	//ridenominare la variabile
	public double getPowerValue(){
		double powerSum=0.0;
		for(Sample s: this.samples){
			if(s!=null)
				powerSum+=Math.pow(s.getModulus(),2.0);
		}
		return powerSum/this.samples.size();
	}

	public List<DigitalSignal> getFragments(int fragmentNumber) {
		List<DigitalSignal> signalFragments=new ArrayList<>();
		DigitalSignal fragment=new DigitalSignal();
		int cont=0;
		for(Sample s: this.getSamples()){
			fragment.addSample(s);
			cont++;
			if(cont==this.getSamples().size()/fragmentNumber){
				signalFragments.add(fragment);
				fragment=new DigitalSignal();
				cont=0;
			}
		}
		return signalFragments;
	}

}
