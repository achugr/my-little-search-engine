package ru.infos.dcn.search_engine;

import ru.infos.dcn.search_engine.beans.Page;
import ru.infos.dcn.util.marshaller.MarshallerWrapper;

import javax.xml.bind.JAXBException;
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
    private Page page;
    private String url;
    private final Parser parser = new Parser();


    public void provide(String url) {
        this.url = url;
        this.page = parser.parse(new Downloader().download(url), url);
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
            marshallerWrapper.marshal(page, Settings.SOURCE_PAGES_DIR + File.separator + hash.toString() + ".xml");
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
