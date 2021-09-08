package kr.kro.hurdoo.je1165.type.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class EntityNode extends HBox {
    Entity entity;
    Text text;
    ImageView image;

    public EntityNode(Entity entity) {
        if(entity.getImage() != null) {
            image = new ImageView(new Image(entity.getImage().toURI().toString()));
            getChildren().add(image);
        }
        if(entity.isRealEntity()) text = new Text(entity.getName());
        else text = new Text("#" + entity.getName());
        getChildren().add(text);
        this.entity = entity;
    }

    public EntityNode clone() throws CloneNotSupportedException {
        return new EntityNode(entity);
    }
}
