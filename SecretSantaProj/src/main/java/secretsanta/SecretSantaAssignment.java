package secretsanta;


public class SecretSantaAssignment {
	public String employeeName;
	public String employeeEmail;
	public String secretChildName;
    public String secretChildEmail;

    public SecretSantaAssignment(String employeeName, String employeeEmail, String secretChildName, String secretChildEmail) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.secretChildName = secretChildName;
        this.secretChildEmail = secretChildEmail;
    }
}