import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void addItemAndGetTotalCost() {
        cart.addItem(new Item(15,1));
        cart.addItem(new Item(15,2));
        assertEquals(45, cart.getTotalCost());
    }
}