package e2;

public class DatosAtaque {
    private final int dmg;
    private final int manaNecesario;
    private int usos;

    public DatosAtaque(int dmg, int manaNecesario, int usos) {
        this.dmg = dmg;
        this.manaNecesario = manaNecesario;
        this.usos = usos;
    }

    public int getDmg() {
        return dmg;
    }

    public int getManaNecesario() {
        return manaNecesario;
    }

    public int getUsos() {
        return usos;
    }

    public void reducirUsos() {
        this.usos--;
    }
}