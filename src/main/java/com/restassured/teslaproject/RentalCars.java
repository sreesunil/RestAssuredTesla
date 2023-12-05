package com.restassured.teslaproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RentalCars {

	@Test
	public void getdata() {

		RestAssured.baseURI = "https://67f509a9-98f1-4548-b221-7bde67c577a8.mock.pstmn.io";

		Response res = RestAssured.given().headers("contentType", "application/json").when().get("/carinfo");

		res.then().assertThat().statusCode(200).extract().response();

		System.out.println(res.asPrettyString());

	}

	@Test
	public void printTeslaBlueColorAndNotes() {

		RestAssured.baseURI = "https://67f509a9-98f1-4548-b221-7bde67c577a8.mock.pstmn.io";

		Response res = RestAssured.given().headers("contentType", "application/json").when().get("/carinfo");

		res.then().assertThat().statusCode(200).extract().response();

		String make = "Tesla";
		String color = "Blue";

		List<String> lmake = res.jsonPath().getList("Car.make");
		System.out.println(lmake);
		
		int setIndex =0;

		for (int i = 0; i < lmake.size(); i++) {

			if (lmake.get(i).equalsIgnoreCase(make)) {
				
				setIndex=i;

				String carcolor = res.jsonPath().getString("Car["+setIndex+"].metadata.Color");

				if (carcolor.equalsIgnoreCase(color)) {

					System.out.println(lmake.get(i));
					System.out.println("Found "+ carcolor + " Color " + make);
					
					String carnotes = res.jsonPath().getString("Car["+setIndex+"].metadata.Notes");
					System.out.println("print only the notes of " + carcolor + " color " + make +". Notes : " + carnotes);
				}
			}

		}
	}
	
	@Test
	public void carsWithLowestPerDayRent() {
		
		
		RestAssured.baseURI = "https://67f509a9-98f1-4548-b221-7bde67c577a8.mock.pstmn.io";

		Response res = RestAssured.given().headers("contentType", "application/json").when().get("/carinfo");

		res.then().assertThat().statusCode(200).extract().response();
		
		List<Double> liperdayrentlist = res.jsonPath().getList("Car.perdayrent");
		System.out.println("Print perdayrental cost of all cars : " + liperdayrentlist);
		
		ArrayList<Double> alperdayrent = new ArrayList<Double>();
		ArrayList<Double> alperdayrentdiscount = new ArrayList<Double>();
		int setIndex = 0;
		for(int i = 0; i<liperdayrentlist.size();i++) {
			setIndex = i;
			
			Double perDayRentPrice = res.jsonPath().getDouble("Car["+setIndex+"].perdayrent.Price");
			Double perDayRentDiscount = res.jsonPath().getDouble("Car["+setIndex+"].perdayrent.Discount");
			Double perDayRentandDiscount=(perDayRentPrice-(perDayRentPrice * perDayRentDiscount/100));
			alperdayrent.add(perDayRentPrice);
			alperdayrentdiscount.add(perDayRentandDiscount);
		}
		
		Collections.sort(alperdayrent);
		System.out.println("Return all cars starting from lowest perdayrent with price : " + alperdayrent );
		Collections.sort(alperdayrentdiscount);
		System.out.println("Return all cars starting from lowest perdayrent with after discount : " +alperdayrentdiscount);
		
		
	}

}

