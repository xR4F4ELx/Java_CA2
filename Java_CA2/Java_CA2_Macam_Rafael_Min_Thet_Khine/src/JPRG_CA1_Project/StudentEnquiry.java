/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA1_Project;

import static JPRG_CA1_Project.GPACalculator.findModuleByName;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTextField;
import static JPRG_CA1_Project.ValidationMethods.*;

/**
 *
 * @author Macam Rafael
 */
public class StudentEnquiry implements Runnable {
    
    //Run method is needed to implement runnable in this class
    public void run() {
        
    }
    
    //Display error message when user input is blank
    public static void viewDisplayUserInputErrorIsBlank() {
        JOptionPane.showMessageDialog(null,
                "Please put a valid Character",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    //Display error message when 
     public static void viewDisplaySizeValidationError() {
        JOptionPane.showMessageDialog(null,
                "Please put a number between 1-5!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
     //Search Student By name using parameters such as all classes in school and name of student
    public static HashMap<String, Object> studentNameSearch(String name) {
        Map<String, List<HashMap<String, Object>>> schoolClasses = Student.getClassMap();
        for (List<HashMap<String, Object>> students : schoolClasses.values()) {
            for (HashMap<String, Object> student : students) {
                if (student.get("name").toString().equalsIgnoreCase(name)) {
                    return student;
                }
            }
        }
        return null; 
    }
    
    //Create a thread to run Option 1 concurrently
    public static class ShowAllStudentsTask implements Runnable {
        private final Map<String, List<HashMap<String, Object>>> schoolClasses;

        public ShowAllStudentsTask(Map<String, List<HashMap<String, Object>>> schoolClasses) {
            this.schoolClasses = schoolClasses;
        }

        @Override
        public void run() {
            showAllStudentsByClass(schoolClasses);
        }
        
        //Option 1 in Student Enquiry
        public static void showAllStudentsByClass(Map<String, List<HashMap<String, Object>>> schoolClasses) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new BorderLayout());
            JLabel contentLabel = new JLabel();
            panel.add(new JScrollPane(contentLabel), BorderLayout.CENTER);

            //Initialize the buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton backButton = new JButton("Back");
            JButton backToMenuButton = new JButton("Menu");
            JButton nextButton = new JButton("Next");
            buttonPanel.add(backButton);
            buttonPanel.add(nextButton);
            buttonPanel.add(backToMenuButton);

            panel.add(buttonPanel, BorderLayout.SOUTH);

            //Setting up the JDialog 
            JDialog dialog = new JDialog(frame, "All Student Report", true);
            dialog.getContentPane().add(panel);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(null);

            List<String> classKeys = new ArrayList<>(schoolClasses.keySet());
            final int[] currentClassIndex = {0};

            // Function to update content
            Runnable updateContent = () -> {
                if (currentClassIndex[0] >= 0 && currentClassIndex[0] < classKeys.size()) {
                    String currentClassKey = classKeys.get(currentClassIndex[0]);
                    List<HashMap<String, Object>> students = schoolClasses.get(currentClassKey);

                    StringBuilder content = new StringBuilder("<html>");
                    content.append("<b>Class: ").append(currentClassKey).append("</b><br><br>");
                    for (Map<String, Object> student : students) {
                        content.append("Name: ").append(student.get("name")).append("<br>");
                        content.append("Admin: ").append(student.get("admin")).append("<br>");
                        content.append("Class: ").append(student.get("class")).append("<br>");
                        content.append("Modules: ").append(student.get("modules")).append("<br><br>");
                    }
                    content.append("</html>");

                    contentLabel.setText(content.toString());
                }
            };

            // Initialize content
            updateContent.run();

            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentClassIndex[0] < classKeys.size() - 1) {
                        currentClassIndex[0]++;
                        updateContent.run();
                    } else {
                        nextButton.setEnabled(false);
                    }
                    backButton.setEnabled(true);
                }
            });

            backToMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close the dialog and return to main menu or exit
                    dialog.dispose(); // Close the dialog
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentClassIndex[0] > 0) {
                        currentClassIndex[0]--;
                        updateContent.run();
                    } else {
                        backButton.setEnabled(false);
                    }
                    nextButton.setEnabled(true);
                }
            });

            // Disable the back button initially if we're on the first page
            backButton.setEnabled(false);

            // Ensure the dialog is shown on the Event Dispatch Thread (EDT)
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dialog.setVisible(true);
                }
            });
        }
    }
    
    //Option 2 of Student Enquiry
    private static void searchStudentByClass() {
        JTextField textField = new JTextField();
        textField.setText("");
        int result = JOptionPane.showConfirmDialog(null, textField, 
            "Enter Class", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String searchClass = textField.getText();

            Double results = GPACalculator.calculateAverageGPAForClass(Student.getClassMap().get(searchClass));

            if (results == null) {
                JOptionPane.showMessageDialog(null, "No Student Found From Class! \nFormat: DIT/FT/2A/01", "Class Summary", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Number of student(s) in " + searchClass + ": " +
                    Student.getClassMap().get(searchClass).size() + "\nAverage GPA: " + 
                    results, "Class Summary", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    //Option 3 of Student Enquiry
    private static void searchStudentByName() {
        JTextField textField = new JTextField();
        String searchName = "";

        while (true) {
            textField.setText("");
            int result = JOptionPane.showConfirmDialog(null, textField,
                "Enter Student Name", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                searchName = textField.getText();

                if (!validateStudentName(searchName)) {
                    continue; 
                }

                if (studentNameSearch(searchName) == null) {
                    JOptionPane.showMessageDialog(null,
                        "Cannot find the student \"" + searchName + "\"",
                        "Student Not Found",
                        JOptionPane.ERROR_MESSAGE);
                    continue; // Prompt again
                } else {
                    HashMap<String, Object> student = studentNameSearch(searchName);
                    Map<String, String> modules = (Map<String, String>) student.get("modules");
                    StringBuilder modulesPresentation = new StringBuilder();
                    int counter = 0;

                    // To format the modules taken for presentation
                    for (Map.Entry<String, String> entry : modules.entrySet()) {
                        counter += 1;
                        String moduleName = entry.getKey();
                        String grade = entry.getValue();

                        Modules module = findModuleByName(moduleName);
                        modulesPresentation.append("\n").append(counter).append(". ");
                        modulesPresentation.append(module.getModCode()).append("/");
                        modulesPresentation.append(module.getModName()).append("/");
                        modulesPresentation.append(module.getModCreditUnit()).append(": ");
                        modulesPresentation.append(grade);
                    }

                    JOptionPane.showMessageDialog(null,
                        "Name: " + student.get("name") + "\nAdmin: " + student.get("admin")
                        + "\nClass: " + student.get("class") + "\nModules Taken: " + modulesPresentation
                        + "\n\nGPA: " + GPACalculator.calculateGPA(student) + "\n ----------",
                        "Student Info", JOptionPane.INFORMATION_MESSAGE);
                    break; // Exit loop after successful search
                }
            } else {
                break; // Exit loop if cancel is pressed
            }
        }
    }
    
    public static void runProgram() {
        while (true) {
            try {
                /////////////////////////////////////
                // Get User Input
                /////////////////////////////////////
                String input = JOptionPane.showInputDialog(null, "Enter your option: "
                        + "\n 1. Display all students"
                        + "\n 2. Search student by class"
                        + "\n 3. Search student by name"
                        + "\n 4. Logout"
                        + "\n 5. Quit", "Student Enquiry System", JOptionPane.QUESTION_MESSAGE);

                if (input == null) {
                    // User pressed cancel, exit the loop
                    break;
                }

                if (blankCharacterInputValidation(input)) {
                    viewDisplayUserInputErrorIsBlank();
                    continue;
                }

                try {
                    int option = Integer.parseInt(input);

                    if (option < 1 || option > 5) {
                        viewDisplaySizeValidationError();
                        continue;
                    }

                    switch (option) {
                        case 1:
                            // Show all students tabulated based on class
                            Thread showStudentsThread = new Thread(new ShowAllStudentsTask(Student.getClassMap()));
                            showStudentsThread.start();
                            break;
                        case 2:
                            searchStudentByClass();
                            break;
                        case 3:
                            searchStudentByName();
                            break;
                        case 4:
                            // Logout, return to main menu
                            return;
                        case 5:
                            // Quit
                            System.exit(0);
                            break;
                        default:
                            break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static {
        runProgram();
    }
}
