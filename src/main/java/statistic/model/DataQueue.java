package statistic.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import com.google.common.collect.MinMaxPriorityQueue;

import statistic.dto.StatisticsResponseDTO;
import statistic.dto.TransactionDTO;

public class DataQueue {
	private BlockingQueue<Data> data = new PriorityBlockingQueue<Data>();
	
	private final int EXPIRE_TIME_IN_MILLIS = 60 * 1000;
	
	private Stats stats = new Stats();
	
	public boolean add(TransactionDTO trDto){
		long now = System.currentTimeMillis();
		if(now - trDto.getTimestamp() > EXPIRE_TIME_IN_MILLIS || now < trDto.getTimestamp()){
			return false;
		}
		
		data.add(new Data(trDto));
		log();
		stats.addNew(trDto.getAmount());
		removeExpired(now);
		log();
		return true;
	}
	
	private void log(){
		System.out.println("--------------------");
		System.out.print(stats.getAvg());
		System.out.print(stats.getSum());
		System.out.print(stats.getCount());
		System.out.print(stats.getMax());
		System.out.print(stats.getMin());
		System.out.println("");
		System.out.println("--------------------");
	}
	
	private void removeExpired(long now){
		boolean last60sec = false;
		System.out.println(5);
		
		while(data.size()!=0 && !last60sec){
			Data pulledData = data.peek();
			System.out.println(6);
			last60sec = now - pulledData.getTime() < EXPIRE_TIME_IN_MILLIS;
			
			if(!last60sec){
				System.out.println(8);
				pulledData = data.remove();
				double removedAmount = pulledData.getAmount();
				stats.removeExpired(removedAmount);
			}
		}
	}
	
	public StatisticsResponseDTO getStats(){
		return stats.getDTO();
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(data.size()!=0){
			Data pulledData;
			sb.append("All: ");
			
			while (data.size()!=0){
				pulledData = data.remove();
				sb.append(String.valueOf(pulledData.getAmount()));
				sb.append(" - ");
				sb.append(String.valueOf(pulledData.getTime()));
				sb.append(" | ");
			}
		} else {
			sb.append("No data");
		}
		return sb.toString();
	}
}
