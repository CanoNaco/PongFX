//paquete donde se encuentra el archivo
package cano.pongfx;

//los nombres de las clases siempre empiezan por mayuscula
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
//contenedor para la bola
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//clase para la bola
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Juanma El Cano
 */
public class PongFX extends Application {
    //variables de posicion inicial de la bola en X e Y
    int ballCenterX = 300;
    int ballCenterY = 200;
    //variable de velocidad de la bola en X e Y
    int ballCurrentSpeedX = 3;
    int ballCurrentSpeedY = 3;
    
    @Override
    public void start(Stage primaryStage) {
        //lienzo de la bola
        Pane root = new Pane();
        //el tamaño de la ventana
        Scene ventana = new Scene(root, 600, 400);
        //cambio de color de la ventana
        ventana.setFill(Color.BLACK);
        primaryStage.setTitle("PongFX");
        primaryStage.setScene(ventana);
        primaryStage.show();
        //crear bola
        Circle ball = new Circle(ballCenterX, ballCenterY, 7);
        //color bola
        ball.setFill(Color.RED);
        //meter bola en el layout
        root.getChildren().add(ball);
        AnimationTimer animationBall = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.setCenterX(ballCenterX);
                ballCenterX+= ballCurrentSpeedX;
                if(ballCenterX >= 600){
                    ballCurrentSpeedX = -3;
                }
                if(ballCenterX <= 0){
                    ballCurrentSpeedX = 3;
                }
                ball.setCenterY(ballCenterY);
                ballCenterY+= ballCurrentSpeedY;
                if(ballCenterY >= 400){
                    ballCurrentSpeedY = -3;
                }
                if(ballCenterY <= 0){
                    ballCurrentSpeedY = 3;
                }
            };
        };
        animationBall.start();
    };
};
