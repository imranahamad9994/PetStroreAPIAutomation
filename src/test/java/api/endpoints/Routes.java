package api.endpoints;

//https://petstore.swagger.io/

//Post  https://petstore.swagger.io/v2/user
//Get  https://petstore.swagger.io/v2/user/{username}
//Put  https://petstore.swagger.io/v2/user/{username}
//Delete https://petstore.swagger.io/v2/user/{username}


public class Routes {
	
	public static String base_url ="https://petstore.swagger.io/v2";
	
	//User Module
	
	public static String post_url = base_url+"/user";
	public static String get_url = base_url+"/user/{username}";
	public static String put_url = base_url+"/user/{username}";
	public static String delete_url = base_url+"/user/{username}";
	
	//Store Module
	
	public static String storePostURL = base_url+"/store/order";
	public static String storeGetAllURL = base_url+"/store/inventory";
	public static String storeGetOneURL = base_url+"/store/order/{orderId}";
	public static String storeDeleteURL = base_url+"/store/order/{orderId}";
	
	//Pet Module
	
	public static String petPostURL = base_url+"/pet";
	public static String petGetURL = base_url+"/pet/{petId}";
	public static String petDeleteURL = base_url+"/pet/{petId}";
	
	public static String petFindByStatusURL = base_url+"/pet/findByStatus";
	

}
