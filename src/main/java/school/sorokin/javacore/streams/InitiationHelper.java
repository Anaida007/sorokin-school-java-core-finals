package school.sorokin.javacore.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class InitiationHelper {

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
        products.add(new Product(13L, "Idiot", "Book", BigDecimal.valueOf(99)));
        products.add(new Product(14L, "Harry Potter", "Book", BigDecimal.valueOf(420)));
        products.add(new Product(15L, "Pyramid", "Children's products", BigDecimal.valueOf(420)));
        products.add(new Product(16L, "Napkin", "Children's products", BigDecimal.valueOf(705)));
        products.add(new Product(17L, "Little cap", "Children's products", BigDecimal.valueOf(1560)));
        products.add(new Product(18L, "Diapers", "Children's products", BigDecimal.valueOf(420)));

        System.out.println("Инициализированы продукты: ");
        products.forEach(System.out::println);
        return products;
    }

    public static List<Order> initOrders(List<Product> allProducts) {
        List<Order> allOrders = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            LocalDate orderDate = LocalDate.of(2021, 3, random.nextInt(15) + 1);
            LocalDate deliveryDate = orderDate.plusDays(random.nextInt(14));
            String status = Status.values()[random.nextInt(NUMBER_OF_STATUSES)].name();
            allOrders.add(new Order((long) i, orderDate, deliveryDate, status, getProducts(allProducts)));
        }

        System.out.println("Инициализированы заказы: ");
        allOrders.forEach(System.out::println);

        return allOrders;
    }

    public static List<Customer> initCustomers(List<Order> allOrders) {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "Kate", 3L, new HashSet<>(allOrders.subList(0, 4))));
        customers.add(new Customer(2L, "John", 1L, new HashSet<>(allOrders.subList(5, 9))));
        customers.add(new Customer(3L, "Nick", 2L, new HashSet<>(allOrders.subList(10, 14))));
        customers.add(new Customer(4L, "Lisa", 1L, new HashSet<>(allOrders.subList(15, 19))));
        customers.add(new Customer(5L, "Margo", 4L, new HashSet<>(allOrders.subList(20, 24))));

        System.out.println("Инициализированы покупатели: ");
        customers.forEach(System.out::println);

        return customers;
    }

    private static Set<Product> getProducts(List<Product> allProducts) {
        Set<Product> products = new HashSet<>();
        for (int i = 0; i < random.nextInt(1, NUMBER_OF_PRODUCTS); i++) {
            products.add(allProducts.get(random.nextInt(NUMBER_OF_PRODUCTS)));
        }
        return products;
    }
}
