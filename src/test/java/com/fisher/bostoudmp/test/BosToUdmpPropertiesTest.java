package com.fisher.bostoudmp.test;

import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;
import org.junit.Test;
import  static org.junit.Assert.*;

/**
 * Created by fisher on 17-3-23.
 */
public class BosToUdmpPropertiesTest {
    @Test
    public void getPropertiesTest(){

     assertEquals( 20,BosToUdmpProperties.getGetImagesSize());
     assertEquals( 10,BosToUdmpProperties.getMaxThreadCount());

    }
}
