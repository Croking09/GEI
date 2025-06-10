package e4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MesaBillarTest {
    @Test
    void testAntesPartida(){
        MesaBillar mesa = new MesaBillar();

        assertFalse(mesa.esPartidaIniciada());
        assertTrue(mesa.bolasMesa().isEmpty());
        assertFalse(mesa.bolasCajetin().isEmpty());
        assertNull(mesa.obtenerGanador());
    }

    @Test
    void testIniciarPartida(){
        MesaBillar mesa = new MesaBillar();

        mesa.iniciarPartida();

        assertTrue(mesa.esPartidaIniciada());
        assertFalse(mesa.bolasMesa().isEmpty());
        assertTrue(mesa.bolasCajetin().isEmpty());
    }

    @Test
    void testMeterBolas(){
        MesaBillar mesa = new MesaBillar();

        assertThrows(IllegalArgumentException.class, () -> mesa.meterBola(null));
        assertThrows(IllegalArgumentException.class, () -> mesa.meterBola(BolaBillar.BLANCA));
        assertThrows(IllegalArgumentException.class, () -> mesa.meterBola(BolaBillar.BOLA1));
        assertThrows(IllegalArgumentException.class, () -> mesa.meterBola(BolaBillar.BOLA8));

        mesa.iniciarPartida();

        assertThrows(IllegalArgumentException.class, () -> mesa.meterBola(null));

        mesa.meterBola(BolaBillar.BLANCA);
        assertTrue(mesa.bolasCajetin().isEmpty());

        mesa.meterBola(BolaBillar.BOLA1);
        assertFalse(mesa.bolasCajetin().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> mesa.meterBola(BolaBillar.BOLA1));

        mesa.meterBola(BolaBillar.BOLA8);
        assertFalse(mesa.esPartidaIniciada());
    }

    @Test
    void testObetenerGanador(){
        MesaBillar mesa = new MesaBillar();

        assertNull(mesa.obtenerGanador());

        mesa.iniciarPartida();

        assertNull(mesa.obtenerGanador());

        for(int i=0;i<2;i++){
            if(i==0){
                mesa.meterBola(BolaBillar.BOLA1);
                assertEquals(BolaBillar.Tipo.LISA, mesa.obtenerGanador());
            }
            else{
                mesa.meterBola(BolaBillar.BOLA10);
                assertNull(mesa.obtenerGanador());
            }
        }
        mesa.meterBola(BolaBillar.BOLA11);
        assertEquals(BolaBillar.Tipo.RAYADA, mesa.obtenerGanador());

        mesa.meterBola(BolaBillar.BOLA8);
        assertEquals(BolaBillar.Tipo.RAYADA, mesa.obtenerGanador());

        mesa.iniciarPartida();

        mesa.meterBola(BolaBillar.BOLA8);
        assertNull(mesa.obtenerGanador());
    }
}
