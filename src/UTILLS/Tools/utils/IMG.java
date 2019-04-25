package UTILLS.Tools.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * 图片处理工具类
 * 
 * @author PanJun
 *
 */
public class IMG {

    /** 图片缩放 */
    public static void scale(File sourceFile, File targetFile, int maxWidth, int maxHeight) throws IOException {
        String ext = "";
        int i = targetFile.getName().lastIndexOf(".");
        if (i > 0) {
            ext = targetFile.getName().substring(i + 1).toLowerCase();
        } else {
            throw new RuntimeException("The targetFile(" + targetFile.getName() + ") is not a image file name");
        }
        scale(new FileInputStream(sourceFile), new FileOutputStream(targetFile), ext, maxWidth, maxHeight);
    }

    /** 图片缩放 */
    public static void scale(String sourceFile, String targetFile, int maxWidth, int maxHeight) throws IOException {
        scale(new File(sourceFile), new File(targetFile), maxWidth, maxHeight);
    }

    /** 图片缩放 */
    public static void scale(InputStream sourceImg, OutputStream targetImg, String targetExt, int maxWidth,
            int maxHeight) throws IOException {
        BufferedImage source = ImageIO.read(sourceImg);
        int w = source.getWidth();
        int h = source.getHeight();
        double rate = Math.min(maxHeight * 1.0 / h, maxWidth * 1.0 / w);
        int nw = (int) (w * rate);
        int nh = (int) (h * rate);

        BufferedImage target = null;
        if (source.getColorModel().hasAlpha()) {
            target = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_ARGB);
        } else {
            target = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g = target.createGraphics();
        try {
            Image image = source.getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
            g.drawImage(image, 0, 0, null);
        } finally {
            g.dispose();
        }

        try {
            ImageIO.write(target, targetExt, targetImg);
        } finally {
            targetImg.close();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedImage bi = ImageIO.read(new File("d:/1.jpg"));
        gradientImage(bi, "lr", 250, 255, 255, 255);
        ImageIO.write(bi, "png", new File("d:/3.jpg"));
    }

    /**
     * 图片左右渐进
     * 
     * @param bi
     * @param lr
     *            包含"L"表示左边渐进，包括"R"表示右边渐进
     * @param bg
     *            背景色
     */
    public static BufferedImage gradientImage(BufferedImage bi, String lr, int gradientLen, Color bg) {
        return gradientImage(bi, lr, gradientLen, bg.getRed(), bg.getGreen(), bg.getBlue());
    }

    /**
     * 图片左右渐进
     * 
     * @param bi
     * @param lr
     *            包含"L"表示左边渐进，包括"R"表示右边渐进
     * @param bgR
     *            背景色RGB中R值
     * @param bgG
     *            背景色RGB中G值
     * @param bgB
     *            背景色RGB中B值
     */
    public static BufferedImage gradientImage(BufferedImage bi, String lr, int gradientLen, int bgR, int bgG, int bgB) {
        lr = lr == null ? "" : lr.toUpperCase();
        final int w = bi.getWidth();
        final int h = bi.getHeight();
        int len = gradientLen;// 渐进长度
        if (len < 200) {
            len = 200;
        }

        Set<IntFunction<Integer>> xcalcSet = new HashSet<>();
        if (lr.contains("L")) {
            xcalcSet.add((int x) -> {
                return x;
            });
        }
        if (lr.contains("R")) {
            xcalcSet.add((int x) -> {
                return w - x - 1;
            });
        }

        boolean hasAlpha = bi.getColorModel().hasAlpha();
        for (IntFunction<Integer> xcalc : xcalcSet) {
            for (int walkX = 0, maxX = Math.min(len, w); walkX < maxX; walkX++) {
                double a = 1.0 - walkX * 1.0 / maxX;
                for (int y = 0; y < h; y++) {
                    int x = xcalc.apply(walkX);
                    int pixel = bi.getRGB(x, y);

                    int aa = hasAlpha ? (pixel >> 24) & 0xff : 0;
                    int r = ((pixel & 0xff0000) >> 16);
                    int g = ((pixel & 0xff00) >> 8);
                    int b = (pixel & 0xff);
                    r = limRgb((1 - a) * r + a * bgR);
                    g = limRgb((1 - a) * g + a * bgG);
                    b = limRgb((1 - a) * b + a * bgB);
                    bi.setRGB(x, y, aa << 24 | r << 16 | g << 8 | b);
                }
            }
        }

        return bi;
    }

    public static int limRgb(double v) {
        int ret = (int) v;
        if (ret < 0) {
            ret = 0;
        }
        if (ret > 255) {
            ret = 255;
        }
        return ret;
    }

    public static String toHtmlRgb(int v) {
        if (v < 0) {
            v = 0;
        }
        if (v > 255) {
            v = 255;
        }
        String ret = Integer.toHexString(v);
        if (ret.length() < 2) {
            ret = '0' + ret;
        }
        return ret;
    }

    public static String toHtmlColor(int r, int g, int b) {
        return "#" + toHtmlRgb(r) + toHtmlRgb(g) + toHtmlRgb(b);
    }

    /**
     * 从html颜色转换成AWT颜色
     * 
     * @param htmlColor
     * @return
     */
    public static Color fromHtmlColor(String htmlColor) {
        if (STR.isBlank(htmlColor)) {
            return null;
        }
        if (htmlColor.startsWith("#")) {
            htmlColor = htmlColor.substring(1);
        }

        if (htmlColor.length() != 6) {
            return null;
        }

        Integer r = toInt16(htmlColor.substring(0, 2));
        Integer g = toInt16(htmlColor.substring(2, 4));
        Integer b = toInt16(htmlColor.substring(4, 6));
        if (r == null || g == null || b == null) {
            return null;
        }
        return new Color(r, g, b);
    }

    private static Integer toInt16(String s) {
        try {
            return Integer.parseInt(s, 16);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 图片旋转
     * 
     * @param image
     *            原始图片
     * @param degree
     *            旋转角度
     * @param bgcolor
     *            背景颜色
     * @return
     * @throws IOException
     */
    public static InputStream rotateImg(BufferedImage image, int degree, Color bgcolor) throws IOException {

        int iw = image.getWidth();// 原始图象的宽度
        int ih = image.getHeight();// 原始图象的高度
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;// 将角度转换到0-360度之间
        double ang = Math.toRadians(degree);// 将角度转为弧度

        /**
         * 确定旋转后的图象的高度和宽度
         */

        if (degree == 180 || degree == 0 || degree == 360) {
            w = iw;
            h = ih;
        } else if (degree == 90 || degree == 270) {
            w = ih;
            h = iw;
        } else {
            // int d = iw + ih;
            // w = (int) (d * Math.abs(Math.cos(ang)));
            // h = (int) (d * Math.abs(Math.sin(ang)));
            double cosVal = Math.abs(Math.cos(ang));
            double sinVal = Math.abs(Math.sin(ang));
            w = (int) (sinVal * ih) + (int) (cosVal * iw);
            h = (int) (sinVal * iw) + (int) (cosVal * ih);
        }

        x = (w / 2) - (iw / 2);// 确定原点坐标
        y = (h / 2) - (ih / 2);
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
        if (bgcolor == null) {
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        } else {
            gs.setColor(bgcolor);
            gs.fillRect(0, 0, w, h);// 以给定颜色绘制旋转后图片的背景
        }

        AffineTransform at = new AffineTransform();
        at.rotate(ang, w / 2, h / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(image, rotatedImage);
        image = rotatedImage;

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut);

        ImageIO.write(image, "png", iamgeOut);
        InputStream inputStream = new ByteArrayInputStream(byteOut.toByteArray());

        return inputStream;
    }

}
