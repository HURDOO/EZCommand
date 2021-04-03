package kr.kro.ezcommand.old.gui.b_main.specific;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import kr.kro.ezcommand.old.gui.StageManager;
import kr.kro.ezcommand.old.gui.b_main.BlockListGUIController;
import kr.kro.ezcommand.old.inner.Types.Attribute;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class SpecificAttributeModifierController implements Initializable
{
    @FXML ChoiceBox attribute;
    @FXML TextField name;
    @FXML TextField uuid;
    @FXML Button newUuid;
    @FXML ChoiceBox calculate;
    @FXML TextField amount;
    @FXML Button ok;
    @FXML Button cancel;

    String tempName;
    String tempUuid;
    double tempAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Attribute.setAttributeList(attribute.getItems());

        attribute.getSelectionModel().select(BlockListGUIController.nowAttributeModifierAdd.attribute.ordinal());
        name.setText(BlockListGUIController.nowAttributeModifierAdd.name);
        uuid.setText(BlockListGUIController.nowAttributeModifierAdd.uuid);
        Attribute.setModifierTypeList(calculate.getItems());
        calculate.getSelectionModel().select(BlockListGUIController.nowAttributeModifierAdd.modifierType.ordinal());
        amount.setText(String.valueOf(BlockListGUIController.nowAttributeModifierAdd.amount));

        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!isModifierName(name.getText()))
                    name.setText(tempName);
                else
                    tempName = name.getText();
            }
        });

        uuid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //if (uuid.getText().matches("[0-9a-f]{8}-[0-9a-f]{4}-4[0-9]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    tempUuid = uuid.getText();
                //}
                //else uuid.setText(tempUuid);
            }
        });

        newUuid.setOnAction(event -> {
            tempUuid = UUID.randomUUID().toString();
            uuid.setText(tempUuid);
        });

        amount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    tempAmount = Double.parseDouble(amount.getText());
                } catch (Exception e)
                {
                    amount.setText(String.valueOf(tempAmount));
                }
            }
        });

        ok.setOnAction(event -> {
            BlockListGUIController.nowAttributeModifierAdd.attribute =
                    Attribute.AttributeList.values()[attribute.getSelectionModel().getSelectedIndex()];
            BlockListGUIController.nowAttributeModifierAdd.modifierType =
                    Attribute.modifierType.values()[calculate.getSelectionModel().getSelectedIndex()];
            BlockListGUIController.nowAttributeModifierAdd.name = this.name.getText();
            BlockListGUIController.nowAttributeModifierAdd.uuid = this.uuid.getText();
            BlockListGUIController.nowAttributeModifierAdd.amount = Double.parseDouble(this.amount.getText());

            BlockListGUIController.nowAttributeModifierAdd.thisController.modifierChanged();
            StageManager.closeSpecificAttributeModifier();
        });
        cancel.setOnAction(event -> {
            StageManager.closeSpecificAttributeModifier();
        });
    }

    private boolean isModifierName(String name)
    {
        char[] s = name.toLowerCase().toCharArray();
        for(int i=0;i<s.length;i++)
        {
            if(!(s[i] == '_' || s[i] == '.' || s[i] == '-' || s[i] == '+' || ('0' <= s[i] && s[i] <= '9') || ('a' <= s[i] && s[i] <= 'z')))
            {
                return false;
            }
        }
        return true;
    }
}
