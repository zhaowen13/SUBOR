package utils;

import java.io.File;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;
/**
 * Secondary package log
 * @author zhaowen
 *@Time 2018-11-12
 */

public class Log {
    private final Class<?> clazz;
    private Logger logger;

   
    public Log(Class<?> clazz) {
	this.clazz = clazz;
	this.logger = LogManager.getLogger(this.clazz);
	System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    public void log(String... comm) {
	String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	if (comm.length == 0) {
	    Reporter.log("[" + time + "] <br />");
	} else {
	    Reporter.log("[" + time + "] " + comm[0] + "<br />");
	}
	logger.info(comm[0]);
    }
    public void info(String comm) {
	  String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	  Reporter.log("<span style=\"color:#16A05D\"><b>[" + time + "] [INFO] " + comm + "</b></span><br />");
	  logger.info(clazz.getCanonicalName()+comm);
    }
    public void debug(String comm) {
	  String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	  Reporter.log("<span style=\"color:#2A00FF\"><b>[" + time + "] [DEBUG] " + comm + "</b></span><br />");
	  logger.debug(clazz.getCanonicalName()+comm);
    }
    
    public void error(String comm) {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Reporter.log("<span style=\"color:#FF0000\"><b>[" + time + "] [ERROR] " + comm + "</b></span><br />");
        logger.error(clazz.getCanonicalName()+comm);
        
    }
    
    public void warn(String comm) {
        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Reporter.log("<span style=\"color:#FF7F27\"><b>[" + time + "] [WARNING] " + comm + "</b></span><br />");
        logger.warn(clazz.getCanonicalName()+comm);
    }
    
    public void highLight(String comm) {
        log("<span style='background-color:#FFE500;'>" + comm + "</span>");
    }

    public void greenLight(String comm) {
        log("<span style='background-color:#CFFFBA;'>" + comm + "</span>");
    }

    public void paraLight(String comm) {
        log("<span style='background-color:#E4FFD9;'>" + comm + "</span>");
    }

    public void mcdbLight(String comm) {
        log("<span style='background-color:#C1E7F7;'>[MCDB]" + comm + "</span>");
    }

    public void title(String comm) {
        String str;
        str = "<p style=\"color:#0068BD;margin-top:25px;margin-bottom:8px\"><b>";
        str = str + "**********************************************************************************************<br>";
        str = str + "* " + comm + "<br>";
        str = str + "**********************************************************************************************</b>";
        str = str + "</p>";
        Reporter.log(str);
    }

    public void screenShotLog(String comm,File file) {
        int width = 350;
        String absolute = "file:" + file.getAbsolutePath();
        Reporter.log("<a target='_blank' href=\"" + absolute + "\">");
        Reporter.log("<img width=\"" + width + "\" src=\"" + absolute + "\" />    " + comm);
        Reporter.log("</a><br />");
    }
  

}
