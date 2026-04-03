package view;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Cruz3DView {

    private Group grupoCruz;

    public Cruz3DView() {
        grupoCruz = new Group();

        // Material rojo 
        PhongMaterial materialRojo = new PhongMaterial();
        materialRojo.setDiffuseColor(Color.rgb(200, 40, 40));
        materialRojo.setSpecularColor(Color.rgb(100, 20, 20));
        materialRojo.setSpecularPower(20.0);

// Material blanco 
        PhongMaterial materialBlanco = new PhongMaterial();
        materialBlanco.setDiffuseColor(Color.rgb(240, 240, 245));
        materialBlanco.setSpecularColor(Color.rgb(150, 150, 160));
        materialBlanco.setSpecularPower(30.0);

        Cylinder base = new Cylinder(45, 6);
        base.setMaterial(materialBlanco);
        base.setTranslateY(38);
        base.setRotationAxis(Rotate.X_AXIS);
        base.setRotate(0);

        Box brazoVertical = new Box(12, 60, 8);
        brazoVertical.setMaterial(materialRojo);
        brazoVertical.setTranslateY(0);

        Box brazoHorizontal = new Box(60, 12, 8);
        brazoHorizontal.setMaterial(materialRojo);
        brazoHorizontal.setTranslateY(0);

        Group grupoRotante = new Group();
        grupoRotante.getChildren().addAll(base, brazoVertical, brazoHorizontal);
        grupoCruz.getChildren().add(grupoRotante);

        RotateTransition rotacion = new RotateTransition(Duration.seconds(16), grupoRotante);
        rotacion.setAxis(Rotate.Y_AXIS);
        rotacion.setByAngle(360);
        rotacion.setCycleCount(RotateTransition.INDEFINITE);
        rotacion.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotacion.play();
    }

    public Group getGrupoCruz() {
        return grupoCruz;
    }
}
