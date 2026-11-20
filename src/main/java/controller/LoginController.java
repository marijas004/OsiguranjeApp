package controller;

import dao.ZaposleniDao;
import model.Zaposleni;
import view.LoginFrame;
import view.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {

    private LoginFrame loginFrame;
    private ZaposleniDao zaposleniDao;

    public LoginController(LoginFrame loginFrame, ZaposleniDao zaposleniDao) {
        this.loginFrame = loginFrame;
        this.zaposleniDao = zaposleniDao;

        this.loginFrame.getLoginButton().addActionListener(e -> obradiLogin());
    }

    private void obradiLogin() {
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame, "Molimo unesite sve podatke!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Pozivamo metodu preko objekta 'zaposleniDao'
            Zaposleni zaposleni = zaposleniDao.autentificiraj(username, password);

            if (zaposleni != null) {
                loginFrame.dispose();
                MainFrame mainFrame = new MainFrame(zaposleni);
                new MainController(mainFrame, zaposleni); // Kreiramo kontroler za glavni prozor
                mainFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Pogrešno korisničko ime ili lozinka!", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginFrame, "Greška prilikom povezivanja sa bazom podataka!", "Greška na bazi", JOptionPane.ERROR_MESSAGE);
        }
    }
}