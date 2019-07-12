package com.weijianlin.webmagic.bossdemo.controller;

import com.weijianlin.webmagic.bossdemo.demo.BossDemo;
import com.weijianlin.webmagic.bossdemo.strengthen.HttpsClientDownloader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;


@Controller
public class HomeController {

    private BossDemo bossDemo;

    public HomeController(BossDemo bossDemo){
        this.bossDemo = bossDemo;
    }
    @GetMapping("/")
    public String index(){
        Spider.create(bossDemo).setDownloader(new HttpsClientDownloader())
                .addUrl("https://www.zhipin.com/c101280600-p100101/?page=1&ka=page-1")
                .addPipeline(new JsonFilePipeline("C:\\webmagic\\")).thread(5).run();
        return "index";
    }
}
