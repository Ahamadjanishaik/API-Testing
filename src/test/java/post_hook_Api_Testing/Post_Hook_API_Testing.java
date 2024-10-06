package post_hook_Api_Testing;
import org.apache.log4j.Logger;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

public class Post_Hook_API_Testing {

	 private static final Logger logger = Logger.getLogger(Post_Hook_API_Testing.class);
	 
	 ExtentReports extent;
	 ExtentTest test;
	 
	 @BeforeClass
	 public void setup() {
		 
		 ExtentSparkReporter sparkreporter = new ExtentSparkReporter("reports.html");
		 
		 extent = new ExtentReports();
		 
		 extent.attachReporter(sparkreporter);
		 
	 }
	
	@Test(priority=1)
	public void schedule() {		//Schedule a Hook
		
		test = extent.createTest("Schedule Hook");
		
		RestAssured.baseURI = "https://posthook-api.mock.beeceptor.com";				
		given()
		.when().post("/v1/hooks").then()
		.statusCode(200);//.log().all();  //Implement a POST request to schedule a hook and validate the response.
		
		 logger.info("Hook scheduled successfully.");
		 test.pass("Hook scheduled successfully.");
	}
	@Test(priority=2)
	public void getHooks() {		//Get Hooks
		
		test = extent.createTest("Get Hook");
		 
		RestAssured.baseURI = "https://posthook-api.mock.beeceptor.com";				
		given()
		.get("/v1/hooks")          //Send a GET request to fetch all hooks and validate the response.
		.then().statusCode(200).log().all();
		  logger.info("Hooks fetched successfully.");
		  test.pass("Hooks fetched successfully.");
		  
	}
	@Test(priority=3)
	public void getHookBy_ID() {		//Get Hook by ID
		
		 test = extent.createTest("Get Hook by ID");
		
		RestAssured.baseURI = "https://posthook-api.mock.beeceptor.com";				
		given()
		.get("/v1/hooks/c1ec9560-65fc-4b88-bfe0-1bc6e56cb3db")          
		.then().statusCode(200).log().all();
		logger.info("Hook fetched successfully.");
		test.pass("Hook fetched successfully.");
	}
	@Test(priority=4)
	public void updateHook() {		//Update Hook		
		
		test = extent.createTest("Update Hook");
		
		RestAssured.baseURI = "https://posthook-api.mock.beeceptor.com";						
		JSONObject js = new JSONObject();		
		js.put("name", "updated-project");
		js.put("domain", "api.example.com");   // Request Boby 
		js.put("headerAuthorization", "");
		js.put("minRetries", 1);
		js.put("retryDelaySecs", 5);
		
		given().contentType("application/json").body(js.toJSONString())
		.when()
			.put("/v1/projects/")
		.then().statusCode(200).log().all();
		logger.info("Hook updated successfully.");
		 test.pass("Hook updated successfully.");
	}
	@Test(priority=5)
	public void deleteHook() {		//Delete Hook by ID
		
		test = extent.createTest("Delete Hook");
		 
		RestAssured.baseURI = "https://posthook-api.mock.beeceptor.com";				
		given()
		.delete("/v1/hooks/c1ec9560-65fc-4b88-bfe0-1bc6e56cb3db")          
		.then().statusCode(200).log().all();
		logger.info("Hook deleted successfully.");
		 test.pass("Hook deleted successfully.");
		
	}
	@AfterClass
	public void tearDown() {
	    extent.flush();  // Make sure this line is present
	}

}
