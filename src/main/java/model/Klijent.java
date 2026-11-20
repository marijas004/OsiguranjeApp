package model;

public class Klijent {
    private int klijentId;
    private String telefon;
    private String email;
    private String adresa;
    private String ziroRacun;
    private String naziv;

    public Klijent(int klijentId, String telefon, String email, String adresa, String ziroRacun, String naziv) {
        this.klijentId = klijentId;
        this.telefon = telefon;
        this.email = email;
        this.adresa = adresa;
        this.ziroRacun = ziroRacun;
        this.naziv = naziv;
    }

    public int getKlijentId() { return klijentId; }
    public String getTelefon() { return telefon; }
    public String getEmail() { return email; }
    public String getAdresa() { return adresa; }
    public String getZiroRacun() { return ziroRacun; }
    public String getNaziv() { return naziv; }

    @Override
    public String toString() {
        return klijentId + " - " + (naziv != null ? naziv : adresa);
    }
}