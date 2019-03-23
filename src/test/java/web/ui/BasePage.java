package web.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.dom4j.DocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Locator;
import utils.Log;
import utils.Xml;
import utils.data_flow;

/**
 * 浏览器配置和元素定位
 * 
 * @author
 *
 */
public class BasePage {
	private WebDriver driver = null;
	public String CurrentTitle;
	public String CurrentUrl;
	WebElement el;
	HashMap<String, Locator> locatorMap;
	protected Log log = new Log(this.getClass());

	public void set(String type) {
		String path = "chromedriver.exe";
		String name = "webdriver.chrome.driver";
		if (type.equals("Firefox")) {
			path = "geckodriver.exe";
			name = "webdriver.gecko.driver";
			// System.setProperty("webdriver.firefox.bin","D:\\firefox\\firefox.exe");
			System.setProperty(name, "./driver\\" + path);
			driver = new FirefoxDriver();

		} else if (type.equals("ie")) {
			path = "IEDriverServer.exe";
			name = "webdriver.ie.driver";
			System.setProperty(name, "./driver\\" + path);
			driver = new InternetExplorerDriver();

		} else if (type.equals("Google")) {
			System.setProperty(name, "./driver\\" + path);
			ChromeOptions options = new ChromeOptions();
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
			options.addArguments("--test-type", "--start-maximized");
			driver = new ChromeDriver(options);
		}
	}

	public BasePage(String pageName, String path, String type) throws DocumentException {
		locatorMap = Xml.setxml(path, pageName);
		set(type); // 选择浏览器
		log.info("pageName:" + pageName + " type:" + type);
	}

	@SuppressWarnings({})
	public WebElement findelement(final Locator loc) {
		WebDriverWait wait = new WebDriverWait(driver, loc.getTimeout()); // Timeout超时时间
		WebElement el = wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				return xpath(d, loc);
			}
		});
		return el;
	}

	public void activate(String url) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		max();
		log.info("打开【" + url + "】页面");
	}

	public void newactivate(String url, String path, String pageName) throws DocumentException, InterruptedException {
		locatorMap = Xml.setxml(path, pageName);
		String js = "window.open('" + url + "')";
		((JavascriptExecutor) driver).executeScript(js);
		log.info("打开【" + url + "】页面");
		String dw = driver.getWindowHandle();
		Thread.sleep(3000);
		try {
			Set<String> cws = driver.getWindowHandles();
			for (String cw : cws) {
				if (cw.compareTo(dw) != 0) {
					driver = driver.switchTo().window(cw);
					break;
				}
			}
			log.info("跳转新的页面成功");
		} catch (Exception e) {
			log.error("跳转新的页面失败");
		}
	}

	public void closeWeb() {
		driver.close();
		log.info("关闭页面");
	}

	// 关闭浏览器
	public void exit() {
		driver.quit();
		log.info("关闭浏览器");
	}

	// 获取当前URL
	public String getCurrentUrl() {
		CurrentUrl = driver.getCurrentUrl();
		log.info("获取当前页面：" + CurrentUrl);
		return CurrentUrl;
	}

	// 获取当前Title
	public String getCurrentTitle() {
		CurrentTitle = driver.getTitle();
		log.info("获取当前网页标题：" + CurrentTitle);
		return CurrentTitle;

	}

	// 刷新浏览器
	public void refresh() {
		driver.navigate().refresh();
		log.info("刷新页面");
	}

	// 浏览器回退
	public void back() {
		driver.navigate().refresh();
		log.info("浏览器后退");
	}

	// 浏览器向前
	public void forword() {
		driver.navigate().forward();
		log.info("浏览器前进");
	}

	// 浏览器最大化
	public void max() {
		driver.manage().window().maximize();
		log.info("浏览器最大化");
	}

	// 暂停操作
	public void pause(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
			log.info("暂停操作" + seconds + "秒");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebElement xpath(WebDriver driver, Locator loc) {
		WebElement thisdriver = null;
		try {

			thisdriver = driver.findElement(By.xpath(loc.getElement()));
			log.info("定位:" + "\"" + loc.getName() + "\"" + " 元素成功");
		} catch (Exception e) {
			log.error("定位:" + loc.getName() + " 元素失败");

		}
		return thisdriver;
	}

	public void click(String name) {
		Locator loc = locatorMap.get(name);
		try {
			WebElement driver = findelement(loc);
			driver.click();
			log.info("点击:" + "\"" + loc.getName() + "\"" + " 按钮成功");
		} catch (Exception e) {

			log.error("点击:" + "\"" + loc.getName() + "\"" + " 按钮失败");
		}
	}

	public void sendKeys(String name, String str) {
		Locator loc = locatorMap.get(name);
		try {
			WebElement driver = findelement(loc);
			driver.sendKeys(str);
			log.info("在:" + "\"" + loc.getName() + "\"" + " 文本框中" + "输入" + str + "成功");
		} catch (Exception e) {
			log.error("在:" + "\"" + loc.getName() + "\"" + " 文本框中" + "输入" + str + "失败");
			e.printStackTrace();
		}
	}

	public void upload_files(String name) {

	}

	public void frame(String name) { // 跳入frame
		Locator loc = locatorMap.get(name);
		WebElement fr = null;
		try {
			fr = driver.findElement(By.xpath(loc.getElement()));
			driver.switchTo().frame(fr);
			log.info(loc.getName() + ":frame跳转成功");
		} catch (Exception e) {
			log.error(loc.getName() + ":frame跳转失败");
		}

	}

	public void outframe() {
		driver.switchTo().defaultContent();

	}

	public void ComboBox(String name, int i) { // 下拉框
		Locator loc = locatorMap.get(name);
		WebElement e = null;
		try {
			e = driver.findElement(By.cssSelector(loc.getElement()));
			Select ss = new Select(e);
			ss.selectByIndex(i);
			log.info("选择下拉框中的： " + loc.getName() + "成功");
		} catch (Exception e2) {
			log.error("选择下拉框中的: " + loc.getName() + "失败");
		}

	}

	public void ComboBox(String name, String str, data_flow df) { // 下拉框
		Locator loc = locatorMap.get(name);
		WebElement e = null;
		try {
			e = driver.findElement(By.cssSelector(loc.getElement()));
			Select ss = new Select(e);
			ss.selectByVisibleText(str);
			log.info("选择下拉框中的： " + loc.getName() + "成功");
		} catch (Exception e2) {
			log.error("选择下拉框中的: " + loc.getName() + "失败");
		}

	}

	public void setScroll(int height) { // 滚动条滑动
		try {
			String setscroll = "document.documentElement.scrollTop=" + height;
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript(setscroll);
			log.info("滚动" + height);
		} catch (Exception e) {
			log.error("无法设置滚动条");
		}
	}

	public void setScroll(String name) { // 滚动条滑动
		Locator loc = locatorMap.get(name);
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",
					driver.findElement(By.xpath(loc.getElement())));
			log.info("滚动到元素：" + name);
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error("无法设置滚动条");
		}
	}

	public void jsclick(String name) { // js弹出框处理
		Locator loc = locatorMap.get(name);
		try {
			WebElement driver = findelement(loc);
			((WebDriver) driver).switchTo().alert().accept();
			log.info("点击:" + "\"" + loc.getName() + "\"" + " 按钮成功");
		} catch (Exception e) {
			try {
				log.error("点击:" + "\"" + loc.getName() + "\"" + " 按钮失败");
			} catch (java.lang.NullPointerException e2) {
				log.error("没有找到要点击的元素");
			}
		}
	}

	public void pagejmp() throws InterruptedException { // 跳转页面
		String dw = driver.getWindowHandle();
		Thread.sleep(3000);
		try {
			Set<String> cws = driver.getWindowHandles();
			for (String cw : cws) {
				if (cw.compareTo(dw) != 0) {
					driver = driver.switchTo().window(cw);
					break;
				}
			}
			log.info("跳转页面成功");
		} catch (Exception e) {
			log.error("跳转页面失败");

		}
	}

	public boolean whether(String name) { // 元素是否存在
		Locator loc = locatorMap.get(name);
		try {
			driver.findElement(By.xpath(loc.getElement()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public WebDriver getdriver() {
		return driver;

	}

}
