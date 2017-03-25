package com.fisher.bostoudmp.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fisher on 17-3-22.
 *
 * @author chenzhichun743
 *         The basic infomation about bos's image which selectd from Oracle.
 */
public class BosImageBatchInfo {
    private final static String IMAGE_SUFFIX = "jpg";
    private String rowID = "";
    private String imagePath = "";
    private String orgCode = "";
    private String trDate = "";
    private String UM = "";
    private String docID="";
    private int pageSize = 0;
    private int flag = 0;

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public String getImagePath() {
        if (this.imagePath == null)
            this.imagePath = "";
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTrDate() {
        return trDate;
    }

    public void setTrDate(String tr_date) {
        this.trDate = tr_date;
    }

    public String getUM() {
        return UM;
    }

    public void setUM(String UM) {
        this.UM = UM;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getLogicImagePaths() {
        List<String> pathsList = new ArrayList<String>();
        if (!this.getImagePath().equalsIgnoreCase("") && this.pageSize > 0) {
            for (int i = this.pageSize; i > 0; i--) {
                String filename = "";
                if (i >= 0 && i < 10) {
                    filename = "00" + i;
                } else if (i >= 10 && i < 100) {
                    filename = "0" + i;
                } else if (i >= 100) {
                    filename = ((Integer) i).toString();
                }
                pathsList.add(this.getImagePath() +"/"+ filename+"." + this.IMAGE_SUFFIX);
            }
        }
        return pathsList;
    }


    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
