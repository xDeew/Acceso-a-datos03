import com.andrelut.gimnasio.gui.Controlador;
import com.andrelut.gimnasio.gui.Modelo;
import com.andrelut.gimnasio.gui.Vista;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            Vista vista = new Vista();
            Modelo modelo = new Modelo(vista);
            Controlador controlador = new Controlador(modelo, vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
