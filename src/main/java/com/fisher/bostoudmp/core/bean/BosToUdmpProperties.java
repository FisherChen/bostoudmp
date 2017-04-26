package com.fisher.bostoudmp.core.bean;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by fisher on 17-3-23.
 */
public class BosToUdmpProperties {
    private static Logger logger = Logger.getLogger(BosToUdmpProperties.class.toString());
    private static int GET_IMAGES_SIZE = 0;
    private static int MAX_THREAD_COUNT = 0;
    private static int PORT=8802;


    private static Properties properties = new Properties();

    static {
        try {
            properties.load(BosToUdmpProperties.class.getClassLoader().getResourceAsStream("bostoudmp.properties"));
            MAX_THREAD_COUNT=Integer.parseInt(properties.getProperty("MAX_THREAD_COUNT"));
            GET_IMAGES_SIZE= Integer.parseInt(properties.getProperty("GET_IMAGES_SIZE"));
            PORT = Integer.parseInt(properties.getProperty("ListenPort"));
        } catch (IOException e) {
            logger.error("Sorry Cant't load bostodump.properties ");
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public static int  getGetImagesSize(){
        return GET_IMAGES_SIZE;
    }

    public static int getMaxThreadCount(){
        return MAX_THREAD_COUNT;
    }

    public static int getPORT(){return PORT;}

}
