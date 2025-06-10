package e2;

public class Fireball extends Objetos implements MetodosAtaque{
    DatosAtaque parteAtaque;
    public Fireball(int dmg, int manaNecesario) {
        super(Tipo.ATAQUE);
        parteAtaque = new DatosAtaque(dmg, manaNecesario, MetodosAtaque.usosFireball);
    }

    @Override
    public int getManaNecesario() {
        return parteAtaque.getManaNecesario();
    }

    @Override
    public int getUsos() {
        return parteAtaque.getUsos();
    }

    @Override
    public void reducirUsos() {
        parteAtaque.reducirUsos();
    }
    @Override
    public int getDmg() {
        if (parteAtaque.getUsos() == 1) {
            return parteAtaque.getDmg() - 1;
        } else {
            return parteAtaque.getDmg();
        }
    }
}