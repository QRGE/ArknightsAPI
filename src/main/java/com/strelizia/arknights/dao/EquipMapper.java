package com.strelizia.arknights.dao;

import com.strelizia.arknights.model.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangzy
 * @Date 2021/3/29 17:07
 **/
@Mapper
public interface EquipMapper {

    Integer insertEquipInfo(EquipInfo equipInfo);

    Integer insertEquipcost(@Param("equipId") String equipId, @Param("materialId")String materialId, @Param("useNumber")Integer useNumber);

    Integer insertEquipBuff(@Param("equipId")String equipId, @Param("buffKey")String buffKey, @Param("value")Double value);

    Integer insertEquipMission(@Param("equipId")String equipId, @Param("missionId")String missionId, @Param("desc")String desc);

    EquipInfo selectEquipByName(String name);

    List<EquipBuff> selectEquipBuffById(String equipId);

    List<MaterialInfo> selectEquipCostById(String equipId);

    List<String> selectEquipMissionById(String equipId);

}
