package ek.alss;

import java.util.List;

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
        allCategories();    // 3. Get a list of all unique categories from the products.
    }


    // 1. Calculate the total price of all orders.
    public static void totalPrice() {
        double total = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .mapToDouble(ol -> ol.product.price * ol.quantity)
                .sum();
        System.out.println("Total Price: " + total);
    }

    // 2. Find all products in the "Books" category.

    // Hmmmm. Pt spytter den alle orderlines med "books" ud, og ikke bare alle producter med kategorien "books".
    public static void allBooks() {
        List<OrderLine> books = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .filter(ol -> ol.product.category.equals("Books"))
                .toList();
        System.out.println(books);
    }

    // 3. Get a list of all unique categories from the products.

    // Denne spytter pt alle categories ud og ikke uniques
    public static void allCategories() {
        List<String> categories = setup().stream()
                .flatMap(o -> o.orderLines.stream())
                .map(ol -> ol.product.category)
                .toList();
        System.out.println(categories);
    }
    // 4. Find the most expensive product.
    // 5. Calculate the average price of products in the "Electronics" category.
    // 6. Get a list of order IDs where the total order price exceeds $100.
    // 7. Count how many products are there in each category.
}
