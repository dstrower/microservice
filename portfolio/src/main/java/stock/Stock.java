package stock;

public class Stock {
    private String stockSymbol;
    private String stockName;
    private String numberOfShares;
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getNumberOfShares() {
		return numberOfShares;
	}
	public void setNumberOfShares(String numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
}
