package kr.kro.ezcommand.old.gui.b_main.blocks.weather;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import kr.kro.ezcommand.old.inner.EZBlocks.WEATHER;

import java.net.URL;
import java.util.ResourceBundle;

import static kr.kro.ezcommand.old.gui.b_main.BlockListGUIController.nowWeather;
import static kr.kro.ezcommand.old.inner.EZBlocks.WEATHER.WEATHERType.RAIN;
import static kr.kro.ezcommand.old.inner.EZBlocks.WEATHER.WEATHERType.THUNDER;

public class WEATHERController implements Initializable
{

    @FXML AnchorPane anchorPane;
    @FXML TextField secondsGUI;
    @FXML ChoiceBox weatherTypeGUI;

    private WEATHER thisWeather;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        thisWeather = nowWeather;
        secondsGUI.setText(String.valueOf(thisWeather.seconds));
        weatherTypeGUI.getItems().add("맑게");
        weatherTypeGUI.getItems().add("비가 오게");
        weatherTypeGUI.getItems().add("폭풍우가 몰아치게");
        switch (thisWeather.weatherType) {
            case CLEAR:
                weatherTypeGUI.getSelectionModel().select(0); //맑게
                break; 
            case RAIN:
                weatherTypeGUI.getSelectionModel().select(1); //비가 오게
                break;
            case THUNDER:
                weatherTypeGUI.getSelectionModel().select(2); //폭풍우가 몰아치게
                break; 
            default:
                throw new NullPointerException("Out of Enum \"WEATHERType\"");
        }
        weatherTypeGUI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                switch(weatherTypeGUI.getSelectionModel().getSelectedIndex())
                {
                    case 0: // CLEAR
                        thisWeather.weatherType = WEATHER.WEATHERType.CLEAR;
                        break;

                    case 1: // RAIN
                        thisWeather.weatherType = RAIN;
                        break;

                    case 2: // THUNDER
                        thisWeather.weatherType = THUNDER;
                        break;

                    default: // ERROR
                        throw new NullPointerException("weatherTypeGUI ChangeListener : Case has to be 0~2, but " + weatherTypeGUI.getSelectionModel().getSelectedIndex() + " selected.");
                }
                //infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());
            }
        });

        secondsGUI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                try
                {
                    thisWeather.seconds = Integer.parseInt(secondsGUI.getText());
                } catch(NumberFormatException e)
                {
                    if(secondsGUI.getText() == null)
                    {
                        thisWeather.seconds = 0;
                    }
                    secondsGUI.setText(String.valueOf(thisWeather.seconds));
                }
                /*Text widthGetter = new Text();
                widthGetter.setText(String.valueOf(seconds));
                widthGetter.setFont(secondsGUI.getFont());
                secondsGUI.setPrefWidth(widthGetter.getLayoutBounds().getWidth());*/
                //secondsGUI.setPrefColumnCount(String.valueOf(seconds).length());
                /*secondsGUI.setPrefWidth(50);

                secondsGUI.setLayoutX(infoGUI[0].getBoundsInParent().getMaxX());
                infoGUI[1].setLayoutX(secondsGUI.getBoundsInParent().getMaxX());
                weatherTypeGUI.setLayoutX(infoGUI[1].getBoundsInParent().getMaxX());
                infoGUI[2].setLayoutX(weatherTypeGUI.getBoundsInParent().getMaxX());

                secondsGUI.positionCaret(secondsGUI.getCaretPosition());*/
            }
        });
    }
}
