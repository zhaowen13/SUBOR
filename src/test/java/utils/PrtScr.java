package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class PrtScr {
	private String path;
	protected Log log = new Log(this.getClass());

	public PrtScr(String path) {
		this.path = path;
	}

	public void takeScreenShot(WebDriver drivername, String name) {
		String currentPath = System.getProperty("user.dir") + "\\截图\\" + path + "\\";
		String path2 = name + "失败截图" + getCurrentDateTime() + ".jpg";
		File scrFile = ((TakesScreenshot) drivername).getScreenshotAs(OutputType.FILE);
		File picFile = new File(currentPath + path2);

		try {
			FileUtils.copyFile(scrFile, picFile);
			log.info(name + "：失败截图成功");
			log.info("失败截图保存的路径:" + currentPath + path2);
		} catch (IOException e) {
			log.error(name + "：截图失败");
			e.printStackTrace();
		}
		log.screenShotLog("截图：" + scrFile, picFile);
	}

	public static String getCurrentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");// 设置日期格式
		return df.format(new Date());
	}
}
