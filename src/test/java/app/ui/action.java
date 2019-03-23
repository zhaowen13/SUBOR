package app.ui;

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
	private Appaction app = null;

	public void actionset(String name) throws DocumentException, IOException, InterruptedException {
		File file = new File("./json/" + name + ".json");
		String content = FileUtils.readFileToString(file, "UTF-8");
		LinkedHashMap<String, String> jsonMap = JSON.parseObject(content,
				new TypeReference<LinkedHashMap<String, String>>() {
				});

		data_flow data = new data_flow();
		data.set("username", "");
		for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
			switch (entry.getKey().split("_")[0]) {
			case "pageName":
				app = new Appaction(jsonMap.get("type"), entry.getValue());
				ps = new PrtScr(jsonMap.get("type"));
				test = new Testng(jsonMap.get("type"));
				ThreadContext.put("ROUTINGKEY", jsonMap.get("type"));
				app.appopen(jsonMap.get("udid"), jsonMap.get("port"));
				break;
			case "click":
				app.click(entry.getKey().split("_")[1]);
				break;
			case "sendKeys":
				String value = entry.getValue();
				if (entry.getValue().indexOf("${") != -1) {
					System.out.println(entry.getKey().split("_")[1] + ":" + entry.getValue() + ":"
							+ data.get(value.substring(2, value.indexOf("}"))));
					app.sendKeys(entry.getKey().split("_")[1], data.get(value.substring(2, value.indexOf("}"))));
				} else {
					app.sendKeys(entry.getKey().split("_")[1], value);
				}
				break;
			// case "testng":
			// test.assertion(name, entry.getKey().split("_")[1],
			// entry.getValue(), app);
			// break;
			default:
				break;
			}
		}
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
