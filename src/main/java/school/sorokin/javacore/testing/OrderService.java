package school.sorokin.javacore.testing;

public class OrderService {

    private OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        try {
            orderRepository.saveOrder(order);
            return OrderConstants.SUCCESS_ORDER;
        } catch (Exception e) {
            return OrderConstants.FAILED_ORDER;
        }
    }

    public double calculateTotal(int id) {
        var order = orderRepository.getOrderById(id);
        if (order.isPresent()) {
            return order.get().getTotalPrice();
        }
        return 0;
    }
}
