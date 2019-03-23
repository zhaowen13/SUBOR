package utils;
/**
 *poi读取用例
 * @author zhaowen
 *
 */
public class Usecase {
	private String id;
	private String name;
	private String data;
	private String[] result;
	private String model;
public  Usecase(String id,String name,String data,String[] result,String model){
	 this.id=id;
	 this.name=name;
	 this.data=data;
	 this.result=result;
	 this.model=model;
}
public String getid (){
	return id;
	
}
public String getName(){
	return name;
	
}
public String getdata(){
	return data;
	
}
public String getresult(int i){
	String res=result[i];
	return res;
	
}
public String getmodel(){
	
	return model;
	
}
}
