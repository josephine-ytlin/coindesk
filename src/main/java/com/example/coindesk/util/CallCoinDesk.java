package com.example.coindesk.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.xml.bind.DatatypeConverter;

import com.example.coindesk.pojo.constant.ResponseConstant;
import com.example.coindesk.entity.Coindesk;
import java.time.format.DateTimeFormatter;



public class CallCoinDesk {
	
	@Autowired
	static
	RestTemplate restTemplate = new RestTemplate();
	
	
    private static final Logger logger = LoggerFactory.getLogger(CallCoinDesk.class);

	public static String callCoindeskAPI() {
        logger.info("get original data");
        Coindesk coinDesk = new Coindesk();
//    	String dt = new DateTime().toString("YYYY/MM/DD HH:mm:ss");  

		String objects = new String();
		
        try {
        	String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        	objects = restTemplate.getForObject(url, String.class);
        	
            JsonObject json = new Gson().fromJson(objects, JsonObject.class);

            JsonElement timeJson = json.getAsJsonObject().get(ResponseConstant.TIME);
            String updateTime =timeJson.getAsJsonObject().get(ResponseConstant.UPDATEDISO).getAsString().substring(0, 18);

            
            org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
            String dt = formatter.parseDateTime(updateTime).toString("YYYY/MM/DD HH:mm:ss");
            coinDesk.setUpdateDate(dt);
            
            JsonElement bpiJson = json.getAsJsonObject().get(ResponseConstant.BPI);

            JsonObject jsonUS = bpiJson.getAsJsonObject().get(ResponseConstant.USD).getAsJsonObject();
            String codeUS =jsonUS.get(ResponseConstant.CODE).getAsString();
            String rateUS =jsonUS.get(ResponseConstant.RATE).getAsString();
            coinDesk.setType(codeUS);
            coinDesk.setRate(rateUS);
            coinDesk.setName(ResponseConstant.USD_tw);
            

            JsonObject jsonGBP = bpiJson.getAsJsonObject().get(ResponseConstant.GBP).getAsJsonObject();
            String codeGBP =jsonGBP.get(ResponseConstant.CODE).getAsString();
            String rateGBP =jsonGBP.get(ResponseConstant.RATE).getAsString();
            coinDesk.setType(codeGBP);
            coinDesk.setRate(rateGBP);
            coinDesk.setName(ResponseConstant.GBP_tw);

            
            
            JsonObject jsonEUR = bpiJson.getAsJsonObject().get(ResponseConstant.EUR).getAsJsonObject();
            String codeEUR =jsonEUR.get(ResponseConstant.CODE).getAsString();
            String rateEUR =jsonEUR.get(ResponseConstant.RATE).getAsString();
            coinDesk.setType(codeEUR);
            coinDesk.setRate(rateEUR);
            coinDesk.setName(ResponseConstant.EUR_tw);



            System.out.print("wait");
            
            
        	
        } catch (Exception e) {
            logger.info("Exception -" + e);
        }
        
		return objects;
	   }

}
