package ru.infos.dcn.search.engine;

import ru.infos.dcn.search.engine.beans.Page;

import java.util.HashSet;
import java.util.Set;

/**
 * Artemij Chugreev
 * Date: 27.05.12
 * Time: 18:58
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Crawler {

    private final String startUrl;
    private final Set<String> visitedUrls = new HashSet<String>();
//    depth of recursion
    private final int depth;
    private PageProvider pageProvider;
//    robots.txt
    private final RobotsTxt robotsTxt;

    public Crawler(String startUrl, int depth) {
        this.startUrl = startUrl;
        this.depth = depth;
//        provide start page
        this.pageProvider = new PageProvider();
//        provide robots.txt from host of url
        this.robotsTxt = new RobotsTxt(startUrl);
        this.robotsTxt.parse();
    }

    /**
     * recursive crawling
     * @param url for crawling
     * @param currentDepth depth of recursion
     */
    public void recursiveCrawl(String url, int currentDepth) {

        if (currentDepth >= depth) {
            return;
        }
//        get current page
        Page page = pageProvider.provide(url);
//        go on all inner links
        for (String link : page.getInnerLinks()) {
//            if robots.txt disallowed this link => go to next link
            if (robotsTxt.isBan(link)) {
                continue;
            }
//            if page isn't visited
            if (!visitedUrls.contains(link)) {
                visitedUrls.add(link);
//                crawl this page
                recursiveCrawl(link, currentDepth + 1);
            }
        }
    }

    /**
     * crawl site from start url
     */
    public void crawl() {
        visitedUrls.add(startUrl);
        recursiveCrawl(startUrl, 0);
    }
}
