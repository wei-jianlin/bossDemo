package com.weijianlin.webmagic.bossdemo;

import com.weijianlin.webmagic.bossdemo.dao.AbilityDao;
import com.weijianlin.webmagic.bossdemo.dao.JobInfoDao;
import com.weijianlin.webmagic.bossdemo.dao.TechnicianDao;
import com.weijianlin.webmagic.bossdemo.demo.BossDemo;
import com.weijianlin.webmagic.bossdemo.strengthen.HttpsClientDownloader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionDefinition;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BossdemoApplicationTests {

    @Autowired
    private AbilityDao abilityDao;
    @Autowired
    private JobInfoDao jobInfoDao;
    @Autowired
    private TechnicianDao technicianDao;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

    @Test
    public void contextLoads() {
       Spider.create(new BossDemo(abilityDao,jobInfoDao,technicianDao,dataSourceTransactionManager,transactionDefinition))
               .setDownloader(new HttpsClientDownloader())
                .addUrl("https://www.zhipin.com/c101280600-p100101/?page=1&ka=page-1")
                .addPipeline(new JsonFilePipeline("C:\\webmagic\\")).thread(5).run();

    }
}
