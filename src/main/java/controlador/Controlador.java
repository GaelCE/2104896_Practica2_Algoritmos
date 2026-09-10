package controlador;

import modelo.BlackJack;
import modelo.CartaInglesa;
import modelo.Jugador;

import java.util.ArrayList;

public class Controlador{
    private BlackJack blackJack;
    private int indiceTurnoActual;
    private boolean rondaTerminada;
    private Jugador ultimoJugador;

    public Controlador(ArrayList<Jugador> jugadores){
        blackJack=new BlackJack(jugadores);
        indiceTurnoActual=0;
        rondaTerminada=false;
    }

    public Jugador getJugadorEnTurno(){
        if(rondaTerminada){
            return null;
        }
        return blackJack.getJugadores().get(indiceTurnoActual);
    }

    public void pedirCarta(){
        Jugador jugador=getJugadorEnTurno();
        blackJack.repartirCarta(jugador);
        if(jugador.getSePaso()){
            avanzarTurno();
        }
    }

    public void plantarse(){
        avanzarTurno();
    }

    private void finalizarRonda(){
        blackJack.finalizarRonda();
        rondaTerminada=true;
    }

    public boolean esRondaTerminada(){
        return rondaTerminada;
    }

    private void avanzarTurno(){
        ultimoJugador=getJugadorEnTurno();
        indiceTurnoActual++;
        if(indiceTurnoActual>=blackJack.getJugadores().size()){
            finalizarRonda();
        }
    }

    public void reiniciarRonda(){
        blackJack.nuevaRonda();
        indiceTurnoActual=0;
        rondaTerminada=false;
    }

    public Jugador getUltimoJugador(){
        return ultimoJugador;
    }

    public String getResultado(Jugador jugador){
        return blackJack.showdown(jugador);
    }

    public int getPuntajeCrupier(){
        return blackJack.getPuntajeCrupier();
    }

    public ArrayList<CartaInglesa> getManoCrupier(){
        return blackJack.getManoCrupier();
    }

    public ArrayList<Jugador> getJugadores(){
        return blackJack.getJugadores();
    }
}