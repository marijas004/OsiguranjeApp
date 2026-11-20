package dao;

import model.UcesnikNezgode;
import java.sql.SQLException;
import java.util.List;

public interface UcesnikNezgodeDao {
    List<UcesnikNezgode> findByNezgodaId(int nezgodaId) throws SQLException;
}