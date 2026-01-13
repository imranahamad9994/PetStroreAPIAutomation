package api.test;

import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import api.endpoints.PetEndPoints;
import api.payload.Pet;
import api.payload.Tag;
import io.restassured.response.Response;

public class PetTest {
	
	Pet petPayload = new Pet();
	Faker faker = new Faker();
	
		@Test
		public void testCreatePet()
		{
			petPayload.setId(faker.number().numberBetween(1, 1000));
			petPayload.setName(faker.name().firstName());
			petPayload.setStatus("available");
			
			 // photoUrls
			List<String> photosUrl = List.of("https://example.com/photo1.jpg","https://example.com/photo2.jpg");
			petPayload.setPhotoUrls(photosUrl);
			
			//tags
			Tag tag1 = new Tag();
			tag1.setId(101);
			tag1.setName("friendly");
			
			Tag tag2 = new Tag();
			tag2.setId(102);
			tag2.setName("vaccinated");
			
			petPayload.setTags(List.of(tag1, tag2));
			
			Response response = PetEndPoints.createPet(petPayload);
			response.then()
				.statusCode(200)
				.assertThat()
				.body(matchesJsonSchemaInClasspath("schemas/pet-schema.json"))
				.body("name", equalTo(petPayload.getName()))
				.body("status", equalTo("available"))
				.body("photoUrls.size()", greaterThan(0))
				.body("photoUrls[0]", equalTo(petPayload.getPhotoUrls().get(0)))
				.body("tags.size()", equalTo(2))
				.body("tags[0].name", equalTo("friendly"))
				.body("tags[1].name", equalTo("vaccinated"))
				.log().all();				
		}
		
		@Test(dependsOnMethods = "testCreatePet")
		public void testGetPet()
		{
			Response response = PetEndPoints.getPet(petPayload.getId());
			response.then()
					.statusCode(200)
					.assertThat()
					.body(matchesJsonSchemaInClasspath("schemas/pet-schema.json"))
					.body("id", equalTo(petPayload.getId()))
					.body("name", equalTo(petPayload.getName()))
					.body("status", equalTo("available"))
					.body("tags", notNullValue())
					.body("tags.size()", greaterThan(0))
					.body("tags.name", hasItems("friendly","vaccinated"))
					.log().all();
			
		}
		
		@Test(dependsOnMethods = "testCreatePet")
		public void testFindPetByStatus()
		{
			Response response = PetEndPoints.findPetsByStatus("available");
			
			response.then()
						.statusCode(200)
						.assertThat()
						.body(matchesJsonSchemaInClasspath("schemas/pet-list-schema.json"))
						.body("size()", greaterThan(0))
						.body("status", everyItem(equalTo("available")))
						.body("name", hasItem(petPayload.getName()))
						.body("id", hasItem(petPayload.getId()))
						.log().all();
		}
		
		
		@Test(dependsOnMethods = "testGetPet")
		public void testDeletePet()
		{
			Response response = PetEndPoints.deletePet(petPayload.getId());
			response.then()
					.statusCode(200)
					.log().all();
			
			Response getResponse = PetEndPoints.getPet(petPayload.getId());
			getResponse.then()
					.statusCode(404);
			
		}
		
		@Test(dependsOnMethods = "testDeletePet")
		public void	testAlreadyDeletedPet()
		{
			Response response = PetEndPoints.deletePet(petPayload.getId());
			response.then()
					.statusCode(404)
					.log().all();
		}
}
