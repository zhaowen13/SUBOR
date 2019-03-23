package utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml读取
 * 
 * @author zhaowen
 *
 */

public class Xml {
	@SuppressWarnings("unchecked")
	public static HashMap<String, Locator> setxml(String path, String pagename) throws DocumentException {
		HashMap<String, Locator> locatorMap = new HashMap<String, Locator>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(path));
		Element root = document.getRootElement();// 得到根节点
		List<Element> pages = root.elements("page");
		for (Element a : pages) {
			String aa = a.attribute("pageName").getText();
			if (aa.equals(pagename)) {
				List<Element> elements = a.elements("locator");
				for (Element i : elements) {
					Attribute attr = i.attribute("value");
					Attribute attr1 = i.attribute("timeOut");
					String name = i.getText();
					String element = attr.getText();
					String timeout = attr1.getText();
					int loc1 = Integer.parseInt(timeout);
					Locator locator = new Locator(name, loc1, element);
					locatorMap.put(name, locator);
				}
				break;
			}
		}
		return locatorMap;
	}
}
