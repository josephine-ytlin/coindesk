package com.example.coindesk.util;

import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.example.coindesk.pojo.constant.ResponseConstant;
import com.example.coindesk.entity.Coindesk;



public class CoinDeskAPI {
	
	
    private static final Logger logger = LoggerFactory.getLogger(CoinDeskAPI.class);

    
    public static Coindesk setCoin(JsonObject json, String type, String name) {
    	logger.info("saveOrupdate orignal data in h2");
    	
        Coindesk coinDesk = new Coindesk();

		JsonElement timeJson = json.getAsJsonObject().get(ResponseConstant.TIME);
		String updateTime = timeJson.getAsJsonObject().get(ResponseConstant.UPDATEDISO).getAsString().substring(0, 19);

		JsonElement bpiJson = json.getAsJsonObject().get(ResponseConstant.BPI);

		JsonObject jsonType = bpiJson.getAsJsonObject().get(type).getAsJsonObject();
		String codeType = jsonType.get(ResponseConstant.CODE).getAsString();
		String rateType = jsonType.get(ResponseConstant.RATE).getAsString();
		coinDesk.setType(codeType);
		coinDesk.setRate(rateType);
		coinDesk.setName(name);
		
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dt = formatter.parseDateTime(updateTime).toString("YYYY/MM/dd HH:mm:ss");
        coinDesk.setUpdateDate(dt);
        
        return coinDesk;
        
    }


}
