/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA2_Project;

import JPRG_CA2_Project.*;
import static JPRG_CA1_Project.StudentManagement.createInputDialog;
import static JPRG_CA1_Project.StudentManagement.createMessageDialog;
import static JPRG_CA1_Project.StudentManagement.adminDialogTitle;

import java.util.*;
import javax.swing.*;
/**
 *
 * @author Min Thet Khine
 */
public class modulesManagement {
    static {
       initialize();
    }
    
    public static void initialize() { 
        // Create sample data
        Modules module1 = new Modules("ST0523", "FOP", 5, "none");
        Modules module2 = new Modules("ST2413", "FOC", 4, "none");
        Modules module3 = new Modules("ST0501", "FED", 5, "none");
        Modules module4 = new Modules("ST0509", "JPRG", 3, "none");
        Modules module5 = new Modules("ST0528", "DBS(P)", 4, "none");
    }
    
//    public static ArrayList<String> createListOfNonPrerequsiteMods() {
//        ArrayList<Modules> allModules = Modules.getAllModules();
//        ArrayList<String> nonPrerequisiteMods =  new ArrayList<String>();
//        
////        for()
//        
//        return nonPrerequisiteMods;
//    }
    
    
    public static void populateStudentModuleDropDown(String studentAdmin, JComboBox<String> comboBox) {
        ArrayList<Modules> allModules = Modules.getAllModules();
        HashMap<String, ArrayList<String>> studentModMap = Student.getStudentModMap();
        
        // remove all the items inside the dropdown box
        comboBox.removeAllItems();
        
        ArrayList<String> completedModules = studentModMap.get(studentAdmin);
        
        // populate the drop down box
        for(Modules module: allModules) {
            boolean hasCompletedPrerequisite = module.getPrerequisite().equals("none") || (completedModules != null && completedModules.contains(module.getPrerequisite()));
            boolean hasNotTakenModule = completedModules == null || !completedModules.contains(module.getModCode());
            
            if (hasCompletedPrerequisite && hasNotTakenModule) {
                comboBox.addItem(module.getModCode() + " [" + module.getModName() + "]");
            }
        }
    }
    
    public static void emptyDropDownBox(JComboBox<String> comboBox) {
        // code to reset
        comboBox.removeAllItems();
        
        comboBox.addItem("Choose");
    }
    
    
    public static boolean isModuleExists(String modCode) {
        ArrayList<Modules> allModules = Modules.getAllModules();
        for (Modules module : allModules) {
            if (module.getModCode().equals(modCode)) {
                return true;
            }
        }
        return false;
    }
    
    public static void createModule() {
        String modCode = "";
        String modCodeUpper = "";
        String modName = "";
        String creditUnitString = "";
        int creditUnit = 0;

        do {
            // Ask for module code
            modCode= createInputDialog("Enter module code:", adminDialogTitle);
            if(modCode == null) return;
            
            modCodeUpper = modCode.toUpperCase();
            
            if (modCodeUpper.trim().isEmpty()) {
                createMessageDialog("Module Code cannot be empty.", "Student Admin System");
                continue;
            } else {
                // Check if the module code exists already
                if (isModuleExists(modCodeUpper)) {
                    String userUpdateInput = "";
                    
                    // giving the loop a name so i can break it LOL
                    innerLoop:
                    do {
                        userUpdateInput = createInputDialog("The module exists already, would you like to update it?\n1. Yes\n2. No", "Student Admin System");
                        if (userUpdateInput == null) {
                            break;
                        }
                        switch (userUpdateInput) {
                            case "1":
                                updateModule(modCodeUpper);
                                return;
                            case "2":
                                break innerLoop;
                            default:
                                createMessageDialog("Please choose either '1' or '2'.", "Student Admin System");
                                break;
                        }
                    } while (true);
                }
                else{
                    break;
                }
                
            }
        } while (true);

        // Ask for module name
        do {
            modName = createInputDialog("Enter module name:", "Student Admin System");
            if(modName == null) return;
            
            if (modName.trim().isEmpty()) {
                createMessageDialog("Module Name cannot be empty.", "Student Admin System");
                continue;
            }
            break;
        } while (true);

        // Ask for credit units
        do {
            try {
                creditUnitString = createInputDialog("Enter credit units:", "Student Admin System");
                if(creditUnitString == null) return;
                
                creditUnit = Integer.parseInt(creditUnitString);
                
                if (creditUnit < 1 || creditUnit > 6) {
                    createMessageDialog("Please enter a valid number from 1-6!", "Student Admin System");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                createMessageDialog("Please enter a valid number from 1-6!", "Student Admin System");
            }
        } while (true);

        // Create the module
//        Modules newModule = new Modules(modCodeUpper, modName, creditUnit, requisite); // NEED TO CHANGE THESE
//        Modules.getAllModules().add(newModule);
        createMessageDialog(String.format("Module %s created successfully.", modCodeUpper), "Student Admin System");
    }
    
    // Update existing module
    public static void updateModule(String modCode) {
        ArrayList<Modules> allModules = Modules.getAllModules();
        for (Modules module : allModules) {
            if (module.getModCode().equals(modCode)) {
                String modName = "";
                String creditUnitString = "";
                int creditUnit = 0;

                // Ask for new module name
                do {
                    modName = createInputDialog("Enter new module name:", "Student Admin System");
                    if(modName == null) return;
                    
                    if (modName.trim().isEmpty()) {
                        createMessageDialog("Module Name cannot be empty.", "Student Admin System");
                        continue;
                    }
                    module.setModName(modName);
                    break;
                } while (true);

                // Ask for new credit units
                do {
                    try {
                        creditUnitString = createInputDialog("Enter new credit units:", "Student Admin System");
                        if(creditUnitString == null) return;
                        
                        creditUnit = Integer.parseInt(creditUnitString);
                        if (creditUnit < 1 || creditUnit > 6) {
                            createMessageDialog("Please enter a valid number from 1-6!", "Student Admin System");
                            continue;
                        }
                        module.setModCreditUnit(creditUnit);
                        break;
                    } catch (NumberFormatException e) {
                        createMessageDialog("Please enter a valid number from 1-6!", "Student Admin System");
                    }
                } while (true);

                createMessageDialog(String.format("Module %s updated successfully.", modCode), "Student Admin System");
                return;
            }
        }
    }
    
    // get mod name from mod code
    public static String modNameToCode(String modCode) {
        ArrayList<Modules> allModules = Modules.getAllModules();
        for (Modules module : allModules) {
            if (module.getModCode().equals(modCode)) {
                return module.getModName();
            }
        }
        return modCode;
    }
}

