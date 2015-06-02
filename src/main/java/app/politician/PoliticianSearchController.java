package app.politician;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoliticianSearchController {

    @Autowired
    PoliticianSearcher politicianSearcher;

    @RequestMapping("/politicians")
    public List<PoliticianDocument> politicians(@RequestParam(required = false) String partyUrlName) {
        if (StringUtils.isNotBlank(partyUrlName)) {
            return politicianSearcher.findPoliticiansByPartyUrlName(partyUrlName);
        } else {
            return politicianSearcher.findPoliticians();
        }
    }
}
