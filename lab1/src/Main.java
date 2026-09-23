import controller.SinController;
import model.SinModel;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SinModel model = new SinModel();
            MainFrame view = new MainFrame(model);
            SinController controller = new SinController(model, view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}