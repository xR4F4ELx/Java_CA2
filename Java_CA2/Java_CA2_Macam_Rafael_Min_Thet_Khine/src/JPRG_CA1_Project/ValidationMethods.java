/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA1_Project;

import static JPRG_CA1_Project.StudentManagement.adminDialogTitle;
import static JPRG_CA1_Project.StudentManagement.createMessageDialog;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Macam Rafael and Min Thet Khine
 */
public class ValidationMethods {

    public static Boolean blankCharacterInputValidation(String character) {
        return character.trim().isEmpty();
    }

    public static Boolean isValidOption(int option) {
        return option < 1 && option > 4;
    }
    
    public static boolean validateAdminNumber(String adminNumber) {
        boolean valid = true;
        if(adminNumber.length() != 8 || adminNumber.charAt(0) != 'p'){
            createMessageDialog("Please Enter a valid admin number that is 8 character long and starts with 'p'!", adminDialogTitle);
            
            return !valid;
        }
        else {
            if (Student.getStudentModMap().containsKey(adminNumber)) {
                createMessageDialog("The admin number (" + adminNumber + ") you have enetered exist already. Please choose a different admin number.", adminDialogTitle);
                return !valid;
            } else {
                return valid;
            }
        }
    }
    
    public static boolean validateStudentName(String studentName) {
        boolean valid = true;
        
        Pattern namePattern = Pattern.compile("^[A-Za-z ]{1,70}$");
        
        // checks if the name fits the regular expression pattern
        Matcher matcher = namePattern.matcher(studentName);
        
        if(!matcher.matches()) {
            createMessageDialog("Please Enter a valid name!", adminDialogTitle);
            return !valid;
        }
        else {
            return valid;
        }
    }

    public static boolean validateStudentClass(String studentClass) {
        boolean valid = true;
        
        // DIT/FT/1A/02
        Pattern namePattern = Pattern.compile("^[A-Za-z0-9]{2,4}/[A-Za-z]{2}/[A-Za-z0-9]{2}/[A-Za-z0-9]{2}$");
        
        Matcher matcher = namePattern.matcher(studentClass);
        
        if(!matcher.matches()) {
            createMessageDialog("Please Enter a valid Class Name!", adminDialogTitle);
            return !valid;
        }
        else {
            return valid;
        }
    }
    
    public static boolean validateModMark(String modMarkStr) {
        boolean valid = true;
        int modMark = 0;
        
        try{
            modMark = Integer.parseInt(modMarkStr);
        }
        catch (NumberFormatException e){
            createMessageDialog("Please enter a valid number 0-100!", adminDialogTitle);
            return !valid;
        }
        
        if(modMark > 100 || modMark < 0) {
            createMessageDialog("Please enter a valid number 0-100!", adminDialogTitle);
            return !valid;
        }
        else {
            return valid;
        }
    }
    
    public static boolean validateStudentModules(String studentAdminNumber, String modCode) {
        Map<String, ArrayList<String>> studentModMap = Student.getStudentModMap();
        ArrayList<String> modules = studentModMap.get(studentAdminNumber);
        if (modules != null) {
            return !modules.contains(modCode);
        }
        return true; // No modules found for student, so they are not taking the module.
    }

    public static boolean validateAddModuleCode(String adminNumber, String modToAdd) {
        // check if the module exists
        if (!modulesManagement.isModuleExists(modToAdd)) {
            createMessageDialog(String.format("Module %s does not exists!", modToAdd), adminDialogTitle);
            return false;
        }

        // check if the student is already taking the module
        if (validateStudentModules(adminNumber, modToAdd)) {
            return true;
        } else {
            createMessageDialog("The student is already taking the module! Please choose another one.", adminDialogTitle);
            return false;
        }
    }
    
    public static boolean validateStudentExistance(String adminNumber) {
        boolean valid = true;
        
        if(validateAdminNumber(adminNumber)) {
                if (!Student.getStudentModMap().containsKey(adminNumber)) {
                    createMessageDialog("Student not found!", adminDialogTitle);
                    return !valid;
                }
                return valid;
        }
        return !valid;
    }
}
