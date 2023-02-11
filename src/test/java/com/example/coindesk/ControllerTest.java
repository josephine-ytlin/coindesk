package com.example.coindesk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
class ControllerTest {
	
	@Autowired
	private MockMvc mvc;

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//	}

	@Test
	void getCoinById() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/newCoindesk/1");
		MvcResult result = mvc.perform(request).andReturn();
//		assertEquals
	}

}
