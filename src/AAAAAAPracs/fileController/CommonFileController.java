//package AAAAAAPracs.fileController;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.URLEncoder;
//import java.util.Base64;
//import java.util.Calendar;
//import java.util.Map;
//
//@Controller
//public class CommonFileController {
//
//    @Reference(version = "1.0.0")
//    private CommonFileService fileService;
//
//    @Value("${file.fileFtpPath}")
//    private String fileFtpPath;
//    private static Logger logger = LoggerFactory.getLogger(MyFilterConfiguration.class);
//
//    /**
//     * 单文件上传
//     *
//     * @param file
//     * @return
//     */
//    @CrossOrigin
//    @RequestMapping(value = "/common/file/upload", headers = "api-version=1.0.0", method = RequestMethod.POST)
//    public Result fileUpload(MultipartFile file) throws IOException {
//        Result result = new Result();
//        logger.debug("文件上传开始，入参参数为：" + file);
//
//        // 获取原始名字
//        String origName = file.getOriginalFilename();
//        String fileName = file.getOriginalFilename();
//        // 获取后缀名
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        byte[] bytes = file.getBytes();
//
//        // 文件保存路径
//        String filePath = fileFtpPath + currentDir();
//        String hash = SHA256Utils.getSha256ByBytes(bytes);
//        fileName = hash + suffixName;
//        // 文件对象
//        String filurl = filePath + fileName;
//        File dest = new File(filurl);
//        // 判断路径是否存在，如果不存在则创建
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        try {
//            // 保存到服务器中
//            file.transferTo(dest);
//            fileService.insertOne(origName, filurl, hash);
//            result.setResltInfo("1", "上传成功", hash);
//        } catch (Exception e) {
//            logger.info("文件上传异常抛出，错误为：" + e);
//            result.setResltInfo("0", "上传失败", e.getMessage());
//            return result;
//        }
//        return result;
//    }
//
//    @CrossOrigin
//    @RequestMapping(value = "/common/file/upbase64", headers = "api-version=1.0.0", method = RequestMethod.POST)
//    public Result up64(@RequestBody Map file) {
//        String base64 = (String) file.get("file");
//        Result result = new Result();
//        String fileName = (String) file.get("fileName");
//        try {
//            byte[] bytes = Base64.getDecoder().decode(base64);
//            // 获取原始名字
//            String origName = fileName;
//            // 获取后缀名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//            // 文件保存路径
//            String filePath = fileFtpPath + currentDir();
//            String hash = SHA256Utils.getSha256ByBytes(bytes);
//            fileName = hash + suffixName;
//            // 文件对象
//            String filurl = filePath + fileName;
//            File dest = new File(filurl);
//            // 判断路径是否存在，如果不存在则创建
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }
//            FileOutputStream fos = new FileOutputStream(dest);
//            fos.write(bytes);
//            fos.flush();
//            fos.close();
//            fileService.insertOne(origName, filurl, hash);
//            result.setResltInfo("1", "上传成功", hash);
//        } catch (Exception e) {
//            logger.info("文件上传异常抛出，错误为：" + e);
//            result.setResltInfo("0", "上传失败", e.getMessage());
//            return result;
//        }
//        return result;
//    }
//
//
//    /**
//     * 通过md5值，预览文件
//     *
//     * @param response
//     * @return
//     * @throws IOException
//     */
//    @CrossOrigin
//    @GetMapping(value = "/common/file/view")
//    public Result previewFileByMd5(String hash, HttpServletResponse response) throws IOException {
//        Result result = new Result();
//        logger.debug("文件下载开始执行，入参参数为：" + hash);
//        try {
//            CommonFile byHash = fileService.getByHash(hash);
//            if (byHash == null) {
//                return Result.getErrorOne("未找到该文件");
//            }
//            byte[] filetobyte = filetobyte(new File(byHash.getFileUrl()));
//            ServletOutputStream outputStream = response.getOutputStream();
//            if (byHash.getFileUrl().endsWith("pdf")) {
//                PDF2PNGUtils.pdf2pngBytes(filetobyte, outputStream);
//            } else {
//                outputStream.write(filetobyte);
//            }
//            outputStream.close();
//            result.setResltInfo("1", "文件下载成功", "预览成功");
//        } catch (Exception e) {
//            logger.info("文件下载异常抛出，错误为：" + e);
//            result.setResltInfo("0", "文件下载失败", e.getMessage());
//            return result;
//        }
//        logger.debug("文件下载结束，出参参数为：" + result);
//        return result;
//    }
//
//    /**
//     * 通过md5值，以附件形式下载文件
//     *
//     * @param response
//     * @return
//     * @throws IOException
//     */
//    @CrossOrigin
//    @GetMapping(value = "/common/file/down")
//    public Result downLoadFileByMd5(String hash, HttpServletResponse response) throws IOException {
//        Result result = new Result();
//        logger.debug("文件下载开始执行，入参参数为：" + hash);
//        try {
//            CommonFile byHash = fileService.getByHash(hash);
//            if (byHash == null) {
//                return Result.getErrorOne("未找到该文件");
//            }
//            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(byHash.getFileName(), "UTF-8"));
//            ServletOutputStream outputStream = response.getOutputStream();
//            outputStream.write(filetobyte(new File(byHash.getFileUrl())));
//            result.setResltInfo("1", "文件下载成功", "预览成功");
//        } catch (Exception e) {
//            logger.info("文件下载异常抛出，错误为：" + e);
//            result.setResltInfo("0", "文件下载失败", e.getMessage());
//            return result;
//        }
//        logger.debug("文件下载结束，出参参数为：" + result);
//        return result;
//    }
//
//
//    /**
//     * 将文件转换成byte数组
//     *
//     * @param tradeFile
//     * @return
//     */
//    public byte[] filetobyte(File tradeFile) {
//        byte[] buffer = null;
//        try {
//            FileInputStream fis = new FileInputStream(tradeFile);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = fis.read(b)) != -1) {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            bos.close();
//            buffer = bos.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return buffer;
//    }
//
//    public String currentDir() {
//        Calendar cal = Calendar.getInstance();
//        int month = cal.get(Calendar.MONTH) + 1;
//        int year = cal.get(Calendar.YEAR);
//        return "" + year + "/" + month + "/";
//
//    }
//}
