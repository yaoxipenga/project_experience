package com.medcaptain.cloud.producttemplate.entity;


/**
 * 整型数值规范
 *
 * @author bingwen.ai
 */
public class IntegerSpec {
    private String unit;

    private String unitName;

    private long min;

    private long max;

    private long step;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
