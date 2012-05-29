package ru.infos.dcn.util.search.engine;

import ru.infos.dcn.search.engine.Settings;
import ru.infos.dcn.search.engine.beans.Page;
import ru.infos.dcn.util.marshaller.MarshallerWrapper;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Artemij Chugreev
 * Date: 29.05.12
 * Time: 10:38
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public abstract class PageUtil {

    static {
        if (!new File(Settings.SOURCE_PAGES_DIR).exists()) {
            new File(Settings.SOURCE_PAGES_DIR).mkdir();
        }
    }

    private PageUtil() {
    }

    public static void save(Page page) {
        try {
            MarshallerWrapper marshallerWrapper = new MarshallerWrapper(Page.class);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, messageDigest.digest(page.getUrl().getBytes()));
            marshallerWrapper.marshal(page, Settings.SOURCE_PAGES_DIR + File.separator + hash.toString() + ".xml");
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
