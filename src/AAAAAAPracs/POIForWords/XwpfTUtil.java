package AAAAAAPracs.POIForWords;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bockey
 */
public class XwpfTUtil {
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    public void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int start = -1;
            int end = -1;
            String str = "";
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString().trim();
                if (!"".equals(runText)) {
                    if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                        start = i;


                    }
                    if ((start != -1)) {
                        str += runText;
                    }
                    if ('}' == runText.charAt(runText.length() - 1)) {
                        if (start != -1) {
                            end = i;
                            break;
                        }
                    }
                }
            }
            if (start != -1) {
                for (int i = start; i < end + 1; i++) {
                    para.removeRun(start);
                }
                XWPFRun createRun = para.insertNewRun(start);
                for (String key : params.keySet()) {
                    if (str.equals(key)) {
                        createRun.setText((String) params.get(key));
                        createRun.setFontSize(16);
                        createRun.addBreak();
                        break;
                    }
                }
            }
        }
    }

    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    public void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
