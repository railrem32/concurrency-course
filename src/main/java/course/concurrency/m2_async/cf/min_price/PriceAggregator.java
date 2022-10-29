package course.concurrency.m2_async.cf.min_price;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
		CompletableFuture<Double>[] minPricesCF = shopIds.stream()
				.map(shopId -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, shopId)))
				.map(f -> f.orTimeout(2900, TimeUnit.MILLISECONDS).exceptionally(e -> null))
				.toArray(CompletableFuture[]::new);

		return CompletableFuture.allOf(minPricesCF).thenApply(
				t -> Arrays.stream(minPricesCF)
						.map(CompletableFuture::join)
						.filter(Objects::nonNull)
						.mapToDouble(r -> r)
						.min()
						.orElse(Double.NaN)
		).join();
	}
}
