package statistic.dto;


public class TransactionDTO {
	private double amount;
	private long timestamp;

    public double getAmount() {
        return this.amount;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}