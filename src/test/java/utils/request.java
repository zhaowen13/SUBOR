package utils;

import java.io.File;
import java.util.Map;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;

public class request {
	/**
	 * Request underlying method
	 * @author zhaowen
     *@Time 2018-11-12
	 */
	private Map<String, String> allCookies = null;
	private request req;
	protected Log log = new Log(this.getClass());
	private RequestSpecification httpRequest = null;
	ToLoggerPrintStream tol = new ToLoggerPrintStream(log);

	public enum InnerEnum {
		all, params, body, headers, cookies, method
	};

	public void url(String url, request req) { // server address,
		RestAssured.baseURI = url;
		this.req = req;
		RestAssured.config = RestAssured.config().logConfig(new LogConfig(tol.getPrintStream(), true));
	}

	public void url(String url, String host, int port, request req) { // server address ,Set proxy																	
		RestAssured.baseURI = url;
		RestAssured.proxy = ProxySpecification.host(host).withPort(port);
		RestAssured.config = RestAssured.config().logConfig(new LogConfig(tol.getPrintStream(), true));
		this.req = req;
	}

	public request param(String name, String str) { // Request parameter
		httpRequest.param(name, str);
		return req;
	}

	public String post(String url) { // post Request, default record return
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = httpRequest.log().uri().when().post(url);
		httpRequest = RestAssured.given();                                        // Reset request after each request
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                  //Print request time
				+"\n"+tol.log(response.getStatusLine().contains("200"))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getStatusLine().contains("200")) {
			log.error(" Request failed");
			Assert.assertTrue(false);
		}
		return response.getBody().asString();
 
	}
 
	public String post(String url, String result) { // post Request, default record return information log												
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = httpRequest.log().uri().when().post(url);
		httpRequest = RestAssured.given();                                               // Reset request after each request
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                //Print request time
				+ "\n"+tol.log(response.getBody().asString().contains(result))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getBody().asString().contains(result)) {              //If the response result does not contain the expected result
			log.error(" Request failed ");                                  //then Request failed
		 Assert.assertTrue(response.getBody().asString().contains(result));//the assertion result is a failure, and the re-run mechanism will be resolved.
		}
		return response.getBody().asString();
	}
	public String post(String url, InnerEnum level) { // post Request,Output different levels of log according to level
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = null;
		switch (level) {
		case all:
			response = httpRequest.log().all().when().post(url); // Log all request specification details including parameters, headers and body
			break;
		case params:
			response = httpRequest.log().params().when().post(url); // Log only the parameters of the request			
			break;
		case body:
			response = httpRequest.log().body().when().post(url); // Log only the request body															
			break;
		case headers:
			response = httpRequest.log().headers().when().post(url); // Log only the request headers
			break;
		case cookies:
			response = httpRequest.log().cookies().when().post(url); // Log only the request cookies															 
			break;
		case method:
			response = httpRequest.log().method().when().post(url); // Log only the request method																																																
			break;		
		}
		httpRequest = RestAssured.given();                                          // Reset request after each request
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                           //Print request time
				+ "\n"+tol.log(response.getStatusLine().contains("200"))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getStatusLine().contains("200")) {
			log.error(" Request failed");
			Assert.assertTrue(false);
		}
		return response.getBody().asString();

	}

	public String post(String url, InnerEnum level, String result) { // post Request,Output different levels of log according to level																																		
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = null;
		switch (level) {
		case all:
			response = httpRequest.log().all().when().post(url); // Log all request specification details including parameters, headers and body
			break;
		case params:
			response = httpRequest.log().params().when().post(url); // Log only the parameters of the request			
			break;
		case body:
			response = httpRequest.log().body().when().post(url); // Log only the request body															
			break;
		case headers:
			response = httpRequest.log().headers().when().post(url); // Log only the request headers
			break;
		case cookies:
			response = httpRequest.log().cookies().when().post(url); // Log only the request cookies															 
			break;
		case method:
			response = httpRequest.log().method().when().post(url); // Log only the request method	
			break;
		default:
			break;
		}
		httpRequest = RestAssured.given();                                               // Reset request after each request
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getBody().asString().contains(result))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getBody().asString().contains(result)) {
			log.error(" Request failed");
			Assert.assertTrue(response.getBody().asString().contains(result));
		}
		return response.getBody().asString();

	}

	public String get(String url) { // get Request
		tol.set();
		Response response = null;
		httpRequest = RestAssured.given();
		if (allCookies != null) {                               // Incoming cookies if cookies are not empty
			httpRequest.cookies(allCookies);
			response = httpRequest.log().uri().when().get(url);

		} else {
			response = httpRequest.log().uri().when().get(url);
			allCookies = response.getCookies();             // Assign all cookies to allCookies											
		}
		response = httpRequest.log().uri().when().get(url);
		httpRequest = RestAssured.given();
		log.info(" Request URL:" +url
				+ "\nRequest method GET"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getStatusLine().contains("200"))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getStatusLine().contains("200")) {
			log.error(" Request failed");
			Assert.assertTrue(false);
		}
		return response.getBody().asString();

	}

	public String get(String url, String result) { // get Request
		tol.set();
		Response response = null;
		if (allCookies != null) {                // Incoming cookies if cookies are not empty
			httpRequest.cookies(allCookies);
			response = httpRequest.log().uri().when().get(url);
		} else {
			response = httpRequest.log().uri().when().get(url);
			allCookies = response.getCookies(); // Assign all cookies to
												// allCookies
		}
		log.info(" Request URL:" +url
				+ "\nRequest method GET"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getBody().asString().contains(result))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getBody().asString().contains(result)) {
			log.error(" Request failed ");
			Assert.assertTrue(response.getBody().asString().contains(result));
		}
		return response.getBody().asString();
	}

	public void login(String email, String password, String url, String result) { // login   
		tol.set();
		Response response = null;
		if (allCookies == null) {   //If login does not need to get the cookise from the previous request
			httpRequest = RestAssured.given();
			response = httpRequest.param("email", email).param("password", password).log().params().when().post(url);			
			allCookies = response.getCookies(); // Assign all cookies to												
		} else {                         
			Json jsonAsMap = new Json();
			jsonAsMap.put("{\"email\":\"${email}\",\"password\":\"${password}\"}");
			jsonAsMap.put("email", email);
			httpRequest.cookies(allCookies);
			response = httpRequest.body(jsonAsMap.put("password", password)).log().params().when().post(url);

		}
		httpRequest = RestAssured.given();                                               // Reset request after each request
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getBody().asString().contains(result))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getBody().asString().contains(result)) {
			log.error(" login fail ");
			Assert.assertTrue(response.getBody().asString().contains(result));
		}

	}

	public void login(String email, String password, String url, String AuthState, String result) {
		httpRequest.cookies(allCookies);
		Response response = httpRequest.param("email", email).param("password", password).param("AuthState", AuthState)
				.log().uri().when().post(url);
		allCookies = response.getCookies();
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getBody().asString().contains(result))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		httpRequest = RestAssured.given();
		if (!response.getBody().asString().contains(result)) {
			log.error(" login fail ");
			Assert.assertTrue(response.getBody().asString().contains(result));
		}
	}

	public void login(String email, String password, String url, String answer1, String answer2, String answer3) { // login																												
		httpRequest = RestAssured.given();
		Response response = httpRequest.param("email", email).param("password", password).log().uri().when().post(url);
		allCookies = response.getCookies();
		httpRequest.cookies(allCookies);
		httpRequest = RestAssured.given();                                         // Reset request after each request
		log.info(" Request URL:" +url
				+ "\nRequest method POST"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getStatusLine().contains("200"))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message

	}

	public request header(String name, String key) {
		httpRequest.header(name, key);
		return req;
	}

	public String getcookie(String name) {

		return allCookies.get(name);

	}

	public void body(String str) {

		httpRequest.body(str);
	}

	public String put(String url) {
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = httpRequest.log().uri().when().put(url);
		httpRequest = RestAssured.given();
		log.info(" Request URL:" +url
				+ "\nRequest method PUT"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getStatusLine().contains("200"))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getStatusLine().contains("200")) {
			log.error(" Request failed");
			Assert.assertTrue(false);
		}
		return response.getBody().asString();
	}

	public String put(String url, String result) {
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = httpRequest.when().put(url);
		httpRequest = RestAssured.given();
		log.info(" Request URL:" +url
				+ "\nRequest method PUT"                                              
				+ "\nResponse time:" + response.time()+"ms"                                     //Print request time
				+ "\n"+tol.log(response.getBody().asString().contains(result))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getBody().asString().contains(result)) {
			log.error(" login fail ");
			Assert.assertTrue(response.getBody().asString().contains(result));
		}
		return response.getBody().asString();
	}

	public request multiPart(String ontrolName, String path) {
		httpRequest.multiPart(ontrolName, new File("D:\\jmeter\\apache-jmeter-5.0\\bin\\test\\" + path));
		return req;
	}

	public request multiPart(String ontrolName, String path, String mimeType) {
		httpRequest.multiPart(ontrolName, new File("D:\\jmeter\\apache-jmeter-5.0\\bin\\test\\" + path, mimeType));
		return req;
	}

	public String delete(String url) {
		tol.set();
		httpRequest.cookies(allCookies);
		Response response = httpRequest.when().delete(url);
		httpRequest = RestAssured.given();
		log.info(" Request URL:" +url
				+ "\nRequest method delete"                                              
				+ "\nResponse time:" + response.time()+"ms"                                    //Print request time
				+"\n"+tol.log(response.getStatusLine().contains("200"))                //If the response body has no expected result, the request parameter is printed.
				+ "Response data"+ response.getBody().asString());           //Print response message
		if (!response.getStatusLine().contains("200")) {
			log.error(" Request failed");
			Assert.assertTrue(false);
		}
		return response.getBody().asString();

	}
}
	
