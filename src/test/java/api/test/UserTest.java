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
	
	@Test(priority =1)
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
	
	@Test(priority =2)
	void getUserByUsername()
	{
		Response response = UserEndPoints.readUser(userPayload.getUsername());
		response.then().log().all();
		
		response.then().body("username", equalTo(userPayload.getUsername()))
						.body("firstName", equalTo(userPayload.getFirstName()))
						.body("lastName", equalTo(userPayload.getLastName()))
						.body("email", equalTo(userPayload.getEmail()))
						.body("id", notNullValue());
		
		//verify data consistency
		String username = response.jsonPath().getString("username");
		Assert.assertEquals(username, userPayload.getUsername());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority =3)
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
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Validate updated data using GET API (MANDATORY)
		Response getUpdateResponse =  UserEndPoints.readUser(userPayload.getUsername());
		
		getUpdateResponse.then().body("firstName", equalTo(userPayload.getFirstName()))
								.body("lastName", equalTo(userPayload.getLastName()))
								.body("email", equalTo(userPayload.getEmail()));
	
	}
	
	@Test(priority =4)
	void deleteUserByUsername()
	{
		Response response = UserEndPoints.deleteUser(userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}

}
