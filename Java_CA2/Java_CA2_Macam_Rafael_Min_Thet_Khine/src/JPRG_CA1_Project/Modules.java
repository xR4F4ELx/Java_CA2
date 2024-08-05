/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA1_Project;

import java.util.*;
/**
 *
 * @author Macam Rafael and Min Thet Khine
 */
public class Modules {
    private String modCode = "";
    private String modName = "";
    private int modCreditUnit = 0;
    private static ArrayList<Modules> allModules = new ArrayList<>();
        
    // --- Constructor ---
    public Modules(String modCode, String modName, int modCreditUnit) {
        this.modCode = modCode;
        this.modName = modName;
        this.modCreditUnit = modCreditUnit;
        
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
    
    public static ArrayList<Modules> getAllModules() {
        return allModules;
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

    
    public void displayInfo() {
        System.out.println("Module Code: " + modCode);
        System.out.println("Module Name: " + modName);
        System.out.println("Credit Unit: " + modCreditUnit);

        System.out.println();
    }
}
