package course.concurrency.m2_async.cf.min_price;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

	public double getMinPrice(long itemId) {
		List<CompletableFuture<Double>> minPricesCF = shopIds.stream()
				.map(shopId -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, shopId)))
				.map(f -> f.completeOnTimeout(Double.NaN, 3, TimeUnit.SECONDS))
				.map(f -> f.exceptionally(e -> Double.NaN))
				.collect(Collectors.toList());

		CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
				minPricesCF.toArray(new CompletableFuture[minPricesCF.size()])
		);
		return resultantCf.thenApply(
				t -> minPricesCF.stream()
						.map(CompletableFuture::join)
						.min(Double::compareTo)
						.orElse(Double.NaN)
		).getNow(Double.NaN);
	}
}
