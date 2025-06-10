package e2;

import java.util.List;

public abstract class Personaje {
    private int vida;
    private int fuerza;
    private int mana;
    private final List<MetodosAtaque> inventarioDeAtaque;
    private final List<MetodosDefensa> inventarioDeDefensa;

    protected Personaje(int vida, int fuerza, int mana, List<MetodosAtaque> inventarioDeAtaque, List<MetodosDefensa> inventarioDeDefensa) {
        if(vida<=0 || vida >20 || fuerza<1 || fuerza>10 || mana < 0 || mana >10){
            throw new IllegalArgumentException("Estadisticas no validas");
        }
        this.vida = vida;
        this.fuerza = fuerza;
        this.mana=mana;
        this.inventarioDeAtaque = inventarioDeAtaque;
        this.inventarioDeDefensa = inventarioDeDefensa;
    }

    protected int getVida() {
        return vida;
    }

    protected int getFuerza() {
        return fuerza;
    }

    protected int getMana() {
        return mana;
    }

    protected List<MetodosAtaque> getInventarioDeAtaque() {
        return this.inventarioDeAtaque;
    }

    protected List<MetodosDefensa> getInventarioDeDefensa() {
        return this.inventarioDeDefensa;
    }

    protected void giveItemAtaque(MetodosAtaque item) {
        if(inventarioDeAtaque.size()==5){
            throw new IllegalArgumentException("Inventario lleno");
        }
        inventarioDeAtaque.add(item);
    }

    protected void giveItemDefensa(MetodosDefensa item) { //Utilizo sobrecarga de metodos
        if(inventarioDeDefensa.size()==5){ //Si es de def
            throw new IllegalArgumentException("Inventario lleno");
        }
        inventarioDeDefensa.add(item);
    }

    protected void pegar(Personaje p){ //Quien llama al metodo pega al argumento
        int dmgTotal=0;
        int proteccionTotal=0;
        int dmgNeto;
        //Calcular dmg total
        for(MetodosAtaque o : this.inventarioDeAtaque){
            if(o.getManaNecesario()<=this.mana && o.getUsos()>0){
                dmgTotal+=o.getDmg();
                o.reducirUsos();
                this.mana-=o.getManaNecesario();
            }
        }
        //Calcular defensa total
        for(MetodosDefensa o : p.inventarioDeDefensa){
            if(o.getFuerzaNecesaria()<=p.fuerza){
                proteccionTotal+=o.getProteccion();
                p.fuerza-=o.getFuerzaNecesaria();
            }
        }
        //Calcular dmg neto
        if(dmgTotal<proteccionTotal){
            dmgNeto=0;
        }
        else{
            dmgNeto=dmgTotal-proteccionTotal;
        }
        //p.vida-=(dmg neto);
        p.vida-=dmgNeto;
    }
}