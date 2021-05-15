package com.ngx.boot.excelOperation;

import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.bean.StuScore;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreateExcel {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";


    public static void writeStuInfo(List<StuInfo> list, String fileName) {
        // 第一步,创建一个workbook文件,对应一个excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步,在excel中添加一个sheet工作簿,参数为该工作簿名字,不写为默认;
        XSSFSheet sheet = wb.createSheet();
        // 第四步,设置单元格样式
//        XSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);//创建一个居中格式
        // 创建表头
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell column0 = row0.createCell(0);
        column0.setCellValue("学号");
        XSSFCell column1 = row0.createCell(1);
        column1.setCellValue("姓名");
        XSSFCell column2 = row0.createCell(2);
        column2.setCellValue("年级");
        XSSFCell column3 = row0.createCell(3);
        column3.setCellValue("性别");
        XSSFCell column4 = row0.createCell(4);
        column4.setCellValue("专业");
        // 第六步,写入实体数据 实际应用中这些数据应该是从数据库中得到
        for (int i = 0; i < list.size(); i++) {
            XSSFRow row = sheet.createRow(i+1);
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(list.get(i).getStuNo());
            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(list.get(i).getStuName());
            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(list.get(i).getStuGrade());
            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue(list.get(i).getStuSex());
            XSSFCell cell5 = row.createCell(4);
            cell5.setCellValue(list.get(i).getStuMajor());
        }
        create(wb,fileName);
    }



    public static void writeStuScore(List<StuScore> list, String fileName) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        // 第一步,创建一个workbook文件,对应一个excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步,在excel中添加一个sheet工作簿,参数为该工作簿名字,不写为默认;
        XSSFSheet sheet = wb.createSheet();
        // 第四步,设置单元格样式
//        XSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);//创建一个居中格式
        // 创建表头
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell column0 = row0.createCell(0);
        column0.setCellValue("学号");
        XSSFCell column1 = row0.createCell(1);
        column1.setCellValue("姓名");
        XSSFCell column2 = row0.createCell(2);
        column2.setCellValue("年级");
        XSSFCell column3 = row0.createCell(3);
        column3.setCellValue("性别");
        XSSFCell column4 = row0.createCell(4);
        column4.setCellValue("专业");
        XSSFCell column5 = row0.createCell(5);
        column5.setCellValue("科目一");
        XSSFCell column6 = row0.createCell(6);
        column6.setCellValue("科目二");
        XSSFCell column7 = row0.createCell(7);
        column7.setCellValue("科目三");
        XSSFCell column8 = row0.createCell(8);
        column8.setCellValue("科目一成绩");
        XSSFCell column9 = row0.createCell(9);
        column9.setCellValue("科目二成绩");
        XSSFCell column10 = row0.createCell(10);
        column10.setCellValue("科目三成绩");
        XSSFCell column11 = row0.createCell(11);
        column11.setCellValue("年份");
        XSSFCell column12 = row0.createCell(12);
        column12.setCellValue("学期数");


        // 第六步,写入实体数据 实际应用中这些数据应该是从数据库中得到
        for (int i = 0; i < list.size(); i++) {
            XSSFRow row = sheet.createRow(i+1);
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(list.get(i).getStuNo());
            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(list.get(i).getStuName());
            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(list.get(i).getStuGrade());
            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue(list.get(i).getStuSex());
            XSSFCell cell5 = row.createCell(4);
            cell5.setCellValue(list.get(i).getStuMajor());

            XSSFCell cell6 = row.createCell(5);
            cell6.setCellValue(list.get(i).getOneCourse());
            XSSFCell cell7 = row.createCell(6);
            cell7.setCellValue(list.get(i).getTwoCourse());
            XSSFCell cell8 = row.createCell(7);
            cell8.setCellValue(list.get(i).getThreeCourse());

            XSSFCell cell9 = row.createCell(8);
            cell9.setCellValue(list.get(i).getOneScore());
            XSSFCell cell10 = row.createCell(9);
            cell10.setCellValue(list.get(i).getTwoScore());
            XSSFCell cell11 = row.createCell(10);
            cell11.setCellValue(list.get(i).getThreeScore());
            XSSFCell cell12 = row.createCell(11);
            cell12.setCellValue(list.get(i).getStuYear());
            XSSFCell cell13 = row.createCell(12);
            cell13.setCellValue(list.get(i).getStuTerm());
        }
        create(wb,fileName);
    }

    public static void create(XSSFWorkbook wb,String fileName){
        OutputStream out = null;
        //写入文件
        try {
            out = new FileOutputStream(fileName);
            wb.write(out);
            System.out.println("写入完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 判断excel的版本，获取workBook工作表
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }


    public static void checkExcelValid(File file) throws Exception {
        if (file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!((file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

}
