import controller.LoginController;
import dao.ZaposleniDao;
import dao.ZaposleniDaoImpl;
import view.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Kreiramo prozor za login
            LoginFrame loginFrame = new LoginFrame();

            // 2. Kreiramo DAO objekat za rad sa bazom
            ZaposleniDao zaposleniDao = new ZaposleniDaoImpl();

            // 3. Spajamo prozor i bazu preko kontrolera
            new LoginController(loginFrame, zaposleniDao);

            // 4. Prikazujemo prozor za login
            loginFrame.setVisible(true);
        });
    }
}