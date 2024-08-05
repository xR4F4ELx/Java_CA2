/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA1_Project;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;
import static JPRG_CA1_Project.ValidationMethods.*;
/**
 *
 * @author Min Thet Khine
 */
public class StudentManagement {
    // ===== SET UP =====
    public static final String adminDialogTitle = "Student Admin System";
    
    public static String createInputDialog(String message, String title) {       
        String userInput = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
        return userInput;
    }
    
    public static void createMessageDialog(String message, String title) {       
        JOptionPane.showMessageDialog(null, message, adminDialogTitle, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // --- Converter ---
    public static String convertMarkToGrade(int modMark) {
        String grade = "";  
        
        if(modMark >= 80) 
            grade = "A";
        else if(modMark >= 70)
            grade = "B";
        else if(modMark >= 60)  
            grade = "C";
        else if(modMark >= 50)
            grade = "D";
        else
            grade = "F";
        
        return grade;
    }
    
    // === Processors ===
    public static void addStudentInsideClass(String name, String adminNumber, String studentClass, HashMap<String, String> modules) {
        Student student = new Student(name, adminNumber, studentClass, modules);
        Student.addStudentToClassMap(student);
    }
    
    public static void addModCodeToSMM(String adminNumber, String modCode) {
        ArrayList<String> modulesTakenArr = Student.getStudentModMap().get(adminNumber);
        modulesTakenArr.add(modCode);
    }
    
    public static double findAverage(double[] arrayList) {
        double avg = 0.0;
        double sum = 0.0;
        
        for(int i = 0; i < arrayList.length; i++) {
            sum += arrayList[i];
        }
        
        avg = sum / arrayList.length;
                
        return avg;
    }
    
    // ===== ADD NEW STUDENT =====
    public static void addNewStudent() {
        // Boot up
        String studentName = "";
        String studentAdminNumber = "";
        String studentClass = "";
        HashMap<String, String> studentModules = new HashMap<>();
        String noOfModsString = "";
        int noOfMods = 0;
        
        // --- get student name ---
        do{
            studentName = createInputDialog("Enter name: ", adminDialogTitle);
            if(studentName == null) return;
            studentName = studentName.trim();
            
            // validation
            if(validateStudentName(studentName)) {
                break;
            }
        }while(true);
        
        // --- get admin number ---
        do{
            studentAdminNumber = createInputDialog("Enter Admin: ", adminDialogTitle);
            if(studentAdminNumber == null) return;
            
            // validation
            if(validateAdminNumber(studentAdminNumber)) {
                // if does not exist, create an empty array for the student and put it inside the mod map
                ArrayList<String> temp = new ArrayList<>();
                Student.getStudentModMap().put(studentAdminNumber, temp);
                
                break;
            }           
        }while(true);
        
        // --- get student class ---
        do{
            studentClass = createInputDialog("Enter Class: ", adminDialogTitle);
            if(studentClass == null) return;
            
            //validate the class
            if(validateStudentClass(studentClass)) {
                break;
            }
        }while(true);
        
        // --- Part-1: get student modules ---
        do{
            try{
                noOfModsString = createInputDialog("Enter number of Modules Taken: ", adminDialogTitle);
                if(noOfModsString == null) return;
                
                noOfMods = Integer.parseInt(noOfModsString);
            }
            catch(NumberFormatException e) {
                createMessageDialog("The amount of modules need to be in numbers", adminDialogTitle);
                continue;
            }
            
            // validate the number of modules
            if(noOfMods < 1) {
                createMessageDialog("The amount of modules need ot be at least one.", adminDialogTitle);
                continue;
            }
            
            break;
        }while(true);
        
        // --- Part-2: add the modules and marks ---
        for(int i = 0; i < noOfMods; i++) {
            String modToAdd = "";
            String modMarkString = "";
            String modName = "";
            String modGrade = "";
            int modMark = 0;
            
            do {
                modToAdd = createInputDialog(String.format("Enter Module Code for module %d:", i + 1), adminDialogTitle).toUpperCase();
                if(modToAdd == null) return;
                
                if(validateAddModuleCode(studentAdminNumber, modToAdd)) {
                    // change mod code to mod name (to put inside the class map)
                    modName = modulesManagement.modNameToCode(modToAdd);

                    // add the mod code inside the studentModMap
                    addModCodeToSMM(studentAdminNumber, modToAdd);
                    
                    break;
                }
                
            }while(true);
            
            // ask for the marks
            do {
                modMarkString = createInputDialog(String.format("Enter Module Mark for module %d:", i + 1), adminDialogTitle);
                if(modMarkString == null) return;
                
                if(validateModMark(modMarkString)) {
                    modMark = Integer.parseInt(modMarkString);
                    // ### Can try to immplement sth that will let them create the modules on the go
                    break;
                }
            }while(true);
            
            // convert mark to grade
            modGrade = convertMarkToGrade(modMark);
            
            
            // add to the hashmap
            studentModules.put(modName, modGrade);
        }
        
        // add all the information inside the classMap now
        addStudentInsideClass(studentName, studentAdminNumber, studentClass, studentModules);
        
        // show successful message for creaation
        createMessageDialog(String.format("You have successfully added the student %s to %s", studentName, studentClass), adminDialogTitle);
    }
    
    // ===== DELETE THE STUDENT =====
    public static void deleteStudent() {
        // --- Boot up ---
        String studentAdminNumber = "";
        
        // validate and check if the admin number exists or not
        do {
            studentAdminNumber = createInputDialog("Enter admin number of student:", adminDialogTitle);
            if(studentAdminNumber == null) return;
            
            // validate
            if(validateStudentExistance(studentAdminNumber)) {
                break;
            }
        }while(true);
        
        // Remove from studentModMap
        Student.getStudentModMap().remove(studentAdminNumber);

        // Remove from classMap
        Iterator<Map.Entry<String, java.util.List<HashMap<String, Object>>>> iterator = Student.getClassMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, java.util.List<HashMap<String, Object>>> entry = iterator.next();
            java.util.List<HashMap<String, Object>> studentsList = entry.getValue();
            
            // remove student using iterator
            Iterator<HashMap<String, Object>> listIterator = studentsList.iterator();
            while (listIterator.hasNext()) {
                HashMap<String, Object> student = listIterator.next();
                if (student.get("admin").equals(studentAdminNumber)) {
                    listIterator.remove();
                }
            }
            // If the list becomes empty after removal, you can remove the key from the map if needed
            if (studentsList.isEmpty()) {
                iterator.remove();
            }
        }
        
        // Tell user the student has been deleted
        createMessageDialog("Student with admin number " + studentAdminNumber + " has been removed.", adminDialogTitle);
    }
    
    // ===== ADD MORE MODULES FOR THE STUDENT =====
    public static void addNewModuleforStudent() {
        // boot up
        String studentAdminNumber = "";
        String modCode = "";
        String modName = "";
        String modGrade = "";
        String modMarkString = "";
        int modMark = 0;
        
        // get student admin number
        do{
            studentAdminNumber = createInputDialog("Enter admin number of student:", adminDialogTitle);
            if(studentAdminNumber == null) return;
            
            // validate
            if(validateStudentExistance(studentAdminNumber)) {
                break;
            }
        }while(true);
        
        // get the module they wanna add
        do {
            modCode = createInputDialog("Enter Module Code for module:", adminDialogTitle);
            if(modCode == null) return;
            
            // validate
            if(validateAddModuleCode(studentAdminNumber, modCode)) {
                    // change mod code to mod name (to put inside the class map)
                    modName = modulesManagement.modNameToCode(modCode);

                    // add the mod code inside the studentModMap
                    addModCodeToSMM(studentAdminNumber, modCode);
                    
                    break;
                }
        }while(true);
        
        // get the module marks
        do {
            modMarkString = createInputDialog("Enter Module Mark for module:", adminDialogTitle);
            if(modMarkString == null) return;
            
            // validate
            if(validateModMark(modMarkString)) {
                modMark = Integer.parseInt(modMarkString);
                
                break;   
            }
        }while(true);
        
        // convert mark to grade
        modGrade = convertMarkToGrade(modMark);
                
        // --- add to the hashmap ---
        for(Map.Entry<String, java.util.List<HashMap<String, Object>>> studentClass : Student.getClassMap().entrySet()) {
            java.util.List<HashMap<String, Object>> studentLists = studentClass.getValue();
            for(HashMap<String, Object> studentInfo: studentLists) {
                if(studentInfo.get("admin").equals(studentAdminNumber)) {
                    HashMap<String, String> modules = (HashMap<String, String>) studentInfo.get("modules");
                    
                    modules.put(modName, modGrade);
                }
            }
        }
        
        // Display that shows that the mod has been added to the classMap
        createMessageDialog(String.format("The new module %s has been added to the student.", modCode), adminDialogTitle);
    }
      
    // ===== Create new modules =====
    public static void addNewModules() {
        modulesManagement.createModule();
    }
    
    // ===== Show General Statistics =====
    public static void showGeneralStatistics() {
        // boot up
        int noOfStudents = 0;
        int noOfClasses = 0;
        int noOfModules = 0;
        String stats = "";
        double avgStudentGPA = 0.0;
        
        
        // --- GPA Bell Curve ---
        // get an array of all the students GPA
        double[] studentGPAList = GPACalculator.getAllStudentGPA();        
        HistogramPanel histogram = new HistogramPanel(studentGPAList);
        
        
        
        // get all the info
        noOfStudents = Student.getStudentModMap().size();
        noOfClasses = Student.getClassMap().size();
        noOfModules = Modules.getAllModules().size();
        avgStudentGPA = findAverage(studentGPAList);
        
        
        // put messages inside the string
        stats += String.format("The above histogram shows how many students get the respective GPA range.\n\n");
        stats += String.format("More Info:\n\n");
        stats += String.format("Numbers of attending students\t: \t%d\n", noOfStudents);
        stats += String.format("Numbers of classes existing\t: \t%d\n", noOfClasses);
        stats += String.format("Numbers of modules avaiable\t: \t%d\n", noOfModules);
        stats += String.format("Average GPA of the entire school: \t%.2f", avgStudentGPA );

        // show the entire thing 
        
        // Create additional components
        JLabel titleLabel = new JLabel("=== General Statistics ===");

        // Create a text area for more information
        JTextArea moreInfoText = new JTextArea(stats);
        
        moreInfoText.setLineWrap(true);
        moreInfoText.setWrapStyleWord(true);
        moreInfoText.setEditable(false);

        // Create main panel to hold all components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add histogram panel to main panel
        mainPanel.add(histogram, BorderLayout.CENTER);

        // Create a sub-panel for more info
        JPanel subInfo = new JPanel();
        subInfo.setLayout(new FlowLayout());
        subInfo.add(titleLabel);

        // Add panels to main panel
        mainPanel.add(subInfo, BorderLayout.NORTH);
        mainPanel.add(moreInfoText, BorderLayout.SOUTH);
        
        JOptionPane.showMessageDialog(null, mainPanel, adminDialogTitle, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ===== EXIT FROM THE PROGRAM =====
    public static void exit() {
        createMessageDialog("Program terminated.\nThank You!", "Message");
    }
    
    // ====== RESPONSE FOR INVALID CHOICE =====
    public static void invalidUserChoice() {
        createMessageDialog("Please choose a number between 1-7!", "Message");
    } 
    
    // ===== MAIN PROGRAM =====
    public static void runProgram() {
        String userMenuChoice = "";
        String menu = "Enter your option:\n\n1. Add new Student\n2. Delete student\n3. Add new module for student\n4. Add new Module*\n5. Display General Statistics\n6. Logout\n7. Quit";
        String adminDialogTitle = "Admin Menu";
        
        while (true) {
            // Display the menu
            userMenuChoice = createInputDialog(menu, adminDialogTitle);
            
            // Check if the user input is null and get it out of the way
            if (userMenuChoice == null) {
                continue; // Show the menu again if the dialog is cancelled
            }
            
            switch (userMenuChoice) {
                case "1":
                    addNewStudent();
                    break;
                case "2":
                    deleteStudent();
                    break;
                case "3":
                    addNewModuleforStudent();
                    break;
                case "4":
                    addNewModules();
                    break;
                case "5":
                    showGeneralStatistics();
                    break;
                case "6":
                    // Logout
                    return;
                case "7":
                    // Quit
                    System.exit(0);
                    break;
                default:
                    invalidUserChoice();
                    break;
            }
        }
    }
    
    // ===== SAMPLE DATA CREATION =====  
    public static void initialize() {
        // create sample modules for the students
        HashMap<String, String> student1Mod = new HashMap<>();
        student1Mod.put("FOP", "A");
        student1Mod.put("FED", "B");
        student1Mod.put("FOC", "C");
        
        HashMap<String, String> student2Mod = new HashMap<>();
        student2Mod.put("FOP", "A");
        student2Mod.put("FED", "A");
        student2Mod.put("FOC", "B");
        
        HashMap<String, String> student3Mod = new HashMap<>();
        student3Mod.put("FED", "B");
        student3Mod.put("FOC", "A");
        
        // Create the sample students
        Student student1 = new Student("Rafael", "p2340557", "DIT/FT/2A/01", student1Mod);
        Student student2 = new Student("Kevin", "p6969420", "DIT/FT/2A/01", student2Mod);
        Student student3 = new Student("Gabriel", "p6942069", "DIT/FT/2A/02", student3Mod);
        Student student4 = new Student("Morbius", "p5570234", "DIT/FT/2A/01", student1Mod);
        Student student5 = new Student("Harry", "p4206969", "DIT/FT/2A/01", student2Mod);
        Student student6 = new Student("Scott", "p9642069", "DIT/FT/2A/02", student3Mod);
        
        // Insert the student inside respective classes
        Student.addStudentToClassMap(student1);
        Student.addStudentToClassMap(student2);
        Student.addStudentToClassMap(student3);
        Student.addStudentToClassMap(student4);
        Student.addStudentToClassMap(student5);
        Student.addStudentToClassMap(student6);
        
        // create arraylists for the modules
        ArrayList<String> student1mods = new ArrayList<>();
        Collections.addAll(student1mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student2mods = new ArrayList<>();
        Collections.addAll(student2mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student3mods = new ArrayList<>();
        Collections.addAll(student3mods, "ST0501", "ST2413");
        
        ArrayList<String> student4mods = new ArrayList<>();
        Collections.addAll(student1mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student5mods = new ArrayList<>();
        Collections.addAll(student2mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student6mods = new ArrayList<>();
        Collections.addAll(student3mods, "ST0501", "ST2413");
        
        
        // put inside the studentModMap
        Student.getStudentModMap().put("p2340557", student1mods);
        Student.getStudentModMap().put("p6969420", student1mods);
        Student.getStudentModMap().put("p6942069", student1mods);
        Student.getStudentModMap().put("p5570234", student1mods);
        Student.getStudentModMap().put("p4206969", student1mods);
        Student.getStudentModMap().put("p9642069", student1mods);
        
        // System.out.println(Student.getClassMap().get("DIT/FT/2A/01").get(0).get("modules"));
    }
    
    static class HistogramPanel extends JPanel {
        private double[] data;
        private int[] frequency;

        public HistogramPanel(double[] data) {
            this.data = data;
            calculateFrequency();
        }

        private void calculateFrequency() {
            // Define GPA ranges
            double[] ranges = {0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
            frequency = new int[ranges.length - 1];

            // Count GPAs in each range
            for (double gpa : data) {
                for (int i = 0; i < ranges.length - 1; i++) {
                    if (gpa >= ranges[i] && gpa < ranges[i + 1]) {
                        frequency[i]++;
                        break;
                    }
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.length == 0) {
                return;
            }

            // Find maximum frequency for scaling
            int maxFrequency = Arrays.stream(frequency).max().orElse(0);

            // Constants for drawing
            int width = getWidth();
            int height = getHeight();
            int padding = 30;
            int barWidth = (width - 2 * padding) / frequency.length;
            int barHeightScale = height - 2 * padding;

            // Draw bars for each GPA range
            int x = padding;
            for (int i = 0; i < frequency.length; i++) {
                int barHeight = (int) (barHeightScale * ((double) frequency[i] / maxFrequency));
                int y = height - padding - barHeight;

                g.setColor(Color.BLUE);
                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);

                // Draw frequency label above the bar
                String frequencyLabel = String.valueOf(frequency[i]);
                g.drawString(frequencyLabel, x + barWidth / 2 - 10, y - 5);

                // Draw range label below the bar
                String rangeLabel = String.format("%.1f-%.1f", i == 0 ? 0.0 : (i * 0.5), (i + 1) * 0.5);
                g.drawString(rangeLabel, x + barWidth / 2 - 15, height - 5);

                x += barWidth;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(450, 300);
        }
    }
    
    public Student merge(Student a, Student b) {
        String name = "";
        String admin = "";
        String studentClass = "";
        HashMap<String, String> modules = new HashMap<>();
        
        name = a.getName();
        admin = a.getAdmin();
        studentClass = a.getStudentClass();
        
        // get the modules
        // loop student a modules and add inside c
        for(Map.Entry<String, String> module: a.getModules().entrySet()) {
            String key = module.getKey();
            String value = module.getValue();
            
            modules.put(key, value);
        }
        
        // loop student b modules and add inside c
        for(Map.Entry<String, String> module: b.getModules().entrySet()) {
            String key = module.getKey();
            String value = module.getValue();
            
            modules.put(key, value);
        }
        
        Student c = new Student(name, admin, studentClass, modules);
        
        return c;
    }
}

