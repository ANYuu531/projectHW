package org.example2.HW3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class KeelungSightsCrawler {
    private static final String BASE_URL = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";

    public List<Sight> getItems(String area) {
        List<Sight> sights = new ArrayList<>();
        String url = "https://www.travelking.com.tw";

        try {
            // 取得景點列表
            Document document = Jsoup.connect(BASE_URL).get();
            Element sightElement = document.selectFirst("div#guide-point");
            // 取得各個區的名字
            Elements zones = sightElement.select("h4");

            // 取得每個區的景點超連結並二次爬蟲
            for (Element zone : zones) {
                // 取得該區的ul元素（F12發現結構為 h4區 + ul景點 ）
                String zoneName = zone.text();
                Elements zoneTitle = document.select("h4:contains(" + zoneName + ")");
                Element targetSection = zoneTitle.first().nextElementSibling();
                Elements links = targetSection.select("a");
                // 取得ul的所有超連結
                for(Element link : links){
                    String linkHref = link.attr("href");
                    Sight sight = crawlSightDetails(url + linkHref);
                    if (sight != null) {
                        sight.setZone(zoneName);
                        sights.add(sight);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sights;
    }

    private Sight crawlSightDetails(String url) {
        try {
            Document document = Jsoup.connect(url).get();

            // 取得連結中景點的所有資訊（除了Zone不用）
            Element sightInf = document.selectFirst("div#point_area");

            // 取得景點名稱 SightName
            Elements nameElement = sightInf.select("meta[itemprop=name]");
            String name = nameElement.attr("content");
            // 取得景點類別 Category
            Element categoryElement = sightInf.selectFirst(".point_type strong");
            String category = categoryElement.text();
            // 取得景點照片 PhotoURL
            Elements photoURLElement = sightInf.select("meta[itemprop=image]");
            String photoURL = photoURLElement.attr("content");
            if(photoURL == "") photoURL = "https://www.ntou.edu.tw/NTOUweb/img/about/ntoulogo.gif";
            // 取得景點描述 Description
            Elements descriptionElement = sightInf.select("meta[itemprop=description]");
            String description = descriptionElement.attr("content");
            if(description.startsWith("旅遊王編輯組")) description = "";
            // 取得景點地址 Address
            String h3 = "地址";
            Elements addressElement = sightInf.select("meta[itemprop=address]");
            String address = addressElement.attr("content");

            return new Sight(name, category, photoURL, description, address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Autowired
    private SightRepository sightRepository;


    private List<Sight> sights = new ArrayList<>();

    public List<Sight> getSights() {
        return sights;
    }

    @PostConstruct
    public void crawlSightsOnStartup() {
        // 執行爬蟲並將結果存儲在 sight 中
        sights = getItems("七堵"); // 此處需要根據實際情況修改
        ///* mongoDB
        for (Sight sight : sights) {
            sightRepository.save(sight);
        }

    }
}
