package it.uniroma3.scr.domain;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe che modella un sequenza discreta
 * @author Jerin George Mathew
 */
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
	
	/**
	 * Calcola la potenza del segnale normalizzata 
	 * alla lunghezza della stessa
	 * @return la potenza del segnale
	 */
	public double getPowerValue(){
		double sum=0.0;
		for(Sample s: this.samples){
			if(s!=null)
				sum+=Math.pow(s.getModulus(),2.0);
		}
		return sum/this.samples.size();
	}
	
	/**
	 * Consente di frammentare il segnale in una serie di blocchi specificati
	 * come parametro
	 * @param fragmentNumber il numero di blocchi in cui deve essere suddiviso il segnale
	 * @return la lista di blocchi in cui è stato frammentato il segnale
	 */
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
