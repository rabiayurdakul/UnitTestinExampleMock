import java.util.List;

public class PayRoll {

    private EmployeeDB employeeDB;

    private EmployeeService employeeService;

    public PayRoll(EmployeeDB employeeDB, EmployeeService employeeService){
        super();
        this.employeeDB = employeeDB;
        this.employeeService = employeeService;
    }

    public int overTimePayment(){
        List<Employee> employees = employeeDB.getAllEmployees();

        for (Employee employee : employees){
            employeeService.makePayment(employee.getEmployeeId(),employee.getOverTime());
        }

        return employees.size();
    }
}
