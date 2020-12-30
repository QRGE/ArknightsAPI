package com.strelizia.arknights.model;

/**
 * @author wangzy
 * @Date 2020/12/14 18:03
 * 干员公招信息
 **/
public class AgentTagsInfo {
    private String agentName;
    private Integer star;
    private String tags;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
