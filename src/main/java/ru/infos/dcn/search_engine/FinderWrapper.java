package ru.infos.dcn.search_engine;

import ru.infos.dcn.reverted_index.Finder;
import ru.infos.dcn.reverted_index.RevInd;
import ru.infos.dcn.search_engine.beans.Page;
import ru.infos.dcn.util.unmarshaller.UnmarshallerWrapper;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Artemij Chugreev
 * Date: 27.05.12
 * Time: 20:27
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class FinderWrapper {
    private final Finder finder;

    public FinderWrapper(RevInd revInd) {
        this.finder = new Finder(revInd);
    }

    /**
     * wrap for method searchByQuery
     * @param query for search
     * @return list of relevant pages
     */
    public List<String> search(String query) {
//        search relevant files
        final List<String> filePaths = finder.searchByQuery(query);
        final List<String> relevantPages = new ArrayList<String>();
//        read page urls from this files
        try {
            UnmarshallerWrapper unmarshallerWrapper = new UnmarshallerWrapper(Page.class);
            Page page;
            for (String filePath : filePaths) {
                page = unmarshallerWrapper.unmarshall(Consts.SOURCE_PAGES_DIR + File.separator + filePath);
                relevantPages.add(page.getUrl());
            }
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return relevantPages;
    }
}
