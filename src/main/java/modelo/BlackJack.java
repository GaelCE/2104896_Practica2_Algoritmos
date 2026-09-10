package modelo;

import java.util.ArrayList;

public class BlackJack{
    private Mazo mazo;
    private ArrayList<Jugador>jugadores;
    private Crupier crupier;

    public BlackJack(ArrayList<Jugador>jugadores){
        mazo=new Mazo();
        this.jugadores=jugadores;
        crupier=new Crupier();
        for(int i=0;i<2;i++){
            for(int j=0;j<jugadores.size();j++){
                repartirCarta(jugadores.get(j));
            }
            repartirCarta(crupier);
        }
        crupier.getMano().getFirst().makeFaceUp();
    }

    public void repartirCarta(Jugador jugador){
        CartaInglesa carta=mazo.obtenerUnaCarta();
        jugador.recibirCarta(carta);
        if(jugador!=crupier){
            carta.makeFaceUp();
        }
    }

    public String showdown(Jugador jugador){
        if(jugador.getSePaso()){
            return "Perdió";
        }else if(crupier.getSePaso()||jugador.getPuntaje()>crupier.getPuntaje()){
            return "Ganó";
        }else if(crupier.getPuntaje()>jugador.getPuntaje()){
            return "Perdió";
        }else{
            return "Empate";
        }
    }

    public void nuevaRonda(){
        mazo = new Mazo();
        for (int i=0;i<jugadores.size();i++){
            jugadores.get(i).vaciarMano();
            repartirCarta(jugadores.get(i));
            repartirCarta(jugadores.get(i));
        }
        crupier.vaciarMano();
        crupier.recibirCarta(mazo.obtenerUnaCarta());
        crupier.recibirCarta(mazo.obtenerUnaCarta());
        crupier.getMano().getFirst().makeFaceUp();
    }

    public boolean todosSePasaron(){
        for(Jugador jugador:jugadores){
            if(!jugador.getSePaso()){
                return false;
            }
        }
        return true;
    }

    public void turnoCrupier(){
        while(crupier.debePedirCarta()){
            repartirCarta(crupier);
        }
    }

    public void finalizarRonda(){
        if(!todosSePasaron()){
            turnoCrupier();
        }
        crupier.setUpMano();
    }

    public ArrayList<Jugador>getJugadores(){
        return jugadores;
    }

    public int getPuntajeCrupier(){
        return crupier.getPuntaje();
    }

    public ArrayList<CartaInglesa>getManoCrupier(){
        return crupier.getMano();
    }
}