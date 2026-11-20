package dao;

import model.Zaposleni;
import java.sql.SQLException;

public interface ZaposleniDao {
    Zaposleni autentificiraj(String korisnickoIme, String lozinka) throws SQLException;
}