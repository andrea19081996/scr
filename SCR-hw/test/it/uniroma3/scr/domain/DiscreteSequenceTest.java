package it.uniroma3.scr.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DiscreteSequenceTest {
	DiscreteSignal fourSamplesSignal;
	DiscreteSignal tenSamplesSignal;
	Sample s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
	
	@Before
	public void setUp(){
		s1=new Sample(1.0,2.0);
		s2=new Sample(2.0,3.0);
		s3=new Sample(3.0,4.0);
		s4=new Sample(4.0,5.0);
		s5=new Sample(5.0,6.0);
		s6=new Sample(6.0,7.0);
		s7=new Sample(7.0,8.0);
		s8=new Sample(8.0,9.0);
		s9=new Sample(9.0,10.0);
		s10=new Sample(10.0,11.0);

		fourSamplesSignal=new DiscreteSignal();
		fourSamplesSignal.addSample(s1);
		fourSamplesSignal.addSample(s2);
		fourSamplesSignal.addSample(s3);
		fourSamplesSignal.addSample(s4);
		
		tenSamplesSignal=new DiscreteSignal();
		tenSamplesSignal.addSample(s1);
		tenSamplesSignal.addSample(s2);
		tenSamplesSignal.addSample(s3);
		tenSamplesSignal.addSample(s4);
		tenSamplesSignal.addSample(s5);
		tenSamplesSignal.addSample(s6);
		tenSamplesSignal.addSample(s7);
		tenSamplesSignal.addSample(s8);
		tenSamplesSignal.addSample(s9);
		tenSamplesSignal.addSample(s10);

	}
	@Test
	public void testFourSamplesSignal() {
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
	@Test
	public void testTenSamplesSignalDividedIn2Signals() {
		assertSame(10,tenSamplesSignal.getSamples().size());
		List<DiscreteSignal> fragments=tenSamplesSignal.getFragments(2);
		assertNotNull(fragments);
		assertSame(2,fragments.size());
		DiscreteSignal firstFragment=fragments.get(0);
		DiscreteSignal secondFragment=fragments.get(1);
		assertEquals(s1,firstFragment.getSamples().get(0));
		assertEquals(s2,firstFragment.getSamples().get(1));
		assertEquals(s3,firstFragment.getSamples().get(2));
		assertEquals(s4,firstFragment.getSamples().get(3));
		assertEquals(s5,firstFragment.getSamples().get(4));
		assertEquals(s6,secondFragment.getSamples().get(0));
		assertEquals(s7,secondFragment.getSamples().get(1));
		assertEquals(s8,secondFragment.getSamples().get(2));
		assertEquals(s9,secondFragment.getSamples().get(3));
		assertEquals(s10,secondFragment.getSamples().get(4));
	}
	@Test
	public void testTenSamplesSignalDividedIn5Signals() {
		assertSame(10,tenSamplesSignal.getSamples().size());
		List<DiscreteSignal> fragments=tenSamplesSignal.getFragments(5);
		assertNotNull(fragments);
		assertSame(5,fragments.size());
		DiscreteSignal firstFragment=fragments.get(0);
		DiscreteSignal secondFragment=fragments.get(1);
		DiscreteSignal thirdFragment=fragments.get(2);
		DiscreteSignal fourthFragment=fragments.get(3);
		DiscreteSignal fifthFragment=fragments.get(4);
		assertSame(2,firstFragment.getSamples().size());
		assertSame(2,secondFragment.getSamples().size());
		assertSame(2,thirdFragment.getSamples().size());
		assertSame(2,fourthFragment.getSamples().size());
		assertSame(2,fifthFragment.getSamples().size());
		assertEquals(s1,firstFragment.getSamples().get(0));
		assertEquals(s2,firstFragment.getSamples().get(1));
		assertEquals(s3,secondFragment.getSamples().get(0));
		assertEquals(s4,secondFragment.getSamples().get(1));
		assertEquals(s4,secondFragment.getSamples().get(1));
		assertEquals(s5,thirdFragment.getSamples().get(0));
		assertEquals(s6,thirdFragment.getSamples().get(1));
		assertEquals(s7,fourthFragment.getSamples().get(0));
		assertEquals(s8,fourthFragment.getSamples().get(1));
		assertEquals(s9,fifthFragment.getSamples().get(0));
		assertEquals(s10,fifthFragment.getSamples().get(1));
	}

}



