package hello;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stock.Portfolio;
import stock.Stock;

@RestController
public class GreetingController {
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
**/
   
    @RequestMapping("/portfolio")
    public Portfolio getPortfolio(@RequestParam(value="id", defaultValue="default") String id) {
	 
		Portfolio portfolio = new Portfolio();
		List<Stock> stockList = new ArrayList<Stock>();
		Stock verizon = new Stock();
		verizon.setNumberOfShares("112");
		verizon.setStockName("Verizon");
		verizon.setStockSymbol("VZ");
		stockList.add(verizon);
		Stock amazon = new Stock();
		amazon.setNumberOfShares("100");
		amazon.setStockSymbol("AZ");
		amazon.setStockName("Amazon");
		stockList.add(amazon);
		Stock microsoft = new Stock();
		microsoft.setNumberOfShares("50");
		microsoft.setStockName("Microsoft");
		microsoft.setStockSymbol("MSFT");
		stockList.add(microsoft);
		portfolio.setStockList(stockList);
		return portfolio;
	}
}
