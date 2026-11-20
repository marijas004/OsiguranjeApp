package dao;

import model.ZapisnikStete;
import java.sql.SQLException;

public interface ZapisnikSteteDao {
    ZapisnikStete findByUcesnikId(int ucesnikId) throws SQLException;
    void kreirajZapisnik(int ucesnikId) throws SQLException;
    void unesiProcjenuStete(int zapisnikId, int procjeniteljId, String opis, double procjenaStete) throws SQLException;
    void likvidirajStetu(int zapisnikId, int likvidatorId, boolean jeKriv) throws SQLException;
}