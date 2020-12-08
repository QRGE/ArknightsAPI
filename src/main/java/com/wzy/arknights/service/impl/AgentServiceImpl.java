package com.wzy.arknights.service.impl;

import com.wzy.arknights.dao.AgentMapper;
import com.wzy.arknights.dao.SixMapper;
import com.wzy.arknights.model.AgentInfo;
import com.wzy.arknights.service.AgentService;
import com.wzy.arknights.util.FoundAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author wangzy
 * @Date 2020/12/7 14:35
 **/
@Service
@Slf4j
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private SixMapper sixMapper;

    @Override
    public String chouKa(String pool,Long qq) {
        return FoundAgentByNum(1,pool,qq);
    }

    @Override
    public String shiLian(String pool,Long qq) {
        return FoundAgentByNum(10,pool,qq);
    }

    /**
     * 抽卡通用方法
     * @param count 抽几张卡
     * @param pool  卡池名称
     * @param qq    抽取人qq
     * @return
     */
    public String FoundAgentByNum(int count,String pool,Long qq){
        //如果没输入卡池名或者卡池不存在
        if (pool==null||agentMapper.selectAgentByStar(pool,6).size()==0){
            pool = "常规";
        }
        String s = "";
        //去数据库中查询这个人的垫刀数
        Integer sum = sixMapper.selectSixByQQ(qq);
        if (sum == null){
            sum = 0;
        }
        //循环抽卡
        for(int j = 0; j < count; j++) {
            int star = FoundAgent.FoundOneByMath(qq,sum);
            if(star == 6){
                //抽到六星垫刀归零
                sixMapper.updateSixByQQ(qq, 0);
            }else {
                //没有六星垫刀+1
                sixMapper.updateSixByQQ(qq, sum++);
            }
            //保存结果集
            List<AgentInfo> agentList;
            //使用不同的方法Math/Random进行随机运算，尽可能取消同一时间戳导致的相同随机数(虽然两个算法本质一样，这样做基本屁用没有)
            double r = Math.random();
            if (r <= 0.5) {
                //获取当前卡池三星/四星/五星/六星列表
                agentList = agentMapper.selectAgentByStar(pool, star);
            }else {
                agentList = agentMapper.selectAgentByStar("常规", star);
            }
            //有可能三星还去up池里找，因为三星不存在up所以报空，重新去常规池里找
            if (agentList.size() == 0){
                agentList = agentMapper.selectAgentByStar("常规", star);
            }
            //随机数种子采用纳秒数+毫秒/qq，尽可能减少时间戳导致的不随机
            Random random = new Random(System.nanoTime() +  System.currentTimeMillis() / qq);
            int i = random.nextInt(agentList.size());
            s = s + " " + agentList.get(i).getName();
        }
        return pool+"池："+s;
    }
}
