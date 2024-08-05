/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPRG_CA2_Project;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public class FileManagement {
    public static void main(String[] args) {
        String studentsInfoFileName = "studentsInformation.txt";
        String modulesInfoFileName = "modulesInformation.txt";
        String studentModMapInfoFileName = "studentModMapInformation.txt";

        // Check if the file exists, if not, create one
        File studentsInfoFile = new File(studentsInfoFileName);
        File modulesInfofile = new File(modulesInfoFileName);
        File studentModMapInfoFile = new File(studentModMapInfoFileName);
        
        initialiseData(studentsInfoFileName, modulesInfoFileName, studentModMapInfoFileName, studentsInfoFile, modulesInfofile, studentModMapInfoFile);
        
        
        // ### Read existing data from the file and set them in respective objects
        // - class map
            Map<String, List<HashMap<String, Object>>> classMapFromFile = readFromStudentFile(studentsInfoFileName);
            Student.setClassMap(classMapFromFile);
            Student.printClassMap();
        
        // - student mod map
            HashMap<String, ArrayList<String>> studentModMapFromFile = readFromStudentModMapFile(studentModMapInfoFileName);
            Student.setStudentModMap(studentModMapFromFile);
            System.out.println(Student.getStudentModMap());
        
        // - modules
            ArrayList<Modules> allModulesFromFile = readFromModulesFile(modulesInfoFileName);
            Modules.setAllModules(allModulesFromFile);
            System.out.println(Modules.getAllModules());
            
    }

    public static void initialiseFiles(String studentsInfoFileName, File studentsInfoFile, String modulesInfoFileName, File modulesInfofile, String studentModMapInfoFileName, File studentModMapInfoFile) {
        // Create the files if they dont exist (temp)
        if (!studentsInfoFile.exists()) {
            try {
                studentsInfoFile.createNewFile();
                System.out.println("File created: " + modulesInfoFileName);
            } catch (IOException e) {
                System.err.println("Failed to create file: " + modulesInfoFileName);
                e.printStackTrace();
                return;
            }
        }
        
        if (!modulesInfofile.exists()) {
            try {
                modulesInfofile.createNewFile();
                System.out.println("File created: " + studentsInfoFileName);
            } catch (IOException e) {
                System.err.println("Failed to create file: " + studentsInfoFileName);
                e.printStackTrace();
                return;
            }
        }
        
        if (!studentModMapInfoFile.exists()) {
            try {
                studentModMapInfoFile.createNewFile();
                System.out.println("File created: " + studentModMapInfoFileName);
            } catch (IOException e) {
                System.err.println("Failed to create file: " + studentModMapInfoFileName);
                e.printStackTrace();
                return;
            }
        }
    }
    
    public static void initialiseData(String studentsInfoFileName, String modulesInfoFileName, String studentModMapInfoFileName, File studentsInfoFile, File modulesInfofile, File studentModMapInfoFile) {
        initialiseFiles(studentsInfoFileName, studentsInfoFile, modulesInfoFileName, modulesInfofile, studentModMapInfoFileName, studentModMapInfoFile);
        // --- temp (store the student mod map in the txt file)---
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
        // done with classMap      
       
        // 
        ArrayList<String> student1mods = new ArrayList<>();
        Collections.addAll(student1mods, "ST0523", "ST2413", "S123501");
        
        ArrayList<String> student2mods = new ArrayList<>();
        Collections.addAll(student2mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student3mods = new ArrayList<>();
        Collections.addAll(student3mods, "ST0501", "ST2413");
        
        ArrayList<String> student4mods = new ArrayList<>();
        Collections.addAll(student4mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student5mods = new ArrayList<>();
        Collections.addAll(student5mods, "ST0523", "ST2413", "ST0501");
        
        ArrayList<String> student6mods = new ArrayList<>();
        Collections.addAll(student6mods, "ST0501", "ST2413");
        
        
        Student.getStudentModMap().put("p2340557", student1mods); 
        Student.getStudentModMap().put("p6969420", student2mods); 
        Student.getStudentModMap().put("p6942069", student3mods); 
        Student.getStudentModMap().put("p5570234", student4mods); 
        Student.getStudentModMap().put("p4206969", student5mods); 
        Student.getStudentModMap().put("p9642069", student6mods); 
        
        // Dummy data for modules
        Modules module1 = new Modules("ST0523", "FOP", 5, "None");
        Modules module2 = new Modules("ST2413", "FOC", 4, "None");
        Modules module3 = new Modules("ST0501", "FED", 5, "None");
        Modules module4 = new Modules("ST0509", "JPRG", 3, "None");
        Modules module5 = new Modules("ST0528", "DBS(P)", 4, "None");
        
        writeToStudentsFile(Student.getClassMap(), studentsInfoFileName);
        writeToStudentModMapFile(Student.getStudentModMap(), studentModMapInfoFileName);
        writeToModulesFile(Modules.getAllModules(), modulesInfoFileName);
    }
    
    
    private static String formatStudentMap(HashMap<String, Object> studentMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : studentMap.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString().trim();
    }
    
    
    
    // ===== Write in the files (will overwrite the existing file) =====
    public static void writeToStudentsFile(Map<String, List<HashMap<String, Object>>> classMap, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, List<HashMap<String, Object>>> entry : classMap.entrySet()) {
                writer.write(entry.getKey());
                writer.newLine();
                for (HashMap<String, Object> studentMap : entry.getValue()) {
                    writer.write("{");
                    writer.newLine();
                    writer.write("    " + formatStudentMap(studentMap).replace("\n", "\n    "));
                    writer.newLine();
                    writer.write("}");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToStudentModMapFile(HashMap<String, ArrayList<String>> studentModMap, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, ArrayList<String>> entry : studentModMap.entrySet()) {
                writer.write(entry.getKey());
                writer.newLine();
                for (String module : entry.getValue()) {
                    writer.write(module);
                    writer.newLine();
                }
                writer.newLine(); // Add a blank line between entries
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToModulesFile(ArrayList<Modules> modules, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Modules module : modules) {
                writer.write("{");
                writer.newLine();
                writer.write("    code: " + module.getModCode());
                writer.newLine();
                writer.write("    name: " + module.getModName());
                writer.newLine();
                writer.write("    credit: " + module.getModCreditUnit());
                writer.newLine();
                writer.write("    prerequisite: " + module.getPrerequisite());
                writer.newLine();
                writer.write("}");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===== Read from the files =====
    public static Map<String, List<HashMap<String, Object>>> readFromStudentFile(String fileName) {
        Map<String, List<HashMap<String, Object>>> classMap = new HashMap<>();
        Pattern classPattern = Pattern.compile("^[A-Za-z0-9]{2,4}/[A-Za-z]{2}/[A-Za-z0-9]{2}/[A-Za-z0-9]{2}$");
        Pattern adminPattern = Pattern.compile("^[pP][0-9]{7}$");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentClass = null;
            List<HashMap<String, Object>> students = null;
            HashMap<String, Object> studentData = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (classPattern.matcher(line).matches()) {
                    // This line indicates the start of a new class
                    currentClass = line;
                    students = classMap.computeIfAbsent(currentClass, k -> new ArrayList<>());
                } else if (line.equals("{")) {
                    // This line indicates the start of a new student record
                    studentData = new HashMap<>();
                } else if (line.equals("}")) {
                    // This line indicates the end of a student record
                    students.add(studentData);
                } else {
                    // This line contains a key-value pair of student data
                    String[] parts = line.split(": ", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (key.equals("admin") && !adminPattern.matcher(value).matches()) {
                        System.err.println("Invalid admin number: " + value);
                        continue;
                    }
                    if (value.startsWith("{") && value.endsWith("}")) {
                        // Handle nested map for modules
                        Map<String, String> modules = new HashMap<>();
                        if (!value.equals("{}")) {
                            String[] moduleEntries = value.substring(1, value.length() - 1).split(", ");
                            for (String entry : moduleEntries) {
                                String[] moduleParts = entry.split("=");
                                if (moduleParts.length == 2) {
                                    modules.put(moduleParts[0], moduleParts[1]);
                                }
                            }
                        }
                        studentData.put(key, modules);
                    } else {
                        studentData.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classMap;
    }

    public static HashMap<String, ArrayList<String>> readFromStudentModMapFile(String fileName) {
        HashMap<String, ArrayList<String>> studentModMap = new HashMap<>();
        Pattern adminPattern = Pattern.compile("^[pP][0-9]{7}$");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentAdmin = null;
            ArrayList<String> modules = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (adminPattern.matcher(line).matches()) {
                    currentAdmin = line;
                    modules = new ArrayList<>();
                } 
                else if (!line.isEmpty()) {
                    if (modules != null) {
                        modules.add(line);
                    }
                } 
                else {
                    if (currentAdmin != null && modules != null) {
                        studentModMap.put(currentAdmin, modules);
                    }
                    currentAdmin = null;
                    modules = null;
                }
            }

            // Handle the last entry
            if (currentAdmin != null && modules != null) {
                studentModMap.put(currentAdmin, modules);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentModMap;
    }
    
    public static ArrayList<Modules> readFromModulesFile(String fileName) {
        ArrayList<Modules> modulesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String code = null;
            String name = null;
            int credit = 0;
            String prerequisite = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("{") || line.isEmpty()) {
                    continue; // Skip empty lines or opening brace
                } else if (line.endsWith("}")) {
                    // Process last line of module
                    if (code != null && name != null) {
                        modulesList.add(new Modules(code, name, credit, prerequisite)); // Add module
                        // Reset variables
                        code = name = prerequisite = null;
                        credit = 0;
                    }
                } else {
                    // Parse module details
                    if (line.startsWith("code:")) {
                        code = line.split("code:")[1].trim();
                    } else if (line.startsWith("name:")) {
                        name = line.split("name:")[1].trim();
                    } else if (line.startsWith("credit:")) {
                        credit = Integer.parseInt(line.split("credit:")[1].trim());
                    } else if (line.startsWith("prerequisite:")) {
                        prerequisite = line.split("prerequisite:")[1].trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modulesList;
    }

    
     // ===== Append new data to the files (NOT IN USED) =====
    public static void appendStudentToStudentFile(Student student, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(student.getStudentClass());
            writer.newLine();
            writer.write("{");
            writer.newLine();
            writer.write("    name: " + student.getName());
            writer.newLine();
            writer.write("    admin: " + student.getAdmin());
            writer.newLine();
            writer.write("    class: " + student.getStudentClass());
            writer.newLine();
            writer.write("    modules: " + (!student.getModules().isEmpty() ? student.getModules().toString() : "{}"));
            writer.newLine();
            writer.write("}");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void appendStudentToSMMFile(Student student, String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(student.getAdmin());
            writer.newLine();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void appendModuleToStudentInSMMFile(String adminNumber, String module, String fileName) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp_" + fileName);
        boolean adminFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();

                if (line.equals(adminNumber)) {
                    adminFound = true;
                    writer.write(module);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (adminFound) {
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.err.println("Failed to replace the original file with the updated file.");
            }
        } else {
            tempFile.delete();
            System.err.println("Admin number not found: " + adminNumber);
        }
    }
}
