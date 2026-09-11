//package com.example._104896_practica2_algoritmos;
//
//import controlador.Controlador;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import vista.PantallaConfiguracion;
//import vista.PantallaPrincipal;
//
//public class HelloApplication extends Application {
//
//    @Override
//    public void start(Stage stage){
//        mostrarConfiguracion(stage);
//    }
//
//    private void mostrarConfiguracion(Stage stage){
//        PantallaConfiguracion configuracion=new PantallaConfiguracion();
//        configuracion.getBotonComenzar().setOnAction(e->{
//            Controlador controlador=new Controlador(configuracion.getJugadores());
//            PantallaPrincipal pantallaPrincipal=new PantallaPrincipal(controlador);
//
//            pantallaPrincipal.getBotonIrAlMenu().setOnAction(ev->mostrarConfiguracion(stage));
//
//            stage.getScene().setRoot(pantallaPrincipal.getPane());
//        });
//
//        if(stage.getScene()==null){
//            stage.setScene(new Scene(configuracion,1280,720));
//            stage.show();
//        }else{
//            stage.getScene().setRoot(configuracion);
//        }
//    }
//
//}