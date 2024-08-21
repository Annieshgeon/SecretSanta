import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import secretsanta.SecretSanta;
import secretsanta.Employee;
import secretsanta.SecretSantaAssignment;

class SecretSantaTest {

    private List<Employee> employees;
    private Map<String, String> previousAssignments;

    @BeforeEach
    void setUp() {
        
        employees = new ArrayList<>();
        employees.add(new Employee("Alice", "alice@example.com"));
        employees.add(new Employee("Bob", "bob@example.com"));
        employees.add(new Employee("Charlie", "charlie@example.com"));
        employees.add(new Employee("David", "david@example.com"));

        // Set up previous year's assignments
        previousAssignments = new HashMap<>();
        previousAssignments.put("alice@example.com", "bob@example.com");
        previousAssignments.put("bob@example.com", "charlie@example.com");
        previousAssignments.put("charlie@example.com", "david@example.com");
        previousAssignments.put("david@example.com", "alice@example.com");
    }
    
    @Test
    void testSaveAssignmentsToCSV() throws IOException {
        // Create a mock assignment
        List<SecretSantaAssignment> assignments = new ArrayList<>();
        assignments.add(new SecretSantaAssignment("Alice", "alice@example.com", "Charlie", "charlie@example.com"));
        assignments.add(new SecretSantaAssignment("Bob", "bob@example.com", "David", "david@example.com"));
        assignments.add(new SecretSantaAssignment("Charlie", "charlie@example.com", "Alice", "alice@example.com"));
        assignments.add(new SecretSantaAssignment("David", "david@example.com", "Bob", "bob@example.com"));

        
        String outputFilePath = "test_secret_santa_output.csv";
        SecretSanta.saveAssignmentsToCSV(assignments, outputFilePath);

        try (BufferedReader br = new BufferedReader(new FileReader(outputFilePath))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            assertEquals(5, lines.size(), "There should be 5 lines in the output file including the header.");

            assertEquals("Employee_Name,Employee_EmailID,Secret_Child_Name,Secret_Child_EmailID", lines.get(0), "Header should match the expected format.");
        }

       
        new File(outputFilePath).delete();
    }
}
