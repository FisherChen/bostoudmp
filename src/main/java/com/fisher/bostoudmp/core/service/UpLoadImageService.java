package com.fisher.bostoudmp.core.service;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.bean.FlagEnum;
import com.fisher.bostoudmp.tools.BosImageFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by fisher on 17-3-23.
 */
public class UpLoadImageService {
    private static Logger logger = Logger.getLogger(UpLoadImageService.class.getName());

    /**
     * flag :
     * 1 - 上传成功 且 张数一致
     * 2 - 已经获取并准备上传
     * 3 - 上传成功但图片张数对不上
     * 4 - 有数据库路径但无图片
     * 5 - 数据库无图片路径
     */

    public static void upLoadImageToUdmp(BosImageBatchInfo img) throws Exception {

        if(img ==null){
            return;
        }
        if (img.getImagePath().equalsIgnoreCase("")) {
            img.setFlag(FlagEnum.NO_PATH.getCode());
            return;
        }

        List<File> imgFileList = BosImageFile.getImageFiles(img.getImagePath());
        if (imgFileList==null){
            img.setFlag(FlagEnum.PATH_NO_IMAGE.getCode());
            return;
        }
        if (imgFileList.size() == 0) {
            img.setFlag(FlagEnum.PATH_NO_IMAGE.getCode());
            return;
        }
        try {

            for (File f : imgFileList) {
                // new FileInputStream(f);
            }

            if (!BosImageFile.isSameFileList(img.getLogicImagePaths(), BosImageFile.getImagesPaths(img.getImagePath()))) {
                img.setFlag(FlagEnum.SUCESS_WRONG_NUM.getCode());
            } else {
                img.setFlag(FlagEnum.SUCESS.getCode());
            }
            //img.setDocID();
            return;
        } catch (Exception e) {
            //未能够上传成功，把异常上抛出，让服务端逻辑处理
            logger.error("Sorry your file i can't upload: " + img.getImagePath());
            logger.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
