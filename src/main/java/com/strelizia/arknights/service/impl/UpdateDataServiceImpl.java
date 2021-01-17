package com.strelizia.arknights.service.impl;

import com.strelizia.arknights.dao.UpdateMapper;
import com.strelizia.arknights.dao.UserFoundMapper;
import com.strelizia.arknights.model.*;
import com.strelizia.arknights.service.UpdateDataService;
import com.strelizia.arknights.util.SendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangzy
 * @Date 2020/12/19 15:46
 **/
@Service
@Slf4j
public class UpdateDataServiceImpl implements UpdateDataService {

    private final String koKoDaYoKeyUrl = "https://api.kokodayo.fun/api/base/info";
    //干员列表
    private final String operatorListUrl = "https://andata.somedata.top/data-2020/char/list/";

    //敌人列表
    private final String enemyListUrl = "https://andata.somedata.top/data-2020/lists/enemy/";

    //干员ID信息
    private final String operatorIdUrl = "https://andata.somedata.top/data-2020/char/data/";

    //技能ID信息
    private final String skillIdUrl = "https://andata.somedata.top/data-2020/skills/";

    //敌人ID信息
    private final String enemyIdUrl = "https://andata.somedata.top/data-2020/enemy/";

    //地图ID信息
    private final String mapIdurl = "https://andata.somedata.top/data-2020/map/exData/";

    //材料列表
    private final String itemListUrl = "https://penguin-stats.cn/PenguinStats/api/v2/items";

    //地图列表
    private final String mapListUrl = "https://penguin-stats.cn/PenguinStats/api/v2/stages?server=CN";

    //章节列表
    private final String zoneListUrl = "https://penguin-stats.cn/PenguinStats/api/v2/zones";

    //地图掉落关联表
    private final String matrixListUrl = "https://penguin-stats.cn/PenguinStats/api/v2/_private/result/matrix/CN/global";

    @Autowired
    private UserFoundMapper userFoundMapper;

    @Autowired
    private UpdateMapper updateMapper;

    @Autowired
    private SendMsgUtil sendMsgUtil;

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public Integer updateAllData() {
        //获取kokodayo的Json数据Key
        String jsonStr = getJsonStringFromUrl(koKoDaYoKeyUrl);
        JSONObject keyJsonObj = new JSONObject(jsonStr);
        String dataVersion = updateMapper.getVersion();
        String charKey = keyJsonObj.getJSONObject("result").getJSONObject("agent").getJSONObject("char").getString("key");
        String enemyKey = keyJsonObj.getJSONObject("result").getJSONObject("level").getJSONObject("enemy").getString("key");
        //版本不同才进行更新
        if (!charKey.equals(dataVersion)) {
            updateMapper.updateVersion(charKey);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            updateMapper.clearOperatorData();
            List<Long> groups = userFoundMapper.selectAllGroups();

            for (Long groupId : groups) {
                String s = "游戏数据闪断更新中，更新期间存在无响应情况，请耐心等待更新完成。\n" +
                        "若十分钟后仍未收到更新完成信息，请联系开发者重新进行更新请求\n--" +
                        sdf.format(new Date());
                sendMsgUtil.CallOPQApiSendMsg(groupId, s, 2);
            }

            updateAllOperator(charKey);
            updateAllEnemy(enemyKey);

            for (Long groupId : groups) {
                String s = "正在从企鹅物流搬运材料数据ing\n--" +
                        sdf.format(new Date());
                sendMsgUtil.CallOPQApiSendMsg(groupId, s, 2);
            }

            updateMapAndItem();

            for (Long groupId : groups) {
                String s = "游戏数据更新完成\n--" + sdf.format(new Date());
                sendMsgUtil.CallOPQApiSendMsg(groupId, s, 2);
            }
        }
        return 0;
    }

    public Integer updateAllOperator(String JsonId){
        //发送请求，封装所有的干员基础信息列表
        String allOperator = getJsonStringFromUrl(operatorListUrl + JsonId + ".json");

        JSONArray json = new JSONArray(allOperator);
        int length = json.length();
        for (int i = 0; i < length; i++){
            JSONObject operator = json.getJSONObject(i);
            String operatorId = operator.getString("No");
            //发送请求遍历干员详细信息
            String operatorJson = getJsonStringFromUrl(operatorIdUrl + operatorId + ".json");
            updateOperatorByJson(operatorJson);
        }
        log.info("更新完成，共更新了{}个干员信息",length);
        return length;
    }

    public Integer updateAllEnemy(String enemyKey){
        log.info("开始更新敌人信息");
        //发送请求，封装所有的敌人面板信息列表
        String allEnemy = getJsonStringFromUrl(enemyListUrl + enemyKey + ".json");
        JSONObject enemyobj = new JSONObject(allEnemy);
        Set<String> enemyJson = enemyobj.keySet();
        int length = enemyJson.size();
        for(String enemyId:enemyJson) {
            String enemyStr = getJsonStringFromUrl(enemyIdUrl + enemyId + ".json");
            JSONArray enemyJsonObj = new JSONArray(enemyStr);
            String name = enemyobj.getJSONObject(enemyId).getString("name");
            for (int j = 0; j < enemyJsonObj.length(); j++) {
                //一个敌人可能有多个阶段，比如我老婆霜星
                JSONObject enemyData = enemyJsonObj.getJSONObject(j).getJSONObject("enemyData");
                JSONObject attributes = enemyData.getJSONObject("attributes");
                Integer atk = attributes.getJSONObject("atk").getInt("m_value");
                Integer baseAttackTime = attributes.getJSONObject("baseAttackTime").getInt("m_value");
                Integer def = attributes.getJSONObject("def").getInt("m_value");
                Integer hpRecoveryPerSec = attributes.getJSONObject("hpRecoveryPerSec").getInt("m_value");
                Integer magicResistance = attributes.getJSONObject("magicResistance").getInt("m_value");
                Integer massLevel = attributes.getJSONObject("massLevel").getInt("m_value");
                Integer maxHp = attributes.getJSONObject("maxHp").getInt("m_value");
                Double moveSpeed = attributes.getJSONObject("moveSpeed").getDouble("m_value");
                Double rangeRadius = enemyData.getJSONObject("rangeRadius").getDouble("m_value");
                Integer silenceImmune = attributes.getJSONObject("silenceImmune").getBoolean("m_value") == true ? 0 : 1;
                Integer sleepImmune = attributes.getJSONObject("sleepImmune").getBoolean("m_value") == true ? 0 : 1;
                Integer stunImmune = attributes.getJSONObject("stunImmune").getBoolean("m_value") == true ? 0 : 1;

                EnemyInfo enemyInfo = new EnemyInfo(enemyId, name, atk, baseAttackTime,
                        def, hpRecoveryPerSec, magicResistance, massLevel, maxHp,
                        moveSpeed, rangeRadius, silenceImmune, sleepImmune, stunImmune, j);

                updateMapper.updateEnemy(enemyInfo);
            }
        }
        log.info("敌人信息更新完成，共更新了{}个敌人信息",length);
        return 0;
    }

    /**
     * 更新地图、材料基础信息
     * @return
     */
    public Integer updateMapAndItem(){
        log.info("从企鹅物流中拉取地图、材料数据");
        MapJson[] maps = restTemplate
                .getForObject(mapListUrl, MapJson[].class);
        for (int i = 0; i < maps.length; i++){
            updateMapper.updateStageData(maps[i]);
        }

        ZoneJson[] zones = restTemplate.getForObject(zoneListUrl,ZoneJson[].class);
        for (int i = 0; i < zones.length; i++){
            updateMapper.updateZoneData(zones[i]);
        }

        ItemJson[] items = restTemplate.getForObject(itemListUrl,ItemJson[].class);
        for (int i = 0; i < items.length; i++){
            try {
                Integer id = Integer.parseInt(items[i].getItemId());
                String name = items[i].getName();
                updateMapper.updateItemData(id, name);
            }catch (NumberFormatException e){
                //忽略家具材料
            }
        }
        //企鹅物流数据缺失双芯片数据，单独插入
        Integer[] DoubleId = {3213,3223,3233,3243,3253,3263,3273,3283};
        String[] DoubleName = {"先锋双芯片", "近卫双芯片", "重装双芯片", "狙击双芯片", "术师双芯片", "医疗双芯片", "辅助双芯片", "特种双芯片"};
        for(int i = 0; i < 8; i++){
            updateMapper.updateItemData(DoubleId[i],DoubleName[i]);
        }

        String matrixJsonStr = restTemplate.getForObject(matrixListUrl,String.class);
        JSONArray matrixJsons = new JSONObject(matrixJsonStr).getJSONArray("matrix");
        int length = matrixJsons.length();
        for (int i = 0; i < length; i++){
            JSONObject matrix = matrixJsons.getJSONObject(i);
            try {
                String stageId = matrix.getString("stageId");
                Integer itemId = Integer.parseInt(matrix.getString("itemId"));
                Integer quantity = matrix.getInt("quantity");
                Integer times = matrix.getInt("times");
                updateMapper.updateMatrixData(stageId, itemId, quantity, times);
            }catch (NumberFormatException e){
                //忽略家具材料
            }
        }
        return 0;
    }

    //发送url的get请求获取结果json字符串
    public String getJsonStringFromUrl(String url){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("User-Agent","PostmanRuntime/7.26.8");
        httpHeaders.set("Authorization","2");
        httpHeaders.set("Host","andata.somedata.top");
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        String s = restTemplate
                .exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        return s;
    }

    @Override
    public Integer updateOperatorByJson(String json) {
        Map<String, Integer> operatorClass = new HashMap<>(8);
        operatorClass.put("PIONEER", 1);
        operatorClass.put("WARRIOR", 2);
        operatorClass.put("TANK", 3);
        operatorClass.put("SNIPER", 4);
        operatorClass.put("CASTER", 5);
        operatorClass.put("SUPPORT", 6);
        operatorClass.put("MEDIC", 7);
        operatorClass.put("SPECIAL", 8);

        JSONObject jsonObj = new JSONObject(json);
        String name = jsonObj.getString("name");
        //近卫兔兔改个名
        if (jsonObj.getJSONArray("phases").getJSONObject(0).getString("characterPrefabKey").equals("char_1001_amiya2")){
            name = "近卫阿米娅";
        }
        int rarity = jsonObj.getInt("rarity") + 1;
        jsonObj.getString("position");
        boolean isNotObtainable = jsonObj.getBoolean("isNotObtainable");

        //封装干员信息
        OperatorInfo operatorInfo = new OperatorInfo();
        operatorInfo.setOperator_name(name);
        operatorInfo.setOperator_rarity(rarity);
        if (isNotObtainable == false) {
            operatorInfo.setAvailable(1);
        } else {
            operatorInfo.setAvailable(0);
        }
        operatorInfo.setIn_limit(0);
        operatorInfo.setOperator_class(operatorClass.get(jsonObj.getString("profession")));

        updateMapper.insertOperator(operatorInfo);
        Integer operatorId = updateMapper.selectOperatorIdByName(name);

        JSONArray phases = jsonObj.getJSONArray("phases");
        if (operatorId != null) {
            int length = phases.length();
            //封装干员面板信息（满级无潜能无信赖）
            JSONArray operatorPanel = phases.getJSONObject(length - 1).getJSONArray("attributesKeyFrames");
            JSONObject panelMax = operatorPanel.getJSONObject(operatorPanel.length() - 1).getJSONObject("data");
            OperatorData operatorData = new OperatorData();
            operatorData.setId(operatorId);
            operatorData.setAtk(panelMax.getInt("atk"));
            operatorData.setDef(panelMax.getInt("def"));
            operatorData.setMagicResistance(panelMax.getInt("magicResistance"));
            operatorData.setMaxHp(panelMax.getInt("maxHp"));
            operatorData.setBlockCnt(panelMax.getInt("blockCnt"));
            operatorData.setCost(panelMax.getInt("cost"));
            operatorData.setBaseAttackTime(panelMax.getInt("baseAttackTime"));
            operatorData.setRespawnTime(panelMax.getInt("respawnTime"));
            updateMapper.updateOperatorData(operatorData);

            //封装干员精英化花费
            for (int i = 1; i < length; i++) {
                JSONObject array = phases.getJSONObject(i);
                if (array.get("evolveCost") instanceof JSONArray) {
                    JSONArray evolveJson = array.getJSONArray("evolveCost");
                    for (int j = 0; j < evolveJson.length(); j++) {
                        JSONObject evolve = evolveJson.getJSONObject(j);
                        //精英i花费
                        OperatorEvolveInfo operatorEvolveInfo = new OperatorEvolveInfo();
                        operatorEvolveInfo.setOperatorId(operatorId);
                        operatorEvolveInfo.setEvolveLevel(i);
                        operatorEvolveInfo.setUseMaterialId(evolve.getInt("id"));
                        operatorEvolveInfo.setUseNumber(evolve.getInt("count"));
                        updateMapper.insertOperatorEvolve(operatorEvolveInfo);

                    }
                }
            }

            //封装干员技能
            JSONArray skills = jsonObj.getJSONArray("skills");
            for (int i = 0; i < skills.length(); i++) {
                OperatorSkillInfo operatorSkillInfo = new OperatorSkillInfo();
                operatorSkillInfo.setOperatorId(operatorId);
                operatorSkillInfo.setSkillIndex(i + 1);
                if (skills.getJSONObject(i).get("skillId") instanceof String) {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.set("User-Agent", "PostmanRuntime/7.26.8");
                    httpHeaders.set("Authorization", "2");
                    httpHeaders.set("Host", "andata.somedata.top");
                    HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
                    //发送请求，封装结果数据
                    String skillName = new JSONObject(restTemplate
                            .exchange(skillIdUrl + skills.getJSONObject(i).getString("skillId") + ".json", HttpMethod.GET, httpEntity, String.class).getBody())
                            .getJSONArray("levels")
                            .getJSONObject(0)
                            .getString("name");
                    ;
                    operatorSkillInfo.setSkillName(skillName);
                    updateMapper.insertOperatorSkill(operatorSkillInfo);
                    Integer skillId = updateMapper.selectSkillIdByName(skillName);
                    //获取技能等级列表(专一专二专三)
                    JSONArray levelUpCostCond = skills.getJSONObject(i).getJSONArray("levelUpCostCond");
                    //该技能专j+1的花费
                    for (int j = 0; j < levelUpCostCond.length(); j++) {
                        JSONObject skillCostObj = levelUpCostCond.getJSONObject(j);
                        if (skillCostObj.get("levelUpCost") instanceof JSONArray) {
                            JSONArray levelUpCost = skillCostObj.getJSONArray("levelUpCost");
                            for (int k = 0; k < levelUpCost.length(); k++) {
                                SkillMaterInfo skillMaterInfo = new SkillMaterInfo();
                                skillMaterInfo.setSkillId(skillId);
                                skillMaterInfo.setMaterLevel(j + 1);
                                skillMaterInfo.setUseMaterialId(levelUpCost.getJSONObject(k).getInt("id"));
                                skillMaterInfo.setUseNumber(levelUpCost.getJSONObject(k).getInt("count"));
                                updateMapper.insertSkillMater(skillMaterInfo);
                            }
                        }
                    }
                }
            }
        }
        return 1;
    }
}
