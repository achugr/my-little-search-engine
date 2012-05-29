package ru.infos.dcn.search.engine;

import ru.infos.dcn.search.engine.beans.Page;
import ru.infos.dcn.util.search.engine.PageUtil;

/**
 * Artemij Chugreev
 * Date: 27.05.12
 * Time: 17:35
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class PageProvider {

    private final Parser parser = new Parser();

    public Page provide(String url){
        Page page = parser.parse(new Downloader().download(url), url);
        PageUtil.save(page);
        return page;
    }

}
