package com.strelizia.arknights.service.impl;

import com.strelizia.arknights.dao.BuildingSkillMapper;
import com.strelizia.arknights.dao.OperatorInfoMapper;
import com.strelizia.arknights.model.BuildingSkill;
import com.strelizia.arknights.service.BuildingSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangzy
 * @Date 2021/3/31 17:04
 **/
@Service
public class BuildingSkillServiceImpl implements BuildingSkillService {

    @Autowired
    private BuildingSkillMapper buildingSkillMapper;

    @Autowired
    private OperatorInfoMapper operatorInfoMapper;


    @Override
    public String getBuildSkillNameServiceByInfos(String[] infos) {
        Map<String, String> roomTypeMap = new HashMap<>();
        roomTypeMap.put("控制中枢","CONTROL");
        roomTypeMap.put("宿舍","DORMITORY");
        roomTypeMap.put("办公室","HIRE");
        roomTypeMap.put("制造站","MANUFACTURE");
        roomTypeMap.put("会客室","MEETING");
        roomTypeMap.put("发电站","POWER");
        roomTypeMap.put("贸易站","TRADING");
        roomTypeMap.put("训练室","TRAINING");
        roomTypeMap.put("加工站","WORKSHOP");

        List<BuildingSkill> allBuildingSkill = buildingSkillMapper.getAllBuildingSkill();
        for (int i = 1; i < infos.length; i++){
            String info = infos[i];
            if (info == null){
                break;
            }
            if (roomTypeMap.containsKey(info)){
                info = roomTypeMap.get(info);
            }
            List<BuildingSkill> buildingSkillByInfo = buildingSkillMapper.getBuildingSkillByInfo(info);
            allBuildingSkill.retainAll(buildingSkillByInfo);
        }
        StringBuilder s = new StringBuilder("查询到的基建技能为：\n");
        if (allBuildingSkill.size() == 0) {
            return "";
        }else if (allBuildingSkill.size() >= 4){
            for (BuildingSkill b: allBuildingSkill){
                s.append(b.getBuffName()).append("\n");
            }
            s.append("结果过多，只显示对应基建技能名称。\n如需查看基建技能详细信息，请缩小搜索范围，比如使用技能名或者干员名来查询");
        }else{
            for (BuildingSkill b: allBuildingSkill){
                String name = operatorInfoMapper.getOperatorNameById(b.getOperatorId());
                s.append(name).append(" ").append(b.getBuffName()).append(" 精英").append(b.getPhase()).append("/").append(b.getLevel()).append("级解锁\n\t").append(b.getDescription()).append("\n");
            }
        }
        return s.toString();
    }

}
