package school.sorokin.javacore.testing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private static OrderRepository orderRepositoryMock;
    private static Order correctOrder;
    private static Order incorrectOrder;
    private static Order zeroQuantityOrder;
    private static Order zeroPriceOrder;
    private static OrderService orderService;

    private static final int QUANTITY = 30;
    private static final double UNIT_PRICE = 100.0;
    private static final int ORDER_ID_SUCCESS = 1;
    private static final int ORDER_ID_FAIL = 2;
    private static final int ORDER_ID_EMPTY = 3;
    private static final int ORDER_ID_ZERO_QUANTITY = 4;
    private static final int ORDER_ID_ZERO_PRICE = 5;

    @BeforeAll
    static void prepareOrdersAndMocks() throws Exception {
        correctOrder = new Order(ORDER_ID_SUCCESS, "pen", QUANTITY, UNIT_PRICE);
        incorrectOrder = new Order(ORDER_ID_FAIL, "pen", 5, 10.5);
        zeroQuantityOrder = new Order(ORDER_ID_ZERO_QUANTITY, "pen", 0, 10.5);
        zeroPriceOrder = new Order(ORDER_ID_ZERO_PRICE, "pen", 5, 0.0);


        orderRepositoryMock = mock(OrderRepository.class);
        when(orderRepositoryMock.saveOrder(correctOrder)).thenReturn(correctOrder.getId());
        when(orderRepositoryMock.saveOrder(incorrectOrder)).thenThrow(new Exception());
        when(orderRepositoryMock.getOrderById(ORDER_ID_SUCCESS)).thenReturn(Optional.of(correctOrder));
        when(orderRepositoryMock.getOrderById(ORDER_ID_EMPTY)).thenReturn(Optional.empty());
        when(orderRepositoryMock.getOrderById(ORDER_ID_ZERO_QUANTITY)).thenReturn(Optional.of(zeroQuantityOrder));
        when(orderRepositoryMock.getOrderById(ORDER_ID_ZERO_PRICE)).thenReturn(Optional.of(zeroPriceOrder));

        orderService = new OrderService(orderRepositoryMock);
    }

    @Test
    void processCorrectOrder() throws Exception {
        assertEquals(OrderConstants.SUCCESS_ORDER, orderService.processOrder(correctOrder));
        verify(orderRepositoryMock, times(1)).saveOrder(correctOrder);
    }

    @Test
    void processIncorrectOrder() throws Exception {
        assertEquals(OrderConstants.FAILED_ORDER, orderService.processOrder(incorrectOrder));
        verify(orderRepositoryMock, times(1)).saveOrder(incorrectOrder);
    }

    @Test
    void calculateTotalSuccess() {
        assertEquals(UNIT_PRICE * QUANTITY, orderService.calculateTotal(ORDER_ID_SUCCESS));
        verify(orderRepositoryMock, times(1)).getOrderById(ORDER_ID_SUCCESS);
    }

    @Test
    void calculateTotalNoOrder() {
        assertEquals(0.0, orderService.calculateTotal(ORDER_ID_EMPTY));
        verify(orderRepositoryMock, times(1)).getOrderById(ORDER_ID_EMPTY);
    }

    @Test
    void calculateTotalZeroQuantity() {
        assertEquals(0.0, orderService.calculateTotal(ORDER_ID_ZERO_QUANTITY));
        verify(orderRepositoryMock, times(1)).getOrderById(ORDER_ID_ZERO_QUANTITY);
    }

    @Test
    void calculateTotalZeroPrice() {
        assertEquals(0.0, orderService.calculateTotal(ORDER_ID_ZERO_PRICE));
        verify(orderRepositoryMock, times(1)).getOrderById(ORDER_ID_ZERO_PRICE);
    }
}