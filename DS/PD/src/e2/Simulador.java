package e2;

public class Simulador {
    //METODOS
    private static String resultado(Flota flota){
        String result;
        if(flota.getHP()<=0){
            result="FAIL";
        }
        else{
            result="SUCCESS";
        }
        return "Sortie Result:\n     " + result + "\n     Last Visited Node: " + flota.getPosicion().getName() + "\n      Final HP: " + flota.getHP();
    }
    public static String recorrido(Mapa mapa, Flota flota) {
        if(mapa==null || flota == null || !mapa.mapaValido()){
            throw new IllegalArgumentException("Argumentos no validos");
        }
        flota.setPosicion(mapa.getInicial());

        while (flota.getHP() > 0 && flota.getPosicion().next(flota) != null) {
            flota.getPosicion().action(flota);
            if (flota.getHP() > 0) {
                flota.setPosicion(flota.getPosicion().next(flota));
            }
        }

        return resultado(flota);
    }
    public static String NewickSim(Mapa mapa){
        return mapa.getInicial().Newick();
    }
    public static String caminoCorto(Mapa mapa){
        return "Smallest Node Count to End: " + mapa.getInicial().caminoCorto(0);
    }
}