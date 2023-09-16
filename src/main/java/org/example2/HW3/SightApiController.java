package org.example2.HW3;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class SightApiController {

    @GetMapping("/")
    public String home() {
        try {
            // 讀取 HW4.html 文件內容並返回給瀏覽器
            Resource resource = new ClassPathResource("static/HW4.html");
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error loading HTML file";
        }
    }

    /*  爬蟲
    private final KeelungSightsCrawler sightsCrawler;

    public SightApiController(KeelungSightsCrawler sightsCrawler) {
        this.sightsCrawler = sightsCrawler;
    }

    @GetMapping("/SightAPI")
    public List<Sight> getSightsByZone(@RequestParam String zone) {
        // 使用 KeelungSightsCrawler 進行爬蟲並取得相應區域的景點資訊
        List<Sight> allSights = sightsCrawler.getSights();
        List<Sight> sights = allSights.stream()
                .filter(s -> s.getZone().contains(zone))
                .toList();

        return sights;
    }


    //*/

    ///*  mongoDB
    private final SightRepository sightRepository;

    public SightApiController(SightRepository sightRepository) {
        this.sightRepository = sightRepository;
    }
    @GetMapping("/SightAPI")
    public List<Sight> getSightsByZone(@RequestParam String zone) {
        // 使用 SightRepository 進行查詢，取得相應區域的景點資訊
        if(zone.contains("區")){
        }else{
            zone = zone + "區";
        }
        List<Sight> sights = sightRepository.findByZone(zone);
        return sights;
    }
    //*/

}
