package ru.infos.dcn.search.engine;

import ru.infos.dcn.reverted.index.RevInd;

import java.util.List;

/**
 * Artemij Chugreev
 * Date: 27.05.12
 * Time: 18:11
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Main {

    public static void main(String[] args) {
        Crawler crawler = new Crawler("http://pipeinpipe.info/forum", 2);
        crawler.crawl();
        RevInd revInd = new RevInd(Settings.SOURCE_PAGES_DIR);
        FinderWrapper finderWrapper = new FinderWrapper(revInd);
        List<String> relevantPages = finderWrapper.search("пайп");
        for(String str : relevantPages){
            System.out.println(str);
        }
    }
}
