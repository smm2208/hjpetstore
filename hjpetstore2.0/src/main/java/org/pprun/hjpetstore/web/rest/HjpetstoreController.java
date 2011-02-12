/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.persistence.jaxb.Products;
import org.pprun.hjpetstore.service.rest.HjpetstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * A RESTful controller supplies search products by keyword which will be exposed to public access.
 * The access authenticated by api-key and secret key via OAuth as Amazon Web Service.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Controller
public class HjpetstoreController extends BaseController {

    private static final Log log = LogFactory.getLog(HjpetstoreController.class);
    private HjpetstoreService hjpetstoreService;

    @Required
    @Autowired
    public void setHjpetstoreService(HjpetstoreService hjpetstoreService) {
        this.hjpetstoreService = hjpetstoreService;
    }

    /**
     * RESTful match path '/products/{keyword}' with apikey as request parameter.
     *
     * <p>
     * For example: user pprun <br />
     * {@code 
     * curl -u pprun:pprunpprun -H 'Content-Type: application/xml' -H 'Accept: application/xml' 'http://localhost:8080/hjpetstore/rest/products/dog?apikey=bc7163dab8eb79a9867b4604b46b0328e9ace555ef5d9526e1fcd748f9864bf85d59e97c044a2d9795736753c2b0d77cd085eb05d854e5849f42f37f85851220&signature=&page=1&max=100'
     * }
     *
     * @param apiKey
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/products/{keyword}", method = RequestMethod.GET)
    public ModelAndView getProductsByKeyword(
            //@RequestParam("apikey") String apiKey,
            @RequestParam("page") int page,
            @RequestParam("max") int max,
            @PathVariable("keyword") String keyword) {

        if (log.isDebugEnabled()) {
            log.debug("HjpetstoreController is processing request for keyword: " + keyword);
        }
        Products products = hjpetstoreService.searchProductList(keyword, page, max);

        ModelAndView mav = new ModelAndView("products");
        mav.addObject(products);
        return mav;
    }
}
