package com.medcaptain.enums;

/**
 * 文件类型（公开、私密）
 *
 * @author chengzhiben
 */
public enum FileType {
    /**
     * 私密
     */
    PRIVATE(0),
    /**
     * 公开
     */
    PUBLIC(1);
    private int code;

    FileType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
