package com.fisher.bostoudmp.core.dao;

/**
 * Created by fisher on 17-3-24.
 */
public class BosAccessDBFactory {
    public static BosAccessBDDao getBosAccessDBDao(){
        return new BosAccessDBDaoImpl();
    }
}
