package com.htsat.search.web;

import com.htsat.search.dto.StatusDTO;
import com.htsat.search.enums.ExcuteStatusEnum;
import com.htsat.search.service.ISearchService;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.StringUtils;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    Logger logger = Logger.getLogger(SearchController.class);

    @Autowired
    ISearchService searchService;

    @RequestMapping(value = "/{version}/search/products/{query}/{page_size}/{page_num}/{sort}/{search}", method = RequestMethod.GET)
    @ResponseBody
    public StatusDTO updateOrderStatusAndPaymentMethodByPayment(@PathVariable("query")String query, @PathVariable("page_size")int page_size,
                                                                @PathVariable("page_num")int page_num, @PathVariable("sort")String sort, @PathVariable("search")String order) {
        StatusDTO status = new StatusDTO();
        status.setUserId(1);
        status.setStatus(ExcuteStatusEnum.SUCCESS);
        logger.info("query : " + query + "   page_size : " + page_size + "   page_num : "+ page_num + "   sort : "+ sort +"   search : " + order);
        logger.info("call success!!!");
        return status;
    }



    @Autowired
    private SolrClient client;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public void searchBySolr(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page_size", required = false) Integer page_size,
                             @RequestParam(value = "page_num", required = false) Integer page_num, @RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "order", required = false) String order) {

        logger.info("query : " + query + "     " + "page_size : " + page_size + "     " + "page_num : " + page_num + "     " + "sort : " + sort + "     " + "order : " + order);

        ModifiableSolrParams params =new ModifiableSolrParams();
        if (!StringUtils.isEmpty(query))
            params.add("q",query);

        params.add("wt","json");

        int start;
        if(page_num != null && page_num > 0 && page_size != null && page_size >= 0) {
            start = page_size * (page_num - 1) ;
            params.add("start", (start + ""));
        }

        if(page_size != null && page_size >= 0) {
            params.add("rows",(page_size + ""));
        }

        if (!StringUtils.isEmpty(sort))
            params.add("sort", sort);

        QueryResponse response=null;
        try{
            response=client.query(params);
            SolrDocumentList results = response.getResults();
            logger.info("results : " + results.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(response.toString());
        logger.info(response.toString());
    }

}
