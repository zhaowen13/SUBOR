package utils;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import web.ui.BasePage;

public class Testng {
	PrtScr ps;

	public Testng(String type) {
		ps = new PrtScr(type);
	}

	protected Log log = new Log(this.getClass());
	static String clss;

	public void title(String name, String value, BasePage bp) throws DocumentException {
		boolean a = false;
		if (bp.getCurrentTitle().equals(value)) {
			a = true;
			log.info(" " + name + "执行成功--网页标题断言");
		} else {
			log.error(" " + name + "执行失败--网页标题断言");
			ps.takeScreenShot(bp.getdriver(), name);
		}
		test(a);
	}

	public void url(String name, String value, BasePage bp) throws DocumentException {
		boolean a = false;
		if (bp.getCurrentUrl().equals(value)) {
			a = true;
			log.info(name + "执行成功--网页url断言");
		} else {
			log.error(name + "执行失败--网页url断言");
			ps.takeScreenShot(bp.getdriver(), name);
		}
		test(a);
	}

	public void element(String name, String value, BasePage bp) {
		boolean a = false;
		if (bp.whether(value)) {
			a = true;
			log.info(" " + name + "执行成功--element断言");
		} else {
			log.error(" " + name + "执行 失败--element断言");
			ps.takeScreenShot(bp.getdriver(), name);
		}
		test(a);
	}

	@Test
	public void test(boolean a) {
		Assert.assertTrue(a);
	}

	public void assertion(String name, String clss, String value, BasePage bp) throws IOException, DocumentException {
		if (clss.equals("element")) {
			element(name, value, bp);
		} else if (clss.equals("网页标题")) {
			title(name, value, bp);
		} else if (clss.equals("网址")) {
			url(name, value, bp);

		}
	}
}
