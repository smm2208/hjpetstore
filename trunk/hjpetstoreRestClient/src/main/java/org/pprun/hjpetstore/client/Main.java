package org.pprun.hjpetstore.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello RESTful!
 *
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java " + Main.class.getName() + " [keyword]");
            System.exit(-1);
        }
        String keyword = args[0];

        // use j2ee default user's apikey
        
        String apikey = "bc7163dab8eb79a9867b4604b46b0328e9ace555ef5d9526e1fcd748f9864bf85d59e97c044a2d9795736753c2b0d77cd085eb05d854e5849f42f37f85851220";

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SearchProductListRest client = applicationContext.getBean("searchProductListRest", SearchProductListRest.class);
        // 0 means use the default pagination values at server side
        int page = 0;
        int max = 0;
        client.searchProductList(keyword, apikey, page, max);
    }
}
