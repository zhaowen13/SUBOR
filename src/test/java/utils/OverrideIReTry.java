package utils;


import org.testng.IRetryAnalyzer; 
import org.testng.ITestResult;
import org.testng.Reporter;
   
public class OverrideIReTry implements IRetryAnalyzer{ 
	 Log log = new Log(this.getClass());
	 private  int initReTryNum=1;//Initial number
	    private  int maxReTryNum=1;//Number of failed rans
	    public boolean retry(ITestResult iTestResult) {
	        if(initReTryNum<=maxReTryNum){
	            String message=" Method "+iTestResult.getName()+" Execution failed, retry"+initReTryNum+"Times";
	            log.error(message);
	            Reporter.setCurrentTestResult(iTestResult);	      
	            initReTryNum++;
	            return true;
	        }
	        return false;
	    }
	}
