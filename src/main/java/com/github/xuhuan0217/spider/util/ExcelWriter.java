package com.github.xuhuan0217.spider.util;

import com.github.xuhuan0217.spider.entity.BookEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Use apache poi to write Excel
 */
public class ExcelWriter {
    private final String filename;
    private final String sheetname;
    private final Workbook workbook;
    private final Sheet sheet;
    private int rownum = 0;
    public ExcelWriter(String filename,String sheetname) throws IOException {
        this.filename = filename;
        this.sheetname = sheetname;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet(sheetname);

    }

    public void createHeader(String [] headers){
        Row header = sheet.createRow(this.rownum);
        this.rownum+=1;
        for(int i = 0;i<headers.length;i++){
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    public void append(int id,BookEntity entity){
        Row row = sheet.createRow(this.rownum);
        this.rownum+=1;
        row.createCell(0).setCellValue(id);
        row.createCell(1).setCellValue(entity.getTitle());
        row.createCell(2).setCellValue(entity.getRating());
        row.createCell(3).setCellValue(entity.getRatingnum());
        row.createCell(4).setCellValue(entity.getAuthor());
        row.createCell(5).setCellValue(entity.getPublication());
        row.createCell(6).setCellValue(entity.getDate());
        row.createCell(7).setCellValue(entity.getPrice());
    }

    public void write() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.filename);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }


}
