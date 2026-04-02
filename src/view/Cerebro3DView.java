package view;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Cerebro3DView {
    
    private Group grupoCerebro;
    
    public Cerebro3DView() {
        grupoCerebro = new Group();
        
        // Color rosa/gris característico del cerebro
        PhongMaterial materialCerebro = new PhongMaterial();
        materialCerebro.setDiffuseColor(Color.rgb(200, 180, 180));
        materialCerebro.setSpecularColor(Color.rgb(100, 80, 80));
        materialCerebro.setSpecularPower(20.0);
        
        PhongMaterial materialDetalle = new PhongMaterial();
        materialDetalle.setDiffuseColor(Color.rgb(180, 150, 150));
        
        // --- HEMISFERIO IZQUIERDO ---
        Sphere hemisferioIzq = new Sphere(45);
        hemisferioIzq.setMaterial(materialCerebro);
        hemisferioIzq.setTranslateX(-30);
        hemisferioIzq.setTranslateY(0);
        hemisferioIzq.setTranslateZ(0);
        
        // --- HEMISFERIO DERECHO ---
        Sphere hemisferioDer = new Sphere(45);
        hemisferioDer.setMaterial(materialCerebro);
        hemisferioDer.setTranslateX(30);
        hemisferioDer.setTranslateY(0);
        hemisferioDer.setTranslateZ(0);
        
        // --- CEREBELO (parte posterior inferior) ---
        Sphere cerebelo = new Sphere(35);
        cerebelo.setMaterial(materialCerebro);
        cerebelo.setTranslateX(0);
        cerebelo.setTranslateY(-15);
        cerebelo.setTranslateZ(-40);
        
        // --- TRONCO ENCEFÁLICO ---
        Cylinder tronco = new Cylinder(12, 30);
        tronco.setMaterial(materialDetalle);
        tronco.setTranslateX(0);
        tronco.setTranslateY(-25);
        tronco.setTranslateZ(-55);
        
        // --- SURCOS Y PLIEGUES (decoración con esferas pequeñas) ---
        for (int i = 0; i < 20; i++) {
            Sphere pliegue = new Sphere(3 + Math.random() * 4);
            pliegue.setMaterial(materialDetalle);
            
            // Posiciones aleatorias alrededor de los hemisferios
            double anguloX = Math.random() * Math.PI * 2;
            double anguloY = Math.random() * Math.PI * 2;
            double radio = 42 + Math.random() * 8;
            
            double x = (Math.sin(anguloX) * Math.cos(anguloY)) * radio;
            double y = (Math.sin(anguloX) * Math.sin(anguloY)) * radio - 5;
            double z = (Math.cos(anguloX)) * radio;
            
            // Distribuir entre hemisferios
            if (x < 0) {
                pliegue.setTranslateX(x - 30);
            } else {
                pliegue.setTranslateX(x + 30);
            }
            pliegue.setTranslateY(y);
            pliegue.setTranslateZ(z);
            
            grupoCerebro.getChildren().add(pliegue);
        }
        
        // --- ANILLOS DE LUZ (efecto de resonancia) ---
        for (int i = 0; i < 3; i++) {
            Cylinder anillo = new Cylinder(55, 2);
            PhongMaterial materialAnillo = new PhongMaterial();
            materialAnillo.setDiffuseColor(Color.rgb(100, 150, 200, 0.3));
            anillo.setMaterial(materialAnillo);
            anillo.setTranslateX(0);
            anillo.setTranslateY(0);
            anillo.setTranslateZ(0);
            anillo.setRotationAxis(Rotate.X_AXIS);
            anillo.setRotate(90 + i * 30);
            grupoCerebro.getChildren().add(anillo);
        }
        
        // Agregar los componentes principales
        grupoCerebro.getChildren().addAll(hemisferioIzq, hemisferioDer, cerebelo, tronco);
        
        // --- ANIMACIÓN DE ROTACIÓN (eje Y) ---
        RotateTransition rotacionY = new RotateTransition(Duration.seconds(20), grupoCerebro);
        rotacionY.setAxis(Rotate.Y_AXIS);
        rotacionY.setByAngle(360);
        rotacionY.setCycleCount(RotateTransition.INDEFINITE);
        rotacionY.setAutoReverse(false);
        rotacionY.play();
        
        // --- ROTACIÓN SECUNDARIA EN EJE X (efecto más dinámico) ---
        RotateTransition rotacionX = new RotateTransition(Duration.seconds(15), grupoCerebro);
        rotacionX.setAxis(Rotate.X_AXIS);
        rotacionX.setByAngle(15);
        rotacionX.setCycleCount(RotateTransition.INDEFINITE);
        rotacionX.setAutoReverse(true);
        rotacionX.play();
    }
    
    public Group getGrupoCerebro() {
        return grupoCerebro;
    }
}