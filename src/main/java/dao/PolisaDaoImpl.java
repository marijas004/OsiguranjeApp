package dao;

import config.DatabaseConnection;
import model.Polisa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolisaDaoImpl implements PolisaDao {

    private static final String SELECT_ALL_SQL =
            "SELECT p.polisa_id, p.datum_izdavanja, p.datum_pocetka, p.datum_isteka, " +
                    "p.ukupan_iznos, p.bonus_malus, " +
                    "v.registarski_broj, " +
                    "vo.naziv_vrste, " +
                    "z.korisnicko_ime " +
                    "FROM polisa p " +
                    "JOIN vozilo v ON p.vozilo_id = v.vozilo_id " +
                    "JOIN vrsta_osiguranja vo ON p.vrsta_osiguranja_id = vo.vrsta_osiguranja_id " +
                    "JOIN zaposleni z ON p.zaposleni_id = z.zaposleni_id";

    @Override
    public List<Polisa> findAll() throws SQLException {
        List<Polisa> listaPolisa = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Polisa polisa = new Polisa(
                        rs.getInt("polisa_id"),
                        rs.getDate("datum_izdavanja"),
                        rs.getDate("datum_pocetka"),
                        rs.getDate("datum_isteka"),
                        rs.getBigDecimal("ukupan_iznos"),
                        rs.getInt("bonus_malus"),
                        rs.getString("registarski_broj"),
                        rs.getString("naziv_vrste"),
                        rs.getString("korisnicko_ime")
                );
                listaPolisa.add(polisa);
            }
        }
        return listaPolisa;
    }

    @Override
    public void kreirajPolisu(int klijentId, int voziloId, int vrstaOsiguranjaId) throws SQLException {
        String sql = "{call sp_kreiraj_polisu(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, klijentId);
            cs.setInt(2, voziloId);
            cs.setInt(3, vrstaOsiguranjaId);

            cs.execute();
        }
    }
}