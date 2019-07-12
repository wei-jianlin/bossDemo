package com.weijianlin.webmagic.bossdemo.dao;

import com.weijianlin.webmagic.bossdemo.pojo.entiy.JobInfo;
import org.springframework.stereotype.Repository;

/**
 * JobInfoDao继承基类
 */
@Repository
public interface JobInfoDao extends MyBatisBaseDao<JobInfo, Long> {
}