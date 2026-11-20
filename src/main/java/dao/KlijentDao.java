package dao;

import model.Klijent;
import java.sql.SQLException;
import java.util.List;

public interface KlijentDao {
    List<Klijent> findAll() throws SQLException;
    Klijent findById(int klijentId) throws SQLException;
}