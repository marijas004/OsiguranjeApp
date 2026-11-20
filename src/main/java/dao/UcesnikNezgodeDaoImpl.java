package dao;

import config.DatabaseConnection;
import model.UcesnikNezgode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UcesnikNezgodeDaoImpl implements UcesnikNezgodeDao {

    private static final String SELECT_BY_NEZGODA_SQL =
            "SELECT ucesnik_id, vozilo_id, nezgoda_id, uloga, kriv " +
                    "FROM ucesnik_nezgode WHERE nezgoda_id = ?";

    @Override
    public List<UcesnikNezgode> findByNezgodaId(int nezgodaId) throws SQLException {
        List<UcesnikNezgode> lista = new ArrayList<>();
        String sql = "SELECT u.ucesnik_id, u.vozilo_id, u.nezgoda_id, u.uloga, u.kriv, v.registarski_broj " +
                "FROM ucesnik_nezgode u " +
                "JOIN vozilo v ON u.vozilo_id = v.vozilo_id " +
                "WHERE u.nezgoda_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nezgodaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UcesnikNezgode u = new UcesnikNezgode(
                            rs.getInt("ucesnik_id"),
                            rs.getInt("vozilo_id"),
                            rs.getInt("nezgoda_id"),
                            rs.getString("uloga"),
                            rs.getBoolean("kriv"),
                            rs.getString("registarski_broj")
                    );
                    lista.add(u);
                }
            }
        }
        return lista;
    }
}