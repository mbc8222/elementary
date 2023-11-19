package controller;

import bean.elementaryDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import service.elementaryServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class elementaryController {
    @Autowired
    elementaryServiceImpl es;

    elementaryDto dto;

    @RequestMapping(value = "/test.do")
    public void test(){
        System.out.println("test controller");
    }

    @RequestMapping(value = "/dbtest.do")
    public void dbtest(){
        System.out.println("dbtest controller start");
        String result = es.test();
        System.out.println("dbtest controller finish : " + result);
    }

    @RequestMapping(value = "/api.do")
    public ModelAndView roadAPI(){
        ModelAndView mv = new ModelAndView();
        int result = 0;
        result = es.elementaryinsert();
        return mv;
    }
}
