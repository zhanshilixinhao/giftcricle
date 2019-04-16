package com.chouchong.utils.upload;

import com.chouchong.utils.properties.GetProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yy
 * @date 2017/12/15
 **/
public class UploadUtils {
    private static final Logger LOG = Logger.getLogger(UploadUtils.class);

    public static final int OTHER = -1;
    public static final int ITEM = 1;

    private static final long MAX_SIZE = 1024 * 200; // 200kb

    // 图片类型
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".gif");
        fileTypes.add(".png");
        fileTypes.add(".apk");
    }

    public static List<String> multiUpload(ArrayList<MultipartFile> files, String module) throws IOException {
        List<String> uriList = new ArrayList();
        for (MultipartFile file : files) {
            uriList.add(upload(file, fileTypes, module));
        }
        return uriList;
    }

    public static String upload(MultipartFile file, List<String> fileTypes, String DirectoryName) throws IOException {
        // 记录上传过程起始时的时间，用来计算上传时间
        // int pre = (int) System.currentTimeMillis();
        // 图片名称
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String fileName = null;
        if (file != null) {
            // 取得当前上传文件的文件名称
            String myFileName = file.getOriginalFilename();
            // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (StringUtils.isNotBlank(myFileName)) {
                // 获得图片的原始名称
                String originalFilename = file.getOriginalFilename();
                // 获得图片后缀名称,如果后缀不为图片格式，则不上传
                String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                if (!fileTypes.contains(suffix)) {
                    throw new ServerException("文件格式不支持!");
                }
                // 如果不是上传apk文件
//                if (!suffix.contains("apk")) {
//                    // 图片限制在200kb以内
//                    // 单位是字节
//                    long size = file.getSize();
//                    if (size > MAX_SIZE) {
//                        throw new ServerException("图片要小于200kb");
//                    }
//                }

                // 获得上传路径的绝对路径地址(/upload)-->
                String realPath = GetProperties.getImageSavePath();
                String module = DirectoryName + "/" + simpleDateFormat.format(new Date()) + "/";;
                realPath = realPath + module;
                System.out.println("realPath = " + realPath);
                // 如果路径不存在，则创建该路径
                File realPathDirectory = new File(realPath);
                if (!realPathDirectory.exists()) {
                    realPathDirectory.mkdirs();
                }
                // 重命名上传后的文件名 111112323.jpg
                fileName = UUID.randomUUID().toString() + suffix;
                // 定义上传路径 .../upload/111112323.jpg
                File uploadFile = new File(realPath + fileName);
                System.out.println("uploadFile = " + uploadFile);
                file.transferTo(uploadFile);
                return "/" + module + fileName;
                // return module + fileName;
            }
        }
        throw new ServerException("文件上传失败!");
    }

    /**
     * 图片上传
     *
     * @param request
     * @param DirectoryName 文件上传目录：比如upload(无需带前面的/) upload/news ..
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @Title upload
     */
    public static String upload(HttpServletRequest request, List<String> fileTypes, String DirectoryName) throws IllegalStateException,
            IOException {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 记录上传过程起始时的时间，用来计算上传时间
                // int pre = (int) System.currentTimeMillis();
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                return upload(file, fileTypes, DirectoryName);
            }

        }
        return null;
    }


    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     *
     * @param request
     * @param response
     * @param DirectoryName 文件上传目录：比如upload(无需带前面的/) upload/..
     * @throws IOException
     * @Title ckeditor
     */
    public static void ckeditor(HttpServletRequest request, HttpServletResponse response, String DirectoryName)
            throws IOException {
        String fileName = upload(request, fileTypes, DirectoryName);
        // 结合ckeditor功能
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
        String imageContextPath = GetProperties.getImageHost() + fileName;
        response.setContentType("text/html;charset=UTF-8");
        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageContextPath + "',''" + ")");
        out.println("</script>");
        out.flush();
        out.close();
    }

    /**
     * elment-ui文件上传
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public static void eluiUpdload(HttpServletRequest request, HttpServletResponse response, String DirectoryName, List<String> fileTypes)
            throws IOException {
        String fileName = upload(request, fileTypes, DirectoryName);
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
        String imageContextPath = GetProperties.getImageHost() + fileName;
        PrintWriter out = response.getWriter();
        System.out.println("path = " + imageContextPath);
        out.write(String.format("{\"name\":\"%s\",\"url\":\"%s\",\"error\":\"0\"}", fileName, imageContextPath));
        out.flush();
        out.close();
    }

    /**
     * {
     * // errno 即错误代码，0 表示没有错误。
     * //       如果有错误，errno != 0，可通过下文中的监听函数 fail 拿到该错误码进行自定义处理
     * errno: 0,
     * <p>
     * // data 是一个数组，返回若干图片的线上地址
     * data: [
     * '图片1地址',
     * '图片2地址',
     * '……'
     * ]
     * }
     */
    public static void wangEditorUpdload(HttpServletRequest request, HttpServletResponse response, String DirectoryName, int type)
            throws IOException {
        String fileName = "../static" + upload(request, fileTypes, DirectoryName);
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
//        String imageContextPath = PropertiesUtil.getImageHost() + fileName;
        System.out.println(fileName);
        PrintWriter out = response.getWriter();
        if (type == 1) {
            out.write(String.format("{\"errno\":\"%s\",\"data\":[\"%s\"]}", 0, fileName));
        } else {
            out.write(String.format("{\"error\":%s,\"url\":\"%s\"}", 0, fileName));
        }
        out.flush();
        out.close();
    }

    public static void uploadApk(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> list = new ArrayList();
            list.add(".apk");
            eluiUpdload(request, response, "apk", list);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @param rich
     */
    public static void upload(HttpServletRequest request, HttpServletResponse response, int rich) {
        String type = request.getParameter("module");
        int module = Integer.parseInt(type);
        switch (module) {
            case OTHER:
                type = "other";
                break;
            case ITEM:
                type = "item";
                break;
        }
        try {
            if(module == 1){
                eluiUpdload(request, response, type, fileTypes);
            }
            if(module == 2){
                wangEditorUpdload(request, response, type, 1);
            }

        } catch (Throwable throwable) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throwable.printStackTrace();
        }
    }
}
