package e2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonajeTest {
    @Test
    void test_pegar() {
        Warrior a1 = new Warrior(20,5,7,new ArrayList<>(),new ArrayList<>());
        Wizard d1 = new Wizard(10,3,7,new ArrayList<>(),new ArrayList<>());

        a1.giveItemAtaque(new Sword(Objetos.Tipo.ATAQUE,5,5,"Espadon",5,5));
        d1.giveItemDefensa(new Armor("Warmog", 2,1));

        a1.pegar(d1);

        assertEquals(7,d1.getVida());
    }

    @Test
    public void test_winner() {
        Personaje a = new Warrior(10, 5, 5, new ArrayList<>(), new ArrayList<>());
        a.giveItemAtaque(new Wand(Objetos.Tipo.ATAQUE,5,5,"Espadon",5,5));

        Personaje b = new Wizard(5, 5, 5, new ArrayList<>(), new ArrayList<>());

        Personaje winner = Juego.lucha(a, b, 10);

        assertEquals(a,winner);
    }

    @Test
    public void test_Empate() {
        Personaje a = new Warrior(10, 5, 5, new ArrayList<>(), new ArrayList<>());
        Personaje b = new Wizard(10, 5, 5, new ArrayList<>(), new ArrayList<>());

        Personaje winner = Juego.lucha(a, b, 5);

        assertNull(winner);
    }

    @Test
    public void test_Sword_Defensa(){
        Personaje a = new Warrior(10,5,5,new ArrayList<>(),new ArrayList<>());
        Personaje b = new Wizard(10,5,5,new ArrayList<>(),new ArrayList<>());

        a.giveItemAtaque(new Fireball(10,3));
        b.giveItemDefensa(new Sword(Objetos.Tipo.DEFENSA,3,5,"Espada",15,1));

        assertEquals(0,Juego.ataque(a,b));
        assertEquals(4,b.getFuerza());
        assertEquals(2,a.getMana());
    }
}