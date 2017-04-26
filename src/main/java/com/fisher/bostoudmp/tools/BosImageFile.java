package com.fisher.bostoudmp.tools;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * Created by fisher on 17-3-23.
 */
public class BosImageFile {
    private static Logger logger = Logger.getLogger(BosImageBatchInfo.class.toString());

    public static List<String> getImagesPaths(String filePath) {
        List<String> urllist = new ArrayList<String>();
        List<File> fileList = BosImageFile.getImageFiles(filePath);
        if(fileList ==null){
           return urllist;
        }
        for (File f : fileList) {
            try {
                urllist.add(f.getCanonicalPath());
            } catch (IOException e) {
                logger.error("Sorry get your file error: " + filePath);
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return urllist;
    }

    public static List<File> getImageFiles(String path) {
        File file = new File(path);
        try {
            return (List<File>) FileUtils.listFiles(file, TrueFileFilter.INSTANCE, null);
        }catch (Exception e){
           logger.error(e.getMessage());
           e.printStackTrace();
           return null;
        }
    }

    public static boolean isSameFileList(List<String> logicFilelist, List<String> trueFileList) {
        boolean b = false;
        if (logicFilelist.size() == trueFileList.size()) {
            for (String s : logicFilelist) {
                if (trueFileList.contains(s)) {
                    b = true;
                }else {
                    b=false;
                }
            }
        }
        return b;
    }
}
