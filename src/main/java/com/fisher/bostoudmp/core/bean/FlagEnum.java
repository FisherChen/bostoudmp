package com.fisher.bostoudmp.core.bean;

/**
 * Created by fisher on 17-3-23.
 * flag :
 * 1 - 上传成功 且 张数一致
 * 2 - 已经获取并准备上传
 * 3 - 上传成功但图片张数对不上
 * 4 - 有数据库路径但无图片
 * 5 - 数据库无图片路径
 */
public enum FlagEnum {
    SUCESS(1), READY(2), SUCESS_WRONG_NUM(3), PATH_NO_IMAGE(4), NO_PATH(5);

    private int code = 0;

    FlagEnum(int index) {
        this.code = index;
    }

    public int getCode() {
        return code;
    }
}
