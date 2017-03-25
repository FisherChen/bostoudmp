package com.fisher.bostoudmp.test;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.dao.BosAccessBDDao;
import com.fisher.bostoudmp.core.dao.BosAccessDBDaoImpl;
import com.fisher.bostoudmp.core.dao.BosAccessDBFactory;
import com.fisher.bostoudmp.core.exception.BosRowIDException;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by fisher on 17-3-22.
 */
public class DBMangerTest {

    public static Logger logger = Logger.getLogger(DBMangerTest.class.toString());

    BosAccessBDDao bosAccessBDDao= BosAccessDBFactory.getBosAccessDBDao();

    @Test
    public void getBosImageInfo() {
        List<BosImageBatchInfo> blist =bosAccessBDDao.getBosImageInfoList(10);
        for (BosImageBatchInfo b : blist) {
            logger.debug(b.getImagePath());
            logger.debug(b.getOrgCode());
            logger.debug(b.getRowID());
            logger.debug(b.getTrDate());
            for (String s:b.getLogicImagePaths()){
                logger.debug(s);
            }
        }
    }

    @Test
    public void prePareUpLoadFile() {
        List<BosImageBatchInfo> blist =bosAccessBDDao.getBosImageInfoList(20);
        bosAccessBDDao.prepareUpLoadFile(blist);
    }

    @Test
    public void updateBosToUdmp() throws BosRowIDException {
        List<BosImageBatchInfo> blist =bosAccessBDDao.getBosImageInfoList(20);
        for (BosImageBatchInfo b : blist) {
            if (b.getRowID() == null) {
                if (b.getImagePath().equalsIgnoreCase("")) {
                    bosAccessBDDao.updateBosImageToUdmp(b.getRowID(), 5, b.getRowID());
                }else {
                    bosAccessBDDao.updateBosImageToUdmp(b.getRowID(), 2, b.getRowID());
                }
            }
        }
    }

}
