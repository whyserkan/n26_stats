package statistic.model;

import statistic.dto.TransactionDTO;

public class Data implements Comparable<Data>{
	private long time;
	private double amount;
	
	public Data(TransactionDTO trDto){
		this.amount = trDto.getAmount();
		this.time = trDto.getTimestamp();
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public int compareTo(Data o) {
		return (int)(this.time - o.getTime());
	}
}
