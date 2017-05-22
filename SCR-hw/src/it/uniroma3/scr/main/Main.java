package it.uniroma3.scr.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.scr.domain.CognitiveTerminal;
import it.uniroma3.scr.domain.DiscreteSignal;
import it.uniroma3.scr.utilities.SignalLoader;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException{
		List<Double> snratios=new ArrayList<>();
		snratios.add(-3.0);
		snratios.add(2.0);
		snratios.add(-8.0);
		snratios.add(-13.0);
		CognitiveTerminal terminal=new CognitiveTerminal(snratios,1000000); //definire una variabile per la lunghezza delle sequenze

		for(Double snr: snratios)
			System.out.println("soglia="+terminal.getThreshold(snr)+" snr=" +snr);
		for(int j=1;j<=3;j++){
			System.out.println("Sequenza"+j);
			for(int i=1;i<=4;i++){
				System.out.print("Osservazione"+i+"snr"+snratios.get(i-1)+" ");
				DiscreteSignal signal= new SignalLoader("resources/Sequenze/Sequenza_"+j+"/output_"+i+".dat").loadSignal();
				System.out.print(terminal.checkPUSignalPresence(signal, snratios.get(i-1))+"\t");
			}
			System.out.println();
		}
	}
}
