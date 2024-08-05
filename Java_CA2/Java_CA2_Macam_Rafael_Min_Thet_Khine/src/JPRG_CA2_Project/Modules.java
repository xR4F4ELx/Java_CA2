/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA2_Project;

import JPRG_CA2_Project.*;
import java.util.*;
/**
 *
 * @author Macam Rafael and Min Thet Khine
 */
public class Modules {
    private String modCode = "";
    private String modName = "";
    private int modCreditUnit = 0;
    private String prerequisite = "";
    private static ArrayList<Modules> allModules = new ArrayList<>();
    private String grade;

        
    // --- Constructor ---
    public Modules(String modCode, String modName, int modCreditUnit, String prerequisite) {
        this.modCode = modCode;
        this.modName = modName;
        this.modCreditUnit = modCreditUnit;
        this.prerequisite = prerequisite;
        
        allModules.add(this);
    }

    
    // ===== Getters =====
    public String getModCode() {
        return modCode;
    }
    
    public String getModName() {
        return modName;
    }

    public int getModCreditUnit() {
        return modCreditUnit;
    }
    
    public String getPrerequisite() {
        return prerequisite;
    }
    
    public static ArrayList<Modules> getAllModules() {
        return allModules;
    }
    
    public String getGrade() {
        return grade;
    }
    
    
    // ===== Setters =====
    public void setModCode(String code) {
        this.modCode = code;
    }
    
    public void setModName(String name) {
        this.modName = name;
    }

    public void setModCreditUnit(int modCreditUnit) {
        this.modCreditUnit = modCreditUnit;
    }

    public static void setAllModules(ArrayList<Modules> allModules) {
        Modules.allModules = allModules;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public void displayInfo() {
        System.out.println("Module Code: " + modCode);
        System.out.println("Module Name: " + modName);
        System.out.println("Credit Unit: " + modCreditUnit);

        System.out.println();
    }
}
