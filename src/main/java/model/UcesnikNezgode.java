package model;

public class UcesnikNezgode {
    private int ucesnikId;
    private int voziloId;
    private int nezgodaId;
    private String uloga;
    private boolean jeKriv;
    private String registarskiBroj; // Novo polje

    public UcesnikNezgode() {}

    public UcesnikNezgode(int ucesnikId, int voziloId, int nezgodaId, String uloga, boolean jeKriv, String registarskiBroj) {
        this.ucesnikId = ucesnikId;
        this.voziloId = voziloId;
        this.nezgodaId = nezgodaId;
        this.uloga = uloga;
        this.jeKriv = jeKriv;
        this.registarskiBroj = registarskiBroj;
    }

    public int getUcesnikId() { return ucesnikId; }
    public void setUcesnikId(int ucesnikId) { this.ucesnikId = ucesnikId; }

    public int getVoziloId() { return voziloId; }
    public void setVoziloId(int voziloId) { this.voziloId = voziloId; }

    public int getNezgodaId() { return nezgodaId; }
    public void setNezgodaId(int nezgodaId) { this.nezgodaId = nezgodaId; }

    public String getUloga() { return uloga; }
    public void setUloga(String uloga) { this.uloga = uloga; }

    public boolean isJeKriv() { return jeKriv; }
    public void setJeKriv(boolean jeKriv) { this.jeKriv = jeKriv; }

    public String getRegistarskiBroj() { return registarskiBroj; }
    public void setRegistarskiBroj(String registarskiBroj) { this.registarskiBroj = registarskiBroj; }
}