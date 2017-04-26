package com.fisher.bostoudmp.tools;


import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by fisher on 17-3-23.
 */
public class BosToUdmpTools {
    private static ArrayBlockingQueue<BosImageBatchInfo> bosQueue = null;

    private BosToUdmpTools() {
    }

    public static ArrayBlockingQueue<BosImageBatchInfo> getBosQueue() {
        if (bosQueue == null) {
            int max_size = BosToUdmpProperties.getGetImagesSize();
            bosQueue = new ArrayBlockingQueue<BosImageBatchInfo>(max_size);
        }
        return bosQueue;
    }


    private static boolean goodBye =true;

    public synchronized static boolean isGoodBye() {
        return goodBye;
    }

    public synchronized static void setGoodBye(boolean goodBye) {
        BosToUdmpTools.goodBye = goodBye;
    }

    private static ExecutorService executorService=null;

    public synchronized static ExecutorService getExecutorService() {
        return executorService;
    }

    public synchronized static void setExecutorService(ExecutorService executorService) {
        BosToUdmpTools.executorService = executorService;
    }
}
