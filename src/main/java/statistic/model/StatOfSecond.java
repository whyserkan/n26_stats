package statistic.model;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import statistic.dto.TransactionDTO;


public class StatOfSecond {
	private long cnt = 0;
	private double sum = 0;
	private double avg = 0;
	private double max = 0;
	private double min = 0;
	private long time = 0;
	
	public StatOfSecond(long time){
		this.time = time;
	}
	
	public static StatOfSecond firstStat(TransactionDTO transaction) {
		StatOfSecond stat = new StatOfSecond(transaction.getTimestamp());
		stat.setAvg(transaction.getAmount());
		stat.setSum(transaction.getAmount());
		stat.setMin(transaction.getAmount());
		stat.setMax(transaction.getAmount());
		stat.setCnt(1);
		return stat;
	}
	
	public void merge(StatOfSecond otherStat){
		this.sum += otherStat.getSum();
		this.cnt += otherStat.getCnt();
		this.avg = this.sum / this.cnt;
		this.max = Math.max(this.max, otherStat.getMax());
		this.min = Math.min(this.min, otherStat.getMin());
	}

	public long getCnt() {
		return cnt;
	}
	public void setCnt(long cnt) {
		this.cnt = cnt;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public int calculateSlot(long baseTime){
		return (int)ChronoUnit.SECONDS.between(
			    new Date(baseTime).toInstant(),
			    new Date(time).toInstant());  
	} 
	
	public boolean slotNotChanged(int oldSlot,long baseTime){
		return oldSlot == calculateSlot(baseTime);
	}
	
	public boolean calculatedSlotIntheRange(long baseTime){
		int newSlot = calculateSlot(baseTime);
		return newSlot >= 0 && newSlot < 60;
	}	
}
