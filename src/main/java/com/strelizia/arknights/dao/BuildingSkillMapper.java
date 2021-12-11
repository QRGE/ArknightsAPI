package com.strelizia.arknights.dao;

import com.strelizia.arknights.model.BuildingSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wangzy
 * @Date 2021/3/31 16:19
 **/
@Mapper
public interface BuildingSkillMapper {
    Integer insertBuildingSkill(BuildingSkill buildingSkill);

    List<BuildingSkill> getBuildingSkillByInfo(String Info);

    List<BuildingSkill> getAllBuildingSkill();
}
