package com.strelizia.arknights.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author wangzy
 * @Date 2021/2/20 10:14
 **/
@Mapper
public interface ExecuteSqlMapper {
    List<Map> executeSql(String sql);
}
