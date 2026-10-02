package br.com.sistema.main;

import br.com.sistema.view.FrmLogin;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            // Configura o Look and Feel nativo para compatibilidade e acabamento moderno
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new FrmLogin().setVisible(true);
        });
    }
}
