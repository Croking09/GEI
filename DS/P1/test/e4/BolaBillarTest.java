package e4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BolaBillarTest {
    public static BolaBillar bola = BolaBillar.BOLA1;

    @Test
    void testGetValor() {
        //BolaBillar bola = BolaBillar.BOLA1;
        assertEquals(1, bola.getValor());
    }

    @Test
    void testGetColor() {
        //BolaBillar bola = BolaBillar.BOLA1;
        assertEquals(BolaBillar.Color.AMARILLO, bola.getColor());
    }

    @Test
    void testGetTipo() {
        //BolaBillar bola = BolaBillar.BOLA1;
        assertEquals(BolaBillar.Tipo.LISA, bola.getTipo());
    }
}
