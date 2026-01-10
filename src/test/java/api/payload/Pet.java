package api.payload;

import java.util.List;

public class Pet {
	
//		{
//		  "id": 1,
//		  "name": "doggie",
//		  "status": "available"
//			"photoUrls": [
//	            "https://example.com/photo1.jpg",
//	            "https://example.com/photo2.jpg"
//				]
//		  "tags": [
//	         {
//	           "id": 101,
//	           "name": "friendly"
//	         },
//	         {
//	           "id": 102,
//	           "name": "vaccinated"
//	         }
//	       		]	         
//		}
	
	private int id;
	private String name;
	private String status;
	private List<String> photoUrls; //JSON array ↔ List<String> in Java
	private List<Tag> tags;
	
	
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getPhotoUrls() {
		return photoUrls;
	}
	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

}
