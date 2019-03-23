package utils;


/**
 * json replace
 * @author zhaowen
 *@Time 2018-11-12
 */
public class Json {
	private String str;
	public String put(String name,String key){    // json Processing, replacing variables
	this.str=this.str.replace("${"+name+"}", key);
	//this.str=this.str.replace(name, key);
		return this.str;
		
	}
	public void put(String str){                 //Raw json data
		this.str=str;	
	}
	public static void main(String[] args) {
		Json j = new Json();
		j.put("{\"participants2\":[{\"activated\":false,\"addressCity\":\"KANSAS CITY\",\"addressState\":\"MO\",\"addressStreet\":\"street_${RANDOM.STRING:5}\",\"addressZip\":\"64101\",\"annualIncome\":\"between_50000_and_100000\",\"birthday\":\"11/13/1996\",\"country\":\"US\",\"email\":\"1000\",\"employer\":\"500\",\"firstName\":\"65464\",\"idExpirationDate\":\"11/30/2028\",\"idIssuedDate\":\"11/30/2008\",\"idIssuedState\":null,\"idNumber\":\"2345666633\",\"idType\":\"passport\",\"issuingAuthority\":\"fsafds\",\"maritalStatus\":\"separated\",\"middleName\":\"fdsafsdafa\",\"occupation\":\"5367567\",\"phoneCell\":\"234-566-6633\",\"phoneHome\":\"234-566-6633\",\"phonePreferred\":\"234-566-6633\",\"phoneWork\":\"234-566-6633\",\"socialSecurityNumber\":\"666-00-0895\",\"uid\":\"fdsafsdafs\",\"usCitizen\":true,\"userUid\":\"fsadfsdafsa\"}],\"trusts\":[]}");
		j.put("fdsafsdafs", "${$.getNewApplicants.response.body.primaryApplicant.participantUid}");
		//j.put("participantId", "34223");
		System.out.println(j.put("500", "${RANDOM.STRING:5}"));
		
		
	}
}
