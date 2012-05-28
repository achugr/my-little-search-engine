package ru.infos.dcn.search_engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;

/**
 * Artemij Chugreev
 * Date: 24.05.12
 * Time: 23:59
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Downloader {

    public Downloader(){
        if(!new File(Consts.SOURCE_PAGES_DIR).exists()){
            new File(Consts.SOURCE_PAGES_DIR).mkdir();
        }
    }

    /**
     * download page
     * @param stringUrl addres of page, you want to download
     * @return Document representation of this page
     */
    public Document download(String stringUrl){
        Document document = null;
        try {
            document = Jsoup.connect(stringUrl).get();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return document;
    }

}
