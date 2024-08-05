/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA1_Project;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 *
 * @author Macam Rafael
 */
public class GPACalculator {
    
    public static double round(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException("Decimal places must be non-negative");

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP); // RoundingMode.HALF_UP for rounding
        return bd.doubleValue();
    }
    
    public class GradePoints {
        private static final Map<String, Double> gradePoints = new HashMap<>();

        static {
            gradePoints.put("A", 4.0);
            gradePoints.put("B", 3.0);
            gradePoints.put("C", 2.0);
            gradePoints.put("D", 1.0);
            gradePoints.put("F", 0.0);
        }
        
        public static double getGradePoint(String grade) {
            return gradePoints.getOrDefault(grade, 0.0);
        }
    }
    
    public static double[] getAllStudentGPA() {
        int totalStudents = 0;
        for(List<HashMap<String, Object>> studentLists : Student.getClassMap().values()) {
            totalStudents += studentLists.size();
        }
        
        double[] studentGPAList = new double[totalStudents];
        int count = 0;
        
        for(Map.Entry<String, List<HashMap<String, Object>>> studentClass : Student.getClassMap().entrySet()) {
            List<HashMap<String, Object>> studentLists = studentClass.getValue();
            for(HashMap<String, Object> studentInfo: studentLists) {
                // calculate GPA and store it temporarily
                double temp = calculateGPA(studentInfo);
                
                // add it inside the list
                studentGPAList[count] = temp;
                count += 1;
            }
        }
        
        return studentGPAList;
    }
    
    public static double calculateGPA(HashMap<String, Object> student) {
        double totalGradePoints = 0.0;
        int totalCreditUnits = 0;

        // Assuming 'modules' is a map within the student object
        Map<String, String> modules = (Map<String, String>) student.get("modules");

        for (Map.Entry<String, String> entry : modules.entrySet()) {
            String moduleName = entry.getKey();
            String grade = entry.getValue();

            Modules module = findModuleByName(moduleName);

            if (module != null) {
                double gradePoint = GradePoints.getGradePoint(grade);
                int creditUnit = module.getModCreditUnit();
                totalGradePoints += gradePoint * creditUnit;
                totalCreditUnits += creditUnit;
            }
        }

        return round((totalCreditUnits == 0) ? 0.0 : totalGradePoints / totalCreditUnits, 2);
    }

    public static Modules findModuleByName(String moduleName) {
        ArrayList<Modules> moduleList = Modules.getAllModules();
        for (Modules module : moduleList) {
            if (module.getModName().equalsIgnoreCase(moduleName)) {
                return module;
            } 
        }
        return null; // Return null if no module is found
    }
    
    public static Double calculateAverageGPAForClass(List<HashMap<String, Object>> schoolClass) {
        if (schoolClass == null || schoolClass.isEmpty()) {
            return null; // Class not found or empty class
        }
        double totalGPA = 0;
        for (HashMap<String, Object> student : schoolClass) {
            totalGPA += GPACalculator.calculateGPA(student);
        }

        return round(totalGPA / schoolClass.size(), 2);
    }
    
    
}
