package fooddelivery.external;

/**
 * Created by uengine on 2020. 4. 18..
 */
public class PayHistoryServiceFallback implements PayHistoryService {
    @Override
    public void pay(PayHistory 주문) {
        //do nothing if you want to forgive it

        System.out.println("Circuit breaker has been opened. Fallback returned instead.");
    }
}
