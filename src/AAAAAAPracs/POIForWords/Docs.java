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









    //XWPFParagraph 段落属性
//paragraphX.addRun(runX0);//似乎并没有什么卵用
//paragraphX.removeRun(1);//按数组下标删除run(文本)
//paragraphX.setAlignment(ParagraphAlignment.LEFT);//对齐方式
//paragraphX.setBorderBetween(Borders.LIGHTNING_1);//边界 （但是我设置了好几个值都没有效果）
//paragraphX.setFirstLineIndent(100);//首行缩进：-----效果不详
//paragraphX.setFontAlignment(3);//字体对齐方式：1左对齐 2居中3右对齐
//paragraphX.setIndentationFirstLine(2);//首行缩进：-----效果不详
//paragraphX.setIndentationHanging(1);//指定缩进，从父段落的第一行删除，将第一行上的缩进移回到文本流方向的开头。-----效果不详
//paragraphX.setIndentationLeft(2);//-----效果不详
//paragraphX.setIndentationRight(2);//-----效果不详
//paragraphX.setIndentFromLeft(2);//-----效果不详
//paragraphX.setIndentFromRight(2);//-----效果不详
//paragraphX.setNumID(new BigInteger("3"));//设置段落编号-----有效果看不懂（仅仅是整段缩进4个字）
//paragraphX.setPageBreak(true);//段前分页
//paragraphX.setSpacingAfter(1);//指定文档中此段最后一行以绝对单位添加的间距。-----效果不详
//paragraphX.setSpacingBeforeLines(2);//指定在该行的第一行中添加行单位之前的间距-----效果不详
//paragraphX.setStyle("标题 3");//段落样式：需要结合addCustomHeadingStyle(docxDocument, "标题 3", 3)配合使用
//paragraphX.setVerticalAlignment(TextAlignment.BOTTOM);//文本对齐方式(我猜在table里面会有比较明显得到效果)
//paragraphX.setWordWrapped(true);//这个元素指定一个消费者是否应该突破拉丁语文本超过一行的文本范围，打破单词跨两行（打破字符水平）或移动到以下行字（打破字级）-----(我没看懂:填个false还报异常了)
}

