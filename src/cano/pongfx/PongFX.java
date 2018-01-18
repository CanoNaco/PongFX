//paquete donde se encuentra el archivo
package cano.pongfx;

//los nombres de las clases siempre empiezan por mayuscula
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
//contenedor para la bola
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//clase para la bola
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    
    //para centar la posiciÃ³n del stick en Y
    int stickPosY = (400 - 50) / 2;
    //tamaÃ±o de la ventana con constantes
    final int SCENE_TAM_X = 600;
    final int SCENE_TAM_Y = 400;
    //tamaño del stick
    final int STICK_WIDTH = 7;
    final int STICK_HEIGHT = 50;
    //velocidad del stick
    int stickCurrentSpeed = 0;
    
    @Override
    public void start(Stage primaryStage) {
        //lienzo de la bola
        Pane root = new Pane();
        //el tamaño de la ventana
        Scene ventana = new Scene(root, 600, 400);
        //cambio de color de la ventana
        ventana.setFill(Color.BLACK);
        //Nombre que aparecera en la ventana
        primaryStage.setTitle("PongFX");
        primaryStage.setScene(ventana);
        primaryStage.show();
        //crear bola
        Circle ball = new Circle(ballCenterX, ballCenterY, 7);
        //color bola
        ball.setFill(Color.RED);
        //meter bola en el layout
        root.getChildren().add(ball);
        
        //crear el stick
        Rectangle rectStick = new Rectangle(SCENE_TAM_X*0.9, stickPosY, STICK_WIDTH, STICK_HEIGHT);
        rectStick.setFill(Color.BLUEVIOLET);
        root.getChildren().add(rectStick);

        //clase que permite el movimiento
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
                //movimiento de la pala
                stickPosY += stickCurrentSpeed;
                rectStick.setY(stickPosY);
                //limites de la pala
                if(stickPosY < 0){
                    stickPosY = 0;
                }
                if(stickPosY+STICK_HEIGHT >= SCENE_TAM_Y){
                    stickPosY = SCENE_TAM_Y-STICK_HEIGHT;
                }
            };
        };
        //inicia la animacion de la bola
        animationBall.start();
        
        //codigo pulsar boton
        ventana.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case UP:
                    //pulsada tecla arriba
                    stickCurrentSpeed = -6;
                    break;
                case DOWN:
                    //pulsada tecla abajo
                    stickCurrentSpeed = 6;
                    break;
            }
        });
        //codigo soltar boton
        ventana.setOnKeyReleased((KeyEvent event) -> {
            stickCurrentSpeed = 0;
        });
    };
};
