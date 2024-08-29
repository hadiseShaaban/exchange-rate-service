package com.example.exchange_rate_service.util;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ECBRateFetcher {
    private static final String ECB_XML_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private final Map<String, Double> rates = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            URL url = new URL(ECB_XML_URL);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
            doc.getDocumentElement().normalize();

            NodeList timeNodes = doc.getElementsByTagName("Cube");
            for (int i = 0; i < timeNodes.getLength(); i++) {
                Node timeNode = timeNodes.item(i);

                if (timeNode.getAttributes().getNamedItem("time") != null) {

                    NodeList currencyNodes = timeNode.getChildNodes();
                    for (int j = 0; j < currencyNodes.getLength(); j++) {
                        Node currencyNode = currencyNodes.item(j);

                        if (currencyNode.getNodeType() == Node.ELEMENT_NODE) {
                            String currency = currencyNode.getAttributes().getNamedItem("currency").getNodeValue();
                            double rate = Double.parseDouble(currencyNode.getAttributes().getNamedItem("rate").getNodeValue());
                            rates.put(currency, rate);
                        }
                    }
                }
            }
            rates.put("EUR", 1.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getRate(String currency) {
        return rates.getOrDefault(currency, 0.0);
    }

    public List<String> getSupportedCurrencies() {
        return rates.keySet().stream().sorted().collect(Collectors.toList());
    }
}
