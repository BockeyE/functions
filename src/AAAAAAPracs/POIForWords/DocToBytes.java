package AAAAAAPracs.POIForWords;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * @author bockey
 */
public class DocToBytes {
    public static void main(String[] args) throws Exception {

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(22);
        run.setText("The paragraph content ...");
        paragraph = document.createParagraph();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        out.close();
        document.close();

        byte[] xwpfDocumentBytes = out.toByteArray();
        // do something with the byte array
        System.out.println(xwpfDocumentBytes);

        // to prove that the byte array really contains the XWPFDocument
        try (FileOutputStream stream = new FileOutputStream("./XWPFDocument.docx")) {
            stream.write(xwpfDocumentBytes);
        }

    }
}
