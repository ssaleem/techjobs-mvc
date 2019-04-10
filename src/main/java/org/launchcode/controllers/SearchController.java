package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController extends TechJobsController{

    private static String searchTypeSelection = "all";

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("searchTypeSelection", searchTypeSelection);
        return "search";
    }

    @RequestMapping(value = "results")
    public String processSearchRequest(
            Model model,
            @RequestParam String searchType,
            @RequestParam(required = false) String searchTerm){

        ArrayList<HashMap<String, String>> results;

        if(searchTerm.equals("")){
            model.addAttribute("emptySearchTerm", true);
        }
        else {
            if(searchType.equals("all")){
                results = JobData.findByValue(searchTerm);
            }
            else {
                results = JobData.findByColumnAndValue(searchType, searchTerm);
            }


            model.addAttribute("jobCount", results.size());
            model.addAttribute("jobs", results);
        }

        searchTypeSelection = searchType;
        model.addAttribute("searchTypeSelection", searchTypeSelection);
        return "search";
    }

}
