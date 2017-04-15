package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class testPOIExcel {
	
	@Test
	public void testWriteExcel03() throws IOException{
		//1、创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//2、创建工作表
		HSSFSheet sheet = workbook.createSheet("sheetA");
		//3、创建行,创建第三行
		HSSFRow row = sheet.createRow(2);
		//4、创建单元格，创建第三行第三列
		HSSFCell cell = row.createCell(2);
		//设置单元格的值
		cell.setCellValue("hello Excel");
		
		//将创建好的Excel表输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("H:\\log\\测试.xls");
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
	
	@Test
	public void testReadExcel03() throws IOException{
		FileInputStream outputStream = new FileInputStream("H:\\log\\测试.xls");
		//
		HSSFWorkbook workbook = new HSSFWorkbook(outputStream);
		//获得第一个表（SheetA）
		HSSFSheet sheet = workbook.getSheetAt(0);
		//
		HSSFRow row = sheet.getRow(2);
		//
		HSSFCell cell = row.getCell(2);
		
		System.out.println("第三行第三列单元格内容为： "+cell.getStringCellValue());
		
	}
	
	/*@Test
	public void testWrite07Excel() throws Exception {
		//1、创建工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		//2、创建工作表
		XSSFSheet sheet = workbook.createSheet("hello world");//指定工作表名
		//3、创建行；创建第3行
		XSSFRow row = sheet.createRow(2);
		//4、创建单元格；创建第3行第3列
		XSSFCell cell = row.createCell(2);
		cell.setCellValue("Hello World");
		
		//输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("H:\\log\\测试.xlsx");
		//把excel输出到具体的地址
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
	
	@Test
	public void testRead07Excel() throws Exception {
		FileInputStream inputStream = new FileInputStream("H:\\log\\测试.xlsx");
		//1、读取工作簿
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		//2、读取第一个工作表
		XSSFSheet sheet = workbook.getSheetAt(0);
		//3、读取行；读取第3行
		XSSFRow row = sheet.getRow(2);
		//4、读取单元格；读取第3行第3列
		XSSFCell cell = row.getCell(2);
		System.out.println("第3行第3列单元格的内容为：" + cell.getStringCellValue());
		
		workbook.close();
		inputStream.close();
	}
	
	//同时处理03、07两个版本
	@Test
	public void testRead03And07Excel() throws Exception {
		String fileName = "H:\\log\\测试.xlsx";
		if(fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){//判断是否excel文档
			
			boolean is03Excel = fileName.matches("^.+\\.(?i)(xls)$");
			
			FileInputStream inputStream = new FileInputStream(fileName);
			
			//1、读取工作簿
			Workbook workbook = is03Excel ?new HSSFWorkbook(inputStream):new XSSFWorkbook(inputStream);
			//2、读取第一个工作表
			Sheet sheet = workbook.getSheetAt(0);
			//3、读取行；读取第3行
			Row row = sheet.getRow(2);
			//4、读取单元格；读取第3行第3列
			Cell cell = row.getCell(2);
			System.out.println("第3行第3列单元格的内容为：" + cell.getStringCellValue());
			
			workbook.close();
			inputStream.close();
		}
	}*/
	
	//合并单元格设置单元格样式
	@Test
	public void testExcelStyle() throws IOException{
		//1、创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//1.1 创建合并单元格的对象，合并第三行的第三到第五列
		CellRangeAddress cellRangeAddress = new CellRangeAddress(2,2,2,4);
		
		//1.2创建单元格的样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置垂直居中
		
		//1.3 创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置加粗
		font.setFontHeightInPoints((short) 16);//设置字体大小
		style.setFont(font);    //将字体加载到样式中
		
		//1.4设置单元格背景
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置背景填充模式（以某一种形状填充，这是完全填满的模式？）
		style.setFillBackgroundColor(HSSFColor.YELLOW.index);//设置填充背景色（填充模式间隙的颜色）
		style.setFillForegroundColor(HSSFColor.RED.index); //设置填充前景色（填充模式的颜色）
		
		//2、创建工作表
		HSSFSheet sheet = workbook.createSheet("sheetA");
		//2.1、加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//3、创建行,创建第三行
		HSSFRow row = sheet.createRow(2);
		//4、创建单元格，创建第三行第三列
		HSSFCell cell = row.createCell(2);
		
		//设置单元格的样式
		cell.setCellStyle(style);
		//设置单元格的值
		cell.setCellValue("hello Excel 11");
		
		//将创建好的Excel表输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("H:\\log\\测试.xls");
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
