package stock;

import java.util.List;

public class PriceHolder {
	private List<StockPrice> stockList;

	public List<StockPrice> getStockList() {
		return stockList;
	}

	public void setStockList(List<StockPrice> stockList) {
		this.stockList = stockList;
	}
}
