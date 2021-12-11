package com.strelizia.arknights.dao;


import com.strelizia.arknights.model.ActivityInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wangzy
 * @Date 2021/10/27
 **/
@Mapper
public interface ActivityMapper {

    Integer insertSendMsg();

    Integer insertSendPic();

    Integer insertGetMsg();

    List<ActivityInfo> selectActivity();

    Integer clearActivity();

    Long getUserCount();

    Long getGroupCount();
}
