package app.ui;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.openqa.selenium.By;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import utils.Locator;
import utils.Log;
import utils.Xml;

public class Appaction {
	/**
	 * 基本动作类
	 * 
	 * @author zhaowen
	 * @Time 2017-09-26
	 */
	protected Log log = new Log(this.getClass());
	// PrtScr jt = new PrtScr(null);
	private AndroidDriver<?> driver;
	Appclass appclass = new Appclass();
	HashMap<String, Locator> locatorMap;

	public Appaction(String path, String page) throws DocumentException { // 选择
																			// page页面
		locatorMap = Xml.setxml(path, page);
	}

	public void appopen(String udid, String port) throws MalformedURLException, InterruptedException {
		driver = appclass.start_up(udid, port);

	}

	public AndroidElement xpath(Locator loc) throws InterruptedException { // 定位元素并设置等待时间
		AndroidElement thisdriver = null;
		try {
			Thread.sleep(loc.getTimeout() * 1000);
			thisdriver = (AndroidElement) driver.findElement(By.xpath(loc.getElement()));
			log.info(loc.getName() + "：定位成功");
		} catch (Exception e) {
			try {
				log.error(loc.getName() + "定位失败" + e);
			} catch (Exception e2) {
				log.error("没有找到该元素");
			}
		}
		return thisdriver;
	}

	public void click(String name) throws InterruptedException { // 点击元素
		Locator loc = locatorMap.get(name);
		try {
			AndroidElement driver = xpath(loc);
			driver.click();
			log.info(loc.getName() + "：点击成功");
		} catch (Exception e) {
			log.error(loc.getName() + "：点击失败" + e);
		}
	}

	public void sendKeys(String name, String str) throws InterruptedException { // 在文本框中输入
		Locator loc = locatorMap.get(name);
		try {
			AndroidElement driver = xpath(loc);
			driver.sendKeys(str);
			// Thread.sleep(loc.getTimeout());
			log.info("在" + loc.getName() + "输入框中输入" + str + "：输入成功");
		} catch (Exception e) {
			log.error("在" + loc.getName() + "输入框中输入" + str + "：输入失败");
		}
	}

	public void click(int x, int y) throws InterruptedException {
		try {
			TouchAction action = new TouchAction(driver);
			action.longPress(x, y).release().perform();
			log.info("点击坐标" + x + ":" + y + "：成功");
		} catch (Exception e) {
			log.error("点击坐标" + x + ":" + y + "：失败");

		}
	}

	public boolean be(String name) {
		Locator loc = locatorMap.get(name);
		boolean status = false;
		try {
			driver.findElement(By.xpath(loc.getElement()));
			status = true;
		} catch (Exception e) {
			log.error(loc.getName() + "元素不存在");
		}
		return status;
	}

	public void slide(int x, int y, int xj, int yj) throws InterruptedException {
		try {
			TouchAction action = new TouchAction(driver);
			action.press(x, y).waitAction(1000).moveTo(xj, yj).release().perform();
			log.info("从" + x + "--" + y + "滑动到" + xj + "--" + xj + "成功");
		} catch (Exception e) {
			log.error("从" + x + "--" + y + "滑动到" + xj + "--" + xj + "失败");

		}
	}

	public void cls(String name) throws InterruptedException {
		try {
			click(name);
			String text = text(name);
			driver.pressKeyCode(123); // 移动到文本最后
			for (int i = 0; i < text.length(); i++) { // a 代表输入的字符串
				driver.pressKeyCode(67); // 删除
			}
			log.info("文本框中的内容成功清空");
		} catch (Exception e) {
			log.error("文本框中的内容没有清空");
		}
	}

	public void button(int i) { // 操作键盘
		try {
			driver.pressKeyCode(i);
			log.info("点击按钮成功");
		} catch (Exception e) {
			log.error("点击按钮失败");
		}

	}

	public String text(String name) throws InterruptedException { // 返回控件文本
		Locator loc = locatorMap.get(name);
		AndroidElement driver = xpath(loc);
		String str = driver.getText();
		return str;
	}

	public void Context_to() { // 切换上下文
		try {
			if (driver.getContext().equals("NATIVE_APP")) {
				driver.context("WEBVIEW_com.zhongqian.zq");
			} else if (driver.getContext().equals("WEBVIEW_com.zhongqian.zq")) {
				driver.context("NATIVE_APP");
			}
			log.info("成功切换上下文");
		} catch (Exception e) {
			log.error("切换上下文失败");
		}
	}

	public void stp(int t) throws InterruptedException {
		log.info("等待" + t + "秒");
		Thread.sleep(t * 1000);
	}

	public void quit() {
		log.info("关闭APP");
		driver.quit();
	}

	public AndroidDriver<?> getdriver() {
		return driver;

	}

}
