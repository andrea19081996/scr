package it.uniroma3.scr.main;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	public static void main(String[] args) throws FileNotFoundException, IOException{
		final int signalLenght=1000000;
		//definire una costante per il numero di sequenze
		//definire una costante per il numero di snr
		//usare una lista costante
		//sistemare la formattazione del testo stampato sulla console
		//inserire i javadoc
		UserInterface io=new UserInterfaceConsole();
		List<Double> snratios=new ArrayList<>();
		Collections.addAll(snratios, -3.0,2.0,-8.0,-13.0);
		CognitiveTerminal terminal=new CognitiveTerminal(snratios,signalLenght); //definire una variabile per la lunghezza delle sequenze
		printSNR2ThresholdValues(io,snratios,terminal);
		printAllSignalDetectionProbability(3,snratios,terminal,io);
	}
	
	private static void printSNR2ThresholdValues(UserInterface io,List<Double> snratios, CognitiveTerminal terminal){
		io.println("\tTabella SNR -> Soglia");
		io.println("\t---------------------");
		io.println("\tSNR\t\tSoglia");
		for(Double snr: snratios)
			io.println("\t"+snr+"\t|\t"+terminal.getThreshold(snr));
		io.println("");
		io.println("");
	}
	private static void printSignalDetectionProbability(int sequenceNumber, List<Double> snratios, CognitiveTerminal terminal,UserInterface io){
		io.print("\tSequenza "+sequenceNumber);
		for(int i=1;i<=snratios.size();i++){
			DigitalSignal signal= new SignalLoader("resources/Sequenze/Sequenza_"+sequenceNumber+"/output_"+i+".dat").loadSignal();
			double pDetection=terminal.findPrimaryUser(signal, snratios.get(i-1));
			String truncatedPDetectionValue=new DecimalFormat("#.###").format(pDetection);
			io.print("\t\t"+snratios.get(i-1)+"dB -> "+truncatedPDetectionValue+"%");
		}
		io.println("");
	}
	private static void printAllSignalDetectionProbability(int numberOfSequences, List<Double> snratios, CognitiveTerminal terminal, UserInterface io){
		io.println("\tTabella Segnale -> Probabilita di detection in base a SNR");
		io.println("\t---------------------------------------------------------");

		for(int j=1;j<=numberOfSequences;j++){
			printSignalDetectionProbability(j,snratios,terminal,io);
		}
	}

}
