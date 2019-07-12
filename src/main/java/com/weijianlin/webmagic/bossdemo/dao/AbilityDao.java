package com.weijianlin.webmagic.bossdemo.dao;

import com.weijianlin.webmagic.bossdemo.pojo.entiy.Ability;
import org.springframework.stereotype.Repository;

/**
 * AbilityDao继承基类
 */
@Repository
public interface AbilityDao extends MyBatisBaseDao<Ability, Long> {
}