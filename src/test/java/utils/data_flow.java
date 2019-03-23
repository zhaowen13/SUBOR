package utils;

import java.util.HashMap;

public class data_flow {
	HashMap<String, String> locatorMap = new HashMap<String, String>();

public void set(String key,String value){
	locatorMap.put(key, value);	
}
public String get(String key){	
	return locatorMap.get(key);
	
}


	

}
