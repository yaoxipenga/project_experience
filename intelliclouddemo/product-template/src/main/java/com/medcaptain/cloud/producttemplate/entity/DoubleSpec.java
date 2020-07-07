package com.medcaptain.cloud.producttemplate.entity;


/**
 * 双精度小数规范
 *
 * @author bingwen.ai
 */
public class DoubleSpec {
    private String unit;

    private String unitName;

    private double min;

    private double max;

    private double step;

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

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
