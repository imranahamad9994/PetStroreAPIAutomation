package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class UserTest {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	void setupData()
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPhone(faker.phoneNumber().cellPhone());		
	}
	
	@Test()
	void testPostUser() {
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		response.then()
						.statusCode(200)
						.body("code", equalTo(200))
						.body("type", equalTo("unknown"))
						.body("message", notNullValue());
		
		response.then().header("Content-Type", containsString("application/json"));
		response.then().header("Connection", "keep-alive");
		response.then().time(lessThan(3000L));
		
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test(dependsOnMethods = "testPostUser")
	void getUserByUsername()
	{
		Response response = UserEndPoints.readUser(userPayload.getUsername());
		response.then().log().all();
		
		response.then().body("username", equalTo(userPayload.getUsername()))
						.body("firstName", equalTo(userPayload.getFirstName()))
						.body("lastName", equalTo(userPayload.getLastName()))
						.body("email", equalTo(userPayload.getEmail()))
						.body("id", notNullValue());
		
		response.then().time(lessThan(2000L));
		
		//verify data consistency
		String username = response.jsonPath().getString("username");
		Assert.assertEquals(username, userPayload.getUsername());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(dependsOnMethods ="getUserByUsername")
	void updateUserByUsername()
	{
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(userPayload.getUsername(), userPayload);
		
		response.then().log().all();
		
		response.then().body("code", equalTo(200))
						.body("type", equalTo("unknown"))
						.body("message", notNullValue());
		response.then().time(lessThan(2000L));
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Validate updated data using GET API (MANDATORY)
		Response getUpdateResponse =  UserEndPoints.readUser(userPayload.getUsername());
		
		getUpdateResponse.then().body("firstName", equalTo(userPayload.getFirstName()))
								.body("lastName", equalTo(userPayload.getLastName()))
								.body("email", equalTo(userPayload.getEmail()));
		getUpdateResponse.then().time(lessThan(2000L));
	}
	
	@Test(dependsOnMethods = "updateUserByUsername")
	void deleteUserByUsername()
	{
		Response response = UserEndPoints.deleteUser(userPayload.getUsername());
		response.then().log().all();
		
		response.then().time(lessThan(2000L));
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	//Negative Tests
	
	@Test(dependsOnMethods = "deleteUserByUsername")// get user that is already deleted
	void getAlreadyDeletedUser()
	{
		UserEndPoints.readUser(userPayload.getUsername())
		.then()		
			.statusCode(404);	
	}
	
	@Test(dependsOnMethods = "getAlreadyDeletedUser")
	// 👉 PUT /user/{username} RE-CREATES THE USER
	//This means:
	//Swagger Petstore treats PUT as UPSERT
	//Update if exists
	//Create if NOT exists ❌ (bad REST design, but real behavior)
	//So your assumption:
	//“User should still NOT exist”
	//is incorrect for this API.
	void updateDeletedUser()
	{
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());

		
		Response response = UserEndPoints.updateUser(userPayload.getUsername(), userPayload);
		
		response.then().log().all();
		
		// Swagger Petstore returns 200 even if user does not exist
	    response.then().statusCode(200); // Should return 404, but this API returns 200

	    // REAL validation: user should still NOT exist
	    UserEndPoints.readUser(userPayload.getUsername())
	    			.then()
	    			.statusCode(200)  // Should return 404, but this API returns 200 // Swagger Petstore recreates user on PUT (UPSERT behavior) 
	    			.body("firstName", equalTo(userPayload.getFirstName()))
	    			.body("lastName", equalTo(userPayload.getLastName()));
		
	}
	
	@Test(dependsOnMethods = "getAlreadyDeletedUser")//Delete user that is already deleted
	void deleteAlreadyDeletedUser()
	{
		Response response = UserEndPoints.deleteUser(userPayload.getUsername());
		response.then().log().all();
		response.then().time(lessThan(2000L));
		response.then().statusCode(404);
		
	}
}
