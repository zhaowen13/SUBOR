package web.ui;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.testng.annotations.Test;

public class NewTest {
	public static action Firefox = new action();
	public static action Google = new action();

	@Test
	public void FirefoxTest() throws DocumentException, IOException, InterruptedException {
		Google.actionset("login");
	}

	@Test
	public void GoogleTest() throws DocumentException, IOException, InterruptedException {
		Firefox.actionset("login2");
	}
}
