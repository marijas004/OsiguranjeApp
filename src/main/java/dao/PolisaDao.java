package dao;

import model.Polisa;
import java.sql.SQLException;
import java.util.List;

public interface PolisaDao {
    List<Polisa> findAll() throws SQLException;
    void kreirajPolisu(int klijentId, int voziloId, int vrstaOsiguranjaId) throws SQLException;
}