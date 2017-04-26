package com.fisher.bostoudmp.test;

import com.fisher.bostoudmp.tools.BosImageFile;
import org.apache.log4j.Logger;
import org.junit.Test;
import static  org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fisher on 17-3-23.
 */
public class BosImageFileTest {
    private static Logger logger = Logger.getLogger(BosImageFileTest.class.toString());

    @Test
    public void getIamgePaths() {
        String path="/home/fisher/workspace/temp";
        List<String> pathList= BosImageFile.getImagesPaths(path);
        for (String s:pathList){
            logger.debug(s);
        }
    }

    @Test
    public void isSameFileList(){
        List<String> str1 = new ArrayList<String>();
        str1.add("/home/1.jpg");
        str1.add("/home/2.jpg");
        str1.add("/home/3.jpg");
        List<String> str2 = new ArrayList<String>();
        str2.add("/home/1.jpg");
        str2.add("/home/2.jpg");
        str2.add("/home/3.jpg");
        assertTrue( BosImageFile.isSameFileList(str1,str2));
        str2.remove(2);
        str2.add("/home/6.jpg");
        assertTrue(!BosImageFile.isSameFileList(str1,str2));
    }




}
