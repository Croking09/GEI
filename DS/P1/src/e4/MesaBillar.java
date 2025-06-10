package e4;

import java.util.ArrayList;
import java.util.Arrays;

public class MesaBillar {
    private final ArrayList<BolaBillar> mesa;
    private final ArrayList<BolaBillar> cajetin;
    private boolean iniciada;
    public MesaBillar(){
        this.iniciada=false;
        this.mesa = new ArrayList<>();
        this.cajetin = new ArrayList<>();
        cajetin.addAll(Arrays.asList(BolaBillar.values()));
    }
    public void iniciarPartida(){
        this.iniciada=true;
        this.mesa.addAll(cajetin);
        this.cajetin.clear();
        System.out.println("Ha comenzado una partida, todas las bolas se han movido del cajetin a la mesa.");
    }

    public void meterBola(BolaBillar bola){
        if(bola==null || !this.mesa.contains(bola)){
            throw new IllegalArgumentException("Bola no valida");
        }

        if(bola==BolaBillar.BLANCA && this.iniciada){
            System.out.println("Se ha metido la bola blanca, volvera a la mesa");
        }
        else if(bola==BolaBillar.BOLA8){
            this.iniciada=false;
        }
        else{
            this.mesa.remove(bola);
            this.cajetin.add(bola);
        }
    }
    public ArrayList<BolaBillar> bolasMesa() {return this.mesa;}

    public ArrayList<BolaBillar> bolasCajetin() {return this.cajetin;}

    public boolean esPartidaIniciada() {return this.iniciada;}

    public BolaBillar.Tipo obtenerGanador(){
        int lisa=0, rayada=0;

        for(BolaBillar bola : this.mesa){
            if(bola.getTipo()==BolaBillar.Tipo.RAYADA){
                rayada++;
            }
            if(bola.getTipo()==BolaBillar.Tipo.LISA && bola!=BolaBillar.BOLA8 && bola!=BolaBillar.BLANCA){
                lisa++;
            }
        }
        if(lisa>rayada){
            return BolaBillar.Tipo.RAYADA;
        }
        else if(rayada>lisa){
            return BolaBillar.Tipo.LISA;
        }
        else return null;
    }
}