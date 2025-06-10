package e2;

public class Armor extends Objetos implements MetodosDefensa{
    DatosDefensa datos;
    public Armor(String codename, int proteccion, int fuerzaNecesaria) {
        super(Tipo.DEFENSA);
        datos = new DatosDefensa(codename, proteccion, fuerzaNecesaria);
    }
    @Override
    public String getCodename() {
        return datos.codename();
    }

    @Override
    public int getProteccion() {
        return datos.proteccion();
    }

    @Override
    public int getFuerzaNecesaria() {
        return datos.fuerzaNecesaria();
    }
}