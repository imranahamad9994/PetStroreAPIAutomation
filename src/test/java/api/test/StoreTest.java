package api.test;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.StoreEndPoints;
import api.payload.Store;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
public class StoreTest {
	
	Faker faker = new Faker();
	Store storePayload = new Store();
	
	

	@Test
	void testCreateOrder()
	{
		storePayload.setId(faker.number().numberBetween(1, 10000));
		storePayload.setPetId(faker.number().numberBetween(1, 1000));
		storePayload.setQuantity(faker.number().numberBetween(1, 100));
		storePayload.setShipDate("2026-01-01T13:10:16.653Z");
		storePayload.setStatus("placed");
		storePayload.setComplete(true);
		
		Response response =StoreEndPoints.createOrder(storePayload);
		//response.then().time(lessThan(2000L));
		response.then()
		.log().all()
					.statusCode(200)
					.body("id", notNullValue())
					.body("status", equalTo("placed"));
		
					
	}
	
	@Test(dependsOnMethods = "testCreateOrder")
	void testGetOrder()
	{
		Response response = StoreEndPoints.getOrder(storePayload.getId());
		//response.then().time(lessThan(2000L));
		response.then()
						.statusCode(200)
						.body("id", equalTo((int)storePayload.getId())) //Type casting to match JASON data type.
						.body("petId", equalTo(storePayload.getPetId()))
						.body("quantity", equalTo(storePayload.getQuantity()))
						.log().all();
	}
	
	@Test(dependsOnMethods = "testGetOrder")
	void testDeleteOrder()
	{
		Response response = StoreEndPoints.deleteOrder(storePayload.getId());
		//response.then().time(lessThan(2000L));
		response.then()
						.statusCode(200)
						.log().all();
		Response getResponse = StoreEndPoints.getOrder(storePayload.getId());
		getResponse.then()
						.statusCode(404)
						.body("message", equalTo("Order not found"))
						.log().all();
	}
	
	//@Test(dependsOnMethods = "testDeleteOrder")
	void testGetDeletedOrder()
	{
		
	}
}
