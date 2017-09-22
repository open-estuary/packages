package com.htsat.search.web;

import com.htsat.search.dto.StatusDTO;
import com.htsat.search.enums.ExcuteStatusEnum;
import com.htsat.search.service.ISearchService;
import org.apache.log4j.Logger;
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

}
