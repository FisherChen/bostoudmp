package com.fisher.bostoudmp.core.dao;

import com.fisher.bostoudmp.core.bean.BosImageBatchInfo;
import com.fisher.bostoudmp.core.exception.BosRowIDException;
import com.fisher.bostoudmp.db.DBManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fisher on 17-3-22.
 */
public class BosAccessDBDaoImpl implements BosAccessBDDao {
    private static Logger logger = Logger.getLogger(BosAccessDBDaoImpl.class.toString());
    private static final int MAX_SIZE = 1000;


    // flag : 1 - 上传成功 且 张数一致；
    //        2 - 已经获取并准备上传 ；
    //        3 - 上传成功但图片张数对不上 ；
    //        4 - 有数据库路径但无图片 ；
    //        5 - 数据库无图片路径

    private static final String SELECT_BOSIMAGEINFO_SQL =
            "select rowid, imagePath,orgCode,to_char(trDate,'yyyymmdd')trDate,UM,pageSize" +
                    " from fisher.bos_sdb_images img " +
                    "where not EXISTS (select 1 from fisher.bos_sdb_image_to_udmp u " +
                    "where img.rowid=u.bos_rowid and u.flag=1)" +
                    "and rownum<=?";

    private static final String INSERT_BOS_IMAGE_TO_UDMP_SQL = "insert into fisher.bos_sdb_image_to_udmp" +
            "(bos_rowid,flag,created_date)values(?,2,sysdate)";

    private static final String UPDATE_BOS_IMAGE_TO_UDMP_SQL = "update fisher.bos_sdb_image_to_udmp set flag=?" +
            ",docId=?,updated_date=sysdate where bos_rowID=?";


    public List<BosImageBatchInfo> getBosImageInfoList(int size) {
        List<BosImageBatchInfo> blist = new ArrayList<BosImageBatchInfo>();
        Connection connection = DBManager.getConnection();
        PreparedStatement pst = null;
        //当前的最大查询数据限制在100 条
        if (size >= 100) {
            size = MAX_SIZE;
        }
        try {
            pst = connection.prepareStatement(SELECT_BOSIMAGEINFO_SQL);
            pst.setInt(1, size);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BosImageBatchInfo bi = new BosImageBatchInfo();
                bi.setRowID(rs.getString("rowID"));
                bi.setImagePath(rs.getString("imagePath"));
                bi.setOrgCode(rs.getString("orgCode"));
                bi.setTrDate(rs.getString("trDate"));
                bi.setUM(rs.getString("UM"));
                bi.setPageSize(rs.getInt("pageSize"));
                blist.add(bi);
            }
        } catch (SQLException e) {
            logger.error("Sorry your SQL : \n " + SELECT_BOSIMAGEINFO_SQL);
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            BosAccessDBDaoImpl.closeAfer(pst, connection);
        }
        logger.debug("Now get " + blist.size() + " bosImages.");
        return blist;
    }

    public boolean prepareUpLoadFile(BosImageBatchInfo bosImageBatchInfo) {
        PreparedStatement pst = null;
        boolean res = false;
        Connection connection = DBManager.getConnection();
        try {
            pst = connection.prepareStatement(INSERT_BOS_IMAGE_TO_UDMP_SQL);
            connection.setAutoCommit(true);
            pst.setString(1, bosImageBatchInfo.getRowID());
            res = pst.execute();
        } catch (SQLException e) {
            logger.error("Sorry your sql can't be excute :'" + INSERT_BOS_IMAGE_TO_UDMP_SQL);
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            closeAfer(pst, connection);
        }
        return res;
    }

    public void prepareUpLoadFile(List<BosImageBatchInfo> blist) {
        PreparedStatement pst = null;
        Connection connection = DBManager.getConnection();
        try {
            pst = connection.prepareStatement(INSERT_BOS_IMAGE_TO_UDMP_SQL);
            connection.setAutoCommit(false);
            for (BosImageBatchInfo bf : blist) {
                pst.setString(1, bf.getRowID());
                pst.addBatch();
            }
            pst.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Sorry your SQL : \n " + INSERT_BOS_IMAGE_TO_UDMP_SQL);
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            BosAccessDBDaoImpl.closeAfer(pst, connection);
        }
    }

    public int updateBosImageToUdmp(String bosRowID, int flag, String docID) throws BosRowIDException {
        int num = 0;
        if (bosRowID == null) {
            throw new BosRowIDException("bosRowID cant't  be null");
        }
        if (docID == null) {
            docID = "";
        }
        PreparedStatement pst = null;
        Connection connection = DBManager.getConnection();
        try {
            pst = connection.prepareStatement(UPDATE_BOS_IMAGE_TO_UDMP_SQL);
            pst.setInt(1, flag);
            pst.setString(2, docID);
            pst.setString(3, bosRowID);
            num = pst.executeUpdate();

        } catch (SQLException e) {
            logger.error("Sorry your SQL : \n " + INSERT_BOS_IMAGE_TO_UDMP_SQL);
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            BosAccessDBDaoImpl.closeAfer(pst, connection);
        }
        return num;
    }

    private static void closeAfer(PreparedStatement pst, Connection connection) {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                logger.error("Can't close PerparedStatement !");
                logger.error(e.getMessage());
                e.printStackTrace();
            } finally {
                DBManager.closeConnection(connection);
            }
        }
    }
}

