package modelo;

public class BlackJack{
    private Mazo mazo;
    private Pila<Jugador>jugadores;
    private Crupier crupier;

    public BlackJack(Pila<Jugador>jugadores){
        mazo=new Mazo();
        this.jugadores=jugadores;
        crupier=new Crupier();
        repartirLasCartas();
    }

    public void repartirCarta(Jugador jugador){
        CartaInglesa carta=mazo.obtenerUnaCarta();
        jugador.recibirCarta(carta);
        if(jugador!=crupier){
            carta.makeFaceUp();
        }
    }

    public void repartirLasCartas(){
        int cantidad=jugadores.getSize();
        Pila<Jugador> pilaAuxiliar=new Pila<>(cantidad);
        Jugador jugador;
        for(int i=0;i<2;i++){
            for(int j=0;j<cantidad;j++){
                jugador=jugadores.pull();
                repartirCarta(jugador);
                pilaAuxiliar.push(jugador);
            }
            repartirCarta(crupier);
            for(int j=0;j<cantidad;j++){
                jugadores.push(pilaAuxiliar.pull());
            }
        }
        CartaInglesa carta=crupier.getMano().pull();
        carta.makeFaceUp();
        crupier.getMano().push(carta);
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
        int cantidad=jugadores.getSize();
        Pila<Jugador> pilaAuxiliar=new Pila<>(cantidad);
        Jugador jugador;
        mazo=new Mazo();
        for(int i=0;i<cantidad;i++){
            jugador=jugadores.pull();
            jugador.vaciarMano();
            repartirCarta(jugador);
            repartirCarta(jugador);
            pilaAuxiliar.push(jugador);
        }
        for(int i=0;i<cantidad;i++){
            jugadores.push(pilaAuxiliar.pull());
        }
        crupier.vaciarMano();
        crupier.recibirCarta(mazo.obtenerUnaCarta());
        crupier.recibirCarta(mazo.obtenerUnaCarta());
        CartaInglesa carta=crupier.getMano().pull();
        carta.makeFaceUp();
        crupier.getMano().push(carta);
    }

    public boolean todosSePasaron(){
        int cantidad=jugadores.getSize();
        Pila<Jugador> pilaAuxiliar=new Pila<>(cantidad);
        Jugador jugador;
        boolean todosPasaron=true;
        for(int i=0;i<cantidad;i++){
            jugador=jugadores.pull();
            if(!jugador.getSePaso()){
                todosPasaron=false;
            }
            pilaAuxiliar.push(jugador);
        }
        for(int i=0;i<cantidad;i++){
            jugadores.push(pilaAuxiliar.pull());
        }
        return todosPasaron;
    }
    //

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

    public Pila<Jugador>getJugadores(){
        return jugadores;
    }

    public int getPuntajeCrupier(){
        return crupier.getPuntaje();
    }

    public Pila<CartaInglesa>getManoCrupier(){
        return crupier.getMano();
    }
}