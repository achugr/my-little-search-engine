package ru.infos.dcn.search.engine;

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
