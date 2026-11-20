package dao;

import model.Vozilo;
import java.sql.SQLException;
import java.util.List;

public interface VoziloDao {
    List<Vozilo> findAll() throws SQLException;
    List<Vozilo> findByKlijentId(int klijentId) throws SQLException;
    Vozilo findById(int voziloId) throws SQLException;
}