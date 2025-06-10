package e2;

import java.util.List;

public class Wizard extends Personaje{
    protected Wizard(int vida, int fuerza, int mana, List<MetodosAtaque> inventarioDeAtaque, List<MetodosDefensa> inventarioDeDefensa) {
        super(vida, fuerza, mana, inventarioDeAtaque, inventarioDeDefensa);
    }
}
