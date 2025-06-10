package e2;

import org.junit.jupiter.api.Test;

import static e2.Simulador.*;
import static org.junit.jupiter.api.Assertions.*;

public class IncursionTest {
    private static Mapa getMapa() {
        Nodo a = new NodoAvistamiento(28,"A");
        Nodo b = new NodoBatalla(new Flota(20,30,25,0,0),"B");
        Nodo c = new NodoTormentaMarina(20,"C");
        Nodo d = new NodoFin("D");
        Nodo e = new NodoAtaqueAereo(151,"E");
        Nodo f = new NodoBatalla(new Flota(30,17,41,0,0),"F");
        Nodo g = new NodoTormentaMarina(67,"G");
        Nodo h = new NodoFin("H");
        Nodo i = new NodoFin("I");
        Nodo k = new NodoFin("K");

        Mapa m1 = new Mapa(a);

        m1.addHijos(m1.getInicial(),b,c); //Los hijos de a son b (Izquierdo) y c (Derecho)
        m1.addHijos(b,d,e);
        m1.addHijos(c,f); //El hijo de c es f
        m1.addHijos(e,k);
        m1.addHijos(f,h,g);
        m1.addHijos(g,i);

        return m1;
    }
    @Test
    void TestAdd(){ //Testeo de que solo se puedan añadir hijos a nodos de un mapa
        Nodo a = new NodoAvistamiento(28,"A");
        Nodo b = new NodoTormentaMarina(20,"C");

        Mapa m = new Mapa(a);

        assertThrows(IllegalArgumentException.class, () -> m.addHijos(b,new NodoFin("C")));
    }
    @Test
    void TestMapa(){ //Testeo de que un mapa sin fin en todos los caminos no es navegable
        Nodo a = new NodoAvistamiento(28,"A");
        Nodo b = new NodoBatalla(new Flota(20,30,25,0,0),"B");
        Nodo c = new NodoTormentaMarina(20,"C");

        Mapa m1 = new Mapa(a);

        m1.addHijos(m1.getInicial(),b,c);
        m1.addHijos(c,new NodoFin("D"));

        assertFalse(m1.mapaValido());

        Mapa m2 = getMapa();

        assertTrue(m2.mapaValido());
    }
    @Test
    void TestResultadoIncursion(){
        Mapa m1 = getMapa();
        Flota f1 = new Flota(11,42,47,0,0);

        String resultado = recorrido(m1,f1);
        String esperado = "Sortie Result:\n     SUCCESS\n     Last Visited Node: H\n      Final HP: 1";

        assertEquals(esperado,resultado);

        Flota f2 = new Flota(1,25,0,46,28);

        resultado = recorrido(m1,f2);
        esperado = "Sortie Result:\n     FAIL\n     Last Visited Node: E\n      Final HP: -69"; //O enunciado dice -33 pero esta mal

        assertEquals(esperado,resultado);
    }
    @Test
    void TestNewick(){
        Mapa m1 = getMapa();

        String esperado = "(A WaypointSpotting, (B Battle, (D End), (E AirRaid, (K End))), (C Maelstrom, (F Battle, (H End), (G Maelstrom, (I End)))))";

        assertEquals(esperado, NewickSim(m1));
    }
    @Test
    void TestCamino(){
        Mapa m1 = getMapa();

        assertEquals("Smallest Node Count to End: 3",caminoCorto(m1));
    }
}