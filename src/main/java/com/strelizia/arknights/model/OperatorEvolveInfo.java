package com.strelizia.arknights.model;

/**
 * @author wangzy
 * @Date 2020/12/21 16:10
 **/
public class OperatorEvolveInfo {
    private Integer operatorId;
    private Integer evolveLevel;
    private Integer useMaterialId;
    private Integer useNumber;

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getEvolveLevel() {
        return evolveLevel;
    }

    public void setEvolveLevel(Integer evolveLevel) {
        this.evolveLevel = evolveLevel;
    }

    public Integer getUseMaterialId() {
        return useMaterialId;
    }

    public void setUseMaterialId(Integer useMaterialId) {
        this.useMaterialId = useMaterialId;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }
}
