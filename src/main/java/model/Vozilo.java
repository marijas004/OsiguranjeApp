package model;

public class Vozilo {
    private int voziloId;
    private String brojSasije;
    private String registarskiBroj;
    private String marka;
    private String model;
    private int godProizvodnje;
    private int snagaMotora;
    private int zapreminaMotora;
    private String vrstaGoriva;
    private int klijentId;
    private int kategorijaId;

    public Vozilo(int voziloId, String brojSasije, String registarskiBroj, String marka,
                  String model, int godProizvodnje, int snagaMotora, int zapreminaMotora,
                  String vrstaGoriva, int klijentId, int kategorijaId) {
        this.voziloId = voziloId;
        this.brojSasije = brojSasije;
        this.registarskiBroj = registarskiBroj;
        this.marka = marka;
        this.model = model;
        this.godProizvodnje = godProizvodnje;
        this.snagaMotora = snagaMotora;
        this.zapreminaMotora = zapreminaMotora;
        this.vrstaGoriva = vrstaGoriva;
        this.klijentId = klijentId;
        this.kategorijaId = kategorijaId;
    }

    public int getVoziloId() { return voziloId; }
    public String getBrojSasije() { return brojSasije; }
    public String getRegistarskiBroj() { return registarskiBroj; }
    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public int getGodProizvodnje() { return godProizvodnje; }
    public int getSnagaMotora() { return snagaMotora; }
    public int getZapreminaMotora() { return zapreminaMotora; }
    public String getVrstaGoriva() { return vrstaGoriva; }
    public int getKlijentId() { return klijentId; }
    public int getKategorijaId() { return kategorijaId; }

    @Override
    public String toString() {
        return voziloId + " - " + marka + " " + model + " (" + registarskiBroj + ")";
    }
}