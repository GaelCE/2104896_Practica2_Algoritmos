package modelo;
/**
 * Write a description of class Mazo here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mazo {
    private Pila<CartaInglesa> cartas = new Pila<>(52);

    public Mazo() {
        llenar(); // crea todas las cartas, excluyendo Jokers
        mezclar();
    }

    /**
     * Obtiene todas las cartas del mazo.
     * @return
     */
//    public ArrayList<CartaInglesa> getCartas() {
//        return cartas;
//    }

    public CartaInglesa obtenerUnaCarta() {
        return cartas.pull();
    }

    private void mezclar() {
        Random random=new Random();
        CartaInglesa [] arregloAuxiliar=new CartaInglesa[52];
        for (int i=0;i<52;i++){
            arregloAuxiliar[i]=cartas.pull();
        }
        for (int i=0;i<arregloAuxiliar.length;i++){
            int j= random.nextInt(i+1);
            CartaInglesa auxiliar=arregloAuxiliar[i];
            arregloAuxiliar[i]=arregloAuxiliar[j];
            arregloAuxiliar[j]=auxiliar;
        }

        for (int i=0;i<52;i++) {
            cartas.push(arregloAuxiliar[i]);
        }
    }

    private void llenar() {
        for (int i = 2; i <=14 ; i++) {
            for (Palo palo : Palo.values()) {
                CartaInglesa c = new CartaInglesa(i,palo, palo.getColor());
                cartas.push(c);
            }
        }
    }

//    public void ordenar() {
//        Collections.sort(cartas);
//    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
