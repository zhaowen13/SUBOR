package app.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import utils.Log;

public class Appclass {
	/**
	 * APP驱动类
	 * 
	 * @author zhaowen
	 * @Time 2017-09-26
	 */
	private AndroidDriver<?> driver;
	protected Log log = new Log(this.getClass());

	public AndroidDriver<?> start_up(String udid, String port) throws MalformedURLException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		try {
			log.info("APP正在打开…………………………………………");

			Thread.sleep(3000);
			log.info("APP打开的端口号为：" + port + "  udid为：" + udid);
			capabilities.setCapability("automationName", "Appium");// 自动化引擎的选择
			// capabilities.setCapability("app",
			// "C:\\Users\\Administrator\\Desktop\\安装包\\金融测试\\JJJR_test_4.0.apk");//APK程序的路径，自动安装app
			// capabilities.setCapability("browserName", "chrome");//H5网页测试
			// 指定打开浏览器
			capabilities.setCapability("deviceName", "小米手机");// 手机设备名称
			capabilities.setCapability("platformName", "Android");// 手机设备系统平台
			capabilities.setCapability("platformVersion", "6.0");// 手机操作系统版本
			capabilities.setCapability("appPackage", "com.zhongqian.zq");// app的包名
																			// com.miui.calculator
																			// com.tencent.mm
			capabilities.setCapability("appActivity", "com.jinjiajinrong.zq.activity.MainActivity"); // app的入口名称打开被测app的
			capabilities.setCapability("udid", udid);// 设备的udid号，必须填对
														// 860SDQAR3QTK
			capabilities.setCapability("unicodeKeyboard", true); // appium支持中文输入
			// capabilities.setCapability("resetKeyboard", true);//appium支持中文输入
			// //重置输入法到原有状态
			capabilities.setCapability("newCommandTimeout", "40");// 没有新命令的超时时间//退出
			driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);// 实例化手机驱动对象
			log.info("APP打开成功端口号为：" + port + "  udid为：" + udid);
			Thread.sleep(3000);
		} catch (Exception e) {
			log.error("APP打开失败" + e);
		}
		return driver;
	}

}
