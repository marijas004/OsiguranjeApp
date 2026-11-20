package dao;

import model.SaobracajnaNezgoda;
import java.sql.SQLException;
import java.util.List;

public interface SaobracajnaNezgodaDao {
    List<SaobracajnaNezgoda> findAll() throws SQLException;
}