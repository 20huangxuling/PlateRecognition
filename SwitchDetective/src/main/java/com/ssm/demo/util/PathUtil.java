package com.ssm.demo.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 这个是路径工具类，有个一段静态的代码段，在这个类载入虚拟机的时候就会运行，所以要用一些路径就直接用这个类。
 * 我把文件目录跟url区分开了，分别放在directory.properties和 url.properties文件
 */

public class PathUtil {
    private static Logger logger = Logger.getLogger(PathUtil.class);
    private static Properties directoryPro;
    private static Properties urlPro;
    private static String directory_toBeDetected_linux;
    private static String directory_toBeDetected_windows;
    private static String directory_uploadPic_linux;
    private static String directory_uploadPic_windows;
    private static String directory_excel_resultRecord_linux;
    private static String directory_excel_resultRecord_windows;
    private static String directory_excel_mistakeRecord_linux;
    private static String directory_excel_mistakeRecord_windows;
    public static boolean isWindows;
    public static String directory_toBeDetected;
    public static String directory_uploadPic;
    public static String directory_excel_resultRecord;
    public static String directory_excel_mistakeRecord;
    public static String url_downloadExcel_resultRecord;
    public static String url_downloadExcel_mistakeRecord;

    static {
        directoryPro = new Properties();
        urlPro = new Properties();
        InputStream in;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("directory.properties");
            directoryPro.load(in);
            in.close();
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("url.properties");
            ;
            urlPro.load(inputStream);
            inputStream.close();
            directory_toBeDetected_linux = directoryPro.getProperty("directory.image_toBeDetected_linux");
            directory_toBeDetected_windows = directoryPro.getProperty("directory.image_toBeDetected_windows");
            directory_uploadPic_linux = directoryPro.getProperty("directory.uploadPic_linux");
            directory_uploadPic_windows = directoryPro.getProperty("directory.uploadPic_windows");
            directory_excel_resultRecord_linux = directoryPro.getProperty("directory.excel_resultRecord_linux");
            directory_excel_resultRecord_windows = directoryPro.getProperty("directory.excel_resultRecord_windows");
            directory_excel_mistakeRecord_linux = directoryPro.getProperty("directory.excel_mistakeRecord_linux");
            directory_excel_mistakeRecord_windows = directoryPro.getProperty("directory.excel_mistakeRecord_windows");
            url_downloadExcel_resultRecord = urlPro.getProperty("url.downloadExcel_resultRecord");
            url_downloadExcel_mistakeRecord = urlPro.getProperty("url.downloadExcel_mistakeRecord");
        } catch (IOException e) {
            logger.error(e);
        }
        String osName = System.getProperties().getProperty("os.name");
        osName = osName.toLowerCase();
        if (osName.contains("linux")) {
            isWindows = false;
            directory_toBeDetected = directory_toBeDetected_linux;
            directory_uploadPic = directory_uploadPic_linux;
            directory_excel_resultRecord = directory_excel_resultRecord_linux;
            directory_excel_mistakeRecord = directory_excel_mistakeRecord_linux;
        } else if (osName.contains("windows")) {
            isWindows = true;
            directory_toBeDetected = directory_toBeDetected_windows;
            directory_uploadPic = directory_uploadPic_windows;
            directory_excel_resultRecord = directory_excel_resultRecord_windows;
            directory_excel_mistakeRecord = directory_excel_mistakeRecord_windows;
        }
        logger.info("directory_toBeDetected_linux: " + directory_toBeDetected_linux);
        logger.info("directory_toBeDetected_windows: " + directory_toBeDetected_windows);
        logger.info("directory_uploadPic_linux: " + directory_uploadPic_linux);
        logger.info("directory_uploadPic_windows: " + directory_uploadPic_windows);
        logger.info("directory_excel_resultRecord_linux: " + directory_excel_resultRecord_linux);
        logger.info("directory_excel_resultRecord_windows: " + directory_excel_resultRecord_windows);
        logger.info("directory_excel_mistakeRecord_linux: " + directory_excel_mistakeRecord_linux);
        logger.info("directory_excel_mistakeRecord_windows: " + directory_excel_mistakeRecord_windows);
        logger.info("url_downloadExcel_resultRecord: " + url_downloadExcel_resultRecord);
        logger.info("url_downloadExcel_mistakeRecord: " + url_downloadExcel_mistakeRecord);
        logger.info("url_downloadExcel_mistakeRecord: " + url_downloadExcel_mistakeRecord);
        logger.info("directory_toBeDetected: " + directory_toBeDetected);
        logger.info("directory_uploadPic: " + directory_uploadPic);
        logger.info("directory_excel_resultRecord: " + directory_excel_resultRecord);
        logger.info("directory_excel_mistakeRecord: " + directory_excel_mistakeRecord);
        logger.info("isWindows: " + isWindows);
    }
}
