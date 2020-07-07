package com.medcaptain.cloud.usermanage.entity;

/**
 * 地理区域信息
 *
 * @author bingwen.ai
 */
public class Region {
    private Integer regionId;

    private String regionName;

    private Byte regionLevel;

    private Integer parentId;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public Byte getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Byte regionLevel) {
        this.regionLevel = regionLevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
