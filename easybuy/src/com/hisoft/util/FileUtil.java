package com.hisoft.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    static Logger logger = Logger.getLogger(FileUtil.class);

    public static String getUploadPath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/files/");
    }

    public static void transportTempFilesToWorkDir(ServletContext context) throws IOException {
        Files.newDirectoryStream(Paths.get("E:/temp/")).forEach(path -> {
            try {
                Files.copy(path,Paths.get(context.getRealPath("/files/")+path.getFileName()));
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("无法将["+path.getFileName()+"]从temp文件夹将产品图片搬运到工作目录,可能会导致一些产品没有图片信息");
            }
        });
    }

}
