package com.hisoft.web.listener;

import com.hisoft.util.FileUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class FileTransformListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(FileTransformListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            logger.info("开始搬运");
            FileUtil.transportTempFilesToWorkDir(servletContextEvent.getServletContext());
            logger.info("搬运完成");
        } catch (IOException e) {
            logger.error("搬运失败");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
