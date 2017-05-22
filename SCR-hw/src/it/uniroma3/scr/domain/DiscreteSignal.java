package it.uniroma3.scr.domain;

import java.util.ArrayList;
import java.util.List;

public class DiscreteSignal {
	private List<Sample> samples;

	public DiscreteSignal(){
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
		Double powerSum=0.0;
		for(Sample s: this.samples){
			if(s!=null)
				powerSum+=Math.pow(s.getModulus(),2.0);
		}
		return powerSum/this.samples.size();
	}

	public List<DiscreteSignal> getFragments(int fragmentNumber) {
		List<DiscreteSignal> signalFragments=new ArrayList<>();
		DiscreteSignal fragment=new DiscreteSignal();
		int cont=0;
		for(Sample s: this.getSamples()){
			fragment.addSample(s);
			cont++;
			if(cont==this.getSamples().size()/fragmentNumber){
				signalFragments.add(fragment);
				fragment=new DiscreteSignal();
				cont=0;
			}
		}
		return signalFragments;
	}

}
