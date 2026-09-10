package vista;

import modelo.Jugador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class PantallaConfiguracion extends AnchorPane{
    private ListView<HBox> listaJugadores;
    private ArrayList<String> nombres;
    private TextField txtNombre;
    private Label lbError;
    private Button btnComenzar;
    private static final int MAX_JUGADORES=4;

    public PantallaConfiguracion(){
        nombres=new ArrayList<>();
        construirPantalla();
    }

    public Button getBotonComenzar(){
        return btnComenzar;
    }

    public ArrayList<Jugador> getJugadores(){
        ArrayList<Jugador> jugadores=new ArrayList<>();
        for(String nombre:nombres){
            jugadores.add(new Jugador(nombre));
        }
        return jugadores;
    }

    private void construirPantalla(){
        ImageView background=new ImageView(new Image(getClass().getResourceAsStream("/recursos/mesaBlackJack.png")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());

        VBox caja=new VBox(12);
        caja.setPadding(new Insets(30));
        caja.setAlignment(Pos.CENTER);
        caja.setStyle("-fx-background-color:#0d2010;-fx-border-color:#c8a96e;-fx-border-width:2;");

        Label lbTitulo=new Label("Blackjack");
        lbTitulo.setStyle("-fx-font-size:28;-fx-font-weight:bold;-fx-text-fill:#c8a96e;");

        Label lbSub=new Label("Selección de jugadores");
        lbSub.setStyle("-fx-font-size:16;-fx-text-fill:#e8d5b0;");

        txtNombre=new TextField();
        txtNombre.setPromptText("Nombre del jugador");
        txtNombre.setStyle("-fx-background-color:#0d2010;-fx-border-color:#8b6914;-fx-font-size:15;-fx-text-fill:white;");

        Button btnAgregar=new Button("Agregar jugador");
        btnAgregar.setStyle("-fx-background-color:#1a4a1a;-fx-text-fill:#e8d5b0;-fx-font-size:15;-fx-font-weight:bold;");
        btnAgregar.setOnAction(e->agregarJugador());

        listaJugadores=new ListView<>();
        listaJugadores.setPrefHeight(150);
        listaJugadores.setStyle("-fx-background-color:#0d2010;-fx-border-color:#8b6914;-fx-control-inner-background:#0d2010;");
        listaJugadores.setCellFactory(lv->new ListCell<>(){
            @Override
            protected void updateItem(HBox item,boolean vacio){
                super.updateItem(item,vacio);
                setStyle("-fx-background-color:#0d2010;");
                setGraphic(vacio?null:item);
            }
        });

        lbError=new Label("");
        lbError.setStyle("-fx-text-fill:#ff6666;-fx-font-size:15;");
        lbError.setVisible(false);
        lbError.setWrapText(true);

        btnComenzar=new Button("COMENZAR");
        btnComenzar.setDisable(true);
        btnComenzar.setStyle("-fx-background-color:#8b6914;-fx-text-fill:#fdf6e3;-fx-font-size:16;-fx-font-weight:bold;");

        caja.getChildren().addAll(lbTitulo,lbSub,txtNombre,btnAgregar,listaJugadores,lbError,btnComenzar);

        this.getChildren().addAll(background,caja);
        centrarEnPane(caja,0.15);
    }

    private void centrarEnPane(Region nodo,double porcentajeY){
        nodo.translateXProperty().bind(this.widthProperty().subtract(nodo.widthProperty()).divide(2));
        nodo.translateYProperty().bind(this.heightProperty().multiply(porcentajeY));
    }

    private void agregarJugador(){
        String nombre=txtNombre.getText().trim();
        if(nombre.isEmpty()){
            mostrarError("Ingresa un nombre.");
            return;
        }
        if(nombres.contains(nombre)){
            mostrarError("Ya existe un jugador con ese nombre.");
            return;
        }
        if(nombres.size()>=MAX_JUGADORES){
            mostrarError("Máximo "+MAX_JUGADORES+" jugadores.");
            return;
        }
        lbError.setVisible(false);
        nombres.add(nombre);

        Label lbNombreJugador=new Label(nombre);
        lbNombreJugador.setStyle("-fx-font-size:15;-fx-text-fill:#e8d5b0;");
        HBox.setHgrow(lbNombreJugador,Priority.ALWAYS);

        Button btnEliminar=new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color:#6b1a1a;-fx-text-fill:#fdf6e3;-fx-font-size:13;");

        HBox fila=new HBox(10,lbNombreJugador,btnEliminar);
        fila.setAlignment(Pos.CENTER_LEFT);

        btnEliminar.setOnAction(e->{
            nombres.remove(nombre);
            listaJugadores.getItems().remove(fila);
            if(nombres.isEmpty()) btnComenzar.setDisable(true);
        });

        listaJugadores.getItems().add(fila);
        txtNombre.clear();
        btnComenzar.setDisable(false);
    }

    private void mostrarError(String mensaje){
        lbError.setText(mensaje);
        lbError.setVisible(true);
    }
}