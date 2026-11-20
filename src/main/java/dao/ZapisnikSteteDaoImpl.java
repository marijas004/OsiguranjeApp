package dao;

import config.DatabaseConnection;
import model.ZapisnikStete;

import java.sql.*;

public class ZapisnikSteteDaoImpl implements ZapisnikSteteDao {

    private static final String SELECT_BY_UCESNIK_SQL =
            "SELECT zapisnik_id, procjena_stete, datum_uvida, opis, procjenitelj_stete_id, " +
                    "likvidator_stete_id, polisa_id, status_zapisnika, ucesnik_id " +
                    "FROM zapisnik_stete WHERE ucesnik_id = ?";

    @Override
    public ZapisnikStete findByUcesnikId(int ucesnikId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_UCESNIK_SQL)) {

            ps.setInt(1, ucesnikId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Integer likvidatorId = rs.getInt("likvidator_stete_id");
                    if (rs.wasNull()) {
                        likvidatorId = null;
                    }

                    return new ZapisnikStete(
                            rs.getInt("zapisnik_id"),
                            rs.getBigDecimal("procjena_stete"),
                            rs.getDate("datum_uvida"),
                            rs.getString("opis"),
                            rs.getInt("procjenitelj_stete_id"),
                            likvidatorId,
                            rs.getInt("polisa_id"),
                            rs.getString("status_zapisnika"),
                            rs.getInt("ucesnik_id")
                    );
                }
            }
        }
        return null; // Vraća null ako zapisnik ne postoji za unesenog učesnika
    }
    @Override
    public void kreirajZapisnik(int ucesnikId) throws SQLException {
        String sql = "{call kreiraj_zapisnik(?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, ucesnikId);
            cs.execute();
        }
    }
    @Override
    public void unesiProcjenuStete(int zapisnikId, int procjeniteljId, String opis, double procjenaStete) throws SQLException {
        String sql = "{call unos_stete_u_zapisnik(?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, zapisnikId);
            cs.setInt(2, procjeniteljId);
            cs.setString(3, opis);
            cs.setDouble(4, procjenaStete);
            cs.execute();
        }
    }
    @Override
    public void likvidirajStetu(int zapisnikId, int likvidatorId, boolean jeKriv) throws SQLException {
        String sql = "{call likvidiraj_stetu(?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, zapisnikId);
            cs.setInt(2, likvidatorId);
            cs.setInt(3, jeKriv ? 1 : 0); // Prevaramo boolean u 1 ili 0 za TINYINT

            cs.execute();
        }
    }
}