package com.example.coindesk;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.example.coindesk.entity.Coindesk;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest{

    @Autowired
    public static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	public MockMvc mvc;
	
	@Test
	void getCointest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/myapp/newCoindesk/USD");
		mvc.perform(request).andDo(print()).andExpect(status().isOk());
		
	}
	
	@Test
	void createCoinTest() throws Exception {
		Coindesk testCoin = new Coindesk(
				"發大財", 
				"66666", 
				"MONEY", 
				"2023/03/01 00:00:00");
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(testCoin);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/myapp/newCoindesk")
				.content(json)
                .contentType(MediaType.APPLICATION_JSON);
		mvc.perform(request).andExpect(status().isCreated());

		
	}
	
	@Test
	void updateCoinTest() throws Exception {
		
		String type = "USD";
		
		Coindesk updateUsCoin = new Coindesk(
				"美金", 
				"66666", 
				"USD", 
				"2023/03/01 00:00:00");
		
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(updateUsCoin);
		
		RequestBuilder request = MockMvcRequestBuilders.put("/myapp/newCoindesk/{type}", type)
				.content(json)
                .contentType(MediaType.APPLICATION_JSON);
		mvc.perform(request).andDo(print()).andExpect(status().isOk());


		
	}
	
	@Test
	void deleteCoinTest() throws Exception {
		String type = "USD";

		RequestBuilder request = MockMvcRequestBuilders.delete("/myapp/newCoindesk/{type}", type);
		mvc.perform(request).andExpect(status().isNoContent());
		
	}
	
	@Test
	void getOriginalDataTest() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.get("/myapp/originalCoindesk");
		mvc.perform(request).andDo(print()).andExpect(status().isOk());
		
	}
	
	@Test
	void getAllCoinTest() throws Exception {
	
		RequestBuilder request = MockMvcRequestBuilders.get("/myapp/newCoindesk");
		mvc.perform(request).andDo(print()).andExpect(status().isOk());
	}

}
