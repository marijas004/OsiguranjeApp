package view;

import model.Uloga;
import model.Zaposleni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    private JTable tablePolise;
    private JTable tableNezgode;
    private JTable tableUcesnici;
    private JTextArea txtZapisnikDetails;

    private JButton btnKreirajPolisu; // Dugme specifično za agenta
    private Zaposleni prijavljeniZaposleni;

    private JButton btnKreirajZapisnik;
    private JButton btnProcijeniStetu;
    private JButton btnLikvidirajStetu;
    public MainFrame(Zaposleni zaposleni) {
        this.prijavljeniZaposleni = zaposleni;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistem za osiguranje - Evidencija (" + prijavljeniZaposleni.getIme() + " - " + prijavljeniZaposleni.getUloga() + ")");
        setSize(1050, 680);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Evidencija polisa", kreirajPanelPolise());
        tabbedPane.addTab("Evidencija saobraćajnih nezgoda", kreirajPanelNezgode());

        add(tabbedPane);
    }

    private JPanel kreirajPanelPolise() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Gornji panel sa naslovom i dugmetom za dodavanje
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Pregled svih polisa osiguranja:"), BorderLayout.WEST);

        btnKreirajPolisu = new JButton("+ Kreiraj novu polisu");

        // DUGME SE PRIKAZUJE SAMO AKO JE KORISNIK AGENT
        if (prijavljeniZaposleni.getUloga() == Uloga.AGENT) {
            topPanel.add(btnKreirajPolisu, BorderLayout.EAST);
        }

        String[] kolone = {
                "ID Polise",
                "Datum izdavanja",
                "Datum početka",
                "Datum isteka",
                "Ukupan iznos",
                "Bonus/Malus",
                "Registracija",
                "Vrsta osiguranja",
                "Zaposleni"
        };
        DefaultTableModel model = new DefaultTableModel(kolone, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablePolise = new JTable(model);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablePolise), BorderLayout.CENTER);

        return panel;
    }

    private JPanel kreirajPanelNezgode() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Gornji panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Pregled saobraćajnih nezgoda i učesnika:"), BorderLayout.WEST);

        // Panel za dugmad sa desne strane
        JPanel rightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));

        // Dugme dostupno svima
        btnKreirajZapisnik = new JButton("+ Kreiraj zapisnik za učesnika");
        rightButtonsPanel.add(btnKreirajZapisnik);

        // Dugme dostupno SAMO PROCJENITELJU
        if (prijavljeniZaposleni.getUloga() == Uloga.PROCJENITELJ) {
            btnProcijeniStetu = new JButton(" Unesi procjenu štete");
            rightButtonsPanel.add(btnProcijeniStetu);
        }
        if (prijavljeniZaposleni.getUloga() == Uloga.LIKVIDATOR) {
            btnLikvidirajStetu = new JButton(" Likvidiraj štetu");
            rightButtonsPanel.add(btnLikvidirajStetu);
        }

        topPanel.add(rightButtonsPanel, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // 1. Tabela za prikaz nezgoda (Gornji dio)
        String[] koloneNezgode = {"ID", "Datum", "Vrijeme", "Lokacija", "Broj zapisnika", "Vrsta nezgode"};
        DefaultTableModel modelNezgode = new DefaultTableModel(koloneNezgode, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableNezgode = new JTable(modelNezgode);
        JScrollPane scrollNezgode = new JScrollPane(tableNezgode);
        scrollNezgode.setBorder(BorderFactory.createTitledBorder("Pregled saobraćajnih nezgoda"));

        // 2. Tabela za učesnike nezgode (Donji lijevi dio)
        String[] koloneUcesnici = {"Registracija", "Uloga", "Krivac"};
        DefaultTableModel modelUcesnici = new DefaultTableModel(koloneUcesnici, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableUcesnici = new JTable(modelUcesnici);
        JScrollPane scrollUcesnici = new JScrollPane(tableUcesnici);
        scrollUcesnici.setBorder(BorderFactory.createTitledBorder("Učesnici u nezgodi"));

        // 3. Zapisnik o šteti (Donji desni dio)
        txtZapisnikDetails = new JTextArea();
        txtZapisnikDetails.setEditable(false);
        txtZapisnikDetails.setLineWrap(true);
        txtZapisnikDetails.setWrapStyleWord(true);
        JScrollPane scrollZapisnik = new JScrollPane(txtZapisnikDetails);
        scrollZapisnik.setBorder(BorderFactory.createTitledBorder("Zapisnik o procjeni štete"));

        // Panel koji drži učesnike i zapisnik jedno pored drugog
        JPanel bottomDetailsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomDetailsPanel.add(scrollUcesnici);
        bottomDetailsPanel.add(scrollZapisnik);

        // Vertikalni split pane za gornju tabelu i donje detalje
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollNezgode, bottomDetailsPanel);
        splitPane.setResizeWeight(0.5);

        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    public JButton getBtnKreirajPolisu() { return btnKreirajPolisu; }
    public JTable getTablePolise() { return tablePolise; }
    public JTable getTableNezgode() { return tableNezgode; }
    public JTable getTableUcesnici() { return tableUcesnici; }
    public JTextArea getTxtZapisnikDetails() { return txtZapisnikDetails; }
    public JButton getBtnKreirajZapisnik() { return btnKreirajZapisnik; }
    public JButton getBtnProcijeniStetu() { return btnProcijeniStetu; }
    public JButton getBtnLikvidirajStetu() { return btnLikvidirajStetu; }
}