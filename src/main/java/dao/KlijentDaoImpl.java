package dao;

import config.DatabaseConnection;
import model.Klijent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KlijentDaoImpl implements KlijentDao {

    private static final String SELECT_ALL_SQL =
            "SELECT k.klijent_id, k.telefon, k.email, k.adresa, k.ziro_racun, " +
                    "COALESCE(CONCAT(f.ime, ' ', f.prezime), p.naziv_firme) AS naziv " +
                    "FROM klijent k " +
                    "LEFT JOIN fizicko_lice f ON k.klijent_id = f.klijent_id " +
                    "LEFT JOIN pravno_lice p ON k.klijent_id = p.klijent_id";

    @Override
    public List<Klijent> findAll() throws SQLException {
        List<Klijent> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Klijent(
                        rs.getInt("klijent_id"),
                        rs.getString("telefon"),
                        rs.getString("email"),
                        rs.getString("adresa"),
                        rs.getString("ziro_racun"),
                        rs.getString("naziv")
                ));
            }
        }
        return lista;
    }

    @Override
    public Klijent findById(int klijentId) throws SQLException { return null; }
}