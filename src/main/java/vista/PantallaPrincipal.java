package vista;

import controlador.Controlador;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import modelo.CartaInglesa;
import modelo.Jugador;

public class PantallaPrincipal{
    private AnchorPane paneBlackJack;
    private Label lbJugadorActual;
    private Controlador controlador;
    private HBox hbCartasJugador;
    private HBox hbCartasCrupier;
    private ImageButton btPedirCarta;
    private ImageButton btPlantarse;
    private Button btnIrAlMenu;
    private static final double RESX = 1920.0;
    private static final double RESGAEL = 1080.0;

    public PantallaPrincipal(Controlador controlador){
        paneBlackJack=new AnchorPane();
        this.controlador=controlador;
        btnIrAlMenu=new Button("Volver al menú");
        actualizarPantalla();
    }

    private void posicionarEnPane(Node nodo, double porcentajeX, double porcentajeY) {
        nodo.translateXProperty().unbind();
        nodo.translateYProperty().unbind();
        nodo.translateXProperty().bind(paneBlackJack.widthProperty().multiply(porcentajeX));
        nodo.translateYProperty().bind(paneBlackJack.heightProperty().multiply(porcentajeY));
    }

    private void transicionDeRonda(Jugador jugador){
        paneBlackJack.getChildren().clear();

        ImageView background=new ImageView(new Image(getClass().getResourceAsStream("/recursos/mesaBlackJack.png")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(paneBlackJack.widthProperty());
        background.fitHeightProperty().bind(paneBlackJack.heightProperty());
        paneBlackJack.getChildren().add(background);

        VBox caja=new VBox(15);
        caja.setAlignment(Pos.CENTER);
        caja.setStyle("-fx-background-color:#0d2010;-fx-padding:30;-fx-border-color:#c8a96e;-fx-border-width:2;");

        Label lbNombre=new Label(jugador.getNombre());
        lbNombre.setStyle("-fx-text-fill:#f5d97a;-fx-font-size:24px;-fx-font-weight:bold;");

        HBox cajaCartas=new HBox(10);
        cajaCartas.setAlignment(Pos.CENTER);
        for(CartaInglesa carta:jugador.getMano()){
            cajaCartas.getChildren().add(new CartaImage(carta));
        }

        Label lbPuntaje=new Label("Puntaje: "+jugador.getPuntaje());
        lbPuntaje.setStyle("-fx-text-fill:white;-fx-font-size:18px;");

        Label lbEstado=new Label(jugador.getSePaso()?"Se pasó de 21":"Turno terminado");
        lbEstado.setStyle("-fx-text-fill:"+(jugador.getSePaso()?"#ff6666":"#88ff88")+";-fx-font-size:18px;-fx-font-weight:bold;");

        Button btnContinuar=new Button("Continuar");
        btnContinuar.setOnAction(e->actualizarPantalla());

        caja.getChildren().addAll(lbNombre,cajaCartas,lbPuntaje,lbEstado,btnContinuar);
        posicionarEnPane(caja,0.35,0.35);
        paneBlackJack.getChildren().add(caja);
    }

    private void construirPantallaJuego(){
        paneBlackJack.getChildren().clear();

        ImageView background=new ImageView(new Image(getClass().getResourceAsStream("/recursos/mesaBlackJack.png")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(paneBlackJack.widthProperty());
        background.fitHeightProperty().bind(paneBlackJack.heightProperty());

        lbJugadorActual=new Label();
        hbCartasCrupier=new HBox(10);
        hbCartasJugador=new HBox(10);

        btPedirCarta=new ImageButton("/recursos/botonPedirCarta.png","/recursos/botonPedirCartaBrillante.png",370,200);
        btPedirCarta.setOnAction(e->{
            Jugador jugadorAntes=controlador.getJugadorEnTurno();
            controlador.pedirCarta();
            if(controlador.getUltimoJugador()==jugadorAntes){
                transicionDeRonda(jugadorAntes);
            }else{
                actualizarPantalla();
            }
        });

        btPlantarse=new ImageButton("/recursos/botonPlantarse.png","/recursos/botonPlantarseBrillante.png",370,200);
        btPlantarse.setOnAction(e->{
            Jugador jugadorAntes=controlador.getJugadorEnTurno();
            controlador.plantarse();
            transicionDeRonda(jugadorAntes);
        });

        paneBlackJack.getChildren().addAll(background,hbCartasCrupier,hbCartasJugador,btPedirCarta,btPlantarse,lbJugadorActual);

        posicionarEnPane(btPedirCarta,450/RESX,800/RESGAEL);
        posicionarEnPane(btPlantarse,950/RESX,800/RESGAEL);
        posicionarEnPane(hbCartasCrupier,830/RESX,50/RESGAEL);
        posicionarEnPane(lbJugadorActual,20/RESX,20/RESGAEL);
    }

    private void actualizarPantalla(){
        construirPantallaJuego();
        if(controlador.esRondaTerminada()){
            resultados();
            return;
        }

        Jugador actual=controlador.getJugadorEnTurno();
        lbJugadorActual.setText("Turno de: "+actual.getNombre());
        lbJugadorActual.setStyle("-fx-text-fill:#f5d97a;-fx-font-size:24px;-fx-font-weight:bold;");

        hbCartasJugador.getChildren().clear();
        for(CartaInglesa carta:actual.getMano()){
            hbCartasJugador.getChildren().add(new CartaImage(carta));
        }

        double posicionX=830-(actual.getMano().size()-2)*100;
        posicionarEnPane(hbCartasJugador,posicionX/RESX,570/RESGAEL);

        hbCartasCrupier.getChildren().clear();
        for(CartaInglesa carta:controlador.getManoCrupier()){
            hbCartasCrupier.getChildren().add(new CartaImage(carta));
        }
    }

    public void resultados(){
        paneBlackJack.getChildren().clear();

        ImageView background=new ImageView(new Image(getClass().getResourceAsStream("/recursos/mesaBlackJack.png")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(paneBlackJack.widthProperty());
        background.fitHeightProperty().bind(paneBlackJack.heightProperty());
        paneBlackJack.getChildren().add(background);

        VBox caja=new VBox(15);
        caja.setAlignment(Pos.CENTER);
        caja.setStyle("-fx-background-color:#0d2010;-fx-padding:30;-fx-border-color:#c8a96e;-fx-border-width:2;");

        HBox jugadores=new HBox(15);
        jugadores.setAlignment(Pos.CENTER);

        for (Jugador jugador : controlador.getJugadores()) {
            VBox resultadoJugador = new VBox(15);
            resultadoJugador.setAlignment(Pos.CENTER);

            Label lbNombre = new Label(jugador.getNombre());
            lbNombre.setStyle("-fx-text-fill:#f5d97a;-fx-font-size:24px;-fx-font-weight:bold;");

            HBox cajaCartas = new HBox(10);
            cajaCartas.setAlignment(Pos.CENTER);
            for (CartaInglesa carta : jugador.getMano()) {
                cajaCartas.getChildren().add(new CartaImage(carta));
            }

            Label lbPuntaje = new Label("Puntaje: " + jugador.getPuntaje());
            lbPuntaje.setStyle("-fx-text-fill:white;-fx-font-size:18px;");

            Label lbResultado = new Label(controlador.getResultado(jugador));
            lbResultado.setStyle("-fx-text-fill:"+(jugador.getSePaso()?"#ff6666":"#88ff88")+";-fx-font-size:18px;-fx-font-weight:bold;");

            resultadoJugador.getChildren().addAll(lbNombre,cajaCartas,lbPuntaje,lbResultado);
            jugadores.getChildren().add(resultadoJugador);
        }
        Label lbCrupier = new Label("Crupier");
        lbCrupier.setStyle("-fx-text-fill:#f5d97a;-fx-font-size:24px;-fx-font-weight:bold;");
        HBox cajaCartas = new HBox(10);
        Label lbPuntaje = new Label("Puntaje: " + controlador.getPuntajeCrupier());
        lbPuntaje.setStyle("-fx-text-fill:white;-fx-font-size:18px;");

        cajaCartas.setAlignment(Pos.CENTER);
        for (CartaInglesa carta : controlador.getManoCrupier()){
            cajaCartas.getChildren().add(new CartaImage(carta));
        }

        Button btnNuevaRonda=new Button("Jugar de nuevo");
        btnNuevaRonda.setOnAction(e->nuevaRonda());
        Button btnSalir=new Button("Salir");
        btnSalir.setOnAction(e-> Platform.exit());

        HBox cajaBotones=new HBox(10);
        cajaBotones.setAlignment(Pos.CENTER);
        cajaBotones.getChildren().addAll(btnNuevaRonda,btnSalir,btnIrAlMenu);

        caja.getChildren().addAll(lbCrupier,cajaCartas,lbPuntaje,jugadores,cajaBotones);
        centrarEnPane(caja,20/RESGAEL);
        paneBlackJack.getChildren().add(caja);
    }

    private void centrarEnPane(Region nodo, double porcentajeY){
        nodo.translateXProperty().bind(
                paneBlackJack.widthProperty().subtract(nodo.widthProperty()).divide(2)
        );
        nodo.translateYProperty().bind(
                paneBlackJack.heightProperty().multiply(porcentajeY)
        );
    }

    private void nuevaRonda(){
        controlador.reiniciarRonda();
        actualizarPantalla();
    }

    public Button getBotonIrAlMenu(){
        return btnIrAlMenu;
    }

    //Getter para testeo
    public AnchorPane getPane(){
        return paneBlackJack;
    }
}