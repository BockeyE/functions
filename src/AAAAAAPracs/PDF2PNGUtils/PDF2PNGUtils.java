package AAAAAAPracs.PDF2PNGUtils;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bockey
 */
public class PDF2PNGUtils {

    public static void pdf2png(String fileAddress, String filename, String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress + "\\" + filename + ".pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 自由确定起始页和终止页
     *
     * @param fileAddress  文件地址
     * @param filename     pdf文件名
     * @param indexOfStart 开始页  开始转换的页码，从0开始
     * @param indexOfEnd   结束页  停止转换的页码，-1为全部
     * @param type         图片类型
     */
    public static void pdf2png(String fileAddress, String filename, int indexOfStart, int indexOfEnd, String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress + "\\" + filename + ".pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = indexOfStart; i < indexOfEnd; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pdf2pngBytes(byte[] bytes, OutputStream outputStream) {
        // 将pdf装图片 并且自定义图片得格式大小
        try {
            PDDocument doc = PDDocument.load(bytes);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            System.out.println("pdf pages" + pageCount);
            if (pageCount > 1) {
                List<BufferedImage> piclist=new ArrayList<>();
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                    piclist.add(image);
                }
                joinPic(piclist,outputStream);
            }else{
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                    // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                    ImageIO.write(image, "png", outputStream);
                }
            }
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void joinPic(List<BufferedImage> piclist, OutputStream outputStream) {// 纵向处理图片  
        if (piclist == null || piclist.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            int height = 0;// 总高度  
            int width = 0;// 总宽度  
            int _height = 0;// 临时的高度 , 或保存偏移高度  
            int __height = 0; // 临时的高度，主要保存每个高度  
            int picNum = piclist.size();// 图片的数量  
            int[] heightArray = new int[picNum]; // 保存每个文件的高度  
            BufferedImage buffer = null; // 保存图片流  
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB  
            int[] _imgRGB; // 保存一张图片中的RGB数据  
            for (int i = 0; i < picNum; i++) {
                buffer = piclist.get(i);
                heightArray[i] = _height = buffer.getHeight();// 图片高度  
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度  
                }
                height += _height; // 获取总高度  
                _imgRGB = new int[width * _height];// 从图片中读取RGB  
                _imgRGB = buffer
                        .getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0  
            // 生成新图片  
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0)
                    _height += __height; // 计算偏移高度  
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i),
                        0, width); // 写入流中
            }
            ImageIO.write(imageResult, "png", outputStream);// 写图片  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
