package AAAAAAPracs.POIForWords;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DocxUtil {
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

        File target = new File("报关承诺函法务模板.docx");
        FileInputStream fis = new FileInputStream(target);
        HashMap m = new HashMap();
        m.put("entName", "xxxxx");
        XWPFDocument document = DocxUtil.replaceTextToXWPF(fis, m);
        InputStream ips = DocxUtil.XWPFDocumentToIps(document);
        FileOutputStream fos = new FileOutputStream("tar.docx");
        byte[] buf = new byte[1024];
        while (ips.read(buf) != -1) {
            fos.write(buf);
            fos.flush();
        }
        fos.close();
        ips.close();
    }
}
