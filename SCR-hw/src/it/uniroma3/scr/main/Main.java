package it.uniroma3.scr.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.uniroma3.scr.domain.CognitiveTerminal;
import it.uniroma3.scr.domain.DigitalSignal;
import it.uniroma3.scr.utilities.SignalLoader;
import it.uniroma3.scr.utilities.UserInterface;
import it.uniroma3.scr.utilities.UserInterfaceConsole;

public class Main {
	private final static int SIGNAL_LENGHT=1000000;
	private UserInterface io;
	private List<Double> snratios;
	private CognitiveTerminal terminal; //definire una variabile per la lunghezza delle sequenze

	public Main(){
		this.io=new UserInterfaceConsole();
		this.snratios=new ArrayList<>();
		Collections.addAll(snratios, -3.0,2.0,-8.0,-13.0);
		this.terminal=new CognitiveTerminal(snratios,SIGNAL_LENGHT);
	}
	
	public static void main(String[] args){
		//definire una costante per il numero di sequenze
		//definire una costante per il numero di snr
		//usare una lista costante
		//sistemare la formattazione del testo stampato sulla console
		//inserire i javadoc
		Main main=new Main();
		main.printSNR2ThresholdValues();
		main.printAllSignalDetectionProbability(3);
	}
	
	private void printSNR2ThresholdValues(){
		this.io.println("\tTabella SNR -> Soglia");
		this.io.println("\t---------------------");
		this.io.println("\tSNR\t\tSoglia");
		for(Double snr: this.snratios)
			this.io.println("\t"+snr+"\t|\t"+this.terminal.getThreshold(snr));
		this.io.println("");
		this.io.println("");
	}
	
	private void printSignalDetectionProbability(int sequenceNumber){
		this.io.print("\tSequenza "+sequenceNumber);
		for(int i=1;i<=this.snratios.size();i++){
			DigitalSignal signal= new SignalLoader("resources/Sequenze/Sequenza_"+sequenceNumber+"/output_"+i+".dat").loadSignal();
			double pDetection=this.terminal.findPrimaryUser(signal, this.snratios.get(i-1));
			String truncatedPDetectionValue=new DecimalFormat("#.###").format(pDetection);
			this.io.print("\t\t"+this.snratios.get(i-1)+"dB -> "+truncatedPDetectionValue+"%");
		}
		this.io.println("");
	}
	
	private void printAllSignalDetectionProbability(int numberOfSequences){
		this.io.println("\tTabella Segnale -> Probabilita di detection in base a SNR");
		this.io.println("\t---------------------------------------------------------");

		for(int j=1;j<=numberOfSequences;j++){
			printSignalDetectionProbability(j);
		}
	}

}
