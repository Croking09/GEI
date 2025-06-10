package e2;

public class Juego {

    public static int ataque(Personaje a, Personaje b){
        int vidainicial = b.getVida();
        a.pegar(b);
        return (vidainicial - b.getVida());
    }

    public static Personaje lucha(Personaje a, Personaje b, int turnos){
        int x=0;

        while(x<turnos && a.getVida()>0 && b.getVida()>0) {
            if (x % 2 == 0) {
                a.pegar(b);
            } else {
                b.pegar(a);
            }
            x++;
        }

        if(a.getVida()<=0){
            return b;
        }
        else if(b.getVida()<=0){
            return a;
        }
        else{
            return null;
        }
    }
}
