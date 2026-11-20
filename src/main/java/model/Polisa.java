package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Polisa {

    private int polisaId;
    private Date datumIzdavanja;
    private Date datumPocetka;
    private Date datumIsteka;
    private BigDecimal ukupanIznos;
    private int bonusMalus;
    private String registarskiBroj;
    private String nazivVrsteOsiguranja;
    private String korisnickoImeZaposlenog;

    public Polisa() {
    }

    public Polisa(int polisaId, Date datumIzdavanja, Date datumPocetka, Date datumIsteka,
                  BigDecimal ukupanIznos, int bonusMalus, String registarskiBroj,
                  String nazivVrsteOsiguranja, String korisnickoImeZaposlenog) {
        this.polisaId = polisaId;
        this.datumIzdavanja = datumIzdavanja;
        this.datumPocetka = datumPocetka;
        this.datumIsteka = datumIsteka;
        this.ukupanIznos = ukupanIznos;
        this.bonusMalus = bonusMalus;
        this.registarskiBroj = registarskiBroj;
        this.nazivVrsteOsiguranja = nazivVrsteOsiguranja;
        this.korisnickoImeZaposlenog = korisnickoImeZaposlenog;
    }

    public int getPolisaId() { return polisaId; }
    public void setPolisaId(int polisaId) { this.polisaId = polisaId; }

    public Date getDatumIzdavanja() { return datumIzdavanja; }
    public void setDatumIzdavanja(Date datumIzdavanja) { this.datumIzdavanja = datumIzdavanja; }

    public Date getDatumPocetka() { return datumPocetka; }
    public void setDatumPocetka(Date datumPocetka) { this.datumPocetka = datumPocetka; }

    public Date getDatumIsteka() { return datumIsteka; }
    public void setDatumIsteka(Date datumIsteka) { this.datumIsteka = datumIsteka; }

    public BigDecimal getUkupanIznos() { return ukupanIznos; }
    public void setUkupanIznos(BigDecimal ukupanIznos) { this.ukupanIznos = ukupanIznos; }

    public int getBonusMalus() { return bonusMalus; }
    public void setBonusMalus(int bonusMalus) { this.bonusMalus = bonusMalus; }

    public String getRegistarskiBroj() { return registarskiBroj; }
    public void setRegistarskiBroj(String registarskiBroj) { this.registarskiBroj = registarskiBroj; }

    public String getNazivVrsteOsiguranja() { return nazivVrsteOsiguranja; }
    public void setNazivVrsteOsiguranja(String nazivVrsteOsiguranja) { this.nazivVrsteOsiguranja = nazivVrsteOsiguranja; }

    public String getKorisnickoImeZaposlenog() { return korisnickoImeZaposlenog; }
    public void setKorisnickoImeZaposlenog(String korisnickoImeZaposlenog) { this.korisnickoImeZaposlenog = korisnickoImeZaposlenog; }
}