package com.fisher.bostoudmp;

import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;
import com.fisher.bostoudmp.core.task.TaskGetBosImages;
import com.fisher.bostoudmp.core.task.TaskListenBreak;
import com.fisher.bostoudmp.core.task.TaskUploadImages;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by fisher on 17-3-22.
 */
public class BosToUdmp {
    private static Logger logger = Logger.getLogger(BosToUdmp.class.toString());

    public static void main(String[] args) {
        int MAX_THREAD_COUNT = BosToUdmpProperties.getMaxThreadCount();
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info("Starting BosToUdmp ........");
        ExecutorService exc = Executors.newCachedThreadPool();
        exc.execute(new TaskListenBreak("LISTEN_BAREAK_THRED"));
        exc.execute(new TaskGetBosImages("GET_BOS_IMAGE_THRED"));
        for (int i = 0; i < MAX_THREAD_COUNT; i++) {
            exc.execute(new TaskUploadImages("UP_LOAD_IMAGE_THREAD" + i));
        }
        //BosToUdmpTools.setExecutorService(exc);
    }
}
