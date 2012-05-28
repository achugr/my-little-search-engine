package ru.infos.dcn.search_engine;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Artemij Chugreev
 * Date: 25.05.12
 * Time: 0:59
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class Parser {
    private final Set<String> allLinks = new HashSet<String>();
    private final Set<String> innerLinks = new HashSet<String>();
    private final Set<String> outerLinks = new HashSet<String>();

    private final Document document;
    private URL url;

    public Parser(Document document, String url) {
        this.document = document;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * extract text from body tag
     * @return text in body tag
     */
    public String extractText() {
        return document.text();
    }

    /**
     * extract all links from page
     */
    public void extractLinks() {
        Elements links = document.select("a[href]");

        // href ...
        for (Element link : links) {
            allLinks.add(link.attr("abs:href"));
        }
    }

    /**
     * group links by type
     */
    public void processLinks() {
        URL linkWrap;
        for (String link : allLinks) {
            try {
                linkWrap = new URL(link);
//                link is inner condition
                if (linkWrap.getHost().equals(url.getHost()) || "".equals(linkWrap.getHost())) {
                    innerLinks.add(link);
                } else {
                    outerLinks.add(link);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public Set<String> getInnerLinks(){
        return innerLinks;
    }

    public Set<String> getOuterLinks(){
        return outerLinks;
    }

    public void parseLinks() {
        this.extractLinks();
        this.processLinks();
    }
}
