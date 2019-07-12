package com.weijianlin.webmagic.bossdemo.dao;

import com.weijianlin.mybatisQuery.QueryExample;
import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * DAO公共基类，由MybatisGenerator自动生成请勿修改
 * @param <Model> The Model Class 这里是泛型不是Model类
 * @param <PK> The Primary Key Class 如果是无主键，则可以用Model来跳过，如果是多主键则是Key类
 */
public interface MyBatisBaseDao<Model, PK extends Serializable> {
    long countByExample(QueryExample example);

    int deleteByExample(QueryExample example);

    int deleteByPrimaryKey(@Param("id") PK id);

    int insert(Model record);

    int insertSelective(Model record);

    List<Model> selectByExample(QueryExample example);

    Model selectByPrimaryKey(@Param("id") PK id);

    int updateByExampleSelective(@Param("record") Model record, QueryExample example);

    int updateByExample(@Param("record") Model record, QueryExample example);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);
}