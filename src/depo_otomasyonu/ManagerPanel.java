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

/**
 *
 * @author talha
 */
public class ManagerPanel extends javax.swing.JFrame {

    private static int customerID;
    private static int productsID;
    private static String pName;
    private static String pPCS;
    private static String sehir;
    private static java.sql.Date date;   
    
   
    public ManagerPanel() {
        initComponents();          
        username_label.setText("Hoşgeldiniz "+Manager_Login.username); 
        onaybekleyentablo();
    }
    
    public void urunsec(){
         DefaultTableModel model = (DefaultTableModel)onaybekleyen_tablo.getModel();        
        //"customerID","productsID","PNAME","PPCS","SCITY","PDURATİON","PRODUCTSTATE"
// get selected row index
        int selectedRowIndex = onaybekleyen_tablo.getSelectedRow();        
// get selected row data 
        customerID = (int) model.getValueAt(selectedRowIndex, 0);
        productsID = (int) model.getValueAt(selectedRowIndex, 1);
        pName = model.getValueAt(selectedRowIndex, 2).toString();
        pPCS = model.getValueAt(selectedRowIndex, 3).toString();
        sehir = model.getValueAt(selectedRowIndex, 4).toString();
        date = (java.sql.Date) model.getValueAt(selectedRowIndex, 5);
    }         
   public void urunsil(){
        try {                 
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = null;
            con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1","SYSTEM","1124");
            Statement st = con.createStatement();            
           // urunsec();
            DefaultTableModel model = (DefaultTableModel) onaybekleyen_tablo.getModel();
            int selectedRowIndex = onaybekleyen_tablo.getSelectedRow();            
            productsID = (int) model.getValueAt(selectedRowIndex, 1);                      
            st.executeQuery("DELETE FROM STORAGE WHERE productsID='"+productsID+"'");                        
            model.setRowCount(0);
            onaylanantablo();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void onaylanantablo(){
     //Manager_Login log = new Manager_Login();    
     DefaultTableModel model = new DefaultTableModel(new String[]{"customerID","productsID","PNAME","PPCS","STYPE","SCITY","SPRİCE","PDURATİON","PRODUCTSTATE"}, 0);        
    try {            
            Class.forName("oracle.jdbc.OracleDriver");
            
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            /*
            Date date1 = new Date(date.getTime());
                    Date now = new Date();
                    long diffInMillies = Math.abs(date1.getTime() - now.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    int urunsuresi = (int)diff;
            */
            
            con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1","SYSTEM","1124");
                       
           PreparedStatement ps = con.prepareStatement("SELECT * FROM STORAGE WHERE mmID=?");           
            ps.setInt(1, Manager_Login.mID);
            rs = ps.executeQuery();
                                                                                             
            while (rs.next()) {                
                    int customerid = rs.getInt("customerID");
                    int productsid = rs.getInt("productsID");
                    String urunadi = rs.getString("pName");  
                    int urunadedi = rs.getInt("pPCS");                     
                    java.sql.Date date = rs.getDate("pDuration");
                    String urundurum = rs.getString("productstate");
                    String uruntipi = rs.getString("sType");
                    int urunfiyat = rs.getInt("sPRİCE");
                    Date date1 = new Date(date.getTime());
                    Date now = new Date();
                    long diffInMillies = Math.abs(date1.getTime() - now.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    int urunsuresi = (int)diff;
                    String urunsehri = rs.getString("sCITY");
                    model.addRow(new Object[]{customerid,productsid,urunadi,urunadedi,uruntipi,urunsehri,urunfiyat,urunsuresi,urundurum});
                };            
            con.close();
            onaybekleyen_tablo.setModel(model); 
           // onaybekleyen_tablo.setEnabled(false);
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(null,"Hata: " + ex.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Veritabanına bağlantı sağlanamadı! " + ex.toString());
        }
    }
    
     public void onaybekleyentablo(){
     //Manager_Login log = new Manager_Login();    
     DefaultTableModel model = new DefaultTableModel(new String[]{"customerID","productsID","PNAME","PPCS","SCITY","PDURATİON","PRODUCTSTATE"}, 0);          
    try {            
            Class.forName("oracle.jdbc.OracleDriver");
            
            Connection con = null;            
            ResultSet rs = null;
            
            con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1","SYSTEM","1124");                      
            PreparedStatement ps = con.prepareCall("SELECT * FROM CustomerProducts WHERE CTY=?");           
            ps.setString(1, Manager_Login.sehir);
            rs = ps.executeQuery();
                                                                                             
            while (rs.next()) {                
                    int customerid = rs.getInt("customerID");
                    int productsid = rs.getInt("productsID");
                    String urunadi = rs.getString("pName");  
                    String urunadedi = rs.getString("pPCS");                                        
                    java.sql.Date date = rs.getDate("pDuration");                                                            
                    String urunsehri = rs.getString("CTY");
                    model.addRow(new Object[]{customerid,productsid,urunadi,urunadedi,urunsehri,date,"Bekleniyor"});
                };            
            con.close();
            onaybekleyen_tablo.setModel(model); 
           // tablo.setEnabled(false);
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(null,"Hata: " + ex.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"Veritabanına bağlantı sağlanamadı! " + ex.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        username_label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        onaybekleyen_tablo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        fiyat_label = new javax.swing.JLabel();
        fiyat_textfield = new javax.swing.JTextField();
        onaydurum_label = new javax.swing.JLabel();
        onaydurum_box = new javax.swing.JComboBox<>();
        uruntip_label = new javax.swing.JLabel();
        uruntip_box = new javax.swing.JComboBox<>();
        islemyap_button = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(850, 650));
        setMinimumSize(new java.awt.Dimension(850, 650));
        setPreferredSize(new java.awt.Dimension(850, 650));
        getContentPane().setLayout(null);
        getContentPane().add(username_label);
        username_label.setBounds(20, 20, 110, 30);

        onaybekleyen_tablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        onaybekleyen_tablo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onaybekleyen_tabloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(onaybekleyen_tablo);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(262, 90, 570, 402);

        jButton1.setText("İşlem Yapılan Ürünleri Göster");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(599, 510, 200, 50);

        fiyat_label.setText("Ürünün Fiyatı:");
        getContentPane().add(fiyat_label);
        fiyat_label.setBounds(20, 190, 80, 30);

        fiyat_textfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiyat_textfieldActionPerformed(evt);
            }
        });
        getContentPane().add(fiyat_textfield);
        fiyat_textfield.setBounds(150, 190, 90, 30);

        onaydurum_label.setText("Onay Durumu:");
        getContentPane().add(onaydurum_label);
        onaydurum_label.setBounds(20, 240, 70, 30);

        onaydurum_box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Onaylandı", "Reddedildi" }));
        onaydurum_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onaydurum_boxActionPerformed(evt);
            }
        });
        getContentPane().add(onaydurum_box);
        onaydurum_box.setBounds(150, 240, 90, 30);

        uruntip_label.setText("Ürünün Tipi:");
        getContentPane().add(uruntip_label);
        uruntip_label.setBounds(20, 290, 70, 30);

        uruntip_box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Kuru Gıda", "Yaş Gıda", "Elektronik", " " }));
        getContentPane().add(uruntip_box);
        uruntip_box.setBounds(150, 290, 90, 30);

        islemyap_button.setText("Ürünü Seç");
        islemyap_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                islemyap_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(islemyap_button);
        islemyap_button.setBounds(310, 40, 100, 30);

        jLabel2.setText("customerID:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 50, 80, 30);

        jLabel3.setText("Ürün Adı:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 90, 80, 30);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(150, 90, 90, 30);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2);
        jTextField2.setBounds(150, 50, 90, 30);

        jButton2.setText("İşlem Yap");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(100, 340, 90, 30);

        jButton3.setText("Sil");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(100, 390, 90, 30);

        jLabel1.setText("Ürün Adedi:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 140, 60, 30);

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField3);
        jTextField3.setBounds(150, 140, 90, 30);

        jButton4.setText("ÇIKIŞ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(740, 580, 59, 23);

        jButton5.setText("İşlem Bekleyen Ürünleri Göster");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(350, 510, 220, 50);

        jLabel4.setText("Filtrele");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(580, 40, 50, 30);

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4);
        jTextField4.setBounds(650, 40, 170, 30);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/depo_otomasyonu/background.jpg"))); // NOI18N
        getContentPane().add(background);
        background.setBounds(0, 0, 850, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        onaylanantablo();
        islemyap_button.setEnabled(false);
        jTextField2.setText("");
        jTextField1.setText("");
        jTextField3.setText("");
        fiyat_textfield.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void onaydurum_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onaydurum_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_onaydurum_boxActionPerformed

    private void islemyap_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_islemyap_buttonActionPerformed
        urunsec();
        jTextField2.setText(String.valueOf(customerID));
        jTextField1.setText(pName);
        jTextField3.setText(pPCS);
    }//GEN-LAST:event_islemyap_buttonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {     
            String sql;
            String onaydurum = onaydurum_box.getSelectedItem().toString();
            String uruntipi = uruntip_box.getSelectedItem().toString();             
            int fiyat;         
            //IF KONTROLÜ ATILACAK!!!!
            Class.forName("oracle.jdbc.OracleDriver");
            
            Connection con = null;          
            ResultSet rs = null;
            
            if(onaydurum.equals("Reddedildi")){
                fiyat = 0;
            }
            else{
            fiyat = Integer.valueOf(fiyat_textfield.getText()); 
        }
                        
            con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1","SYSTEM","1124");                    
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO STORAGE (mmID,productsID,sCity,sType,SPRİCE,pName,PDURATİON,productstate,customerID,PPCS) VALUES (?,?,?,?,?,?,?,?,?,?)");
                    pstmt.setInt(1, Manager_Login.mID);
                    pstmt.setInt(2, productsID);
                    pstmt.setString(3, sehir);
                    pstmt.setString(4, uruntipi);
                    pstmt.setInt(5, fiyat);
                    pstmt.setString(6, pName);
                    pstmt.setDate(7, date);
                    pstmt.setString(8, onaydurum);
                    pstmt.setInt(9, customerID); 
                    pstmt.setString(10, pPCS);
                    pstmt.execute();
                    // Statement st = con.createStatement();
                    // st.executeQuery("DELETE FROM CustomerProducts WHERE productsID='"+productsID+"'");
                                                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel model = (DefaultTableModel) onaybekleyen_tablo.getModel();
        model.setRowCount(0);
        onaybekleyentablo();                
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      /*try{                                         
          urunsec();
          Class.forName("oracle.jdbc.OracleDriver");
          Connection con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1","SYSTEM","1124");
          String sql = "DELETE FROM CustomerProducts WHERE productsID='"+productsID+"'";      
              
             // PreparedStatement pst = con.PrepareStatement("DELETE FROM CustomerProducts WHERE productsID=?");      
          //Burası Yapılacak BURADA KALDIK.
      }   catch (ClassNotFoundException ex) {
            Logger.getLogger(ManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }  */
      
      urunsil();
      jTextField2.setText("");
      jTextField1.setText("");
      jTextField3.setText("");
      fiyat_textfield.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void onaybekleyen_tabloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onaybekleyen_tabloMouseClicked
       
    }//GEN-LAST:event_onaybekleyen_tabloMouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void fiyat_textfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiyat_textfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fiyat_textfieldActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.hide();
        Giris_Ekrani giris = new Giris_Ekrani();
        giris.show();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        onaybekleyentablo();
        islemyap_button.setEnabled(true);
        jTextField2.setText("");
        jTextField1.setText("");
        jTextField3.setText("");
        fiyat_textfield.setText("");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
       TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) onaybekleyen_tablo.getModel())); 
       sorter.setRowFilter(RowFilter.regexFilter(jTextField4.getText()));
       onaybekleyen_tablo.setRowSorter(sorter);
    }//GEN-LAST:event_jTextField4ActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel fiyat_label;
    private javax.swing.JTextField fiyat_textfield;
    private javax.swing.JButton islemyap_button;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable onaybekleyen_tablo;
    private javax.swing.JComboBox<String> onaydurum_box;
    private javax.swing.JLabel onaydurum_label;
    private javax.swing.JComboBox<String> uruntip_box;
    private javax.swing.JLabel uruntip_label;
    private javax.swing.JLabel username_label;
    // End of variables declaration//GEN-END:variables
}
