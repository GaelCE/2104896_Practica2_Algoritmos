package controlador;

import modelo.BlackJack;
import modelo.CartaInglesa;
import modelo.Jugador;
import modelo.Pila;

public class Controlador{
    private BlackJack blackJack;
    private Pila<Jugador> porJugar;
    private Pila<Jugador> yaJugaron;
    private boolean rondaTerminada;

    public Controlador(Pila<Jugador> jugadoresIniciales){
        int cantidad=jugadoresIniciales.getSize();
        Jugador[] arreglo=new Jugador[cantidad];

        for(int i=cantidad-1;i>=0;i--){
            arreglo[i]=jugadoresIniciales.pull();
        }

        Pila<Jugador> paraBlackJack=new Pila<>(cantidad);
        for(int i=0;i<cantidad;i++){
            paraBlackJack.push(arreglo[i]);
        }
        this.blackJack=new BlackJack(paraBlackJack);
        this.porJugar=new Pila<>(cantidad);
        for(int i=cantidad-1;i>=0;i--){
            this.porJugar.push(arreglo[i]);
        }

        this.yaJugaron=new Pila<>(cantidad);
        this.rondaTerminada=false;
    }

    public Jugador getJugadorEnTurno(){
        if(porJugar.vacia()){
            return null;
        }
        return porJugar.verTope();
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

    private void avanzarTurno(){
        yaJugaron.push(porJugar.pull());
        if(porJugar.vacia()){
            finalizarRonda();
        }
    }

    private void finalizarRonda(){
        blackJack.finalizarRonda();
        rondaTerminada=true;
    }

    public boolean esRondaTerminada(){
        return rondaTerminada;
    }

    public void reiniciarRonda(){
        while(!yaJugaron.vacia()){
            porJugar.push(yaJugaron.pull());
        }
        rondaTerminada=false;
        blackJack.nuevaRonda();
    }

    public Jugador getUltimoJugador(){
        return yaJugaron.verTope();
    }

    public String getResultado(Jugador jugador){
        return blackJack.showdown(jugador);
    }

    public int getPuntajeCrupier(){
        return blackJack.getPuntajeCrupier();
    }

    public Pila<CartaInglesa> getManoCrupier(){
        return blackJack.getManoCrupier();
    }

    public Pila<Jugador> getJugadores(){
        return blackJack.getJugadores();
    }
}