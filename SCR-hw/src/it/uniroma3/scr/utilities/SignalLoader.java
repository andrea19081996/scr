package it.uniroma3.scr.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import it.uniroma3.scr.domain.DigitalSignal;
import it.uniroma3.scr.domain.Sample;

/**
 * Classe di utility usata per caricare una sequenza di campioni
 * da file, noto il file path. Il file deve essere composto da due colonne 
 * di numeri separati da un \t
 * @author Jerin George Mathew
 */
public class SignalLoader {
	private BufferedReader reader;

	public SignalLoader(String filePath){
		try{
			this.reader=new BufferedReader(new FileReader(filePath));}
		catch(FileNotFoundException e){
			throw new IllegalArgumentException("File non esistente!");
		}
	}
	
	/**
	 * Carica una sequenza da file
	 * @return la sequenza letta da file
	 * @throws IllegalArgumentException se il file non è nel formato previsto
	 */
	public DigitalSignal loadSignal(){
		try{
			DigitalSignal loadedSignal=new DigitalSignal();
		String line;
		while((line=reader.readLine())!=null){
			Sample s= readSample(line);
			loadedSignal.addSample(s);
		}
		reader.close();
		return loadedSignal;
		}
		catch(IOException e){
			throw new IllegalArgumentException("Formato del file non valido!");
		}
	}
	/**
	 * Legge una riga e la separa usando il carattere \t per poi generare 
	 * un campione inizializzato con i valori della line 
	 * @param line la riga letta da file
	 * @return un campione inizializzato con i valori letti da line
	 */
	private Sample readSample(String line){
		String[] splittedLine=line.split("\t");
		double realPart=Double.parseDouble(splittedLine[0]);
		double imaginaryPart=Double.parseDouble(splittedLine[1]);
		return new Sample(realPart,imaginaryPart);
	}

}
