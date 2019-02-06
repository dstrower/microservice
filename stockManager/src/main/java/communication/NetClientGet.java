package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;

import stock.Portfolio;
import stock.Stock;

public class NetClientGet {
	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {

		try {

			// URL url = new URL("http://localhost:8080/greeting?name=Dave");
			URL url = new URL("http://localhost:8080/portfolio?id=Dave");
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
				if(json == null) {
					json = output;
				} else {
					json = json+ output;
				}
				//System.out.println(output);
				Gson gson = new Gson(); // Or use new GsonBuilder().create();
				Portfolio portfolio = gson.fromJson(json, Portfolio.class);
				printPortfolio(portfolio);
			}

			conn.disconnect();
			System.out.println(json);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private static void printPortfolio(Portfolio portfolio) {
		if(portfolio != null) {
			List<Stock> stockList = portfolio.getStockList();
			if(stockList != null) {
				for(Stock stock: stockList) {
					System.out.println(stock);
				}
			}
		}
		
	}
}
