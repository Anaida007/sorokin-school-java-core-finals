package school.sorokin.javacore.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InitiationHelper {

    private static final String NEW = "NEW";
    private static final String FORMING = "FORMING";
    private static final String FORMED = "FORMED";
    private static final String READY_FOR_DELIVERY = "READY FOR DELIVERY";
    private static final String DELIVERY = "DELIVERY";
    private static final String DELIVERED = "DELIVERED";

    private static final int NUMBER_OF_STATUSES = 6;
    private static final int NUMBER_OF_PRODUCTS = 18;
    private static final int NUMBER_OF_ORDERS = 25;

    private static final Random random = new Random();

    public static List<Product> initProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Doll", "Toys", BigDecimal.valueOf(90)));
        products.add(new Product(2L, "Little car", "Toys", BigDecimal.valueOf(54)));
        products.add(new Product(3L, "Constructor", "Toys", BigDecimal.valueOf(49)));
        products.add(new Product(4L, "Potato", "Vegetable", BigDecimal.valueOf(32)));
        products.add(new Product(5L, "Tomato", "Vegetable", BigDecimal.valueOf(120)));
        products.add(new Product(6L, "Carrot", "Vegetable", BigDecimal.valueOf(40)));
        products.add(new Product(7L, "Onion", "Vegetable", BigDecimal.valueOf(32)));
        products.add(new Product(8L, "Apple", "Fruit", BigDecimal.valueOf(115)));
        products.add(new Product(9L, "Orange", "Fruit", BigDecimal.valueOf(186)));
        products.add(new Product(10L, "Banana", "Fruit", BigDecimal.valueOf(203)));
        products.add(new Product(11L, "Grape", "Fruit", BigDecimal.valueOf(215)));
        products.add(new Product(12L, "Lord of the Rings", "Book", BigDecimal.valueOf(560)));
        products.add(new Product(13L, "Idiot", "Book", BigDecimal.valueOf(350)));
        products.add(new Product(14L, "Harry Potter", "Book", BigDecimal.valueOf(420)));
        products.add(new Product(15L, "Pyramid", "Children's products", BigDecimal.valueOf(420)));
        products.add(new Product(16L, "Napkin", "Children's products", BigDecimal.valueOf(705)));
        products.add(new Product(17L, "Little cap", "Children's products", BigDecimal.valueOf(1560)));
        products.add(new Product(18L, "Diapers", "Children's products", BigDecimal.valueOf(420)));

        return products;
    }

    public static List<Order> initOrders(List<Product> allProducts) {
        List<String> allStatuses = List.of(NEW, FORMING, FORMED, READY_FOR_DELIVERY, DELIVERY, DELIVERED);
        List<Order> allOrders = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            LocalDate orderDate = LocalDate.of(2025, 9, random.nextInt(30)+1);
            LocalDate deliveryDate = orderDate.plusDays(random.nextInt(32));
            String status = allStatuses.get(random.nextInt(NUMBER_OF_STATUSES ));
            allOrders.add(new Order((long) i, orderDate, deliveryDate, status, getProducts(allProducts)));

        }
        return allOrders;
    }

    public static List<Customer> initCustomers(List<Order> allOrders) {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "Kate", 3L, allOrders.stream().limit(5).collect(Collectors.toSet())));
        customers.add(new Customer(2L, "John", 1L, Set.of(allOrders.get(5), allOrders.get(6), allOrders.get(7), allOrders.get(8), allOrders.get(9))));
        customers.add(new Customer(3L, "Nick", 2L, Set.of(allOrders.get(10), allOrders.get(11), allOrders.get(12), allOrders.get(13), allOrders.get(14))));
        customers.add(new Customer(4L, "Lisa", 1L, Set.of(allOrders.get(15), allOrders.get(16), allOrders.get(17), allOrders.get(18), allOrders.get(19))));
        customers.add(new Customer(5L, "Margo", 4L, Set.of(allOrders.get(20), allOrders.get(21), allOrders.get(22), allOrders.get(23), allOrders.get(24))));
        return customers;
    }

    private static Set<Product> getProducts(List<Product> allProducts) {
        Set<Product> products = new HashSet<>();
        for (int i = 0; i < random.nextInt(NUMBER_OF_PRODUCTS + 1) - 1; i++) {
            products.add(allProducts.get(i));
        }
        return products;
    }
}
