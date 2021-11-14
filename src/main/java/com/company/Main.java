package com.company;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        System.out.print("Input institute (Example:ІКНІ): ");
        String institute = in.nextLine();

        System.out.print("Input group (Example:КН-405): ");
        String group = in.nextLine();

        System.setProperty("webdriver.opera.driver", "C://Users//marat//Desktop//selenium driver//drivers//operadriver_win64/operadriver.exe");

        OperaDriver driver=new OperaDriver();
        InputData urls = new InputData();
        urls.resultString(institute, group);

        driver.get(urls.resultString(institute, group));

        Workbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet("Schedule "+institute+" "+group);

        Row row =  s.createRow(0);
        row.createCell(0).setCellValue("ПН");
        row.createCell(1).setCellValue("ВТ");
        row.createCell(2).setCellValue("СР");
        row.createCell(3).setCellValue("ЧТ");
        row.createCell(4).setCellValue("ПТ");

        List<WebElement> contentList = driver.findElements(By.xpath("//div[@class='view-content']/div"));
        int i = 0;
        int j = 1;
        for(WebElement element:contentList){
            System.out.println("Section "+i+":");
            List<WebElement> dayList = contentList.get(i).findElements(By.cssSelector("div.stud_schedule"));
            for(WebElement element1:dayList){
                System.out.println(element1.getText());
                Row row1 = s.createRow(j);
                row1.createCell(i).setCellValue(element1.getText());
                j++;
            }
            i++;
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\marat\\Desktop\\Schedule.xls");
            wb.write(fileOut);
            fileOut.close();
        }
        catch (Exception e){
        }
    }
}
