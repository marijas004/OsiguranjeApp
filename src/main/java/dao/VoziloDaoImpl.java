package dao;

import config.DatabaseConnection;
import model.Vozilo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoziloDaoImpl implements VoziloDao {

    private static final String SELECT_ALL_SQL =
            "SELECT vozilo_id, broj_sasije, registarski_broj, marka, model, " +
                    "god_proizvodnje, snaga_motora, zapremina_motora, vrsta_goriva, klijent_id, kategorija_id " +
                    "FROM vozilo";

    @Override
    public List<Vozilo> findAll() throws SQLException {
        List<Vozilo> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Vozilo(
                        rs.getInt("vozilo_id"),
                        rs.getString("broj_sasije"),
                        rs.getString("registarski_broj"),
                        rs.getString("marka"),
                        rs.getString("model"),
                        rs.getInt("god_proizvodnje"),
                        rs.getInt("snaga_motora"),
                        rs.getInt("zapremina_motora"),
                        rs.getString("vrsta_goriva"),
                        rs.getInt("klijent_id"),
                        rs.getInt("kategorija_id")
                ));
            }
        }
        return lista;
    }

    @Override
    public List<Vozilo> findByKlijentId(int klijentId) throws SQLException { return new ArrayList<>(); }

    @Override
    public Vozilo findById(int voziloId) throws SQLException { return null; }
}