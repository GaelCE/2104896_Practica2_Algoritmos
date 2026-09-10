package vista;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

public class ImageButton extends Button {
    private ImageView normal;
    private ImageView resaltado;

    public ImageButton(String rutaNormal,String rutaResaltado,double ancho,double alto){
        Image imagenNormal=new Image(getClass().getResourceAsStream(rutaNormal));
        normal=new ImageView(imagenNormal);
        normal.setFitWidth(ancho);
        normal.setFitHeight(alto);
        normal.setPreserveRatio(false);

        Image imagenResaltada=new Image(getClass().getResourceAsStream(rutaResaltado));
        resaltado=new ImageView(imagenResaltada);
        resaltado.setFitWidth(ancho);
        resaltado.setFitHeight(alto);
        resaltado.setPreserveRatio(false);

        setGraphic(normal);
        setStyle("-fx-background-color:transparent;-fx-padding:0;");
        setCursor(Cursor.HAND);
        setOnMouseEntered(e->setGraphic(resaltado));
        setOnMouseExited(e->setGraphic(normal));
    }
}