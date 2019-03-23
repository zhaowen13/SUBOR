package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel读取
 * 
 * @author zhaowen
 * @Time 2017-09-26
 */

public class Excel {
	public String path;
	private Workbook workbook;

	// public Excel(String path){
	// this.path="./用例//"+path+".xls";
	// }
	public Map<String, Usecase> getUITestData(String pathx) throws IOException {
		String path = "./用例//" + pathx + ".xls";
		Map<String, Usecase> testcasesmap = new HashMap<String, Usecase>();
		File execlfile = new File(path);
		FileInputStream is = new FileInputStream(execlfile);
		@SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook(is);
		Sheet sheet = workbook.getSheetAt(0);
		// y=sheet.getPhysicalNumberOfRows()-1; //获取总列数
		int y = gety(path) - 1;
		System.out.println(y);
		for (int i = 1; i <= y; i++) {
			Row row = sheet.getRow(i);
			String id = row.getCell(0).toString();
			String name = row.getCell(1).toString();
			String data = row.getCell(2).toString();
			String[] result = row.getCell(3).toString().split(",");
			String model = row.getCell(5).toString();
			Usecase key = new Usecase(id, name, data, result, model);
			testcasesmap.put(id, key);
		}
		return testcasesmap;
	}

	public String[] get(String pathx) throws IOException { // 把所有用例名称取出来放到数组中
		String path = "./用例//" + pathx + ".xls";
		File execlfile = new File(path);
		FileInputStream is = new FileInputStream(execlfile);
		@SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook(is);
		Sheet sheet = workbook.getSheetAt(0);
		int y = gety(path) - 1;
		String[] idss = new String[y];
		for (int i = 1; i <= idss.length; i++) {
			Row row = sheet.getRow(i);
			String id = row.getCell(0).toString();
			idss[i - 1] = id;
		}
		return idss;
	}

	public void test(String value, Usecase Example) {
		try {
			int i = Integer.parseInt(Example.getid());
			// 创建Excel的工作书册 Workbook,对应到一个excel文档
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(path));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell((short) 4);
			cell.setCellValue(value);
			FileOutputStream os;
			os = new FileOutputStream(path);
			wb.write(os);
			os.close();
			i++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int gety(String path) throws IOException { // 获取列数
														// sheet.getPhysicalNumberOfRows()方法获得的列数，表格原来有数据删除了，还是把列数据算在里面
		int i = 0;
		File execlfile = new File(path);
		FileInputStream is = new FileInputStream(execlfile);
		workbook = new HSSFWorkbook(is);
		Sheet sheet = workbook.getSheetAt(0);
		String str = "111";
		for (int y = 0; y < sheet.getPhysicalNumberOfRows(); y++) {
			Row row = sheet.getRow(y);
			str = row.getCell(0).toString();
			if (!str.equals("")) {
				i++;
			}
		}
		return i;
	}

	public static void main(String[] args) throws IOException {
		Excel e = new Excel();
		String[] ids = e.get("华为荣耀4x");
		Map<String, Usecase> map = e.getUITestData("华为荣耀4x");
		Usecase u = map.get("1,华为荣耀4x,4726");
		String a = u.getdata();
		System.out.println("这是data" + a);
		for (String s : ids) {
			System.out.println("第一行" + s);

		}

	}

}

/*
 * public static void main(String[] args) throws IOException {
 * Map<String,Usecase> testcasesmap = new HashMap<String,Usecase>(); //String[]
 * testcasesmap =new String[6]; File execlfile = new File("D:\\java\\s.xlsx");
 * //FileInputStream is = new FileInputStream(execlfile); //Workbook workbook =
 * new HSSFWorkbook(is); InputStream is = new FileInputStream(execlfile);
 * 
 * XSSFWorkbook wb = new XSSFWorkbook(is); Workbook workbook = new
 * XSSFWorkbook(is); Sheet sheet = workbook.getSheetAt(0);
 * i=sheet.getPhysicalNumberOfRows(); //获取总列数 for(int
 * i=1;i<sheet.getPhysicalNumberOfRows();i++){ Row row = sheet.getRow(i); String
 * name= row.getCell(0).toString(); String password=row.getCell(1).toString();
 * String data=row.getCell(2).toString(); String
 * result=row.getCell(3).toString(); Usecase key=new
 * Usecase(name,password,data); testcasesmap.put(name,key);
 * 
 * 
 * Usecase password1=testcasesmap.get("第一条"); String
 * ext=password1.getpassword(); System.out.println(ext); } } }
 * 
 */
