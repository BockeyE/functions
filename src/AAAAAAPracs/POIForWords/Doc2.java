package AAAAAAPracs.POIForWords;


import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 */
public class Doc2 {
      //table.addNewRowBetween 没实现，官网文档也说明，只有函数名，但没具体实现，但很多文章还介绍如何使用这个函数，真是害人
             //table.insertNewTableRow 本文用这个可以，但是要创建 cell，否则不显示数据
            //table.addRow() 在表格最后加一行
              // table.addRow(XWPFTableRow row, int pos) 没试过，你可以试试。
             //table.createRow() 在表格最后一加行

    public static void writeTblWithImageToDocx_2() {
        BufferedReader in = null;
        XWPFDocument  temp = null;
        BufferedOutputStream out = null;
        File tempDoc = new File("d:\\test\\test11.docx");
        try {
            //in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\test\\2.doc"), "ISO8859_1"));
            temp = new XWPFDocument (new BufferedInputStream(new FileInputStream(tempDoc)));

            out = new BufferedOutputStream(new FileOutputStream("D:\\test\\test_2.docx"));
            XWPFParagraph p = temp.getParagraphArray(0);
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run = p.insertNewRun(0);

            //表格生成 6行5列.
            int rows = 6;
            int cols = 5;
            XmlCursor cursor = p.getCTP().newCursor();
            XWPFTable tableOne = temp.insertNewTbl(cursor);

            //样式控制
            CTTbl ttbl = tableOne.getCTTbl();
            CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
            CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
            CTJc cTJc = tblPr.addNewJc();
            cTJc.setVal(STJc.Enum.forString("center"));//表格居中
            tblWidth.setW(new BigInteger("8000"));//每个表格宽度
            tblWidth.setType(STTblWidth.DXA);

            //表格创建
            XWPFTableRow tableRowTitle = tableOne.getRow(0);
            tableRowTitle.setHeight(380);

            tableRowTitle.getCell(0).setText("标题");
            tableRowTitle.addNewTableCell().setText("内容");
            tableRowTitle.addNewTableCell().setText("姓名");
            tableRowTitle.addNewTableCell().setText("日期");
            tableRowTitle.addNewTableCell().setText("备注");
            for (int i = 1; i < rows; i++) {
                XWPFTableRow createRow = tableOne.createRow();
                for (int j = 0; j < cols; j++) {
                    createRow.getCell(j).setText("我是第"+i+"行,第"+(j+1)+"列");
                }
            }

            //插入图片测试
            XWPFTableRow rowTest = tableOne.getRow(0);

            XWPFTableCell imageCell = rowTest.getCell(0);
            List<XWPFParagraph> paragraphs = imageCell.getParagraphs();
            XWPFParagraph newPara = paragraphs.get(0);
            XWPFRun imageCellRunn = newPara.createRun();
            String id = temp.addPictureData(new FileInputStream("d:/test/1.png"), XWPFDocument.PICTURE_TYPE_PNG);//添加图片数据

            int id2=temp.getAllPackagePictures().size()+1;

            CTInline ctinline=imageCellRunn.getCTR().addNewDrawing().addNewInline();//设置段落行
//            temp.createPic(id,id2, 259, 259,ctinline);//添加图片

            mergeCellsHorizontal(tableOne,0,0,1);//WPS不支持跨列
            mergeCellsVertically(tableOne,1,1,2);
            run.addBreak();
            temp.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
            // tempDoc.deleteOnExit();
        }

        System.out.println("写入完成。。。。。。。。。。。。。。");
    }

    // word跨列合并单元格
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    // word跨行并单元格
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }







    public static InputStream replaceTextToIps(InputStream inputStream, Map<String, Object> contentMap) {
        XWPFDocument document = replaceTextToXWPF(inputStream, contentMap);
        return XWPFDocumentToIps(document);
    }

    public static XWPFDocument replaceTextToXWPF(InputStream inputStream, Map<String, Object> contentMap) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //处理段落
        List<XWPFParagraph> paragraphList = document.getParagraphs();
        processParagraphs(paragraphList, contentMap);

        //处理表格
        Iterator<XWPFTable> it = document.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                    processParagraphs(paragraphListTable, contentMap);
                }
            }
        }
        return document;
    }

    public static InputStream XWPFDocumentToIps(XWPFDocument document) {
        InputStream ips = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            ips = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ips;
    }

    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = "${" + entry.getKey() + "}";
                            if (text.contains(key)) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        File target = new File("设计变更联系单.docx");
        FileInputStream fis = new FileInputStream(target);
        HashMap m = new HashMap();
        m.put("projName", "项目A");
        m.put("projNum", "PRJ-A");
        m.put("designDepartment", "设计单位");
        m.put("changeCause", "因为我们觉得需要改");
        m.put("changeAdvice", "就这么改");
        m.put("inCharge", "因经理");
        m.put("department", "设计单位x");
        m.put("changeDate", "2012-12-12");
        m.put("changeCause1", "审批原因x");
        m.put("changeAdvice1", "我方意见A");
        m.put("changeAdvice2", "我方意见B");
        m.put("deny1", "不同意");
        m.put("engineer1", "工程师张");
        m.put("changeDate1", "2012-11-11");
        m.put("deny2", "也不同意");
        m.put("engineer2", "工程师xx");
        m.put("changeDate2", "2002-02-02");
        XWPFDocument document = DocxUtil.replaceTextToXWPF(fis, m);
        InputStream ips = DocxUtil.XWPFDocumentToIps(document);
        FileOutputStream fos=new FileOutputStream("tar.docx");
        byte[] buf=new byte[1024];
        while (ips.read(buf)!=-1){
            fos.write(buf);
            fos.flush();
        }
        fos.close();
        ips.close();
    }
}
