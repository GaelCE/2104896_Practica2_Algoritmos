package modelo;

public class Crupier extends Jugador{

    public Crupier(){
        super("Crupier");
    }

    public boolean debePedirCarta(){
        return super.getPuntaje()<17;
    }

}
