package JPRG_CA2_Project;

import JPRG_CA2_Project.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Macam Rafael and Min Thet Khine
 */
public class StudentUser {

    public static void main(String[] args) {
        // Initialize the sample data as per requirements
        StudentManagement.initialize();
        modulesManagement.initialize();

        // Show the role selection dialog in a loop to allow returning after logout
        while (true) {
            // Options for role selection
            Object[] options = {"Student", "Admin", "Quit"};

            // JOptionPane for role selection, used to decide between which menu to show
            int choice = JOptionPane.showOptionDialog(null,
                "Choose your role:",
                "Role Selection",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                // Run Student method
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
                    java.util.logging.Logger.getLogger(StudentEnquiryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(StudentEnquiryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(StudentEnquiryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(StudentEnquiryView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new StudentEnquiryView().setVisible(true);
                    }
                });
            } else if (choice == JOptionPane.NO_OPTION) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new StudentManagementView().setVisible(true);
                    }
                });
            } else {
                // Quit the program if the "Quit" button is selected or the dialog is closed
                System.exit(0);
            }
        }
    }
}
