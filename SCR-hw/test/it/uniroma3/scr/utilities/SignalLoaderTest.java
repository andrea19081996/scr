package it.uniroma3.scr.utilities;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.scr.domain.DiscreteSignal;
import it.uniroma3.scr.domain.Sample;
//fare test file non esistente e sequenza 1 del file 1
public class SignalLoaderTest {
	private SignalLoader existingFileLoader;
	private String existingFilePath;
	
	@Before
	public void setUp(){
		existingFilePath="resources/Sequenze/Sequenza_1/output_1.dat";
		existingFileLoader=new SignalLoader(existingFilePath);
	}
	@Test
	public void testReadExistingFile() throws IOException {
		DiscreteSignal loadedSignal=existingFileLoader.loadSignal();
		List<Sample> samplesFromLoadedSignal=loadedSignal.getSamples();
		assertEquals(1000000,samplesFromLoadedSignal.size());
		Sample firstSample=samplesFromLoadedSignal.get(0);
		assertEquals(-0.35596,firstSample.getReal(),0);
		assertEquals(-2.7268,firstSample.getImaginary(),0);
		Sample lastSample=samplesFromLoadedSignal.get(samplesFromLoadedSignal.size()-1);
		assertEquals(1.2178,lastSample.getReal(),0);
		assertEquals(-0.56035,lastSample.getImaginary(),0);
	}

}
