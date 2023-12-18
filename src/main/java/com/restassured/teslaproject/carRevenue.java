package com.restassured.teslaproject;

public class carRevenue implements Comparable<carRevenue>{
	
	public String sVin;
	public Double fCarRevenue;

	public carRevenue(String vin ,Double carRevenue) {
		this.sVin=vin;
		this.fCarRevenue=carRevenue;
		
	}

	@Override
	public int compareTo(carRevenue obj) {
		
		return (int)((Double) this.fCarRevenue-obj.fCarRevenue);
	}

}
