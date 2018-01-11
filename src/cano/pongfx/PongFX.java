/*PAQUETE DONDE SE ENCUENTRA EL ARCHIVO*/
package cano.pongfx;

/*LOS NOMBRES DE LAS CLASES SIEMPRE EMPIEZAN POR MAYUSCULA*/
import javafx.application.Application;
import javafx.scene.Scene;
/*CONTENEDOR PARA LA BOLA*/
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/*CLASE PARA LA BOLA*/
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Juanma El Cano
 */
public class PongFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        /*LIENZO DONDE ESTA LA BOLA*/
        Pane root = new Pane();
        /*EL TAMAÑO DE LA VENTANA*/
        Scene ventana = new Scene(root, 600, 400);
        /*CAMBIO DE COLOR DE LA VENTANA*/
        ventana.setFill(Color.BLACK);
        primaryStage.setTitle("PongFX");
        primaryStage.setScene(ventana);
        primaryStage.show();
        /*CREAR CIRCULO*/
        Circle ball = new Circle(10, 30, 7);
        /*COLOR DEL CIRCULO*/
        ball.setFill(Color.RED);
        /*METER BOLA EN EL LAYOUT*/
        root.getChildren().add(ball);
    }
}
