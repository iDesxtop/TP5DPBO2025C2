import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JSlider umurSlider;
    private JLabel umurLabel;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // isi listMahasiswa
//        populateList();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-Laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // atur isi slider
        umurSlider.setMinimum(0);
        umurSlider.setMaximum(99);
        umurSlider.setValue(0);
        umurSlider.setMajorTickSpacing(10);
        umurSlider.setMinorTickSpacing(1);
        umurSlider.setPaintTicks(true);
        umurSlider.setPaintLabels(true);

        // Update label saat slider berubah
        umurSlider.addChangeListener(e -> {
            umurLabel.setText("Umur: " + umurSlider.getValue());
        });

        // Set nilai awal label
        umurLabel.setText("Umur: " + umurSlider.getValue());

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex == -1){
                    insertData();
                }else{
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex >= 0){
                    deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saat tombol
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                // Ambil nilai umur
                int selectedUmur = Integer.parseInt(mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString());


                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                umurSlider.setValue(selectedUmur);



                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin","Umur"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while(resultSet.next()){
                Object[] row = new Object[5];

                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getInt("Umur");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


//        // isi tabel dengan listMahasiswa
//        for(int i = 0; i < listMahasiswa.size(); i++){
//            Object[] row = new Object[4];
//            row[0] = i + 1;
//            row[1] = listMahasiswa.get(i).getNim();
//            row[2] = listMahasiswa.get(i).getNama();
//            row[3] = listMahasiswa.get(i).getJenisKelamin();
//
//            temp.addRow(row);
//        }








        return temp; // return juga harus diganti
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int umur = umurSlider.getValue();

        // cari nim yang sama
        try {
            ResultSet resultSet = database.selectQuery("SELECT nim FROM mahasiswa");
            while(resultSet.next()){
                String nimSudahAda = resultSet.getString("nim");

                if(nim.equals(nimSudahAda)){
                    JOptionPane.showMessageDialog(null, "NIM '" + nim + "'Sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Hentikan proses insert jika ada input kosong
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Validasi input kosong
        if(nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || umur == 0) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Hentikan proses insert jika ada input kosong
        }


        // tambahkan data ke dalam list/database
        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + umur + "');";
        database.insertUpdateDeleteQuery(sql);

//        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin));

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int umur = umurSlider.getValue();

        // Validasi input kosong
        if(nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || umur == 0) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Hentikan proses insert jika ada input kosong
        }

        // tambahkan data ke dalam list/database
        String sql = "UPDATE mahasiswa SET nim = '" + nim + "', nama = '" + nama + "', jenis_kelamin = '" + jenisKelamin + "', umur = '" + umur + "' WHERE nim = '" + nim + "' ;";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");

    }

    public void deleteData() {
        int result = JOptionPane.showConfirmDialog( // dialog konfirmasi
                null,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus Data",
                JOptionPane.YES_NO_OPTION
        );
        if(result == JOptionPane.YES_NO_OPTION) { // jika ya
            String nim = nimField.getText();
            // hapus data dari list
            // listMahasiswa.remove(selectedIndex);

            // hapus data dari database
            String sql = "DELETE from mahasiswa WHERE nim = '" + nim + "';";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete Berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        umurSlider.setValue(0); // Reset nilai slider


        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}
