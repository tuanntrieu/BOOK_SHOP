package com.example.projectbase.util;
import com.example.projectbase.domain.entity.Bill;
import com.example.projectbase.domain.entity.BillDetail;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExportUtil {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Bill> bills;

    public ExcelExportUtil(List<Bill> bills){
        this.bills=bills;
        workbook=new XSSFWorkbook();
    }
    private void createCells(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount,true);
        Cell cell=row.createCell(columnCount);
        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if(value instanceof Double){
            cell.setCellValue((Double)value);}
            else if(value instanceof Float){
                cell.setCellValue((Float)value);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }
        else if(value instanceof Long){
            cell.setCellValue((Long)value);
        }

        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void createHeaderRow(){
        sheet=workbook.createSheet("Bill statistics");
        Row row= sheet.createRow(0);
        CellStyle style= workbook.createCellStyle();
        XSSFFont font=new XSSFFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCells(row,0,"Bill statistics",style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,12));
        font.setFontHeightInPoints((short) 10);

        row= sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight((short)16);
        style.setFont(font);
        createCells(row,0,"Mã đơn hàng",style);
        createCells(row,1,"Ngày tạo",style);
        createCells(row,2,"Tên khách hàng",style);
        createCells(row,3,"Số điện thoại",style);
        createCells(row,4,"Địa chỉ nhận hàng",style);
        createCells(row,5,"Trạng thái",style);
        createCells(row,6,"Phí ship",style);
        font.setItalic(true);
        style.setFont(font);
        createCells(row,7,"Tên sản phẩm",style);
        createCells(row,8,"Số lượng",style);
        createCells(row,9,"Đơn giá",style);
        createCells(row,10,"Giảm giá",style);
        createCells(row,11,"Thành tiền",style);
        createCells(row,12,"Tổng tiền",style);

    }
    private void writeData(){
        int rowCount=2;
        CellStyle style1=workbook.createCellStyle();
        CellStyle style2= workbook.createCellStyle();
        CellStyle style3= workbook.createCellStyle();
        XSSFFont font= workbook.createFont();
        XSSFFont font2= workbook.createFont();
        font.setFontHeight(14);
        style1.setFont(font);
        font.setItalic(true);
        style2.setFont(font);
        font.setFontHeight(14);
        font2.setFontHeight(14);
        font2.setBold(true);
        font2.setColor(IndexedColors.RED.getIndex());
        style3.setFont(font2);
        for(Bill bill:bills){
            Row row= sheet.createRow(rowCount++);
            int columnCount=0;
            createCells(row,columnCount++,bill.getId(),style1);
            createCells(row,columnCount++,bill.getCreatedDate().toString(),style1);
            createCells(row,columnCount++,bill.getCustomer().getName(),style1);
            createCells(row,columnCount++,bill.getCustomer().getPhonenumber(),style1);
            createCells(row,columnCount++,bill.getCustomer().getAddress(),style1);
            createCells(row,columnCount++,bill.getStatus(),style1);
            createCells(row,columnCount++,bill.getFeeShip(),style1);


            if(bill.getBillDetail().size()!=1)
            sheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount+bill.getBillDetail().size()-2,12,12));
            createCells(row,12,bill.getTotal(),style3);
            for (BillDetail bt:bill.getBillDetail()){
                int columnCount1=columnCount;
                createCells(row,columnCount1++,bt.getProduct().getName(),style2);
                createCells(row,columnCount1++,bt.getQuantity(), style2);
                createCells(row,columnCount1++,bt.getProduct().getPrice(), style2);
                createCells(row,columnCount1++,bt.getProduct().getDiscount(),style2);
                createCells(row,columnCount1++,bt.getQuantity()*(bt.getProduct().getPrice()-bt.getProduct().getPrice()*bt.getProduct().getDiscount()/100),style2);
                row= sheet.createRow(rowCount++);
            }
        }
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeData();
        ServletOutputStream outputStream= response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
