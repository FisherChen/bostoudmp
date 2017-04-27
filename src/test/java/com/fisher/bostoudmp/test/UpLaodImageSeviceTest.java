package com.fisher.bostoudmp.test;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.bean.FlagEnum;
import com.fisher.bostoudmp.core.service.UpLoadImageService;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fisher on 17-3-23.
 */
public class UpLaodImageSeviceTest {
    private static Logger logger = Logger.getLogger(UpLoadImageService.class.toString());

    @Test
    public void upLoacImageToUdmp() {

        BosImageBatchInfo bosImageBatchInfo = new BosImageBatchInfo();

        try {
            bosImageBatchInfo.setFlag(FlagEnum.READY.getCode());
            bosImageBatchInfo.setImagePath("/home/fisher/workspace/temp");
            UpLoadImageService.upLoadImageToUdmp(bosImageBatchInfo);
            assertEquals(FlagEnum.PATH_NO_IMAGE.getCode(), bosImageBatchInfo.getFlag());

            bosImageBatchInfo.setImagePath("/www/");
            UpLoadImageService.upLoadImageToUdmp(bosImageBatchInfo);
            assertEquals(FlagEnum.NO_PATH.getCode(), bosImageBatchInfo.getFlag());

            bosImageBatchInfo.setImagePath("");
            UpLoadImageService.upLoadImageToUdmp(bosImageBatchInfo);
            assertEquals(FlagEnum.NO_PATH.getCode(), bosImageBatchInfo.getFlag());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
