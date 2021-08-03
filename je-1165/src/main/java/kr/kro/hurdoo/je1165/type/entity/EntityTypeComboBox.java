package kr.kro.hurdoo.je1165.type.entity;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import kr.kro.ezcommand.engine.parser.EZElementContainer;
import org.controlsfx.control.SearchableComboBox;

import java.util.LinkedList;
import java.util.List;

public class EntityTypeComboBox implements EZBlockElement {

    SearchableComboBox<EntityNode> ui;
    public EntityTypeComboBox()
    {
        List<EntityNode> list = new LinkedList<>();
        for(Entity e : Entity.entities) list.add(e.getUi());
        ui = new SearchableComboBox<>(FXCollections.observableList(list));
    }

    @Override
    public Node getUI() {
        return ui;
    }

    @Override
    public String getId() {
        return "entityTypeComboBox";
    }

    @Override
    public String toCommand() {
        for(Entity e : Entity.entities)
        {
            if(e.getUi().equals(ui.getSelectionModel().getSelectedItem()))
                return e.toString();
        }
        return null;
    }

    @Override
    public EZElementContainer getParent() {
        return null;
    }

    @Override
    public EZBlockElement clone(EZBlock block) throws CloneNotSupportedException {
        return null;
    }
}
