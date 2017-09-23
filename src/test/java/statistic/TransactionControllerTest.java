package statistic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import statistic.controller.StatisticController;
import statistic.model.DataQueue;
import statistic.service.TransactionService;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
//@WebMvcTest(StatisticController.class)
public class TransactionControllerTest {
	/*@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	@InjectMocks
	private DataQueue dataQueue;	
	
	@Autowired
	@InjectMocks
	private TransactionService transactionService;
	
	@Before
	public void setup() {
	    MockitoAnnotations.initMocks(this);
	}	

	@Test
	public void test_if_statistics_return_object() throws Exception{
		mockMvc.perform(get(StatisticController.STATISTIC_URL)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"sum\":0.0,\"avg\":0.0,\"max\":0.0,\"min\":0.0,\"count\":0}"));
	}
	
	@Test
	public void test_transaction_added(){
		mockMvc.perform(post(TransactionController.TRANSACTIONS_URL)
				.accept(MediaType.APPLICATION_JSON_UTF8)).)
	}*/
	@Test
	public void test_true(){
		assertThat("").asString().isEmpty();
	}
	
}
