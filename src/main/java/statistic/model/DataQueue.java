package statistic.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import com.google.common.collect.MinMaxPriorityQueue;

import statistic.dto.StatisticsResponseDTO;
import statistic.dto.TransactionDTO;

public class DataQueue {
	private BlockingQueue<Data> data = new PriorityBlockingQueue<Data>(20);
	
	public final int EXPIRE_TIME_IN_MILLIS = 60 * 1000;
	
	private Stats stats = new Stats();
	
	public boolean add(TransactionDTO trDto){
		long now = System.currentTimeMillis();
		if(now - trDto.getTimestamp() > EXPIRE_TIME_IN_MILLIS || now < trDto.getTimestamp()){
			return false;
		}
		
		data.add(new Data(trDto));
		log();
		stats.addNew(trDto.getAmount());
		//removeExpired(now);
		log();
		return true;
	}
	
	public void log(){
		System.out.println("--------------------");
		System.out.println("avg: "+stats.getAvg());
		System.out.println("sum: "+stats.getSum());
		System.out.println("cnt: "+stats.getCount());
		System.out.println("max: "+stats.getMax());
		System.out.println("min: "+stats.getMin());
		System.out.println("--------------------");
	}
	
	public StatisticsResponseDTO getStats(){
		return stats.getDTO();
	}
	
	public Data peek(){
		return data.peek();	
	}
	
	public int getQueueSize(){
		return data.size();
	}
	
	public Data remove(){
		return data.remove();
	}
	
	public void removeFromAmounts(double amount){
		stats.removeExpired(amount);
	}
}
