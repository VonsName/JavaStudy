package test;

import com.cv4j.netdiscovery.core.Spider;

/**
 * @author Administrator
 */
public class TestSpider {
    public static void main(String[] args) {
        String url="http://www.szrc.cn/HrMarket/WLZP/ZP/0/%E6%95%B0%E6%8D%AE";
        Spider.create()
                .name("spider-1")
                .url(url)
                .parser(new TestParser())
                .run();
    }
}
