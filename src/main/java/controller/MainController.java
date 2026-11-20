package controller;

import dao.*;
import model.*;
import view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private MainFrame mainFrame;
    private Zaposleni prijavljeniZaposleni;
    private PolisaDao polisaDao;
    private SaobracajnaNezgodaDao saobracajnaNezgodaDao;
    private UcesnikNezgodeDao ucesnikNezgodeDao;
    private ZapisnikSteteDao zapisnikSteteDao;
    private KlijentDao klijentDao;
    private VoziloDao voziloDao;

    private List<UcesnikNezgode> trenutniUcesnici = new ArrayList<>();

    public MainController(MainFrame mainFrame, Zaposleni zaposleni) {
        this.mainFrame = mainFrame;
        this.prijavljeniZaposleni = zaposleni;
        this.polisaDao = new PolisaDaoImpl();
        this.saobracajnaNezgodaDao = new SaobracajnaNezgodaDaoImpl();
        this.ucesnikNezgodeDao = new UcesnikNezgodeDaoImpl();
        this.zapisnikSteteDao = new ZapisnikSteteDaoImpl();
        this.klijentDao = new KlijentDaoImpl();
        this.voziloDao = new VoziloDaoImpl();

        prikaziSvePolise();
        prikaziSveNezgode();
        initListeners();
    }

    private void initListeners() {
        // 1. Klik na dugme za kreiranje polise
        if (mainFrame.getBtnKreirajPolisu() != null) {
            mainFrame.getBtnKreirajPolisu().addActionListener(e -> otvoriFormuZaKreiranjePolise());
        }

        // 2. Klik na dugme za kreiranje zapisnika
        if (mainFrame.getBtnKreirajZapisnik() != null) {
            mainFrame.getBtnKreirajZapisnik().addActionListener(e -> kreirajZapisnikZaOdabranogUcesnika());
        }

        // 3. Selekcija reda u tabeli NEZGODE -> prikazuje učesnike te nezgode
        mainFrame.getTableNezgode().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mainFrame.getTableNezgode().getSelectedRow();
                if (selectedRow != -1) {
                    int nezgodaId = (int) mainFrame.getTableNezgode().getValueAt(selectedRow, 0);
                    prikaziUcesnikeZaSelektovanuNezgodu(nezgodaId);
                }
            }
        });

        // 4. Selekcija reda u tabeli UČESNICI -> prikazuje zapisnik o šteti
        mainFrame.getTableUcesnici().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                prikaziZapisnikZaSelektovanogUcesnika();
            }
        });

        if (mainFrame.getBtnProcijeniStetu() != null) {
            mainFrame.getBtnProcijeniStetu().addActionListener(e -> otvoriFormuZaProcjenuStete());
        }

        if (mainFrame.getBtnLikvidirajStetu() != null) {
            mainFrame.getBtnLikvidirajStetu().addActionListener(e -> otvoriFormuZaLikvidacijuStete());
        }
    }

    private void otvoriFormuZaKreiranjePolise() {
        try {
            List<Klijent> klijenti = klijentDao.findAll();
            List<Vozilo> vozila = voziloDao.findAll();

            if (klijenti.isEmpty() || vozila.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Nije moguće kreirati polisu jer u bazi nema registrovanih klijenata ili vozila.",
                        "Upozorenje", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JComboBox<Klijent> klijentBox = new JComboBox<>(klijenti.toArray(new Klijent[0]));
            JComboBox<Vozilo> voziloBox = new JComboBox<>(vozila.toArray(new Vozilo[0]));

            String[] vrste = {"1 - Autoodgovornost (AO)", "2 - Kasko", "3 - Kratkoročno"};
            JComboBox<String> vrstaBox = new JComboBox<>(vrste);

            Object[] message = {
                    "Odaberite Klijenta:", klijentBox,
                    "Odaberite Vozilo:", voziloBox,
                    "Vrsta osiguranja:", vrstaBox
            };

            int option = JOptionPane.showConfirmDialog(
                    mainFrame,
                    message,
                    "Kreiranje nove polise",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                Klijent selektovaniKlijent = (Klijent) klijentBox.getSelectedItem();
                Vozilo selektovanoVozilo = (Vozilo) voziloBox.getSelectedItem();

                int klijentId = selektovaniKlijent.getKlijentId();
                int voziloId = selektovanoVozilo.getVoziloId();
                int vrstaId = Integer.parseInt(((String) vrstaBox.getSelectedItem()).split(" - ")[0]);

                polisaDao.kreirajPolisu(klijentId, voziloId, vrstaId);

                JOptionPane.showMessageDialog(mainFrame, "Polisa je uspješno kreirana!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
                prikaziSvePolise();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Greška iz baze", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziSvePolise() {
        try {
            List<Polisa> polise = polisaDao.findAll();
            DefaultTableModel model = (DefaultTableModel) mainFrame.getTablePolise().getModel();
            model.setRowCount(0);

            for (Polisa p : polise) {
                model.addRow(new Object[]{
                        p.getPolisaId(),
                        p.getDatumIzdavanja(),
                        p.getDatumPocetka(),
                        p.getDatumIsteka(),
                        p.getUkupanIznos(),
                        p.getBonusMalus(),
                        p.getRegistarskiBroj(),
                        p.getNazivVrsteOsiguranja(),
                        p.getKorisnickoImeZaposlenog()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Greška pri učitavanju polisa: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziSveNezgode() {
        try {
            List<SaobracajnaNezgoda> nezgode = saobracajnaNezgodaDao.findAll();
            DefaultTableModel model = (DefaultTableModel) mainFrame.getTableNezgode().getModel();
            model.setRowCount(0);

            for (SaobracajnaNezgoda n : nezgode) {
                model.addRow(new Object[]{
                        n.getNezgodaId(),
                        n.getDatumNezgode(),
                        n.getVrijemeNezgode(),
                        n.getLokacija(),
                        n.getBrojPolicijskogZapisnika(),
                        n.getVrstaNezgode()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Greška pri učitavanju nezgoda: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziUcesnikeZaSelektovanuNezgodu(int nezgodaId) {
        try {
            trenutniUcesnici = ucesnikNezgodeDao.findByNezgodaId(nezgodaId);
            DefaultTableModel model = (DefaultTableModel) mainFrame.getTableUcesnici().getModel();
            model.setRowCount(0);

            for (UcesnikNezgode u : trenutniUcesnici) {
                model.addRow(new Object[]{
                        u.getRegistarskiBroj(),
                        u.getUloga(),
                        u.isJeKriv() ? "DA" : "NE"
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void prikaziZapisnikZaSelektovanogUcesnika() {
        int selectedRow = mainFrame.getTableUcesnici().getSelectedRow();

        if (selectedRow != -1 && selectedRow < trenutniUcesnici.size()) {
            int ucesnikId = trenutniUcesnici.get(selectedRow).getUcesnikId();

            try {
                ZapisnikStete zapisnik = zapisnikSteteDao.findByUcesnikId(ucesnikId);
                if (zapisnik != null) {
                    String tekst = "ID Zapisnika: " + zapisnik.getZapisnikId() + "\n" +
                            "Datum uvida: " + zapisnik.getDatumUvida() + "\n" +
                            "Procjena štete: " + zapisnik.getProcjenaStete() + " KM\n" +
                            "Status: " + zapisnik.getStatusZapisnika() + "\n\n" +
                            "Opis:\n" + zapisnik.getOpis();
                    mainFrame.getTxtZapisnikDetails().setText(tekst);
                } else {
                    mainFrame.getTxtZapisnikDetails().setText("Za odabranog učesnika još nije kreiran zapisnik.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            mainFrame.getTxtZapisnikDetails().setText("");
        }
    }

    private void kreirajZapisnikZaOdabranogUcesnika() {
        int selectedRow = mainFrame.getTableUcesnici().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Molimo vas da prvo odaberete učesnika u tabeli učesnika!",
                    "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ucesnikId = trenutniUcesnici.get(selectedRow).getUcesnikId();

        try {
            zapisnikSteteDao.kreirajZapisnik(ucesnikId);

            JOptionPane.showMessageDialog(mainFrame,
                    "Zapisnik sa statusom INICIJALIZOVAN je uspješno kreiran!",
                    "Uspjeh", JOptionPane.INFORMATION_MESSAGE);

            prikaziZapisnikZaSelektovanogUcesnika();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Nije moguće kreirati zapisnik: " + ex.getMessage(),
                    "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void otvoriFormuZaProcjenuStete() {
        int selectedRow = mainFrame.getTableUcesnici().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Prvo odaberite učesnika iz tabele!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ucesnikId = trenutniUcesnici.get(selectedRow).getUcesnikId();

        try {
            ZapisnikStete zapisnik = zapisnikSteteDao.findByUcesnikId(ucesnikId);

            if (zapisnik == null) {
                JOptionPane.showMessageDialog(mainFrame, "Za odabranog učesnika još nije kreiran zapisnik!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextArea txtOpis = new JTextArea(4, 20);
            JTextField txtProcjena = new JTextField();

            Object[] inputFields = {
                    "ID Zapisnika: " + zapisnik.getZapisnikId(),
                    "Opis oštećenja:", new JScrollPane(txtOpis),
                    "Procijenjeni iznos štete (KM):", txtProcjena
            };

            int option = JOptionPane.showConfirmDialog(
                    mainFrame,
                    inputFields,
                    "Unos procjene štete",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                String opis = txtOpis.getText().trim();
                double procjena = Double.parseDouble(txtProcjena.getText().trim());

                zapisnikSteteDao.unesiProcjenuStete(
                        zapisnik.getZapisnikId(),
                        prijavljeniZaposleni.getZaposleniId(),
                        opis,
                        procjena
                );

                JOptionPane.showMessageDialog(mainFrame, "Procjena štete je uspješno unesena!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
                prikaziZapisnikZaSelektovanogUcesnika();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Molimo unesite ispravan brojčani iznos procjene štete!", "Greška pri unosu", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Greška iz baze", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void otvoriFormuZaLikvidacijuStete() {
        int selectedRow = mainFrame.getTableUcesnici().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Prvo odaberite učesnika u tabeli učesnika!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ucesnikId = trenutniUcesnici.get(selectedRow).getUcesnikId();

        try {
            ZapisnikStete zapisnik = zapisnikSteteDao.findByUcesnikId(ucesnikId);

            if (zapisnik == null) {
                JOptionPane.showMessageDialog(mainFrame, "Za odabranog učesnika ne postoji zapisnik u bazi!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] opcijeKrivice = {
                    "0 - Oštećeni (Nije kriv)",
                    "1 - Krivac / Samostalna šteta"
            };
            JComboBox<String> boxKrivica = new JComboBox<>(opcijeKrivice);

            Object[] message = {
                    "ID Zapisnika: " + zapisnik.getZapisnikId(),
                    "Odaberite odgovornost za odabranog učesnika:", boxKrivica
            };

            int option = JOptionPane.showConfirmDialog(
                    mainFrame,
                    message,
                    "Likvidacija štete",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                boolean jeKriv = boxKrivica.getSelectedIndex() == 1;

                zapisnikSteteDao.likvidirajStetu(
                        zapisnik.getZapisnikId(),
                        prijavljeniZaposleni.getZaposleniId(),
                        jeKriv
                );

                JOptionPane.showMessageDialog(
                        mainFrame,
                        "Proces likvidacije je uspješno završen!",
                        "Uspjeh",
                        JOptionPane.INFORMATION_MESSAGE
                );

                prikaziZapisnikZaSelektovanogUcesnika();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Greška pri likvidaciji", JOptionPane.ERROR_MESSAGE);
        }
    }
}