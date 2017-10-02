package statistic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import statistic.dto.StatisticsResponseDTO;
import statistic.dto.TransactionDTO;

@RunWith(SpringRunner.class)
public class TimeArrayTest {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@InjectMocks
	private TimeArray timeArray;
	
	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_if_one_trx_result_is_correct() {
		long now = System.currentTimeMillis();
		TransactionDTO trx = new TransactionDTO();
		
		trx.setAmount(110.3);
		trx.setTimestamp(now-3000);
		
		timeArray.add(trx);
		StatisticsResponseDTO statsDTO = timeArray.calculate();
		
		assertTrue("avg is wrong", statsDTO.getAvg()==110.3);
		assertTrue("sum is wrong",statsDTO.getSum()==110.3);
		assertTrue("max is wrong",statsDTO.getMax()==110.3);
		assertTrue("min is wrong",statsDTO.getMin()==110.3);
		assertTrue("count is wrong",statsDTO.getCount()==1);
	}
	
	@Test
	public void test_if_old_time_not_added() {
		long now = System.currentTimeMillis();
		TransactionDTO trx = new TransactionDTO();
		
		trx.setAmount(110.3);
		trx.setTimestamp(now-60001);
		
		assertFalse("Older than 60 seconds transanction added error",timeArray.add(trx));
		
		trx.setTimestamp(now+1);
		assertFalse("Future transanction added error",timeArray.add(trx));
	}
	
	@Test
	public void test_if_returns_false_while_fields_are_empty() {
		TransactionDTO trx = new TransactionDTO();
		assertFalse("Resturn value is wrong while fields are empty",timeArray.add(trx));
		trx.setAmount(0.1);
		assertFalse("Resturn value is wrong while time is empty",timeArray.add(trx));
	}
	
	@Test
	public void test_if_none_trx_result_is_correct() {
		StatisticsResponseDTO statsDTO = timeArray.calculate();
		
		assertTrue("avg is wrong", statsDTO.getAvg()==0);
		assertTrue("sum is wrong",statsDTO.getSum()==0);
		assertTrue("max is wrong",statsDTO.getMax()==0);
		assertTrue("min is wrong",statsDTO.getMin()==0);
		assertTrue("count is wrong",statsDTO.getCount()==0);
	}
	
	@Test()
	public void test_if_one_trx_result_is_correct_after_expired() {
		long time = System.currentTimeMillis()-50000;
		TransactionDTO trx = new TransactionDTO();
		
		trx.setAmount(110.35);
		trx.setTimestamp(time);
		
		timeArray.add(trx);
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			LOG.error("Error in sleep",e);
		}
		
		StatisticsResponseDTO statsDTO = timeArray.calculate();
		
		assertEquals("avg is wrong",0 ,statsDTO.getAvg(),Double.NaN);
		assertEquals("min is wrong",0 ,statsDTO.getMin(),Double.NaN);
		assertEquals("max is wrong",0 ,statsDTO.getMax(),Double.NaN);
		assertEquals("count is wrong",0 ,statsDTO.getCount());
		assertEquals("sum is wrong",0 ,statsDTO.getSum(),Double.NaN);
	}
	
	
	@Test()
	public void test_if_many_trx_result_is_correct_after_some_expired() {
		long now = System.currentTimeMillis();
		
		TransactionDTO trx = new TransactionDTO();
		trx.setAmount(12);
		trx.setTimestamp(now-50000);

		TransactionDTO trx1 = new TransactionDTO();
		trx1.setAmount(25);
		trx1.setTimestamp(now-49000);

		TransactionDTO trx2 = new TransactionDTO();
		trx2.setAmount(36);
		trx2.setTimestamp(now-48000);
		
		TransactionDTO trx3 = new TransactionDTO();
		trx3.setAmount(50);
		trx3.setTimestamp(now-47000);
		
		
		timeArray.add(trx); // this will be expired
		timeArray.add(trx1); // this will be expired
		timeArray.add(trx2);
		timeArray.add(trx3);
		
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			LOG.error("Error in sleep",e);
		}
		
		StatisticsResponseDTO statsDTO = timeArray.calculate();
		
		assertEquals("sum is wrong",(36+50) ,statsDTO.getSum(),Double.NaN);
		assertEquals("avg is wrong",(36+50)/2 ,statsDTO.getAvg(),Double.NaN);
		assertEquals("min is wrong",36 ,statsDTO.getMin(),Double.NaN);
		assertEquals("max is wrong",50 ,statsDTO.getMax(),Double.NaN);
		assertEquals("count is wrong",2 ,statsDTO.getCount());
		
	}
	
	@Test()
	public void test_if_negative_values_not_ok() {
		long now = System.currentTimeMillis();
		
		
		TransactionDTO trx = new TransactionDTO();
		trx.setAmount(-12.34);
		trx.setTimestamp(now-50000);
		
		timeArray.add(trx);
		
		StatisticsResponseDTO statsDTO = timeArray.calculate();
		
		assertTrue("avg is wrong", statsDTO.getAvg()==0);
		assertTrue("sum is wrong",statsDTO.getSum()==0);
		assertTrue("max is wrong",statsDTO.getMax()==0);
		assertTrue("min is wrong",statsDTO.getMin()==0);
		assertTrue("count is wrong",statsDTO.getCount()==0);
	}
	
	@Test()
	public void test_if_too_may_trx_result_is_correct() {
		long now = System.currentTimeMillis();
		
		class Adder extends Thread{
			private TransactionDTO trx;
			private TimeArray timeArray;
			
			Adder(TransactionDTO trx, TimeArray timeArray) {
				this.trx = trx;
				this.timeArray = timeArray;
			}
			@Override
			public void run() {
				timeArray.add(trx);
			}
		}
		
		double[] amounts = {12.1, 3.4, 4.7, 10.3 , 550};
		double expectedAvg = (double)(12.1+3.4+4.7+10.3+550)/5;
		double expectedMin = 3.4;
		double expectedMax = 550;
		long expectedCount = 100000*5;
		double expectedSum =  (double) ( (double)(12.1*100000) + (double)(3.4*100000) + 
				                         (double)(4.7*100000) + (double)(10.3*100000) + 
				                         (double)(550*100000) );
		
		int time = 1;
		
		for(double j : amounts) {
			
			TransactionDTO trx1 = new TransactionDTO();
			trx1.setAmount(j);
			trx1.setTimestamp(now- 30000 - time*1000);
			time++;

			for(int i=0;i<100000;i++) {
				Adder adder = new Adder(trx1,timeArray);
				adder.run();
			}
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			LOG.error("Error in sleep",e);
		}
		
		StatisticsResponseDTO statsDTO = timeArray.calculate();
		
		assertEquals("count is wrong",expectedCount ,statsDTO.getCount());
		assertEquals("sum is wrong",round(expectedSum) ,round(statsDTO.getSum()),Double.NaN);
		assertEquals("avg is wrong",round(expectedAvg) ,round(statsDTO.getAvg()),Double.NaN);
		assertEquals("min is wrong",expectedMin ,statsDTO.getMin(),Double.NaN);
		assertEquals("max is wrong",expectedMax ,statsDTO.getMax(),Double.NaN);
	}	
	
	private double round(double d) {
		return (double)Math.round(d*1000)/1000;
	}
}
