package e2;

public class Wand extends Objetos implements MetodosAtaque, MetodosDefensa{
    DatosAtaque parteAtaque;
    DatosDefensa parteDefensa;
    public Wand(Objetos.Tipo tipo, int dmg, int manaNecesario, String codename, int proteccion, int fuerzaNecesaria) {
        super(tipo);
        parteAtaque = new DatosAtaque(dmg, manaNecesario, MetodosAtaque.usosWand);
        parteDefensa = new DatosDefensa(codename,proteccion,fuerzaNecesaria);
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

    @Override
    public int getDmg() {
        if(parteAtaque.getUsos()==MetodosAtaque.usosWand){
            return 2*parteAtaque.getDmg();
        }
        else{
            return parteAtaque.getDmg();
        }
    }
}