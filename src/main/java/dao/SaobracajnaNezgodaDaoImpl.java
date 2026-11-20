package dao;

import config.DatabaseConnection;
import model.SaobracajnaNezgoda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaobracajnaNezgodaDaoImpl implements SaobracajnaNezgodaDao {

    private static final String SELECT_ALL_SQL =
            "SELECT nezgoda_id, datum_nezgode, vrijeme_nezgode, lokacija, " +
                    "broj_policijskog_zapisnika, vrsta_nezgode FROM saobracajna_nezgoda";

    @Override
    public List<SaobracajnaNezgoda> findAll() throws SQLException {
        List<SaobracajnaNezgoda> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SaobracajnaNezgoda nezgoda = new SaobracajnaNezgoda(
                        rs.getInt("nezgoda_id"),
                        rs.getDate("datum_nezgode"),
                        rs.getTime("vrijeme_nezgode"),
                        rs.getString("lokacija"),
                        rs.getString("broj_policijskog_zapisnika"),
                        rs.getString("vrsta_nezgode")
                );
                lista.add(nezgoda);
            }
        }
        return lista;
    }
}