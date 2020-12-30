package com.strelizia.arknights.model;

import io.swagger.models.auth.In;

/**
 * @author wangzy
 * @Date 2020/12/21 16:13
 * 技能专精材料信息
 **/
public class SkillMaterInfo {
    private Integer skillId;
    private Integer materLevel;
    private Integer useMaterialId;
    private Integer useNumber;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getMaterLevel() {
        return materLevel;
    }

    public void setMaterLevel(Integer materLevel) {
        this.materLevel = materLevel;
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
