package api.payload;

public class Store {

//		{
//		  "id": 0,
//		  "petId": 0,
//		  "quantity": 0,
//		  "shipDate": "2026-01-01T13:10:16.653Z",
//		  "status": "placed",
//		  "complete": true
//		}
	
	long id;
	int petId;
	int quantity;
	String shipDate;
	String status;
	boolean complete;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
		


}
