package model;

public class Zaposleni {
    private int zaposleniId;
    private String ime;
    private String prezime;
    private String jmbg;
    private String korisnickoIme;
    private String lozinka;
    private Uloga uloga;
    public Zaposleni() {}

    public Zaposleni(int zaposleniId, String ime, String prezime, String jmbg, String korisnickoIme, String lozinka, Uloga uloga) {
        this.zaposleniId = zaposleniId;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.uloga=uloga;
    }


    public int getZaposleniId() { return zaposleniId; }
    public void setZaposleniId(int zaposleniId) { this.zaposleniId = zaposleniId; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getJmbg() { return jmbg; }
    public void setJmbg(String jmbg) { this.jmbg = jmbg; }

    public String getKorisnickoIme() { return korisnickoIme; }
    public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }

    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

    public Uloga getUloga() { return uloga; }
    public void setUloga(Uloga uloga) { this.uloga = uloga; }
}