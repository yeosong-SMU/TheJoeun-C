package com.example.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	private static final DataFormatter formatter = new DataFormatter();
	
	public static List<Product> parseProducts(InputStream is) throws Exception {
		List<Product> list = new ArrayList<>();
		try (Workbook wb = new XSSFWorkbook(is)) {
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			Sheet sh = wb.getSheetAt(0);
			int rows = sh.getPhysicalNumberOfRows();
			
			for (int r = 1; r < rows; r++) {
				Row row = sh.getRow(r);
				if (row == null)
					continue;
				
				String name = getString(row, 0, evaluator);
				if (name == null || name.isBlank())
					continue;
				
				String category = getString(row, 1, evaluator);
				Double price = getDouble(row, 2, evaluator);
				Integer qty = getInteger(row, 3, evaluator);
				
				Product p = Product.builder().name(name.trim()).category(category).price(price).stockQty(qty).build();
				list.add(p);
			}
		}
		return list;
	}
	
	public static Workbook toWorkbook(List<Product> data) {
		Workbook wb = new XSSFWorkbook();
		Sheet sh = wb.createSheet("products");
		
		int r = 0;
		Row header = sh.createRow(r++);
		header.createCell(0).setCellValue("id");
		header.createCell(1).setCellValue("name");
		header.createCell(2).setCellValue("category");
		header.createCell(3).setCellValue("price");
		header.createCell(4).setCellValue("stock_qty");
		header.createCell(5).setCellValue("created_at");
		
		for (Product p : data) {
			Row row = sh.createRow(r++);
			row.createCell(0).setCellValue(p.getId() == null ? 0 : p.getId());
			row.createCell(1).setCellValue(nvl(p.getName()));
			row.createCell(2).setCellValue(nvl(p.getCategory()));
			row.createCell(3).setCellValue(p.getPrice() == null ? 0 : p.getPrice());
			row.createCell(4).setCellValue(p.getStockQty() == null ? 0 : p.getStockQty());
			row.createCell(5).setCellValue(p.getCreatedAt() == null ? "" : p.getCreatedAt().toString());
		}
		
		for (int i = 0; i <= 5; i++)
			sh.autoSizeColumn(i);
		return wb;
	}
	
	private static String getString(Row row, int idx, FormulaEvaluator evaluator) {
		Cell c = row.getCell(idx);
		if (c == null)
			return null;
		return formatter.formatCellValue(c, evaluator);
	}
	
	private static Double getDouble(Row row, int idx, FormulaEvaluator evaluator) {
		Cell c = row.getCell(idx);
		if (c == null)
			return null;
		try {
			String val = formatter.formatCellValue(c, evaluator);
			return (val == null || val.isBlank()) ? null : Double.parseDouble(val);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static Integer getInteger(Row row, int idx, FormulaEvaluator evaluator) {
		Double d = getDouble(row, idx, evaluator);
		return d == null ? null : d.intValue();
	}
	
	private static String nvl(String s) {
		return (s == null) ? "" : s;
	}
}