package com.example.coindesk;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.coindesk.repository.CoindeskRepository;



//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CoindeskApplication.class)
//@WebAppConfiguration
//@WebMvcTest(Controller.class)


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest{
//	
//	@Autowired
//	@MockBean
//	CoindeskRepository cr;
//	
//	@Autowired
//	private WebApplicationContext webApplicationContext;
	
	@Autowired
	public MockMvc mvc;

//	@Before
//	public void setUpBeforeClass() throws Exception {
//		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
	
	@Test
	void getCointest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/myapp/newCoindesk/USD");
		mvc.perform(request).andDo(print()).andExpect(status().isOk());
		
	}
	
	
	
	
	@Test
	void getOriginalDataTest() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.get("/myapp/originalCoindesk");
		mvc.perform(request).andDo(print()).andExpect(status().isOk());
		
	}
	
	@Test
	void getAllCoinTest() throws Exception {
		
//		mockMvc.perform(
//                MockMvcRequestBuilders
//                        .delete("/data/domain")
//                        .content(objectMapper.writeValueAsString(dataDomain))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
		
		
		
		RequestBuilder request = MockMvcRequestBuilders.get("/myapp/newCoindesk");
		mvc.perform(request).andDo(print()).andExpect(status().isOk());
//				;
//		MockHttpServletResponse response = result.getResponse();
//		System.out.println(response.getContentAsString());
//
//		assertEquals(200,response.getStatus());
	}

}
