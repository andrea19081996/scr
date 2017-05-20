package it.uniroma3.scr.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SampleTest {
	DiscreteSignal fourSamplesSignal;
	Sample s1,s2,s3,s4;
	
	@Before
	public void setUp(){
		fourSamplesSignal=new DiscreteSignal();
		s1=new Sample(1,2);
		fourSamplesSignal.addSample(s1);
		s2=new Sample(2,3);
		fourSamplesSignal.addSample(s2);
		s3=new Sample(3,4);
		fourSamplesSignal.addSample(s3);
		s4=new Sample(4,5);
		fourSamplesSignal.addSample(s4);
	}
	@Test
	public void test() {
		assertSame(4,fourSamplesSignal.getSamples().size());
		List<DiscreteSignal> fragments=fourSamplesSignal.getFragments(2);
		assertNotNull(fragments);
		assertSame(2,fragments.size());
		DiscreteSignal firstFragment=fragments.get(0);
		DiscreteSignal secondFragment=fragments.get(1);
		assertEquals(s1,firstFragment.getSamples().get(0));
		assertEquals(s2,firstFragment.getSamples().get(1));
		assertEquals(s3,secondFragment.getSamples().get(0));
		assertEquals(s4,secondFragment.getSamples().get(1));
	}

}
