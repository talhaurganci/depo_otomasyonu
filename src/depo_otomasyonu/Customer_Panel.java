/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depo_otomasyonu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Customer_Panel extends javax.swing.JFrame {

    public static int urunadedi;
    public String pName;
    public String date;
    public String pPCS;
    public String sehir;
    public int productsID;

    public Customer_Panel() {
        initComponents();
        Customer_Login log = new Customer_Login();
        username_label.setText("Hoşgeldiniz " + log.username);
        tablo();
        uruntablo.enable();
    }

    public void tablo() {
        Customer_Login log = new Customer_Login();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Ürün Adı", "Ürün Adedi", "Bitiş Tarihi", "Kalan Süre", "Ürünün Durumu", "Ürünün Şehri","ÜRÜN ID"}, 0);
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = null;
            Statement st = null;
            ResultSet rs = null;

            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1", "SYSTEM", "1124");

            st = con.createStatement();

            rs = st.executeQuery("SELECT pName,pPCS,pDuration,ProductState,Cty,productsID FROM CustomerProducts WHERE customerID='" + log.customerid + "'");

            // alttaki 3 satırda raporlar formundak toplam tutarı ekrana yazdırdık
            while (rs.next()) {
                String urunadi = rs.getString("pName");
                urunadedi = rs.getInt("pPCS");
                int productsID = rs.getInt("productsID");
                java.sql.Date date = rs.getDate("pDuration");
                Date date1 = new Date(date.getTime());
                Date now = new Date();
                long diffInMillies = Math.abs(date1.getTime() - now.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                int urunsuresi = (int) diff;
                String urundurumu = rs.getString("ProductState");
                String urunsehri = rs.getString("Cty");
                model.addRow(new Object[]{urunadi, urunadedi, date, urunsuresi, urundurumu, urunsehri,productsID});
            };
            con.close();
            uruntablo.setModel(model);
            uruntablo.setEnabled(false);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Hata: " + ex.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanına bağlantı sağlanamadı! " + ex.toString());
        }
    }

    public void tablo2() {
        Customer_Login log = new Customer_Login();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Ürün Adı", "Ürün Tipi", "Şehir", "Ürünün Fiyatı", "Ürünün Süresi", "Ürünün Durumu"}, 0);
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = null;
            Statement st = null;
            ResultSet rs = null;

            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1", "SYSTEM", "1124");

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM urun_onay WHERE customerID='" + Customer_Login.customerid + "'");

            // alttaki 3 satırda raporlar formundak toplam tutarı ekrana yazdırdık
            while (rs.next()) {
                String urunadi = rs.getString("pName");
                String uruntipi = rs.getString("sType");
                String urunsehri = rs.getString("sCITY");
                int fiyat = rs.getInt("sPRİCE");
                java.sql.Date date = rs.getDate("pDuration");
                Date date1 = new Date(date.getTime());
                Date now = new Date();
                long diffInMillies = Math.abs(date1.getTime() - now.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                int urunsuresi = (int) diff;
                String urundurumu = rs.getString("ProductState");

                model.addRow(new Object[]{urunadi, uruntipi, urunsehri, fiyat, urunsuresi, urundurumu});
            };
            con.close();
            uruntablo.setModel(model);
            uruntablo.setEnabled(false);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Hata: " + ex.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanına bağlantı sağlanamadı! " + ex.toString());
        }
    }

    public void urunsec() {
        DefaultTableModel model = (DefaultTableModel) uruntablo.getModel();

        int selectedRowIndex = uruntablo.getSelectedRow();

        pName = model.getValueAt(selectedRowIndex, 0).toString();

        date = model.getValueAt(selectedRowIndex, 2).toString();

        pPCS = model.getValueAt(selectedRowIndex, 1).toString();

        //  Date date = (java.sql.Date) model.getValueAt(selectedRowIndex, 2);
        sehir = model.getValueAt(selectedRowIndex, 5).toString();
        
        productsID = Integer.valueOf(model.getValueAt(selectedRowIndex, 6).toString());

        urunadi_textfield.setText(pName);
        urunadedi_textfield.setText(pPCS);
        jDateChooser1.setDate((Date) uruntablo.getModel().getValueAt(selectedRowIndex, 2));
        sehirbox.getModel().setSelectedItem(sehir);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logout_button = new javax.swing.JButton();
        urunadi_textfield = new javax.swing.JTextField();
        urunadedi_textfield = new javax.swing.JTextField();
        urunadi_label = new javax.swing.JLabel();
        urunadedi_label = new javax.swing.JLabel();
        urunsuresi_label = new javax.swing.JLabel();
        Filtrele_label = new javax.swing.JLabel();
        urunekle_button = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        uruntablo = new javax.swing.JTable();
        username_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sehirbox = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Müşteri Paneli");
        setMaximumSize(new java.awt.Dimension(850, 650));
        setMinimumSize(new java.awt.Dimension(850, 650));
        setPreferredSize(new java.awt.Dimension(850, 650));
        getContentPane().setLayout(null);

        logout_button.setText("ÇIKIŞ YAP");
        logout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(logout_button);
        logout_button.setBounds(750, 600, 81, 23);

        urunadi_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urunadi_textfieldActionPerformed(evt);
            }
        });
        getContentPane().add(urunadi_textfield);
        urunadi_textfield.setBounds(140, 90, 110, 30);
        getContentPane().add(urunadedi_textfield);
        urunadedi_textfield.setBounds(140, 130, 110, 30);

        urunadi_label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        urunadi_label.setText("Ürün Adı:");
        getContentPane().add(urunadi_label);
        urunadi_label.setBounds(30, 90, 90, 30);

        urunadedi_label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        urunadedi_label.setText("Ürün Adedi:");
        getContentPane().add(urunadedi_label);
        urunadedi_label.setBounds(30, 130, 90, 30);

        urunsuresi_label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        urunsuresi_label.setText("Ürünün Süresi:");
        getContentPane().add(urunsuresi_label);
        urunsuresi_label.setBounds(30, 170, 100, 30);

        Filtrele_label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Filtrele_label.setText("Filtrele");
        getContentPane().add(Filtrele_label);
        Filtrele_label.setBounds(350, 40, 50, 30);

        urunekle_button.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        urunekle_button.setText("Ekle");
        urunekle_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urunekle_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(urunekle_button);
        urunekle_button.setBounds(30, 310, 100, 40);

        uruntablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        uruntablo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uruntabloMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(uruntablo);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(280, 100, 540, 402);
        getContentPane().add(username_label);
        username_label.setBounds(20, 20, 120, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Şehir:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 210, 90, 20);

        sehirbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "İstanbul", "Amasya", "Ankara", "Sivas" }));
        sehirbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sehirboxActionPerformed(evt);
            }
        });
        getContentPane().add(sehirbox);
        sehirbox.setBounds(140, 210, 110, 30);
        getContentPane().add(jDateChooser1);
        jDateChooser1.setBounds(140, 170, 110, 30);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Onaylananları Göster");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(50, 390, 170, 50);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton2.setText("Güncelle");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(150, 310, 100, 40);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Onay Bekleyenleri Göster");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(40, 460, 190, 50);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(420, 40, 190, 30);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/depo_otomasyonu/background.jpg"))); // NOI18N
        background.setText("jLabel1");
        getContentPane().add(background);
        background.setBounds(0, 0, 850, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void urunadi_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urunadi_textfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urunadi_textfieldActionPerformed

    private void urunekle_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urunekle_buttonActionPerformed
        String urunadi = urunadi_textfield.getText();
        String urunadedi = urunadedi_textfield.getText();
        Date date = new Date();
        java.sql.Date urunsuresi = new java.sql.Date(jDateChooser1.getDate().getTime());
        System.out.println(urunsuresi);
        String productstate = "Bekleniyor";
        String sehir = String.valueOf((sehirbox.getSelectedItem()));

        try {

            if (urunadi.equals("") || urunadedi.equals("") || urunsuresi.equals(null)) {

                JOptionPane.showMessageDialog(this, "Lütfen Boş Alanları Doldurunuz!");

            } else {
                Customer_Login log = new Customer_Login();
                Class.forName("oracle.jdbc.OracleDriver");

                Connection con = null;

                con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1", "SYSTEM", "1124");
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO CustomerProducts (pName,pPCS,pDuration,customerID,ProductState,Cty) VALUES (?,?,?,?,?,?)");
                pstmt.setString(1, urunadi);
                pstmt.setString(2, urunadedi);
                pstmt.setDate(3, urunsuresi);
                pstmt.setInt(4, log.customerid);
                pstmt.setString(5, productstate);
                pstmt.setString(6, sehir);
                pstmt.execute();
                DefaultTableModel model = (DefaultTableModel) uruntablo.getModel();
                model.setRowCount(0);
                tablo();

                JOptionPane.showMessageDialog(null, urunadi + "  adlı ürün eklenmiştir.");
            }

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Hata: " + ex.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanına bağlantı sağlanamadı! " + ex.toString());
        }
        tablo();
        uruntablo.enable();
        jButton2.setEnabled(true);
    }//GEN-LAST:event_urunekle_buttonActionPerformed

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        // TODO add your handling code here:
        this.hide();
        Giris_Ekrani giris = new Giris_Ekrani();
        giris.show();
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) uruntablo.getModel();
        model.setRowCount(0);
        tablo2();
        uruntablo.disable();
        jButton2.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sehirboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sehirboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sehirboxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            pName = urunadi_textfield.getText();
            pPCS = urunadedi_textfield.getText();
            java.sql.Date date = new java.sql.Date(jDateChooser1.getDate().getTime());
            sehir = sehirbox.getSelectedItem().toString();

            Class.forName("oracle.jdbc.OracleDriver");
            // pName,pPCS,pDuration,customerID,ProductState,Cty
            Connection con = null;

            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1", "SYSTEM", "1124");
            PreparedStatement pstmt = con.prepareStatement("UPDATE CustomerProducts SET pName=?,pPCS=?,pDuration=?,CTY=? WHERE productsID=?");
            pstmt.setString(1, pName);
            pstmt.setString(2, pPCS);
            pstmt.setDate(3, date);
            pstmt.setString(4, sehir);
            pstmt.setInt(5, productsID);
            pstmt.executeQuery();
            
            JOptionPane.showMessageDialog(null, pName + "  adlı ürün güncellenmiştir.");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Customer_Panel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Customer_Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
         tablo();
        uruntablo.enable();
        jButton2.setEnabled(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tablo();
        uruntablo.enable();
        jButton2.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void uruntabloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uruntabloMouseClicked
        urunsec();
    }//GEN-LAST:event_uruntabloMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
       TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) uruntablo.getModel())); 
       sorter.setRowFilter(RowFilter.regexFilter(jTextField1.getText()));
       uruntablo.setRowSorter(sorter);
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Customer_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customer_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customer_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customer_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customer_Panel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Filtrele_label;
    private javax.swing.JLabel background;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton logout_button;
    private javax.swing.JComboBox<String> sehirbox;
    private javax.swing.JLabel urunadedi_label;
    private javax.swing.JTextField urunadedi_textfield;
    private javax.swing.JLabel urunadi_label;
    private javax.swing.JTextField urunadi_textfield;
    private javax.swing.JButton urunekle_button;
    private javax.swing.JLabel urunsuresi_label;
    private javax.swing.JTable uruntablo;
    private javax.swing.JLabel username_label;
    // End of variables declaration//GEN-END:variables
}
