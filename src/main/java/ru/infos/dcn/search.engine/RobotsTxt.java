package ru.infos.dcn.search.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Artemij Chugreev
 * Date: 27.05.12
 * Time: 20:50
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class RobotsTxt {
    private static final String DISALLOW = "disallow";
    private static final String SPACE = "\\s";
    private static final String ROBOTS_TXT_FILE = "/robots.txt";

    private final Set<String> disallowedDirs;
    private String robotsTxtUrl;

    /**
     * init robots.txt of this site
     * @param startUrl of site
     */
    public RobotsTxt(String startUrl){
        this.disallowedDirs = new HashSet<String>();
        try {
            URL urlWrap = new URL(startUrl);
//            robots.txt file placed in root of site (like so: mySite.ru/robots.txt)
//            than we need host name
            this.robotsTxtUrl = urlWrap.getHost() + ROBOTS_TXT_FILE;
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    //TODO don't sure, that's effective method

    /**
     * check if link contain disallowed dir
     * @param link
     * @return
     */
    public boolean isBan(String link){
        try {
            URL urlWrap = new URL(link);
//            go on all disallowed dirs from robots.txt
            for(String disallowedDir : disallowedDirs) {
//                if url path contains disallowed dir => url is disallowed
                if(urlWrap.getPath().contains(disallowedDir)){
                    return true;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }

    /**
     * download
     */
    public void parse(){
        try {
            Document document = Jsoup.connect(robotsTxtUrl).get();
            String robotsTxt = document.text();
            StringTokenizer stringTokenizer = new StringTokenizer(robotsTxt, SPACE);
//            go on robots.txt
            while (stringTokenizer.hasMoreTokens()){
//                if current token contains "disallow" => next token is disallowed dir
                if(stringTokenizer.nextToken().toLowerCase().contains(DISALLOW)){
//                    add this dir do set of disallowed
                    disallowedDirs.add(stringTokenizer.nextToken());
                }
            }
        } catch (IllegalArgumentException e){
//            it means that site hasn't robots.txt
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
