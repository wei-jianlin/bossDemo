package com.weijianlin.webmagic.bossdemo.demo;

import com.alibaba.fastjson.JSONObject;
import com.weijianlin.mybatisQuery.QueryExample;
import com.weijianlin.webmagic.bossdemo.dao.AbilityDao;
import com.weijianlin.webmagic.bossdemo.dao.JobInfoDao;
import com.weijianlin.webmagic.bossdemo.dao.TechnicianDao;
import com.weijianlin.webmagic.bossdemo.pojo.entiy.Ability;
import com.weijianlin.webmagic.bossdemo.pojo.entiy.JobInfo;
import com.weijianlin.webmagic.bossdemo.pojo.entiy.Technician;
import com.weijianlin.webmagic.bossdemo.strengthen.HttpsClientDownloader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.wltea.analyzer.lucene.IKAnalyzer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BossDemo implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    private static final String baseUrl = "https://www.zhipin.com";
    private static final String detailUrl = "https://www.zhipin.com/wapi/zpgeek/view/job/card.json";

    private AbilityDao abilityDao;
    private JobInfoDao jobInfoDao;
    private TechnicianDao technicianDao;
    private DataSourceTransactionManager dataSourceTransactionManager;
    private TransactionDefinition transactionDefinition;

    public BossDemo(AbilityDao abilityDao, JobInfoDao jobInfoDao, TechnicianDao technicianDao,
                    DataSourceTransactionManager dataSourceTransactionManager,
                    TransactionDefinition transactionDefinition){
        this.abilityDao = abilityDao;
        this.jobInfoDao = jobInfoDao;
        this.technicianDao = technicianDao;
        this.dataSourceTransactionManager  = dataSourceTransactionManager;
        this.transactionDefinition = transactionDefinition;
    }

    public void process(Page page) {
        String url = page.getUrl().get();
        //手动开启事务
        //TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        if(url.contains("card.json")){
            JSONObject jsonObject = JSONObject.parseObject(page.getJson().get()).getJSONObject("zpData");
            String htmlDetail = jsonObject.getString("html");
            //System.out.println(htmlDetail);
            Document document = Jsoup.parse(htmlDetail);
            String detail = Xsoup.compile("//div[@class=detail-bottom]/div[@class=detail-bottom-text]/text()")
                    .evaluate(document).get();
            //构建IK分词器，使用smart分词模式
            Analyzer analyzer = new IKAnalyzer(true);

            //获取Lucene的TokenStream对象
            TokenStream ts = null;
            try {
                ts = analyzer.tokenStream("myfield", new StringReader(detail));
                //获取词元文本属性
                CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
                //重置TokenStream（重置StringReader）
                ts.reset();
                String pattern = "^[0-9a-zA-Z]+$";
                Pattern r = Pattern.compile(pattern);
                //迭代获取分词结果
                while (ts.incrementToken()) {
                    String word = term.toString();
                    Matcher m = r.matcher(word);
                    if(m.find() && word.length() > 1) {
                        //纯技术
                        QueryExample queryExample = new QueryExample();
                        queryExample.or().addCriterion("word = ",word);
                        List<Technician> technicians = technicianDao.selectByExample(queryExample);
                        if(technicians.size() == 0){
                            Technician model = new Technician();
                            model.setWord(word);
                            model.setNum(1);
                            technicianDao.insert(model);
                            //手动提交事务
                            //dataSourceTransactionManager.commit(transactionStatus);//提交
                        }else{
                            Technician technician = technicians.get(0);
                            Integer num = technician.getNum() == null ? 0 : technician.getNum();
                            technician.setNum(num + 1);
                            technicianDao.updateByPrimaryKey(technician);
                            //手动提交事务
                            //dataSourceTransactionManager.commit(transactionStatus);//提交
                        }
                    }else if(word.length() > 1){
                        //能力
                        QueryExample queryExample = new QueryExample();
                        queryExample.or().addCriterion("word = ",word);
                        List<Ability> abilitys = abilityDao.selectByExample(queryExample);
                        if(abilitys.size() == 0){
                            Ability model = new Ability();
                            model.setWord(word);
                            model.setNum(1);
                            abilityDao.insert(model);
                            //手动提交事务
                            //dataSourceTransactionManager.commit(transactionStatus);//提交
                        }else{
                            Ability ability = abilitys.get(0);
                            Integer num = ability.getNum() == null ? 0 : ability.getNum();
                            ability.setNum(num + 1);
                            abilityDao.updateByPrimaryKey(ability);
                            //手动提交事务
                            //dataSourceTransactionManager.commit(transactionStatus);//提交
                        }
                    }
                }
                //关闭TokenStream（关闭StringReader）
                ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

            } catch (IOException e) {
                //dataSourceTransactionManager.rollback(transactionStatus);
                e.printStackTrace();
            } finally {
                //释放TokenStream的所有资源
                if(ts != null){
                    try {
                        ts.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            List<String> datas = page.getHtml().$(".job-primary").all();
            for(String data : datas){
                Document document = Jsoup.parse(data);
                //薪水 10-18K 15-25K·13薪
                String salary = Xsoup.compile("//span/@class=red/text()").evaluate(document).get();
                //地点 深圳 福田区 购物公园
                String address = Xsoup.compile("//div/@class=info-primary/p/text(1)").evaluate(document).get();
                //工作年限 3-5年 经验不限 1年以内 应届生 10年以上
                String year = Xsoup.compile("//div/@class=info-primary/p/text(2)").evaluate(document).get();
                //学历 大专 中专/中技
                String educational = Xsoup.compile("//div/@class=info-primary/p/text(3)").evaluate(document).get();
                JobInfo jobInfo = new JobInfo();
                String[] split = salary.split("-|·");
                float low = 1,high = 1,num = 12;
                low = Float.valueOf(split[0]);
                high = Float.valueOf(split[1].substring(0,split[1].length() - 1));
                if(!salary.contains("w")){
                    if(split.length == 3){
                        num = Float.valueOf(split[2].substring(0,2));
                    }
                    low = low * num / 10;
                    high = high * num / 10;
                }
                jobInfo.setYearlySalaryLow(low);
                jobInfo.setYearlySalaryHigh(high);
                String[] addresss = address.split(" ");
                jobInfo.setCity(addresss[0]);
                if(addresss.length >= 2){
                    jobInfo.setArea(addresss[1]);
                }
                if(addresss.length >= 3){
                    jobInfo.setAddress(addresss[2]);
                }
                jobInfo.setWorkingSeniority(year);
                jobInfo.setEducation(educational);
                jobInfoDao.insert(jobInfo);
                //手动提交事务
                //dataSourceTransactionManager.commit(transactionStatus);//提交
                String jid = Xsoup.compile("//div/@class=info-primary/h3/a/@data-jid").evaluate(document).get();
                String lid = Xsoup.compile("//div/@class=info-primary/h3/a/@data-lid").evaluate(document).get();
                Request request = new Request(detailUrl + "?jid=" + jid + "&lid=" + lid);
                request.setMethod(HttpConstant.Method.GET);
                page.addTargetRequest(request);
            }
            String s = page.getHtml().$(".page").$(".next").get();
            Document document = Jsoup.parse(s);
            //拿到a 标签 href属性的值
            String nextHref = Xsoup.compile("//a/@href").evaluate(document).get();
            if(!nextHref.contains("javascript")){
                Request request = new Request(baseUrl + nextHref);
                request.setMethod(HttpConstant.Method.GET);
                page.addTargetRequest(request);
            }
        }
    }

    public Site getSite() {
        site.setCharset("utf-8");
        return site;
    }

    public static void main(String[] args) {
 /*      Spider.create(new BossDemo()).setDownloader(new HttpsClientDownloader())
                .addUrl("https://www.zhipin.com/c101280600-p100101/?page=1&ka=page-1")
                .addPipeline(new JsonFilePipeline("C:\\webmagic\\")).thread(5).run();*/
    }
}
