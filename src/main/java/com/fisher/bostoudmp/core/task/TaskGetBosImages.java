package com.fisher.bostoudmp.core.task;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;
import com.fisher.bostoudmp.core.dao.BosAccessBDDao;
import com.fisher.bostoudmp.core.dao.BosAccessDBFactory;
import com.fisher.bostoudmp.tools.BosToUdmpTools;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by fisher on 17-3-23.
 */
public class TaskGetBosImages implements Runnable {
    private static Logger logger = Logger.getLogger(TaskGetBosImages.class.toString());
    private ArrayBlockingQueue<BosImageBatchInfo> bosQueue = BosToUdmpTools.getBosQueue();
    private String name = "";

    public TaskGetBosImages(String name) {
        this.name = name;
    }

    public void run() {
        Thread.currentThread().setName(this.name);
        logger.info(Thread.currentThread().getName() + ": Now getting  images for update !");
        List<BosImageBatchInfo> blist = null;
        BosAccessBDDao bosAccessBDDao=BosAccessDBFactory.getBosAccessDBDao();
        int size = BosToUdmpProperties.getGetImagesSize();
        while (true) {
            blist =bosAccessBDDao.getBosImageInfoList(size);
            for (BosImageBatchInfo b : blist) {
                try {
                    bosQueue.put(b);
                } catch (InterruptedException e) {
                    logger.error("Sorry the thread has been interrupted :" + Thread.currentThread().getName());
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }

        }

    }
}
