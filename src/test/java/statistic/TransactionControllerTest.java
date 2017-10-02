package statistic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


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
