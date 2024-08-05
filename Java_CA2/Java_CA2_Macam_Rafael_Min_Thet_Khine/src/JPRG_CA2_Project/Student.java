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
public class Student {
    private String name;
    private String admin;
    private String studentClass;
    private HashMap<String, String> modules;
    
    // Hash map to keep track of all the students admin number and which modules they have already taken
    private static HashMap<String, ArrayList<String>> studentModMap = new HashMap<>();
    
    // Static map to keep track of students by class
    private static Map<String, List<HashMap<String, Object>>> classMap = new HashMap<>();
    
    
    
    // --- Constructors ---
    public Student() {}
    
    public Student(String name, String admin, String studentClass, HashMap<String, String> modules) {
        this.name = name;
        this.admin = admin;
        this.studentClass = studentClass;
        this.modules = modules;
    }
    
    // ===== Getters =====
    public String getName() {
        return name;
    }
    
    public String getAdmin() {
        return admin;
    }
    
    public String getStudentClass() {
        return studentClass;
    }
    
    public HashMap<String, String> getModules() {
        return modules;
    }

    public static Map<String, List<HashMap<String, Object>>> getClassMap() {
        return classMap;
    }
    
    public static HashMap<String, ArrayList<String>> getStudentModMap() {
        return studentModMap;
    }
    
    // ===== Setters =====
    public static void setClassMap(Map<String, List<HashMap<String, Object>>> newClassMap) {
        classMap = newClassMap;
    }
    
    public static void setStudentModMap(HashMap<String, ArrayList<String>> newStudentModMap) {
        studentModMap = newStudentModMap;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAdmin(String adminNumber) {
        this.admin = adminNumber;
    }
    
    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
    
    public void setModules(HashMap<String, String> modules) {
        this.modules = modules;
    }
    
    // ===== Processors =====
    // Convert Student object to HashMap
    public HashMap<String, Object> convertStudentToHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("admin", admin);
        map.put("class", studentClass);
        map.put("modules", modules);
        return map;
    }

//    // Add a student to the class map
//    public static void addStudentToClassMap(Student student) {
//        // check if the class you wanna add already exisit or not. If dont exist, create one
//        String studentClass = student.getStudentClass();
//        List<HashMap<String, Object>> studentList = classMap.getOrDefault(studentClass, new ArrayList<>());
//        
//        // add to exisiting or newly created key
//        studentList.add(student.convertStudentToHashMap());
//        classMap.put(studentClass, studentList);
//    }
    
    public static void addStudentToClassMap(Student student) {
    String className = student.getStudentClass();
    List<HashMap<String, Object>> studentsInClass = classMap.getOrDefault(className, new ArrayList<>());

    HashMap<String, Object> studentData = new HashMap<>();
    studentData.put("name", student.getName());
    studentData.put("admin", student.getAdmin());
    studentData.put("class", student.getStudentClass());
    studentData.put("modules", student.getModules());

    studentsInClass.add(studentData);
    classMap.put(className, studentsInClass);
}
    
    
    // ===== testers =====
    public static void printClassMap() {
        for (Map.Entry<String, List<HashMap<String, Object>>> entry : classMap.entrySet()) {
            System.out.println("Class: " + entry.getKey());
            System.out.println("Class members: " + entry.getValue());
            System.out.println();
        }
    }
    
    public void displayInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Admin: " + admin);
        System.out.println("Class: " + studentClass);
        System.out.print("Modules: ");
        
        int i = 1;
        for (Map.Entry<String, String> module : getModules().entrySet()) {
            String key = module.getKey();
            String value = module.getValue();
        
            System.out.println(String.format("%d. %s \t:%s", i, key, value));
            i++;
        }
        System.out.println();
    }
}
