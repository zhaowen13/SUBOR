package web.ui;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.testng.annotations.Test;

public class search {
	public static action Firefox = new action();
	public static action Google = new action();

	@Test
	public void GoogleTest() throws DocumentException, IOException, InterruptedException {
		Google.actionset("search");
	}

	@Test
	public void Firefox() throws DocumentException, IOException, InterruptedException {
		Firefox.actionset("search");
	}
}
