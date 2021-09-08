package kr.kro.hurdoo.je1165.type.entity;

import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import kr.kro.ezcommand.engine.parser.EZBlockElement;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;
import java.util.List;

public class EntityTypeSelector implements EZBlockElement {

    // @TODO: 노드 선택하면 투명됨

    SearchableComboBox<EntityNode> ui = new SearchableComboBox<>();
    //ComboBox<EntityNode> ui = new ComboBox<>();
    //AutoCompleteBox ui = new AutoCompleteBox();

    public EntityTypeSelector(String id,JsonObject arg)
    {
        this.id = id;
        this.object = arg;
        if(arg.has("description"))
        {
            String description = arg.get("description").getAsString();
            ui.setTooltip(new Tooltip(description));
        }

        List<EntityNode> list = new ArrayList<>();
        for(Entity e : Entity.entities) {
            list.add(e.getUi());
        }
        ui.setItems(FXCollections.observableArrayList(list));


        if(!ui.getItems().isEmpty()) ui.getSelectionModel().selectFirst();
    }

    @Override
    public Node getUI() {
        return ui;
    }

    private final String id;
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toCommand() {
        for(Entity e : Entity.entities)
        {
            if(e.equals(ui.getSelectionModel().getSelectedItem().entity))
                return e.toCommand();
        }
        return null;
    }

    private final JsonObject object;

    @Override
    public EntityTypeSelector clone() throws CloneNotSupportedException {
        return new EntityTypeSelector(id,object);
    }
}
