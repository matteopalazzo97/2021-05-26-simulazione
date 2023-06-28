package it.polito.tdp.yelp.model;

public class Best {
	
	private Business b;
	private Double val;
	
	public Best(Business b, Double val) {
		super();
		this.b = b;
		this.val = val;
	}

	public Business getB() {
		return b;
	}

	public void setB(Business b) {
		this.b = b;
	}

	public Double getVal() {
		return val;
	}

	public void setVal(Double val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return b.getBusinessName();
	}
	

}
