package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.Store;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndPoints {
	
	public static Response  createOrder(Store payload)
	{
		Response response = given()
			.body(payload)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post(Routes.storePostURL);
		
		
		return response ;
	}
	
	public static Response  getOrder(long orderId)
	{
		Response response = given()
			.when()
			.get(Routes.storeGetOneURL, orderId);
		
		return response ;
	}

	public static Response  deleteOrder(long orderId)
	{
		Response response = given()
				.when()
				.delete(Routes.storeGetOneURL, orderId);
		
		return response ;
	}
	
	public static Response  getOrderInventory()
	{
		Response response = given()
		.when()
			.get(Routes.storeGetAllURL);	
		return null ;
	}

}
