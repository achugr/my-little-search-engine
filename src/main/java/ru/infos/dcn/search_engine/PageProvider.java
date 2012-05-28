package ru.infos.dcn.search_engine;

import ru.infos.dcn.search_engine.beans.Page;
import ru.infos.dcn.util.marshaller.MarshallerWrapper;
import sun.security.provider.MD5;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Artemij Chugreev
 * Date: 27.05.12
 * Time: 17:35
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class PageProvider {
    private final Parser parser;
    private final Page page = new Page();
    private final String url;

    public PageProvider(final String url){
        this.url = url;
//        init new parser with downloaded page
        this.parser = new Parser(new Downloader().download(url), url);
    }

    public void provide(){
        parser.parseLinks();
        page.getInnerLinks().addAll(parser.getInnerLinks());
        page.getOuterLinks().addAll(parser.getOuterLinks());
        page.setText(parser.extractText());
        page.setUrl(url);
        savePage();
    }

    public Page getPage(){
        return page;
    }

    /**
     * marshall provided page to file
     */
    private void savePage() {
        try {
            MarshallerWrapper marshallerWrapper = new MarshallerWrapper(Page.class);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, messageDigest.digest(url.getBytes()));
            marshallerWrapper.marshal(page, Consts.SOURCE_PAGES_DIR + File.separator + hash.toString() + ".xml");
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
