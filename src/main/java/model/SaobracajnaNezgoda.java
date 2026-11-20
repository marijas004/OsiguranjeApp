package model;

import java.sql.Date;
import java.sql.Time;

public class SaobracajnaNezgoda {
    private int nezgodaId;
    private Date datumNezgode;
    private Time vrijemeNezgode;
    private String lokacija;
    private String brojPolicijskogZapisnika;
    private String vrstaNezgode;

    public SaobracajnaNezgoda(int nezgodaId, Date datumNezgode, Time vrijemeNezgode,
                              String lokacija, String brojPolicijskogZapisnika, String vrstaNezgode) {
        this.nezgodaId = nezgodaId;
        this.datumNezgode = datumNezgode;
        this.vrijemeNezgode = vrijemeNezgode;
        this.lokacija = lokacija;
        this.brojPolicijskogZapisnika = brojPolicijskogZapisnika;
        this.vrstaNezgode = vrstaNezgode;
    }

    public int getNezgodaId() { return nezgodaId; }
    public Date getDatumNezgode() { return datumNezgode; }
    public Time getVrijemeNezgode() { return vrijemeNezgode; }
    public String getLokacija() { return lokacija; }
    public String getBrojPolicijskogZapisnika() { return brojPolicijskogZapisnika; }
    public String getVrstaNezgode() { return vrstaNezgode; }
}