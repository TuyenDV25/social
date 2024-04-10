package com.example.social_network.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.social_network.exception.AppException;
import com.example.social_network.exception.ErrorCode;

public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Total Post", "Total New Friend", "Total Like", "Total Comment" };
	static String SHEET = "reports";

	public static ByteArrayInputStream tutorialsToExcel(Long countFriends, Long countPost,
			Long countComment, Long countLikes) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			Row row = sheet.createRow(1);
			row.createCell(0).setCellValue(countPost);
			row.createCell(1).setCellValue(countFriends);
			row.createCell(2).setCellValue(countLikes);
			row.createCell(3).setCellValue(countComment);

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new AppException(ErrorCode.ERROR_IMPORT_DATA_EXCEL);
		}
	}

}
