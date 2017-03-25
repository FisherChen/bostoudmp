package com.fisher.bostoudmp.tools;


import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.bean.BosToUdmpProperties;
import java.util.concurrent.ArrayBlockingQueue;

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

}
