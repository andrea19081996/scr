package it.uniroma3.scr.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import it.uniroma3.scr.domain.DiscreteSignal;
import it.uniroma3.scr.domain.Sample;
//da ultimare
//testare la dimensione della signal letta da file
//usare lo split
public class SignalLoader {
	private BufferedReader reader;

	public SignalLoader(String filePath){
		try{
			this.reader=new BufferedReader(new FileReader(filePath));}
		catch(FileNotFoundException e){
			throw new IllegalArgumentException("File non esistente");
		}
	}

	//cambiare il nome della variabile di ritorno
	public DiscreteSignal loadSignal() throws IOException{
		DiscreteSignal loadedSignal=new DiscreteSignal();
		String line;
		while((line=reader.readLine())!=null){
			Sample s= readSample(line);
			loadedSignal.addSample(s);
		}
		reader.close();
		return loadedSignal;
	}

	private Sample readSample(String line){
		String[] splittedLine=line.split("\t");
		double realPart=Double.parseDouble(splittedLine[0]);
		double imaginaryPart=Double.parseDouble(splittedLine[1]);
		return new Sample(realPart,imaginaryPart);
	}

}
