package com.softdev.system.generator.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Administrator
 * @version $v: 1.0.0, $time:2015/9/29 16:58 Exp $
 */
@Slf4j
public class FileUtil {

    /**
     * 保存Java文件
     * @param content 文件内容
     * @param homePath 文件主路径
     * @param packageName package名
     * @param className class名
     */
    public static void saveJavaFile(String content, String homePath, String packageName, String className){
        // 文件夹路径
        String dirPath = homePath  + File.separator+ getPackagePath(packageName);
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 文件路径
        String filePath = dirPath + File.separator + className;

        System.out.println("" + filePath);
        // 保存文件
        saveFile(content, filePath);
        if (!filePath.contains("ideaProject")) {
            try {
                Process p = Runtime.getRuntime().exec("C:\\Users\\Administrator\\AppData\\Local\\Programs\\EmEditor\\EmEditor.exe "+filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 保存文件
     * @param content
     * @param filePath
     */
    public static void saveFile(String content, String filePath) {
        File file = new File(filePath);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file); // 输出到文件流
            out.write(content.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 得到包路径 **/
    private static String getPackagePath(String packageName) {
        return packageName.replace(".", File.separator);
    }
}
