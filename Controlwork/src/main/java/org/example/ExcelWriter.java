package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {
    public static void saveToExcel(List<Program> programs, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Programs");

        int rowNum = 0;
        for (Program program : programs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(program.getChannel());
            row.createCell(1).setCellValue(program.getTime().toString());
            row.createCell(2).setCellValue(program.getTitle());
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
