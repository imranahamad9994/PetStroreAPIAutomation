package api.endpoints;

import static io.restassured.RestAssured.*;
import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndPoints {

	
	public static Response createPet(Pet petPayload)
	{
		Response response =given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(petPayload)
			
		.when()
			.post(Routes.petPostURL);
			
		return response;
		
	}
	
	public static Response getPet(int petId)
	{
		Response response = given()
				
		.when()
			.get(Routes.petGetURL, petId);
							
		
		return response;
		
	}
	
	public static Response deletePet(int petId)
	{
		Response response = given()
							.when()
								.delete(Routes.petDeleteURL, petId);
									
				
		return response;
	}
	
	public static Response findPetsByStatus(String status)
	{
		Response response = given()
								.queryParam("status", status)
							.when()
								.get(Routes.petFindByStatusURL);
		return response;
	}

	
}
