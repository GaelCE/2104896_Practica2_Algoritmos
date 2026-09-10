package modelo;

import java.util.ArrayList;

public class Jugador {
    private ArrayList<CartaInglesa> mano;
    private String nombre;

    public Jugador(String nombre){
        mano = new ArrayList<>();
        this.nombre = nombre;
    }

    public void recibirCarta(CartaInglesa carta){
        mano.add(carta);
    }

    public void vaciarMano(){
        mano.clear();
    }

    public void setUpMano(){
        for (int i=0;i<mano.size();i++){
            mano.get(i).makeFaceUp();
        }
    }

    //Getters
    public ArrayList<CartaInglesa> getMano(){ return mano; }
    public String getNombre() { return nombre;}
    public int getPuntaje() {
        int puntaje=0;
        int ases=0;
        for (CartaInglesa carta : mano){
            if (carta.getValor()==14){
                puntaje+=11;
                ases++;
            }
            else if (carta.getValor()>10&&carta.getValor()<14){
                puntaje+=10;
            }
            else {
                puntaje+= carta.getValor();
            }
        }
        while (puntaje>21&&ases>0){
            puntaje-=10;
            ases--;
        }
        return puntaje;
    }

    public boolean getSePaso(){
        return getPuntaje()>21;
    }
}
