import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void invalidConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Item(-5,2));
        assertThrows(IllegalArgumentException.class, () -> new Item(5,-2));
    }

    @Test
    void createItem() {
        Item newItem = new Item(5,5);
        assertEquals(5, newItem.getPrice());
    }

    @Test
    void getTotalCost() {
        Item newItem = new Item(5,5);
        assertEquals(25, newItem.getTotalCost());
    }

}