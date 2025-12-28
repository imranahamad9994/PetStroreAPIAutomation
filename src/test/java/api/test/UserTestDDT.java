package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class UserTestDDT {
	
	
	User userPayload;
	
	
	
	@Test(priority =1,dataProvider = "Data", dataProviderClass = DataProviders.class)
	void testCreateAndDeleteUser(String username, String firstname, String lastname, String email, String phone) {
		
userPayload = new User();
		
		//userPayload.setId(Integer.parseInt(id));
		userPayload.setUsername(username);
		userPayload.setFirstName(firstname);
		userPayload.setLastName(lastname);
		userPayload.setEmail(email);
		userPayload.setPhone(phone);	
		
		Response CreateResponse = UserEndPoints.createUser(userPayload);
		CreateResponse.then().log().all();
		Assert.assertEquals(CreateResponse.statusCode(), 200);
		
		Response DeleteResponse = UserEndPoints.deleteUser(username);
		DeleteResponse.then().log().all();
		Assert.assertEquals(DeleteResponse.getStatusCode(), 200);
	}

	
	//@Test(priority =2,dataProvider = "DataUsername", dataProviderClass = DataProviders.class)
	void deleteUserByUsername(String usernmae)
	{
		Response response = UserEndPoints.deleteUser(usernmae);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}

}
