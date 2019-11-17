package hello;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stock.PriceHolder;
import stock.StockPrice;

@RestController
public class PriceController {
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
**/
   
    @RequestMapping("/prices")
    public PriceHolder getPortfolio(@RequestParam(value="id", defaultValue="default") String id) {
	 
		PriceHolder portfolio = new PriceHolder();
		List<StockPrice> stockList = new ArrayList<StockPrice>();
		StockPrice verizon = new StockPrice();
		verizon.setPrice("68.40");
		verizon.setStockSymbol("CERN");
		stockList.add(verizon);
		StockPrice amazon = new StockPrice();
		amazon.setPrice("1739.49");
		amazon.setStockSymbol("AZ");
		stockList.add(amazon);
		StockPrice microsoft = new StockPrice();
		microsoft.setPrice("149.97");
		microsoft.setStockSymbol("MSFT");
		stockList.add(microsoft);
		portfolio.setStockList(stockList);
		return portfolio;
	}
}
