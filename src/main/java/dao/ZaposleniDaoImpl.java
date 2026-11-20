package dao;

import config.DatabaseConnection;
import model.Uloga;
import model.Zaposleni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZaposleniDaoImpl implements ZaposleniDao {

    // SQL provjerava u kojoj se tabeli nalazi ID i vraća naziv uloge
    private static final String AUTH_SQL =
            "SELECT z.zaposleni_id, z.ime, z.prezime, z.jmbg, z.korisnicko_ime, z.lozinka, " +
                    "CASE " +
                    "   WHEN a.zaposleni_id IS NOT NULL THEN 'AGENT' " +
                    "   WHEN p.zaposleni_id IS NOT NULL THEN 'PROCJENITELJ' " +
                    "   WHEN l.zaposleni_id IS NOT NULL THEN 'LIKVIDATOR' " +
                    "   ELSE 'NEPOZNATO' " +
                    "END AS uloga " +
                    "FROM zaposleni z " +
                    "LEFT JOIN agent a ON z.zaposleni_id = a.zaposleni_id " +
                    "LEFT JOIN procjenitelj_stete p ON z.zaposleni_id = p.zaposleni_id " +
                    "LEFT JOIN likvidator_stete l ON z.zaposleni_id = l.zaposleni_id " +
                    "WHERE z.korisnicko_ime = ? AND z.lozinka = ?";

    @Override
    public Zaposleni autentificiraj(String korisnickoIme, String lozinka) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(AUTH_SQL)) {

            ps.setString(1, korisnickoIme);
            ps.setString(2, lozinka);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToZaposleni(rs);
                }
            }
        }
        return null;
    }

    private Zaposleni mapRowToZaposleni(ResultSet rs) throws SQLException {
        String ulogaStr = rs.getString("uloga");
        Uloga uloga = Uloga.valueOf(ulogaStr); // Prevara string iz baze u Java Enum

        return new Zaposleni(
                rs.getInt("zaposleni_id"),
                rs.getString("ime"),
                rs.getString("prezime"),
                rs.getString("jmbg"),
                rs.getString("korisnicko_ime"),
                rs.getString("lozinka"),
                uloga
        );
    }
}