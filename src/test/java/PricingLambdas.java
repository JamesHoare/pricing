import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertEquals;

/**
 * Testing some Java 8 Features for simulating product pricing in functional style
 */
public class PricingLambdas {

    private final List<Product> products = asList(new Product("Clothing"),
            new Product("Bags"),
            new Product("Shoes"),
            new Product("Lingerie"),
            new Product("Clothing"));

    private final Executor executor = Executors.newFixedThreadPool(products.size(), r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    @Test
    public void run() {
        execute("sequential", () -> findPriceSequential());
        execute("parallel", () -> findPriceParallel());
        execute("composed CompletableFuture", () -> findPrice());
        printPricesStream();
    }

    @Test
    public void testLambda() {

        List<String> collected = Stream.of("a", "b", "hello")
                .map(string -> string.toUpperCase())
                .collect(toList());
        assertEquals(asList("A", "B", "HELLO"), collected);

    }

    @Test(expected = Throwable.class)
    public void testExceptionally() {

        new CompletableFuture().exceptionally(ex -> new Throwable());

    }

    private static void execute(String msg, Supplier<List<Integer>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }


    /**
     * @return price for each product with discount applied
     */
    public List<Integer> findPriceSequential() {
        return products.stream()
                .map((Product p) -> p.getPrice(new Random().nextInt()))
                .collect(toList());
    }

    /**
     * Executed leveraging fork/join pool
     *
     * @return price for each product with discount applied
     */
    public List<Integer> findPriceParallel() {
        return products.parallelStream()
                .map((Product p) -> p.getPrice(new Random().nextInt()))
                .collect(toList());
    }

    /**
     * Executed as CompletableFuture
     *
     * @return price for each product with discount applied
     */
    public List<Integer> findPrice() {
        List<CompletableFuture<Integer>> priceFutures = findPriceStream()
                .collect(toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    /**
     * @return stream of completablefutures
     */
    public Stream<CompletableFuture<Integer>> findPriceStream() {
        return products.stream()
                .map(product -> CompletableFuture.supplyAsync(() -> product.getPrice(new Random().nextInt()), executor));
        //.map(future -> future.thenCompose(CompletableFuture.supplyAsync(() -> Discount::applyDiscount(10), executor)));
    }

    public void printPricesStream() {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPriceStream()
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }

    private class Product {

        Discount discount = new Discount();

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        private String category;

        private Product(String category) {
        }

        public Integer getPrice(Integer price) {
            return Optional.ofNullable(discount.applyDiscount(price)).orElse(50);
        }
    }

    private class Discount {

        public Integer applyDiscount(Integer discount) {
            return 20 - discount;
        }
    }
}




