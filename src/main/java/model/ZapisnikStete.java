package model;

import java.math.BigDecimal;
import java.sql.Date;

public class ZapisnikStete {
    private int zapisnikId;
    private BigDecimal procjenaStete;
    private Date datumUvida;
    private String opis;
    private int procjeniteljSteteId;
    private Integer likvidatorSteteId;
    private int polisaId;
    private String statusZapisnika;
    private int ucesnikId;

    public ZapisnikStete(int zapisnikId, BigDecimal procjenaStete, Date datumUvida, String opis,
                         int procjeniteljSteteId, Integer likvidatorSteteId, int polisaId,
                         String statusZapisnika, int ucesnikId) {
        this.zapisnikId = zapisnikId;
        this.procjenaStete = procjenaStete;
        this.datumUvida = datumUvida;
        this.opis = opis;
        this.procjeniteljSteteId = procjeniteljSteteId;
        this.likvidatorSteteId = likvidatorSteteId;
        this.polisaId = polisaId;
        this.statusZapisnika = statusZapisnika;
        this.ucesnikId = ucesnikId;
    }

    public int getZapisnikId() { return zapisnikId; }
    public BigDecimal getProcjenaStete() { return procjenaStete; }
    public Date getDatumUvida() { return datumUvida; }
    public String getOpis() { return opis; }
    public int getProcjeniteljSteteId() { return procjeniteljSteteId; }
    public Integer getLikvidatorSteteId() { return likvidatorSteteId; }
    public int getPolisaId() { return polisaId; }
    public String getStatusZapisnika() { return statusZapisnika; }
    public int getUcesnikId() { return ucesnikId; }
}