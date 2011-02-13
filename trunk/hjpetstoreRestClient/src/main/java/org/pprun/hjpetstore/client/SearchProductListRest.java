/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.client;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.transform.Source;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.input.DOMBuilder;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;
import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Unit Test using RestTemplate to call public service.
 * 
 * @author pprun
 */
public class SearchProductListRest {
    private static Log log = LogFactory.getLog(SearchProductListRest.class);
    
    private static final String ZONE_DATE_FORMAT = "EEE, yyyy-MM-dd HH:mm:ss zzz";
    private RestOperations restTemplate;
    private XPathOperations xpathTemplate;
    private String apiKey;
    private String secretKey;

    @Required
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Required
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public SearchProductListRest(RestOperations restTemplate, XPathOperations xpathTemplate) {
        this.restTemplate = restTemplate;
        this.xpathTemplate = xpathTemplate;
    }

    public void searchProductList(String keyword, String apikey, int page, int max) {
        List<BufferedImage> images = searchProductImage(keyword, apikey, page, max);
        displayImage(keyword, images);
    }

    @SuppressWarnings("unchecked")
    private List<BufferedImage> searchProductImage(String keyword, String apikey, int page, int max) {

        final String restUri = "http://localhost:8080/hjpetstore/rest/products/{keyword}?page={page}&max={max}";
        //the server need authenticate, we have set the http header

        HttpHeaders requestHeaders = createHttpHeader();

        String url = buildURL(HttpMethod.GET, requestHeaders, restUri, keyword, page, max);
        HttpEntity<String> requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<Source> response = restTemplate.exchange(url,
                HttpMethod.GET,
                requestEntity,
                Source.class,
                keyword, page, max);
        Source product = response.getBody();

        //Source product = restTemplate.getForObject(url, Source.class, keyword, page, max);

        final String imageUrl = "http://localhost:8080/hjpetstore/images/{imageFileName}";

        List<BufferedImage> images = (List<BufferedImage>) xpathTemplate.evaluate("//image", product, new NodeMapper() {

            @Override
            public Object mapNode(Node node, int i) throws DOMException {
                //Element image = (Element) node;
                org.jdom.Element image = new DOMBuilder().build((Element) node);
                String imageFileName = image.getValue();
                //String imageFileName = image.getFirstChild().getNodeValue();

                return restTemplate.getForObject(imageUrl, BufferedImage.class, imageFileName);
            }
        });

        return images;
    }


    private String buildURL(HttpMethod httpVerb, HttpHeaders requestHeaders, String path, Object... urlVariables) {
        // construct the url
        String originPath = path; // make a copy because the process still need parametered path outside this method,
        if (urlVariables != null) {
            // if the path contains variable, expand it
            // http://localhost:8080/hjpetstore/rest/paymentpartner/{name}

            UriTemplate uriTemplate = new UriTemplate(path);
            URI expanded = uriTemplate.expand(urlVariables);
            path = expanded.getRawPath();
            if (log.isDebugEnabled()) {
                log.debug("expanded path = " + path);
            }
        }

        String httpMethod = httpVerb.toString();
        String date = requestHeaders.getFirst("Date");
        String signature = MessageDigestUtil.calculateSignature(httpMethod, date, path, secretKey);
        try {
            StringBuilder s = new StringBuilder();
            s.append(originPath);
            s.append("&apiKey=");
            s.append(URLEncoder.encode(apiKey, "UTF-8"));
            s.append("&signature=");
            s.append(URLEncoder.encode(signature, "UTF-8"));
            if (log.isDebugEnabled()) {
                log.debug(s.toString());
            }

            return s.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("UnsupportedEncodingException when URLEncoder.encode", ex);
        }
    }

    /**
     * help method create HTTP headers.
     * @return
     */
    private HttpHeaders createHttpHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        // Accept
        requestHeaders.set("Accept", "application/xml");
        requestHeaders.set("Accept-Charset", "UTF-8");

        // Date
        String date = getDateStringWithZone(Calendar.getInstance(), ZONE_DATE_FORMAT, TimeZone.getTimeZone("UTC"), Locale.US);
        requestHeaders.set("Date", date);

        return requestHeaders;
    }
// now use OAuth, instead of Basic Authentication
//    /**
//     * If the server need authenticate, we have set the http header.
//     * @return
//     */
//    private HttpEntity<?> setHttpHeader() {
//        HttpHeaders requestHeaders = new HttpHeaders();
//        // Authorization
//        String userPassword = "pprun" + ":" + "pprunpprun";
//        String encoding = new String(Base64.encodeBase64(userPassword.getBytes()), Charset.forName("UTF-8"));
//        requestHeaders.set("Authorization", "Basic " + encoding);
//        // Accept
//        requestHeaders.set("Accept", "application/xml");
//        requestHeaders.set("Accept-Charset", "UTF-8");
//
//        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
//        return requestEntity;
//    }

    private void displayImage(String keyword, List<BufferedImage> imageList) {
        JFrame frame = new JFrame("HJpetstore " + keyword + " images");
        frame.setLayout(new GridLayout(3, imageList.size() / 3));
        for (BufferedImage image : imageList) {
            frame.add(new JLabel(new ImageIcon(image)));
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

        public static String getDateStringWithZone(Calendar cal, String format, TimeZone timeZone, Locale locale) {
        if (cal == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        sdf.setTimeZone(timeZone);
        return sdf.format(cal.getTime());
    }
}
