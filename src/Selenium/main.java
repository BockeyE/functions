package Selenium;


//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.util.Scanner;

/**
 * @author bockey
 */
public class main {
    static String filepath = "C:\\auto\\AutoFill.xls";

    public static void main(String[] args) throws Exception {
        main m = new main();
        Sheet sheet = m.getLinesFromPoi(new File(filepath));
        System.out.println(sheet.getRow(1).getCell(1).getStringCellValue());
        sheet.getLastRowNum();

        m.mainAction(sheet);
    }

    public int getRowslenth(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        return lastRowNum - firstRowNum;
    }

    public void startAction() {

    }

    //    public Sheet getLinesFromExcel(File file) throws IOException, BiffException {
//        InputStream ips = new FileInputStream(file.getAbsolutePath());
//        Workbook wb = Workbook.getWorkbook(file);
//        Sheet sheet = wb.getSheet(0);
//        return sheet;
//
//    }
    public Sheet getLinesFromPoi(File file) throws Exception {
        Workbook wb = WorkbookFactory.create(file);
        return wb.getSheetAt(0);
    }


    public String readRow(Sheet sheet, int colIndex, int rowIndex) {
        Row row = sheet.getRow(colIndex);
        Cell cell = row.getCell(rowIndex);
        if (cell.getCellTypeEnum().toString().equals("NUMERIC")) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }

    public void mainAction(Sheet sheet) throws InterruptedException, IOException {
        ChromeDriver page = new ChromeDriver();
        page.get("https://www.zhongdengwang.org.cn/");
        String title = page.getTitle();
        System.out.println(("网页title： " + title));
        String www = page.getCurrentUrl();
        System.out.println(("访问的地址： " + www));
        String name = "";
        String pasw = "";
        System.out.println("请在看到此提示后，在命令窗口正确输入验证码，并回车；输入失败时请重启程序");
        Scanner sc = new Scanner(System.in);
        page.switchTo().frame(page.findElementByXPath("/html/body/div[2]/div[1]/div[2]/iframe"));
        while (true) {
            page.findElementById("userCode").sendKeys(name);
            page.findElementById("showpassword").click();
            page.findElementById("password").sendKeys(pasw);
            System.out.println("--请输入图片验证码： --------------");
            String vali = sc.nextLine();
            System.out.println("您输入的验证码为： " + vali);
            page.findElementById("validateCode").sendKeys(vali);
            page.findElementById("login_btn").click();
            try {
                WebElement check_info = page.findElementByXPath("/html/body/div[2]/div[1]/div[2]/iframe");
                if (check_info == null) {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("验证成功，继续执行");
        page.switchTo().defaultContent();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i < lastRowNum; i++) {
            String fillingNumber = readRow(sheet, i, 1);
            String transfer = readRow(sheet, i, 0);
            String registrationPeriod = readRow(sheet, i, 11);
            String representative = readRow(sheet, i, 2);
            String scale = readRow(sheet, i, 4);
            String province = readRow(sheet, i, 5);
            String city = readRow(sheet, i, 6);
            String address = readRow(sheet, i, 7);
            String orgCode = readRow(sheet, i, 8);
            String contractNo = readRow(sheet, i, 9);
            String totalAssets = readRow(sheet, i, 10);
            String assetsDescription = readRow(sheet, i, 12);
            String sector = readRow(sheet, i, 3);
            String country = "中国";
            String contractCurrency = "人民币";
            if (!sector.endsWith("业")) {
                sector = sector + "业";
            }
            System.out.println("-----当执行条目： " + transfer + "-----");
//            点击初始登记
            page.findElementByXPath("/html/body/div[2]/table[6]/tbody/tr[1]/td[1]/table/tbody/tr[2]/td/table/tbody/tr[1]/td/a").click();
            page.findElementByXPath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr[2]/td/table/tbody/tr[2]/td").click();
            page.findElementByXPath("/html/body/div[2]/table[3]/tbody/tr[1]/td/input[1]").click();
            page.findElementByXPath("//*[@id=\"next\"]").click();
//            选择期限
            Select period = new Select(page.findElementByXPath("//*[@id=\"timelimit\"]"));
            period.selectByVisibleText(registrationPeriod);
//            填表人归档号
            page.findElementById("title").sendKeys(fillingNumber);
            page.findElementByXPath("/html/body/div[2]/form/div/input[1]").click();
            Thread.currentThread().sleep(200);
//            点击 增加出让人
            page.findElementByXPath("//*[@id=\"addDebtor\"]").click();
            Select type = new Select(page.findElementByXPath("//*[@id=\"debtorType\"]"));
            type.selectByIndex(2);
//            填写各项出让人信息
            page.findElementByXPath("//*[@id=\"debtorName\"]").sendKeys(transfer);
            page.findElementByXPath("//*[@id=\"orgCode\"]").sendKeys(orgCode);
            page.findElementByXPath("//*[@id=\"businessCode\"]").sendKeys(orgCode);
//            page.findElementByXPath("//*[@id=\"lei\"]").sendKeys();
            page.findElementByXPath("//*[@id=\"responsiblePerson\"]").sendKeys(representative);
            new Select(page.findElementByXPath("//*[@id=\"industryCode\"]")).selectByVisibleText(sector);
            new Select(page.findElementByXPath("//*[@id=\"scale\"]")).selectByVisibleText(scale);
            new Select(page.findElementByXPath("//*[@id=\"country\"]")).selectByVisibleText(country);
            new Select(page.findElementByXPath("//*[@id=\"province\"]")).selectByVisibleText(province);
            new Select(page.findElementByXPath("//*[@id=\"city\"]")).selectByVisibleText(city);
            page.findElementByXPath("//*[@id=\"address\"]").sendKeys(address);
            page.findElementByXPath("//*[@id=\"saveButton\"]").click();
//            增加受让人信息
            page.findElementByXPath("//*[@id=\"secondName\"]").click();
            page.findElementByXPath("//*[@id=\"addDebtorAuto\"]").click();
//            跳转到转让财产信息
            page.findElementByXPath("//*[@id=\"typeName\"]").click();
            page.findElementByXPath("//*[@id=\"maincontractno\"]").sendKeys(contractNo);
            new Select(page.findElementByXPath("//*[@id=\"maincontractcurrency\"]")).selectByVisibleText(contractCurrency);
            page.findElementByXPath("//*[@id=\"maincontractsum\"]").sendKeys(totalAssets);
            page.findElementByXPath("//*[@id=\"description\"]").sendKeys(assetsDescription);
            page.manage().window().maximize();
            page.manage().window().setSize(new Dimension(1920, 1080));
            File screenshotAs = page.getScreenshotAs(OutputType.FILE);
            String picName = "C:\\auto\\pics\\" + fillingNumber + "3.png";
            FileUtils.copyFile(screenshotAs, new File(picName));
        }


//        #点击这里上传附件
//        #page.find_element_by_xpath('//*[@id="attachinfo"]').click()
//        #点击保存按钮
//        #page.find_element_by_xpath('/html/body/div[2]/table/tbody/tr/td/table[2]/tbody/tr/td/input').click()
//        #点击预览按钮
//        #page.find_element_by_xpath('//*[@id="previewbutton"]').click()
//        print("请在键盘输入任意字符，继续执行excel中待登记记录:")
//        valix = raw_input()
//        #返回主页
//        # #点击返回主页按钮
//        page.find_element_by_xpath('//*[@id="previewbutton"]').click()
//        Alert(page).accept()
//        page.find_element_by_xpath('/html/body/div[2]/table[18]/tbody/tr/td/input[4]').click()
//        Alert(page).accept()
//    # #点击alert确认
//    #Alert(page).accept();
//
//    #b.close()  #关闭当前窗口
//    #b.quit()  #关闭浏览器
//
//        print('已经全部执行完毕，退出程序')


    }


}
