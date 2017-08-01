package hmc.xry.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 *<p>Copyright 2017 OSFF</p>
 *<p></p>
 *<p>类描述  ：[这个类是：抓取百度"美女"图片/p>
 *<p>创建人  ：[xry] </p>
 *<p>创建时间：[2017年7月15日下午12:45:50]</p>
 *<p>修改人  ：[xry] 描述：[]</p>
 */
public class A implements PageProcessor{
	
	
	
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public Site getSite() {
		
		return site;
	}

	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	@Override
	public void process(Page page) {
		// 部分二：定义如何抽取页面信息，并保存下来
		JSONObject jsonObject = (JSONObject) JSONObject.parse(page.getRawText());  
        JSONArray data = (JSONArray) jsonObject.get("data"); 
        for(int i=0;i<data.size();i++){
        	String url = (String) data.getJSONObject(i).get("thumbURL");  
            String name = (String) data.getJSONObject(i).get("fromPageTitleEnc");  
            page.putField(name, url);
        }
        
        
	}

	public static void main(String[] args) {
		 String key = "美女";
		 
		 Spider.create(new A())
         //从"https://github.com/code4craft"开始抓
         .addUrl("https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&queryWord="+key+"&word="+key+"&pn="+1*3+"0")
         //开启5个线程抓取
         .thread(5)
         //启动爬虫
         .run();
		 
	}
}
