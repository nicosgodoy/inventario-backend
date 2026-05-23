package com.company.inventario.util;

import com.company.inventario.model.Product;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ProductExcelExport {

    private XSSFWorkbook workbook;

    private XSSFSheet sheet;
    private List<Product> product;

    public ProductExcelExport(List<Product> products) {
        this.product = products;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Resultado");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setFont(font);

        createCell(row,0,"Id",style);
        createCell(row,1,"Nombre",style);
        createCell(row,2,"Precio",style);
        createCell(row,3,"Cantidad",style);
        createCell(row,4,"Categoria",style);


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {

        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if(value instanceof Integer){
            cell.setCellValue((Integer) value);

        } else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);

        } else if(value instanceof BigDecimal){
            cell.setCellValue(((BigDecimal) value).doubleValue());

        } else {
            cell.setCellValue(String.valueOf(value));
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowcount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Product result: product){
            Row row = sheet.createRow(rowcount++);
            int columnCount = 0;
            createCell(row,0,String.valueOf(result.getId()),style);
            createCell(row,1,result.getName(),style);
            createCell(row,2,result.getPrice(),style);
            createCell(row,3,result.getAccount(),style);
            createCell(row,4,result.getCategory().getName(),style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {


        writeHeaderLine(); //write the header
        writeDataLines(); //weite the data

        ServletOutputStream servletOutputStream = response.getOutputStream();
        workbook.write(servletOutputStream);
        workbook.close();

        servletOutputStream.close();
    }
}
