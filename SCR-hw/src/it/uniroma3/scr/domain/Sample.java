package it.uniroma3.scr.domain;

public class Sample {


	private Double realPart;
	private Double imaginaryPart;
	
	public Sample(Double real, Double imaginary){
		this.realPart=real;
		this.imaginaryPart=imaginary;
	}

	public Double getReal() {
		return realPart;
	}

	public void setReal(Double real) {
		this.realPart = real;
	}

	public Double getImaginary() {
		return imaginaryPart;
	}

	public void setImaginary(Double imaginary) {
		this.imaginaryPart = imaginary;
	}
	
	public Double getModulus(){
		return Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imaginaryPart == null) ? 0 : imaginaryPart.hashCode());
		result = prime * result + ((realPart == null) ? 0 : realPart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sample other = (Sample) obj;
		if (imaginaryPart == null) {
			if (other.imaginaryPart != null)
				return false;
		} else if (!imaginaryPart.equals(other.imaginaryPart))
			return false;
		if (realPart == null) {
			if (other.realPart != null)
				return false;
		} else if (!realPart.equals(other.realPart))
			return false;
		return true;
	}
}
