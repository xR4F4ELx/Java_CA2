/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA2_Project;

import java.util.*;
import javax.swing.*;

/**
 *
 * @author ASUS
 */
public class StudentManagementController {
    private final StudentManagementView view;
    private final ValidationMethods validationMethods;
    
    public StudentManagementController(StudentManagementView view) {
        this.view = view;
        this.validationMethods = new ValidationMethods(view);
    }
    
    // Deal with drop downs
    public void addModulesInsideDropdownbox(String adminNumber, JComboBox<String> comboBox) {
        // populate the module drop down box
        modulesManagement.populateStudentModuleDropDown(adminNumber, comboBox);
    }
    
    public void resetDropDownBox(JComboBox<String> comboBox) {
        modulesManagement.emptyDropDownBox(comboBox);
    }
    
    
    // handle button clicks
    public boolean handleCreateStudentButtonClicked1(String name, String adminNumber, String studentClass, HashMap<String, String> modules) {
        boolean success = true;
        
        // check if the student admin number already exists or not
        if(validationMethods.validateStudentExistance(adminNumber)) {
            if(validationMethods.validateStudentName(name)) {
                if(validationMethods.validateStudentClass(studentClass)) {
                    ArrayList<String> temp = new ArrayList<>();
                    
                    // create student and add to classMap and studentModMap
                    StudentManagement.addStudentInsideClass(name, adminNumber, studentClass, modules);
                    Student.getStudentModMap().put(adminNumber, temp); 
                    
                    // handle successful student creation (propmt user to add modules)
                    view.showJOptionDialog("The student has been successfully created. Please proceed to add modules to the student!", "Successfully created!", JOptionPane.INFORMATION_MESSAGE);
                   
                    return success;
                }
                
            }
        }
        
        return !success;
    }
    
    public boolean handleAddModuleButtonClicked(String studentAdminNumber, String moduleMarkStr, String selectedModuleInput) {
        // --- boot up ---
        boolean success = true;
        int modMark = 0;
        String modGrade = "";
        
        // get the mod name and code
        String[] selectedModuleArray = selectedModuleInput.split(" ");
        String selectedModuleCode = selectedModuleArray[0];
        String selectedModduleName = selectedModuleArray[1].substring(1, selectedModuleArray[1].length() - 1);;
        
        // validate the mark
        if(!validationMethods.validateModMark(moduleMarkStr) || moduleMarkStr == null) {
            return !success;
        }
        modMark = Integer.parseInt(moduleMarkStr);
        modGrade = StudentManagement.convertMarkToGrade(modMark);
        
               
        // add inside the classMap and studentModMap
        // - add to classMap
        StudentManagement.addStudentModuleToClassMap(studentAdminNumber, selectedModduleName, modGrade);
        
        // - add to SMM
        StudentManagement.addModCodeToSMM(studentAdminNumber, selectedModuleCode);
        
        view.showJOptionDialog(String.format("You have successfully added '%s' for the student '%s' with '%d' marks.", selectedModuleCode, studentAdminNumber, modMark), "Info", JOptionPane.INFORMATION_MESSAGE);
        
        return success;
    }
    
    public boolean handleDeleteStudentButtonClicked(String adminNumber) {
        boolean success = true;
        
        // ask if they really want to delete
        boolean confirmation = false;
        confirmation = view.showYesNoJOptionDialog("Are you sure you want to delete the student?", "Warning");
        
        if(!confirmation) {
            return !success;
        }
        
        // delete the student from classMap
        StudentManagement.removeStudentFromClassMap(adminNumber);
        
        // delete the student from SMM
        Student.getStudentModMap().remove(adminNumber);
        
        view.showJOptionDialog("Student '" + adminNumber  + "' has been removed.", "Info", JOptionPane.INFORMATION_MESSAGE);
        
        return success;
    }

    // === Exit program ===
    public void exitProgram() {
        // overwrite all the info into respective files
        FileManagement.writeToStudentsFile(Student.getClassMap(), "studentsInformation.txt");
        FileManagement.writeToStudentModMapFile(Student.getStudentModMap(), "studentModMapInformation.txt");
        FileManagement.writeToModulesFile(Modules.getAllModules(), "modulesInformation.txt");
        
    }
}
