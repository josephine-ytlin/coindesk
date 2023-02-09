package com.example.coindesk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.coindesk.pojo.constant.ResponseConstant;
import com.example.coindesk.entity.Coindesk;



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
            String updateTime =timeJson.getAsJsonObject().get(ResponseConstant.UPDATED).getAsString();
            SimpleDateFormat parser = new SimpleDateFormat("YYYY/MM/DD HH:mm:ss");
            Date date = parser.parse(updateTime);
            coinDesk.setUpdate_date(date);
            
            JsonElement bpiJson = json.getAsJsonObject().get(ResponseConstant.BPI);

            JsonObject jsonUS = bpiJson.getAsJsonObject().get(ResponseConstant.USD).getAsJsonObject();
            String codeUS =jsonUS.get(ResponseConstant.CODE).getAsString();
            String rateUS =jsonUS.get(ResponseConstant.RATE).getAsString();
            coinDesk.setType(codeUS);
            coinDesk.setRate(Integer.parseInt(rateUS));
            coinDesk.setName(ResponseConstant.USD_tw);
            

            JsonObject jsonGBP = bpiJson.getAsJsonObject().get(ResponseConstant.GBP).getAsJsonObject();
            String codeGBP =jsonGBP.get(ResponseConstant.CODE).getAsString();
            String rateGBP =jsonGBP.get(ResponseConstant.RATE).getAsString();
            coinDesk.setType(codeGBP);
            coinDesk.setRate(Integer.parseInt(rateGBP));
            coinDesk.setName(ResponseConstant.GBP_tw);

            
            
            JsonObject jsonEUR = bpiJson.getAsJsonObject().get(ResponseConstant.EUR).getAsJsonObject();
            String codeEUR =jsonEUR.get(ResponseConstant.CODE).getAsString();
            String rateEUR =jsonEUR.get(ResponseConstant.RATE).getAsString();
            coinDesk.setType(codeEUR);
            coinDesk.setRate(Integer.parseInt(rateEUR));
            coinDesk.setName(ResponseConstant.EUR_tw);



            System.out.print("wait");
            
            
        	
        } catch (Exception e) {
            logger.info("Exception -" + e);
        }
        
		return objects;
	   }

}
