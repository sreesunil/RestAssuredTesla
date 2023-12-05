package com.restassured.teslaproject;


import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class BaseTest {
	
	
	public static int iPort = 9090;
	public WireMockServer wireMockServer;
	
	@BeforeSuite
	public void setUpWireMockServer() {
		
		wireMockServer = new WireMockServer(iPort);
		WireMock wireMock = new WireMock("localhost",9999);
		wireMockServer.start();
		
		//http://localhost:9898/getrentalinfo
		//C:\JavaPrograms\WireMockService
	}
	
	
	@BeforeTest
	public void getMappingRequest() {

		wireMockServer.stubFor(get(urlEqualTo("/getcars"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type","application/json")
						.withBodyFile("rentalcarschema.json")));
	
		
	}
	
	
	@AfterSuite
	public void teardown() {
		wireMockServer.stop();
	}
	
	

}
