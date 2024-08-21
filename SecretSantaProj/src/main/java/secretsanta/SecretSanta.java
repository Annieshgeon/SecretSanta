package secretsanta;
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Random;

	public class SecretSanta {

	    public static List<Employee> loadEmployeeData(String filePath) throws IOException {
	        List<Employee> employees = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(",");
	                employees.add(new Employee(values[0], values[1]));
	            }
	        }
	        return employees;
	    }


	    public static Map<String, String> loadPreviousAssignments(String filePath) throws IOException {
	        Map<String, String> previousAssignments = new HashMap<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            br.readLine(); 
	            while ((line = br.readLine()) != null) {
	                String[] values = line.split(",");
	                
	                
	                if (values.length < 4) {
	                    System.out.println("Skipping row due to incorrect format: " + line);
	                    continue;
	                }
	                
	                previousAssignments.put(values[1], values[3]); 
	            }
	        }
	        return previousAssignments;
	    }


	    public static List<SecretSantaAssignment> assignSecretSanta(List<Employee> employees, Map<String, String> previousAssignments) {
	        List<Employee> potentialChildren = new ArrayList<>(employees);
	        List<SecretSantaAssignment> assignments = new ArrayList<>();
	        Random random = new Random();

	        for (Employee employee : employees) {
	            String employeeEmail = employee.email;
	            String previousChildEmail = previousAssignments.get(employeeEmail);

	            List<Employee> validChildren = new ArrayList<>();
	            for (Employee child : potentialChildren) {
	                if (!child.email.equals(employeeEmail) && (previousChildEmail == null || !child.email.equals(previousChildEmail))) {
	                    validChildren.add(child);
	                }
	            }

	            if (validChildren.isEmpty()) {
	                throw new IllegalArgumentException("Unable to find valid Secret Santa assignments under the given constraints.");
	            }

	            Employee secretChild = validChildren.get(random.nextInt(validChildren.size()));
	            assignments.add(new SecretSantaAssignment(employee.name, employee.email, secretChild.name, secretChild.email));
	            potentialChildren.remove(secretChild);
	        }

	        return assignments;
	    }

	    public static void saveAssignmentsToCSV(List<SecretSantaAssignment> assignments, String outputFilePath) throws IOException {
	        try (FileWriter writer = new FileWriter(outputFilePath)) {
	            writer.write("Employee_Name,Employee_EmailID,Secret_Child_Name,Secret_Child_EmailID\n");
	            for (SecretSantaAssignment assignment : assignments) {
	                writer.write(String.format("%s,%s,%s,%s\n",
	                        assignment.employeeName,
	                        assignment.employeeEmail,
	                        assignment.secretChildName,
	                        assignment.secretChildEmail));
	            }
	        }
	    }

	    public static void main(String[] args) {
	        String currentYearFile = "current_year_employees.csv";
	        String previousYearFile = "previous_year_assignments.csv";
	        String outputFile = "secret_santa_assignments.csv";

	        try {
	            List<Employee> employees = loadEmployeeData(currentYearFile);
	            Map<String, String> previousAssignments = loadPreviousAssignments(previousYearFile);
	            List<SecretSantaAssignment> assignments = assignSecretSanta(employees, previousAssignments);
	            saveAssignmentsToCSV(assignments, outputFile);
	            System.out.println("Secret Santa assignments saved to " + outputFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
