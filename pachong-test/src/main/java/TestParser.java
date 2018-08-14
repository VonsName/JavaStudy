import com.cv4j.netdiscovery.core.domain.Page;
import com.cv4j.netdiscovery.core.parser.Parser;
import com.cv4j.netdiscovery.core.parser.selector.Selectable;

import java.util.List;

/**
 * @author Administrator
 */
public class TestParser  implements Parser {
    @Override
    public void process(Page page) {
        String xpaht="//*[@id=\\\"grid\\\"]/div/div[1]/table/tbody/tr";
        List<Selectable> list=page.getHtml().xpath(xpaht).nodes();
        for(Selectable tr : list) {
            String companyName = tr.xpath("//td[@class='td_companyName']/text()").get();
            String positionName = tr.xpath("//td[@class='td_positionName']/a/text()").get();

            if(null != companyName && null != positionName) {
                System.out.println(companyName+"------"+positionName);
            }
        }

    }
}
