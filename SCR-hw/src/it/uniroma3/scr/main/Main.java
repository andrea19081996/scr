package it.uniroma3.scr.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import it.uniroma3.scr.domain.CognitiveTerminal;
import it.uniroma3.scr.domain.DigitalSignal;
import it.uniroma3.scr.utilities.SignalLoader;
import it.uniroma3.scr.utilities.UserInterface;
import it.uniroma3.scr.utilities.UserInterfaceConsole;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException{
		int signalLenght=1000000;
		//usare una lista costante
		//sistemare la formattazione del testo stampato sulla console
		List<Double> snratios=new ArrayList<>();
		UserInterface io=new UserInterfaceConsole();
		snratios.add(-3.0);
		snratios.add(2.0);
		snratios.add(-8.0);
		snratios.add(-13.0);
		CognitiveTerminal terminal=new CognitiveTerminal(snratios,signalLenght); //definire una variabile per la lunghezza delle sequenze


		for(Double snr: snratios)
			io.println("soglia="+terminal.getThreshold(snr)+" snr=" +snr);
		for(int j=1;j<=3;j++){
			io.println("Sequenza"+j);
			for(int i=1;i<=4;i++){
				io.print("Osservazione"+i+"snr"+snratios.get(i-1)+" ");
				DigitalSignal signal= new SignalLoader("resources/Sequenze/Sequenza_"+j+"/output_"+i+".dat").loadSignal();
				io.print(terminal.findPrimaryUser(signal, snratios.get(i-1))+"\t");
			}
			io.println("");
		}
	}
}
