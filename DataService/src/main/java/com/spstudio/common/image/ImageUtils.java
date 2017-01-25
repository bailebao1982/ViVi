/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.image;

import com.spstudio.common.log.ServiceExceptionLogAspect;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

/**
 *
 * @author wewezhu
 */
public class ImageUtils {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ImageUtils.class);

    public static String cutImage(String sourcePath, String targetPath, int x, int y, int width, int height) throws IOException {
        File imageFile = new File(sourcePath);
        if (!imageFile.exists()) {
            throw new IOException("Not found the images:" + sourcePath);
        }
        if (targetPath == null || targetPath.isEmpty()) {
            targetPath = sourcePath;
        }
        String format = sourcePath.substring(sourcePath.lastIndexOf(".") + 1, sourcePath.length());
        BufferedImage image = ImageIO.read(imageFile);
        image = image.getSubimage(x, y, width, height);
        ImageIO.write(image, format, new File(targetPath));
        return targetPath;
    }

    public static String zoom(String sourcePath, String targetPath, int width, int height) throws IOException {
        File imageFile = new File(sourcePath);
        if (!imageFile.exists()) {
            throw new IOException("Not found the images:" + sourcePath);
        }
        if (targetPath == null || targetPath.isEmpty()) {
            targetPath = sourcePath;
        }
        String format = sourcePath.substring(sourcePath.lastIndexOf(".") + 1, sourcePath.length());
        BufferedImage image = ImageIO.read(imageFile);
        image = zoom(image, width, height);
        ImageIO.write(image, format, new File(targetPath));
        return targetPath;
    }

    public static BufferedImage zoom(BufferedImage sourceImage, int width, int height) {
        BufferedImage zoomImage = new BufferedImage(width, height, sourceImage.getType());
        Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Graphics gc = zoomImage.getGraphics();
        gc.setColor(Color.WHITE);
        gc.drawImage(image, 0, 0, null);
        return zoomImage;
    }

    /**
     * 加载本地文件,并转换为byte数组
     *
     * @return
     */
    public static byte[] loadFile() {
        File file = new File("d:/touxiang.jpg");

        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        byte[] data = null;

        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream((int) file.length());

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }

                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * 加载本地文件,并转换为byte数组
     *
     * @return
     */
    public static void writeFile(byte[] bao) {
        File file = new File("d:/touxiang1.jpg");

        FileOutputStream fos = null;
        ByteArrayOutputStream baos = null;
        byte[] data = null;

        try {
            fos = new FileOutputStream(file);
            fos.write(bao);

            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 对byte[]进行压缩
     *
     * @param 要压缩的数据
     * @return 压缩后的数据
     */
    public static byte[] compress(byte[] data) {
        System.out.println("before:" + data.length);

        GZIPOutputStream gzip = null;
        ByteArrayOutputStream baos = null;
        byte[] newData = null;

        try {
            baos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(baos);

            gzip.write(data);
            gzip.finish();
            gzip.flush();

            newData = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                gzip.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("after:" + newData.length);
        return newData;
    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            logger.error("gzip uncompress error.", e);
        }

        return out.toByteArray();
    }

    public static String byteToString(byte[] data) {

        String dataString = null;
        try {

            dataString = encodeBase64String(data);
        } catch (Exception e) {
            logger.error("Byte convert to string error.", e);

        }
        return dataString;
    }

    public static byte[] stringToByte(String picString) {
        byte[] srtbyte = null;

      
       srtbyte = decodeBase64(picString);

        return srtbyte;
    }
    
    public static void main(String[] args){
        byte[] orgByte = ImageUtils.loadFile();
        String parseString = byteToString(orgByte);
        System.out.println(parseString);
        
        byte[] convertedByte = ImageUtils.stringToByte(parseString);
        ImageUtils.writeFile(convertedByte);
        
        
        
        
    }

}
