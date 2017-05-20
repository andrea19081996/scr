package it.uniroma3.scr.domain;

public class Sample {
	private double realPart;
	private double imaginaryPart;
	
	public Sample(double real, double imaginary){
		this.realPart=real;
		this.imaginaryPart=imaginary;
	}

	public double getReal() {
		return realPart;
	}

	public void setReal(double real) {
		this.realPart = real;
	}

	public double getImaginary() {
		return imaginaryPart;
	}

	public void setImaginary(double imaginary) {
		this.imaginaryPart = imaginary;
	}
	
	public double getModulus(){
		return Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
	}
}
