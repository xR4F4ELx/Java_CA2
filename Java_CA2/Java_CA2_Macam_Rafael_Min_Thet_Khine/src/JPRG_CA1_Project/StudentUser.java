package JPRG_CA1_Project;

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
                StudentEnquiry.runProgram();
            } else if (choice == JOptionPane.NO_OPTION) {
                // Run Admin method
                StudentManagement.runProgram();
            } else {
                // Quit the program if the "Quit" button is selected or the dialog is closed
                System.exit(0);
            }
        }
    }
}