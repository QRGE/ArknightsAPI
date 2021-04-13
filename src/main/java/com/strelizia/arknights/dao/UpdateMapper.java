package com.strelizia.arknights.dao;

import com.strelizia.arknights.model.*;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangzy
 * @Date 2020/12/19 18:42
 **/
public interface UpdateMapper {

    //插入一个干员信息
    Integer insertOperator(OperatorInfo operatorInfo);

    //根据名字查询一个干员id
    Integer selectOperatorIdByName(String name);

    //插入一个干员精英化材料信息
    Integer insertOperatorEvolve(OperatorEvolveInfo operatorEvolveInfo);

    //插入一个干员技能信息
    Integer insertOperatorSkill(OperatorSkillInfo operatorSkillInfo);

    //根据技能名获取技能id
    Integer selectSkillIdByName(String SkillName);

    //插入一个技能升级材料信息
    Integer insertSkillMater(SkillMaterInfo skillMaterInfo);

    //插入一个材料合成公式
    Integer insertMaterialMade(@Param("materialId")Integer material_id, @Param("useMaterialId")Integer useMaterialId, @Param("useNumber")Integer useNumber);

    //清空数据库重新插入
    Integer clearOperatorData();

    //更新干员面板数据
    Integer updateOperatorData(OperatorData operatorData);

    //更新地图数据
    Integer updateStageData(MapJson mapJson);

    //更新章节数据
    Integer updateZoneData(ZoneJson zoneJson);

    //更新材料数据
    Integer updateItemData(@Param("id") Integer id, @Param("name") String name, @Param("icon") String icon);

    //更新掉落数据
    Integer updateMatrixData(@Param("stageId") String stageId, @Param("itemId") Integer itemId
            , @Param("quantity") Integer quantity, @Param("times") Integer times);

    Integer updateEnemy(EnemyInfo enemyInfo);

    Integer updateTags(@Param("name") String name, @Param("rarity") Integer rarity, @Param("tags") String tags);

    String getVersion();

    Integer updateVersion(String newVersion);

    Integer updateOperatorInfo(OperatorBasicInfo operatorInfo);

    Integer updateSkillDecs(SkillDesc skillDesc);
}
