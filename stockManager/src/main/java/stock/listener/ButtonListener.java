package stock.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import stock.Portfolio;
import stock.PriceHolder;
import stock.Stock;
import stock.StockPrice;

public class ButtonListener implements ActionListener {

	DefaultTableModel defaultTableModel = null;
	private static final String ZUUL_URL = "http://localhost:10000/";

	public ButtonListener(DefaultTableModel dtm) {
		this.defaultTableModel = dtm;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
		if (action.equals("Get Portfolio")) {
			System.out.println("'Get Portfolio' Button pressed!");
			callApi("portfolio", "portfolio");
		} else if (action.equals("Get Prices")) {
			System.out.println("'Get Prices' Button pressed!");
			callApi("pricing","prices");
		}

	}

	private void callApi(String api, String apiMethod) {

		try {

			// URL url = new URL("http://localhost:8080/greeting?name=Dave");
			String apiUrl = ZUUL_URL + api + "/" + apiMethod;
			System.out.println("apiUrl = " + apiUrl);
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			String json = null;
			while ((output = br.readLine()) != null) {
				if (json == null) {
					json = output;
				} else {
					json = json + output;
				}
				// System.out.println(output);

			}

			conn.disconnect();
			Gson gson = new Gson(); // Or use new GsonBuilder().create();
			//Gson gson = GsonBuilder().create();
			if (api.equalsIgnoreCase("portfolio")) {
				Portfolio portfolio = gson.fromJson(json, Portfolio.class);
				System.out.println(json);
				updateModel(portfolio);
			} else if(api.equalsIgnoreCase("pricing")) {
				System.out.println(json);
				PriceHolder priceHolder = gson.fromJson(json, PriceHolder.class);
				updateModel(priceHolder);
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	private void updateModel(PriceHolder priceHolder) {
		HashMap<String,Integer> stockSymbolMap = new HashMap<String,Integer>();
		for(int row=0; row < defaultTableModel.getRowCount();row++) {
			String stockSymbol = (String) defaultTableModel.getValueAt(row, 1);
			stockSymbolMap.put(stockSymbol, row);
		}
		if(priceHolder == null) {
			System.out.println("priceHolder is null");
		} else {
			List<StockPrice> stockList = priceHolder.getStockList();
			for(StockPrice stockPrice: stockList) {
				String stockSymbol = stockPrice.getStockSymbol();
				if(stockSymbolMap.containsKey(stockSymbol)) {
					int rowToUpdate = stockSymbolMap.get(stockSymbol);
					String price = stockPrice.getPrice();
					System.out.println("Need to update row: "+ rowToUpdate);
					defaultTableModel.setValueAt(price,rowToUpdate, 3);
				} else {
					System.out.println("Cannot find row: "+ stockSymbol );
				}
			}
		}
	}

	private void updateModel(Portfolio portfolio) {
		if (portfolio == null) {
			System.out.println("Portfolio is null.");
		} else {
			List<Stock> stockList = portfolio.getStockList();
			if (stockList == null) {
				System.out.println("Stock List is null");
			} else {
				int count = 0;
				for (Stock stock : stockList) {
					String stockName = stock.getStockName();
					String stockSymbol = stock.getStockSymbol();
					String numberOfShares = stock.getNumberOfShares();
					if (count == 0) {
						defaultTableModel.setValueAt(stockName, 0, 0);
						defaultTableModel.setValueAt(stockSymbol, 0, 1);
						defaultTableModel.setValueAt(numberOfShares, 0, 2);
					} else {
						Vector<String> rowData = new Vector<String>();
						rowData.add(stockName);
						rowData.add(stockSymbol);
						rowData.add(numberOfShares);
						defaultTableModel.addRow(rowData);
					}
					count++;
				}
			}
		}

	}
}
