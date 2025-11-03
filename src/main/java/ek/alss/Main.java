package ek.alss;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    record Product(String productId, String category, double price) {
    }

    record OrderLine(Product product, int quantity) {
    }

    record Order(String orderId, List<OrderLine> orderLines) {
    }

    public static List<Order> setup() {
        List<Product> products = List.of(
                new Product("A100", "Electronics", 199.99),
                new Product("B200", "Books", 29.99),
                new Product("C300", "Clothing", 49.99),
                new Product("D400", "Electronics", 99.99),
                new Product("E500", "Books", 15.99)
        );

        List<Order> orders = List.of(
                new Order("O1", List.of(
                        new OrderLine(products.get(0), 1),
                        new OrderLine(products.get(1), 2)
                )),
                new Order("O2", List.of(
                        new OrderLine(products.get(2), 3),
                        new OrderLine(products.get(3), 1)
                )),
                new Order("O3", List.of(
                        new OrderLine(products.get(4), 5),
                        new OrderLine(products.get(1), 1)
                ))
        );
        return orders;
    }

    public static void main(String[] args) {
        List<Order> orders = setup();
        // TODO: Implement the functions below and call them here to test
        totalPrice();    // 1. Calculate the total price of all orders.
        allBooks();    // 2. Find all products in the "Books" category.
        allUniqueCategories();    // 3. Get a list of all unique categories from the products.
        mostExpensiveProduct();    // 4. Find the most expensive product.
        averageElectronicsPrice();    // 5. Calculate the average price of products in the "Electronics" category.
        expensiveOrders();    // 6. Get a list of order IDs where the total order price exceeds $100.
        productsPerCategory();    // 7. Count how many products are there in each category.
    }


    // 1. Calculate the total price of all orders.
    public static void totalPrice() {
        double total = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .mapToDouble(ol -> ol.product.price * ol.quantity)
                .sum();
        System.out.println("1. Total Price: " + total);
    }

    // 2. Find all products in the "Books" category.

    // upraktisk måde at gøre det på, men der skal køres igennem det hele for at nå til products pga setup()
    public static void allBooks() {
        List<Product> books = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .map(OrderLine::product)// ol -> ol.product()
                .filter(p -> p.category.equals("Books"))
                .distinct() // fjerner dubletter
                .toList();
        System.out.println("2. All Books: " + books);
    }

    // 3. Get a list of all unique categories from the products.
    public static void allUniqueCategories() {
        List<String> categories = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .map(ol -> ol.product.category)
                .distinct() // fjerner dubletter
                .toList();
        System.out.println("3. Unique Categories: " + categories);
    }

    // 4. Find the most expensive product.

    // Lige nu returnerer den prisen på det dyreste produkt, og ikke selve produktet.
    public static void mostExpensiveProduct() {
        double product = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .map(OrderLine::product)
                .mapToDouble(p -> p.price)
                .max()
                .orElse(0.0);
        System.out.println("4. Most expensive product: " + product);
    }

    // 5. Calculate the average price of products in the "Electronics" category.
    public static void averageElectronicsPrice() {
        double avg = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .map(OrderLine::product) // ol -> ol.product()
                .filter(p -> p.category.equals("Electronics"))
                .mapToDouble(p -> p.price)
                .average()
                .orElse(0.0);
        System.out.println("5. Average price of electronics: " + avg);
    }

    // 6. Get a list of order IDs where the total order price exceeds $100.
    public static void expensiveOrders() {
        List<String> orders = setup().stream()
                .filter(o -> o.orderLines().stream()
                        .mapToDouble(ol -> ol.product.price * ol.quantity)
                        .sum() > 100.0
                )
                .map(o -> o.orderId)
                .toList();
        System.out.println("6. Order IDs exceeding $100: " + orders);
    }

    // 7. Count how many products are there in each category.
    public static void productsPerCategory() {
        Map<String, Long> map = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .map(OrderLine::product) // ol -> ol.product()
                .collect(Collectors.groupingBy(p -> p.category, Collectors.counting()));
        System.out.println("7. Count of products per category: " + map);
    }
}
