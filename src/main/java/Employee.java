public class Employee {

    private String employeeId;

    private  String name;

    private int overTime;


    public Employee(String employeeId, String name, int overTime){

        this.employeeId = employeeId;
        this.name = name;
        this.overTime = overTime;
    }

    public String getEmployeeId(){
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public int getOverTime() {
        return overTime;
    }
}
