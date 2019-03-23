package web.ui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.ThreadContext;
import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import utils.PrtScr;
import utils.Testng;
import utils.data_flow;

public class action {
	private Testng test = null;
	private PrtScr ps = null;
	private BasePage bp = null;

	public void actionset(String name) throws DocumentException, IOException {
		File file = new File("./json\\web\\" + name + ".json");
		String content = FileUtils.readFileToString(file, "UTF-8");
		LinkedHashMap<String, String> jsonMap = JSON.parseObject(content,
				new TypeReference<LinkedHashMap<String, String>>() {
				});

		data_flow data = new data_flow();
		// data.set("username", "");
		for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
			switch (entry.getKey().split("_")[0]) {
			case "pageName":
				bp = new BasePage(entry.getValue(), jsonMap.get("xmlpath"), jsonMap.get("type"));
				bp.activate(jsonMap.get("url"));
				ps = new PrtScr("web\\" + jsonMap.get("type"));
				test = new Testng(jsonMap.get("type"));
				ThreadContext.put("ROUTINGKEY", "web\\" + jsonMap.get("type"));
				break;
			case "click":
				bp.click(entry.getKey().split("_")[1]);
				break;
			case "sendKeys":
				String value = entry.getValue();
				if (entry.getValue().indexOf("${") != -1) {
					System.out.println(entry.getKey().split("_")[1] + ":" + entry.getValue() + ":"
							+ data.get(value.substring(2, value.indexOf("}"))));
					bp.sendKeys(entry.getKey().split("_")[1], data.get(value.substring(2, value.indexOf("}"))));
				} else {
					bp.sendKeys(entry.getKey().split("_")[1], value);
				}
				break;
			case "testng":
				test.assertion(name, entry.getKey().split("_")[1], entry.getValue(), bp);
				break;
			default:
				break;
			}
		}
		ps.takeScreenShot(bp.getdriver(), name);
	}

	public static void main(String[] args) throws DocumentException, IOException, InterruptedException {
		File file = new File("./json/" + "login.json");
		String content = FileUtils.readFileToString(file, "UTF-8");
		LinkedHashMap<String, String> jsonMap = JSON.parseObject(content,
				new TypeReference<LinkedHashMap<String, String>>() {
				});
		System.out.println(jsonMap.get("sendKeys1"));

	}
}
