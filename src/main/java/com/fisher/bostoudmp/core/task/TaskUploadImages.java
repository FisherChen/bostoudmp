package com.fisher.bostoudmp.core.task;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.dao.BosAccessBDDao;
import com.fisher.bostoudmp.core.dao.BosAccessDBFactory;
import com.fisher.bostoudmp.core.service.UpLoadImageService;
import com.fisher.bostoudmp.tools.BosToUdmpTools;
import org.apache.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by fisher on 17-3-23.
 */
public class TaskUploadImages implements Runnable {
    private static Logger logger = Logger.getLogger(TaskUploadImages.class.toString());
    private ArrayBlockingQueue<BosImageBatchInfo> bosQueue = BosToUdmpTools.getBosQueue();
    private int errTimes = 0;
    private String name = "";

    public TaskUploadImages(String name) {
        this.name = name;
    }

    public void run() {
        Thread.currentThread().setName(this.name);
        logger.info(Thread.currentThread().getName() + ": Hi ,I'm going  to update image !");
        BosAccessBDDao bosAccessBDDao= BosAccessDBFactory.getBosAccessDBDao();
        while (BosToUdmpTools.isGoodBye()) {
            //check is err times .
            if (isErrorOver100()) {
                logger.info("Now we can't update image over than 100 times . Please check logs !");
                break;
            }

            try {
                BosImageBatchInfo bosImageBatchInfo = bosQueue.take();
                if (bosAccessBDDao.prepareUpLoadFile(bosImageBatchInfo)) {
                    try {
                        UpLoadImageService.upLoadImageToUdmp(bosImageBatchInfo);
                        bosAccessBDDao.afterUploadedFile(bosImageBatchInfo.getRowID(), bosImageBatchInfo.getFlag(), bosImageBatchInfo.getDocID());
                    } catch (Exception e) {
                        errTimes++;
                        logger.error("Sorry img can't upload we will do next one ,until 100, now this Thread error size is :" + errTimes);
                        e.printStackTrace();
                    }
                } else {
                    errTimes++;
                }
            } catch (InterruptedException e) {
                logger.error(Thread.currentThread().getName() + " : Sorry I'm been interrupted ! ");
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private boolean isErrorOver100() {
        boolean b = false;
        if (errTimes > 100) {
            b = true;
        }
        return b;
    }

}
