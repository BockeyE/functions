package AAAAAAPracs.POIForWords;

/**
 * @author bockey
 */
public class Docs {
//    public void exportBg(OutputStream out) {
//        String srcPath = "D:/tp/fx.docx";
//        String targetPath = "D:/tp/fx2.docx";
//        String key = "$key";// 在文档中需要替换插入表格的位置
//        XWPFDocument doc = null;
//        File targetFile = null;
//        try {
//            doc = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
//            List<XWPFParagraph> paragraphList = doc.getParagraphs();
//            if (paragraphList != null && paragraphList.size() > 0) {
//                for (XWPFParagraph paragraph : paragraphList) {
//                    List<XWPFRun> runs = paragraph.getRuns();
//                    for (int i = 0; i < runs.size(); i++) {
//                        String text = runs.get(i).getText(0).trim();
//                        if (text != null) {
//                            if (text.indexOf(key) >= 0) {
//                                runs.get(i).setText(text.replace(key, ""), 0);
//                                XmlCursor cursor = paragraph.getCTP().newCursor();
//                                // 在指定游标位置插入表格
//                                XWPFTable table = doc.insertNewTbl(cursor);
//
//                                CTTblPr tablePr = table.getCTTbl().getTblPr();
//                                CTTblWidth width = tablePr.addNewTblW();
//                                width.setW(BigInteger.valueOf(8500));
//
//                                this.inserInfo(table);
//
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//
//            doc.write(out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            throw new SysException(ERRORConstants.COMMON_SYSTEM_ERROR, e);
//        }
//    }
//
//    /**
//     * 把信息插入表格
//     * @param table
//     * @param data
//     */
//    private void inserInfo(XWPFTable table) {
//        List<DTO> data = mapper.getInfo();//需要插入的数据
//        XWPFTableRow row = table.getRow(0);
//        XWPFTableCell cell = null;
//        for (int col = 1; col < 6; col++) {//默认会创建一列，即从第2列开始
//            // 第一行创建了多少列，后续增加的行自动增加列
//            CTTcPr cPr =row.createCell().getCTTc().addNewTcPr();
//            CTTblWidth width = cPr.addNewTcW();
//            if(col==1||col==2||col==4){
//                width.setW(BigInteger.valueOf(2000));
//            }
//        }
//        row.getCell(0).setText("指标");
//        row.getCell(1).setText("指标说明");
//        row.getCell(2).setText("公式");
//        row.getCell(3).setText("参考值");
//        row.getCell(4).setText("说明");
//        row.getCell(5).setText("计算值");
//        for(DTO item : data){
//            row = table.createRow();
//            row.getCell(0).setText(item.getZbmc());
//            cell = row.getCell(1);
//            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
//            cell.setText(item.getZbsm());
//            cell = row.getCell(2);
//            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
//            cell.setText(item.getJsgs());
//            if(item.getCkz()!=null&&!item.getCkz().contains("$")){
//                row.getCell(3).setText(item.getCkz());
//            }
//            cell = row.getCell(4);
//            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
//            cell.setText(item.getSm());
//            row.getCell(5).setText(item.getJsjg()==null?"无法计算":item.getJsjg());
//        }
//    }

}

