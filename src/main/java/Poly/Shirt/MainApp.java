package Poly.Shirt;

import Poly.Shirt.Ui.LoginFrame;

public class MainApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
