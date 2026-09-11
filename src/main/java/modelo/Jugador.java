package modelo;

import java.util.ArrayList;

public class Jugador {
    private Pila<CartaInglesa> mano;
    private String nombre;

    public Jugador(String nombre){
        mano = new Pila<>();
        this.nombre = nombre;
    }

    public void recibirCarta(CartaInglesa carta){
        mano.push(carta);
    }

    public void vaciarMano(){
        mano.clear();
    }

    public void setUpMano(){
        CartaInglesa carta;
        int cantidadCartas=mano.getSize();
        Pila<CartaInglesa> pilaAuxiliar=new Pila<>(cantidadCartas);
        for(int i=0;i<cantidadCartas;i++){
            carta=mano.pull();
            carta.makeFaceUp();
            pilaAuxiliar.push(carta);
        }
        for(int i=0;i<cantidadCartas;i++){
            mano.push(pilaAuxiliar.pull());
        }
    }

    //Getters
    public Pila<CartaInglesa> getMano(){ return mano; }
    public String getNombre() { return nombre;}
    public int getPuntaje() {
        int puntaje=0;
        int ases=0;
        CartaInglesa carta;
        int cantidadCartas=mano.getSize();
        Pila<CartaInglesa> pilaAuxiliar=new Pila<>(cantidadCartas);
        for(int i=0;i<cantidadCartas;i++){
            carta=mano.pull();
            if(carta.getValor()==14){
                puntaje+=11;
                ases++;
            }else if(carta.getValor()>10&&carta.getValor()<14){
                puntaje+=10;
            }else{
                puntaje+=carta.getValor();
            }
            pilaAuxiliar.push(carta);
        }
        for(int i=0;i<cantidadCartas;i++){
            mano.push(pilaAuxiliar.pull());
        }
        while(puntaje>21&&ases>0){
            puntaje-=10;
            ases--;
        }
        return puntaje;
    }

    public boolean getSePaso(){
        return getPuntaje()>21;
    }
}
