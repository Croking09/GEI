package e2;

public class Sword extends Objetos implements MetodosAtaque, MetodosDefensa{
    DatosAtaque parteAtaque;
    DatosDefensa parteDefensa;

    public Sword(Objetos.Tipo tipo, int dmg, int manaNecesario, String codename, int proteccion, int fuerzaNecesaria){
        super(tipo);
        parteAtaque = new DatosAtaque(dmg, manaNecesario, MetodosAtaque.usosSword);
        parteDefensa = new DatosDefensa(codename, proteccion, fuerzaNecesaria);
    }
    @Override
    public int getDmg() {
        return parteAtaque.getDmg();
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
    public String getCodename() {
        return parteDefensa.codename();
    }

    @Override
    public int getProteccion() {
        return parteDefensa.proteccion();
    }

    @Override
    public int getFuerzaNecesaria() {
        return parteDefensa.fuerzaNecesaria();
    }
    @Override
    public void reducirUsos() {
        parteAtaque.reducirUsos();
    }
}