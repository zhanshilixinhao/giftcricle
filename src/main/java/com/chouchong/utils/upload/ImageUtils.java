package com.chouchong.utils.upload;

import com.chouchong.utils.properties.GetProperties;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理工具类
 *
 * @author yichenshanren
 * @date 2017/10/12
 */

public class ImageUtils {

    /**
     * base64编码的字符串转图片编码
     *
     * @param base64 base64编码的图片字符串
     * @param module 图片模块 头像avatar | 商品 item
     * @param suffix 图片后缀名
     * @return 图片保存到磁盘后的路径
     * @throws IOException
     */
    public static String base64ToImage(String base64, String module, String suffix) throws IOException {
//        System.out.println(base64);
//        base64 = URLDecoder.decode(base64, "UTF-8");
//        System.out.println(base64);
        /** 生成图片路径 自定义路径+当天日期 **/
        String imgUri = module.trim() +
                "/" +
                today("yyyyMMdd") + "/";

        /** 生成图片磁盘全路径 **/
        String ImgPath =  GetProperties.getImageSavePath() + imgUri;
        /** 如果图片的存储路不存在，就创建图片的存储路径 **/
        createDirs(ImgPath);
        /** 解码base64 **/
        byte[] byteArray = (new BASE64Decoder()).decodeBuffer(base64);
        /** 字节数组转为输入流 **/
        BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream(byteArray));
        /** 读取为buffer流 **/
        BufferedImage bufferedImg = ImageIO.read(is);
        /** 获取上传原图的宽 **/
        int imgWidth = bufferedImg.getWidth();
        /** 获取上传原图的高 **/
        int imgHeight = bufferedImg.getHeight();
        /** 裁剪尺寸 **/
//        BufferedImage thumbnail = Thumbnails.of(bufferedImg).size(imgWidth, imgHeight).asBufferedImage();
        // 生成随机图片名
        String fileName = genPictureName(imgWidth, imgHeight) + "." + suffix;
        /** 图片写入磁盘 **/
        ImageIO.write(bufferedImg, suffix, new File(ImgPath + fileName));
        /** 返回数据库存储路径 **/
        return imgUri + fileName;
    }

    /**
     * 创建目录
     *
     * @param imgPath 目录
     */
    private static void createDirs(String imgPath) {
        File file = new File(imgPath);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }
    }

    /**
     * 删除图片
     *
     * @param uri 图片uri
     */
    public static void delImage(String uri) {
        String path =  GetProperties.getImageSavePath();
        if (path == null) return;
        File file = new File(path + uri);
        if (file.exists()) {
            file.delete();
        }
    }

    private static String today(String fo) {
        return new SimpleDateFormat(fo).format(new Date());
    }

    private static String genPictureName(int width, int height) {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d-%s-%s", end3, width, height);
        return str;
    }

}
