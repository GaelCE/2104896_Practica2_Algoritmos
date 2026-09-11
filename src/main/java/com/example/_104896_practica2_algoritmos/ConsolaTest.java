package com.example._104896_practica2_algoritmos;

import controlador.Controlador;
import modelo.CartaInglesa;
import modelo.Jugador;
import modelo.Pila;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int decisionSalir;
        int decision;
        do{
            Pila<Jugador> jugadores=new Pila<>();
            jugadores.push(new Jugador("Jugador1"));
            jugadores.push(new Jugador("Jugador2"));
            Controlador controlador=new Controlador(jugadores);
            while(!controlador.esRondaTerminada()){
                Jugador actual=controlador.getJugadorEnTurno();
                do{
                    System.out.println("Turno de: "+actual.getNombre());
                    System.out.println("Crupier: "+controlador.getManoCrupier());
                    System.out.println("Mano actual: "+actual.getMano());
                    System.out.println("Puntaje actual: "+actual.getPuntaje());
                    System.out.println("[1] Plantarse  [2] Pedir carta");
                    decision = scanner.nextInt();
                }while(decision<1||decision>2);

                if(decision==2){
                    controlador.pedirCarta();
                }else{
                    controlador.plantarse();
                }
            }

            System.out.println("Mano del crupier: "+controlador.getManoCrupier());
            System.out.println("Puntaje del crupier: "+controlador.getPuntajeCrupier());
            int cantidadJugadores=controlador.getJugadores().getSize();
            Pila<Jugador> auxiliar=new Pila<>(cantidadJugadores);

            for(int i=0;i<cantidadJugadores;i++){
                Jugador jugador=controlador.getJugadores().pull();
                System.out.println(jugador.getNombre()+": "+jugador.getMano().toString()+" "+controlador.getResultado(jugador));
                auxiliar.push(jugador);
            }
            for(int i=0;i<cantidadJugadores;i++){
                controlador.getJugadores().push(auxiliar.pull());
            }
            do{
                System.out.println("Desea jugar de nuevo?");
                System.out.println("[1] Jugar de nuevo");
                System.out.println("[2] Salir");
                decisionSalir = scanner.nextInt();
            }while(decisionSalir<1||decisionSalir>2);
        }while(decisionSalir!=2);
    }

}