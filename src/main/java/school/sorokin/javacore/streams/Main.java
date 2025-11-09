package school.sorokin.javacore.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static school.sorokin.javacore.streams.InitiationHelper.*;

public class Main {

    public static void main(String[] args) {
        // инициализация всех списков
        List<Product> allProducts = initProducts();
        List<Order> allOrders = initOrders(allProducts);
        List<Customer> customers = initCustomers(allOrders);

        // Задание 1
        // Получите список продуктов из категории "Book" с ценой более 100.
        List<Product> books = getAllProductsStream(customers)
                .distinct()
                .filter(product -> product.getCategory().equals("Book"))
                .filter(product -> product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0)
                .toList();
        logMessage(books, "1. Список продуктов из категории \"Book\" с ценой более 100: ");

        // Задание 2
        // Получите список заказов с продуктами из категории "Children's products"
        List<Order> orders = getAllOrdersStream(customers)
                .filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals("Children's products")))
                .toList();
        logMessage(orders, "2. Список заказов с продуктами из категории \"Children's products");

        // Задание 3
        // Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        // продуктов.
        BigDecimal sum = getAllProductsStream(customers)
                .filter(product -> product.getCategory().equals("Toys"))
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("3. К списку продуктов из категории \"Toys\" применина скидка 10% и получена сумму всех продуктов - " + sum);

        // Задание 4
        // Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        List<Product> products = customers.stream()
                .filter(customer -> customer.getLevel() == 2L)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)))
                .filter(order -> order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .toList();
        logMessage(products, "4. Список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021");

        // Задание 5
        // Получите топ 2 самые дешевые продукты из категории "Book".
        List<Product> cheapBooks = getAllProductsStream(customers)
                .filter(p -> p.getCategory().equals("Book"))
                .distinct()
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .toList();
        logMessage(cheapBooks, "5. Топ 2 самые дешевые продукты из категории \"Book\"");

        // Задание 6
        // Получите 3 самых последних сделанных заказа.
        List<Order> lastOrders = getAllOrdersStream(customers)
                .sorted((a, b) -> b.getOrderDate().compareTo(a.getOrderDate()))
                .limit(3)
                .toList();
        logMessage(lastOrders, "6. 3 самых последних сделанных заказа");

        // Задание 7
        // Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        // список их продуктов.
        List<Product> products1 = getAllOrdersStream(customers)
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println(order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .toList();
        logMessage(products1, "7. Список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните список их продуктов");

        // Задание 8
        // Рассчитайте общую сумму всех заказов, сделанных в феврале 2021
        BigDecimal sum1 = getAllOrdersStream(customers)
                .filter(order -> order.getOrderDate().getMonthValue() == 2 && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        System.out.println("8. Рассчитана общая сумма всех заказов, сделанный в феврале 2021 - " + sum1);

        // Задание 9
        // Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        double average = getAllOrdersStream(customers)
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 14)))
                .map(order -> order.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)))
                .mapToDouble(BigDecimal::doubleValue)
                .average().orElse(0);
        System.out.println("9. Средний платеж по заказам, сделанным 14-марта-2021 - " + average);

        // Задание 10
        // Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        // продуктов категории "Книги"
        var sum2 = getAllProductsStream(customers)
                .filter(product -> product.getCategory().equals("Book"))
                .distinct()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("10. Сумма цен на все книги - " + sum2);

        var avr = getAllProductsStream(customers)
                .filter(product -> product.getCategory().equals("Book"))
                .distinct()
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .average().orElse(0);
        System.out.println("10. Среднее цен на все книги - " + avr);

        var max = getAllProductsStream(customers)
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .max().orElse(0);
        System.out.println("10. Максимальная цена среди всех книг - " + max);

        var min = getAllProductsStream(customers)
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .min().orElse(0);
        System.out.println("10. Минимальная цена среди всех книг - " + min);

        var count = getAllProductsStream(customers)
                .filter(product -> product.getCategory().equals("Book"))
                .distinct()
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .count();
        System.out.println("10. Количество всех книг - " + count);

        // Задание 11
        // Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        Map<Long, Integer> map = getAllOrdersStream(customers)
                .collect(Collectors.toMap(order -> order.getId(), order1 -> order1.getProducts().size(), (s1, s2) -> s1 + s2));
        logMapMessage(map, "11. Данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе");

        // Задание 12
        // Создайте Map<Customer, Set<Order>> → key - покупатель, value - список его заказов
        Map<Customer, Set<Order>> map1 = customers.stream()
                .collect(Collectors.toMap(c -> c, Customer::getOrders));
        logMapMessage(map1, "12. Map<Customer, Set<Order>> → key - покупатель, value - список его заказов");

        // Задание 13
        // Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Order, Double> map2 = getAllOrdersStream(customers)
                .collect(Collectors.toMap(o -> o, o -> o.getProducts().stream()
                        .distinct()
                        .map(p -> p.getPrice())
                        .mapToDouble(BigDecimal::doubleValue)
                        .sum()));
        logMapMessage(map2, "13. Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа");

        // Задание 14
        // Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        Map<String, List<String>> map3 = getAllProductsStream(customers)
                .distinct()
                .collect(Collectors.toMap(p -> p.getCategory(), product -> new ArrayList<>(Arrays.asList(product.getName())), (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }));
        logMapMessage(map3, "14. Map<String, List<String>> → key - категория, value - список названий товаров в категории");

        // Задание 15
        // Получите Map<String, Product> → самый дорогой продукт по каждой категории
        Map<String, Product> map4 = getAllProductsStream(customers)
                .collect(Collectors.toMap(p -> p.getCategory(), p -> p, (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) >= 0 ? p1 : p2));
        logMapMessage(map4, "15. Map<String, Product> → самый дорогой продукт по каждой категории");
    }

    private static Stream<Order> getAllOrdersStream(List<Customer> customers) {
        return customers.stream()
                .flatMap(c -> c.getOrders().stream());
    }

    private static Stream<Product> getAllProductsStream(List<Customer> customers) {
        return customers.stream()
                .flatMap(c -> c.getOrders().stream())
                .flatMap(o -> o.getProducts().stream());
    }

    private static void logMessage(List objects, String logMessage) {
        System.out.println(logMessage);
        objects.forEach(System.out::println);
    }

    private static void logMapMessage(Map objects, String logMessage) {
        System.out.println(logMessage);
        objects.forEach((k, v) -> System.out.println(k + " - " + v));
    }

}
