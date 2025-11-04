package school.sorokin.javacore.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static school.sorokin.javacore.streams.InitiationHelper.*;

public class Main {

    public static void main(String[] args) {
        // инициализация всех списков
        List<Product> allProducts = initProducts();
        List<Order> allOrders = initOrders(allProducts);
        List<Customer> customers = initCustomers(allOrders);

        // Задание 1
        // Получите список продуктов из категории "Books" с ценой более 100.
        List<Product> books = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .filter(product -> product.getCategory().equals("Books"))
                .filter(product -> product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0)
                .toList();

        // Задание 2
        // Получите список заказов с продуктами из категории "Children's products"
        List<Order> orders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals("Children's products")))
                .toList();

        // Задание 3
        // Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        // продуктов.
        BigDecimal sum = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Toys"))
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b.multiply(BigDecimal.valueOf(0.9))));

        // Задание 4
        // Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        List<Product> products = customers.stream()
                .filter(customer -> customer.getLevel() == 2)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)))
                .filter(order -> order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .toList();

        // Задание 5
        // Получите топ 2 самые дешевые продукты из категории "Books".
        List<Product> cheapBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .toList();

        // Задание 6
        // Получите 3 самых последних сделанных заказа.
        List<Order> lastOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted((a, b) -> b.getOrderDate().compareTo(a.getOrderDate()))
                .limit(3)
                .toList();

        // Задание 7
        // Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        // список их продуктов.
        List<Order> orders1 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println(order.getId()))
                .toList();

        // Задание 8
        // Рассчитайте общую сумму всех заказов, сделанных в феврале 2021
        BigDecimal sum1 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().getMonthValue() == 2 && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        // Задание 9
        // Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        double average = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 14)))
                .map(order -> order.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)))
                .mapToDouble(BigDecimal::doubleValue)
                .average().orElse(0);

        // Задание 10
        // Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        // продуктов категории "Книги"
        var sum2 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        var avr = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .average().orElse(0);

        var max = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .max().orElse(0);

        var min = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .min().orElse(0);

        var count = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Book"))
                .map(Product::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .count();

        // Задание 11
        // Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        Map<Long, Integer> map = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(order -> order.getId(), order1 -> order1.getProducts().size()));

        // Задание 12
        // Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        Map<Customer, List<Order>> map1 = customers.stream()
                .collect(Collectors.toMap(c -> c, customer -> customer.getOrders().stream().toList()));

        // Задание 13
        // Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Order, Double> map2 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(o -> o, o -> o.getProducts().stream()
                        .map(p -> p.getPrice())
                        .mapToDouble(BigDecimal::doubleValue)
                        .sum()));

        // Задание 14
        // Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        Map<String, List<String>> map3 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toMap(p -> p.getCategory(), product -> List.of(product.getName()), (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }));

        // Задание 15
        // Получите Map<String, Product> → самый дорогой продукт по каждой категории
        Map<String, Product> map4 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toMap(p -> p.getCategory(), p -> p, (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) == 1 ? p1 : p2));
    }

}
