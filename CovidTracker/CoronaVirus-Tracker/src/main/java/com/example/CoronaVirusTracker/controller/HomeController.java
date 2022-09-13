package com.example.CoronaVirusTracker.controller;


import com.example.CoronaVirusTracker.Service.CoronaVirusDataService;
import com.example.CoronaVirusTracker.models.LocationStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model)
    {
        List<LocationStats> allStats =coronaVirusDataService.getAllStats();
        int totalcases=0;
        int lastdaytotalcases=0;
        for(int i=1;i<allStats.size();i++)
        {
            totalcases=totalcases+Integer.parseInt(allStats.get(i).getLatestTotal());
            lastdaytotalcases=lastdaytotalcases+allStats.get(i).getDiffFromPrevDay();
        }
      //  System.out.println("kanishk "+totalcases);
        model.addAttribute("localcationStats",coronaVirusDataService.getAllStats());
        model.addAttribute("totalReportedCases",totalcases);
        model.addAttribute("lastdaytotalcases",lastdaytotalcases);

        return "home";
    }
}
