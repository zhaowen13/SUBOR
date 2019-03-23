package utils;

/**
 * 元素定位信息基本类
 * @author Lqj
 * @Time 2017-01-13 09:21
 */

public class Locator {
	//枚举类型，元素定位方法使用
	
	private String name;   //元素名称  
	private int timeout; //元素定位信息
	private String element;    //元素定位超时时间
	public String getName() {
		return name;
	}
	public String getElement() {
		return element;
	}
	public int getTimeout() {
		return timeout;
	}
  
	public Locator(String name,int timeout,String element){
		this.name =name;
		this.element = element;
		this.timeout = timeout;
	}
	
}
