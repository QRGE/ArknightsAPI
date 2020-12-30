package com.strelizia.arknights.dao;

import com.strelizia.arknights.model.MaterialInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangzy
 * @Date 2020/12/14 11:12
 **/
public interface SkillMateryMapper {

    //根据技能名和技能等级查询升级所需的材料
    List<MaterialInfo> selectSkillUpBySkillName(@Param("skillName") String skillName, @Param("level") Integer level);

    //根据干员名，第几技能和技能等级查询所需材料
    List<MaterialInfo> selectSkillUpByAgentAndIndex(@Param("agent") String agent, @Param("index") Integer index, @Param("level") Integer level);
}
