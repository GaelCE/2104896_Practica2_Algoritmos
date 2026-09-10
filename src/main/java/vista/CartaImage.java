package vista;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.CartaInglesa;

public class CartaImage extends Label {

    private double width = 100;
    private double height = 120;
    private CartaInglesa carta;
    private String ruta;
    public CartaImage(CartaInglesa carta) {
        this.carta = carta;
        crearImagenCarta();
    }

    private void crearImagenCarta() {
        String ruta = obtenerRuta();
        Image cartaImagen = new Image(getClass().getResourceAsStream(ruta));
        ImageView view = new ImageView(cartaImagen);
        if (carta.isFaceup()) {
            view.setFitWidth(width);
            view.setFitHeight(height);
        } else {
            view.setFitWidth(width);
            view.setFitHeight(height+19);
        }
        setGraphic(view);
    }

    private void crearImagenCartaBack(String ruta){
        this.ruta = ruta;
        Image cartaImagen = new Image(getClass().getResourceAsStream(ruta));
        ImageView view = new ImageView(cartaImagen);
        view.setFitWidth(width * 1.1);
        view.setFitHeight(height * 1.1);
        setGraphic(view);
    }

    public CartaInglesa getCarta(){
        return carta;
    }

    public String toString(){
        return carta.toString();
    }

    public void setWidthAndHeight(double w, double h){
        width = w;
        height = h;
        if(carta == null) crearImagenCartaBack(ruta);
        else crearImagenCarta();
    }

    private String obtenerRuta(){
        if (carta.isFaceup()) {
            return "/cartas/"+obtenerValor()+"_of_"+obtenerPalo()+".png";
        } else {
            return "/cartas/Backcard.png";
        }
    }

    private String obtenerValor(){
        String valor = "";
        switch(carta.getValor()){
            case 11:
                valor += "jack";
                break;
            case 12:
                valor += "queen";
                break;
            case 13:
                valor += "king";
                break;
            case 14:
                valor += "ace";
                break;
            default:
                valor += carta.getValor();
                break;
        }
        return valor;
    }

    private String obtenerPalo(){
        String palo = "";
        switch(carta.getPalo()){
            case PICA:
                palo += "spades";
                break;
            case TREBOL:
                palo += "clubs";
                break;
            case CORAZON:
                palo += "hearts";
                break;
            case DIAMANTE:
                palo += "diamonds";
                break;
        }
        return palo;
    }
}
