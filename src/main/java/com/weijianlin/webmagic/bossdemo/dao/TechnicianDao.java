package com.weijianlin.webmagic.bossdemo.dao;

import com.weijianlin.webmagic.bossdemo.pojo.entiy.Technician;
import org.springframework.stereotype.Repository;

/**
 * TechnicianDao继承基类
 */
@Repository
public interface TechnicianDao extends MyBatisBaseDao<Technician, Long> {
}