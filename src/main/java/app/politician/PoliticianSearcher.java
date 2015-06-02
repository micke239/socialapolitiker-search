package app.politician;

import java.util.List;

public interface PoliticianSearcher {

    List<PoliticianDocument> findPoliticiansByPartyUrlName(String partyUrlName);

    List<PoliticianDocument> findPoliticians();

}
