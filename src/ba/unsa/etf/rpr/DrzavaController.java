package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController {
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;
    public RadioButton radioIsti,radioDrugi;
    public  ChoiceBox<Grad> choiceGradNajveci;
    private Drzava drzava;
    private ObservableList<Grad> listGradovi;

    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        this.drzava = drzava;
        listGradovi = FXCollections.observableArrayList(gradovi);
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(listGradovi);
        choiceGradNajveci.setItems(listGradovi);
        if (drzava != null) {
            fieldNaziv.setText(drzava.getNaziv());
            choiceGrad.getSelectionModel().select(drzava.getGlavniGrad());
            Grad glavni = drzava.getGlavniGrad();
            Grad najveci = drzava.getNajveciGrad();
            for(Grad grad : listGradovi){
                if(grad.getId() == glavni.getId()){
                    choiceGrad.getSelectionModel().select(grad);
                }
                if(grad.getId() == najveci.getId()){
                    choiceGradNajveci.getSelectionModel().select(grad);
                }
            }         if (glavni.getId() == najveci.getId())
                radioIsti.setSelected(true);
            else
                radioDrugi.setSelected(true);
        } else {
            choiceGrad.getSelectionModel().selectFirst();
            choiceGradNajveci.getSelectionModel().selectFirst();
            choiceGradNajveci.setDisable(true);
            radioIsti.setSelected(true);
        }

    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        if (drzava == null) drzava = new Drzava();
        if(radioIsti.isSelected()){
            drzava.setNajveciGrad(choiceGrad.getSelectionModel().getSelectedItem());
        }else {
            drzava.setNajveciGrad(choiceGradNajveci.getSelectionModel().getSelectedItem());
        }
        drzava.setNaziv(fieldNaziv.getText());
        drzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());
       // drzava.setNajveciGrad(choiceGradNajveci.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void clickCancel(ActionEvent actionEvent) {
        drzava = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    public void clickButton(ActionEvent actionEvent){
        if(radioIsti.isSelected()){
            choiceGradNajveci.setDisable(true);
        }else{
            choiceGradNajveci.setDisable(false);
        }
    }
}
