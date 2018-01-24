//paquete donde se encuentra el archivo
package cano.pongfx;

//los nombres de las clases siempre empiezan por mayuscula
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
//contenedor para la bola
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//clase para la bola
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    //tamaño del texto de los marcadores
    final int TEXT_SIZE = 20;
    //puntuacion actual
    int score;
    //puntuacion maxima
    int highScore;
    //texto puntuacion
    Text textScore;
    //lienzo de la bola
    Pane root = new Pane();
    
      
    @Override
    public void start(Stage primaryStage) {
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
        //Llama al metodo de la red
        
            
        //LAYOUTS PARA MOSTRAR PUNTUACIONES
        //Layout Principal
        HBox paneScores = new HBox();
        paneScores.setTranslateY(20);
        paneScores.setMinWidth(SCENE_TAM_X);
        paneScores.setAlignment(Pos.CENTER);
        paneScores.setSpacing(100);
        root.getChildren().add(paneScores);
        
        //Layout para puntuacion actual
        HBox paneCurrentScore = new HBox();
        paneCurrentScore.setSpacing(20);
        paneScores.getChildren().add(paneCurrentScore);
        
        //Layout para la puntuación máxima
        HBox paneHighScore = new HBox();
        paneCurrentScore.setSpacing(20);
        paneScores.getChildren().add(paneHighScore);
        
        //Texto de la etiqueta para la puntuación
        Text textTitleScore = new Text("Score:");
        textTitleScore.setFont(Font.font(TEXT_SIZE));
        textTitleScore.setFill(Color.WHITE);
        
        //texto para la puntuacion
        textScore = new Text("0");
        textScore.setFont(Font.font(TEXT_SIZE));
        textScore.setFill(Color.WHITE);
        
        //Texto de etiqueta ara la puntiacion maxima
        Text textTitleHighScore = new Text("Max Score: ");
        textTitleHighScore.setFont(Font.font(TEXT_SIZE));
        textTitleHighScore.setFill(Color.WHITE);
        
        //Texto puntuacion maxima
        Text textHighScore = new Text("0");
        textHighScore.setFont(Font.font(TEXT_SIZE));
        textHighScore.setFill(Color.WHITE);
        
        //Añadir texto a los los layout reservados para ellos
        paneCurrentScore.getChildren().add(textTitleScore);
        paneCurrentScore.getChildren().add(textScore);
        paneHighScore.getChildren().add(textTitleHighScore);
        paneHighScore.getChildren().add(textHighScore);
        
        resetGame();
        drawNet(10, 4, 30);
    
        //clase que permite el movimiento
        AnimationTimer animationBall;
        animationBall = new AnimationTimer() {
            
            //variable para detectar la seccion de colision
            int collisionZone = getStickCollisionZone(ball, rectStick);
            
            @Override
            public void handle(long now) {
                
                ball.setCenterX(ballCenterX);
                ballCenterX+= ballCurrentSpeedX;
                if(ballCenterX >= SCENE_TAM_X){
                    if (score > highScore) {
                        highScore = score;
                        textHighScore.setText(String.valueOf(highScore));
                    }
                    //reiniciar partida
                    resetGame();
                }
                if(ballCenterX <= 0){
                    ballCurrentSpeedX = 3;
                }
                ball.setCenterY(ballCenterY);
                ballCenterY+= ballCurrentSpeedY;
                if(ballCenterY >= SCENE_TAM_Y){
                     if (ballCurrentSpeedY == 3) {
                        ballCurrentSpeedY = -3;
                    }
                    if (ballCurrentSpeedY == 6){
                        ballCurrentSpeedY = -6;
                    }
                }
                if(ballCenterY <= 0){
                     if (ballCurrentSpeedY == -3) {
                        ballCurrentSpeedY = 3;
                    }
                    if (ballCurrentSpeedY == -6){
                        ballCurrentSpeedY = 6;
                    }
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
                //colision de la bola con la pala
                Shape.intersect(ball, rectStick);
                Shape shapeColision = Shape.intersect(ball, rectStick);
                boolean colisionVacia = shapeColision.getBoundsInLocal().isEmpty();
                
                //Marca la puntuacion cuando la bola choca con la pala
                if(colisionVacia == false && ballCurrentSpeedX > 0){
                    
                    ballCurrentSpeedX = - 3;
                    score++;
                    textScore.setText(String.valueOf(score));
                }
                calculateBallSpeed(collisionZone);
            };
            
        };
        //inicia la animacion de la bola
        animationBall.start();
    };
    private void darwNet(Pane root, int portionHeight, int portionWidth, int portionSpacing){
        for (int i=0; i<SCENE_TAM_Y; i+=portionSpacing) {
            Line line = new Line(SCENE_TAM_X/2, i, SCENE_TAM_X/2, i+portionHeight);
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(4);
            root.getChildren().add(line);
        };
    };
    private void resetGame(){
        score = 0;
        textScore.setText(String.valueOf(score));
        ballCenterX = 10;
        ballCurrentSpeedY = 3;
        //posicion inicial de la bola aleatoria
        Random random = new Random();
        ballCenterY = random.nextInt(SCENE_TAM_Y);
        
    }
    private void drawNet(int portionHeight, int portionWidth, int portionSpacing){
        for (int i=0; i<SCENE_TAM_Y; i+=portionSpacing) {
            Line line = new Line(SCENE_TAM_X/2, i, SCENE_TAM_X/2, i+portionHeight);
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(4);
            root.getChildren().add(line);
        };
    };
    private int getStickCollisionZone(Circle ball, Rectangle stick){
        if(Shape.intersect(ball, stick).getBoundsInLocal().isEmpty()){
            return 0;
        }
        else {
            double offsetBallStick = ball.getCenterY()- stick.getY();
            if(offsetBallStick < stick.getHeight() * 0.1){
                return 1;
            }
            else if(offsetBallStick < stick.getHeight() / 2){
                return 2;
            }
            else if(offsetBallStick >= stick.getHeight() / 2 && offsetBallStick < stick.getHeight() * 0.9){
                return 3;
            }
            else {
                return 4;
            }
        }
    };
    private void calculateBallSpeed (int collisionZone){
        switch(collisionZone){
            case 0:
                //No hay colision
                break;
            case 1:
                //Hay colision en la esquina superior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedY = -6;
                break;
            case 2:
                // Hay colision lado superior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedY = -3;
                break;
            case 3:
                //Hay colision lado inferior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedY = 3;
                break;
            case 4:
                //Hay colisión esquina inferior
                ballCurrentSpeedX = -3;
                ballCurrentSpeedY = 6;
                break;
        }
    }
};
