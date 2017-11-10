package com.htsat.search.web;

import com.htsat.search.dto.SearchResultDTO;
import com.htsat.search.dto.TestSearchResultDTO;
import com.htsat.search.service.ILoadBalanceService;
import com.htsat.search.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.StringUtils;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.bouncycastle.util.test.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SolrClient client;

    @Autowired
    private ISearchService searchService;

    @RequestMapping(value = "/v1/search", method = RequestMethod.GET)
    public List<String> searchBySolr(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page_size", required = false) Integer page_size,
                             @RequestParam(value = "page_num", required = false) Integer page_num, @RequestParam(value = "sort", required = false) String sort) {

        logger.info("query : " + query + "     " + "page_size : " + page_size + "     " + "page_num : " + page_num + "     " + "sort : " + sort);

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

        List<String> list = new ArrayList<>();
        try{
            response=client.query(params);
            SolrDocumentList results = response.getResults();
            logger.info("results : " + results.toString());

            for (SolrDocument solrDocument : results) {
                String id = (String) solrDocument.getFieldValue("id");
//                Integer productId = (Integer) solrDocument.getFieldValue("productid");
//                Integer cateId = (Integer) solrDocument.getFieldValue("cateid");
//                Integer price = (Integer) solrDocument.getFieldValue("price");
//                String url = (String) solrDocument.getFieldValue("url");
//                String version = (String) solrDocument.getFieldValue("_version_");
//                Integer quantity = (Integer) solrDocument.getFieldValue("quatity");
                String skuJson = searchService.getskuByRedis(id);


                list.add(skuJson);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("search failed");
            return null;
        }

        return list;
    }

    @Autowired
    ILoadBalanceService loadbalanceService;

    @RequestMapping(value = "/loadbalance")
    public String loadbalance(@RequestParam String name){
        return loadbalanceService.loadbalanceService(name);
    }

}
