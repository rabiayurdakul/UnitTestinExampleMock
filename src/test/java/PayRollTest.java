import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class PayRollTest {


    private PayRoll payRoll;

    private  EmployeeDB employeeDB;

    private List<Employee> employees;

    private  EmployeeService employeeService;


    /***
     * @BeforeEach
     * Denotes that the annotated method should be executed before each @Test, @RepeatedTest, @ParameterizedTest,
     * or @TestFactory method in the current class; analogous to JUnit 4’s @Before. Such methods are inherited
     * unless they are overridden.
     */
    @BeforeEach
    void setUp() {
        employees = new ArrayList<Employee>();
        // mock creation
        employeeDB = mock(EmployeeDB.class);
        employeeService = mock(EmployeeService.class);
        //The when section is that behavior that you're specifying.
        //Finally the then section describes the changes you expect due to the specified behavior.
        when(employeeDB.getAllEmployees()).thenReturn(employees);

        payRoll = new PayRoll(employeeDB,employeeService);

    }


    @Test
    void testNoEmployees() {
        // Asserts that two objects are equal.
        assertEquals(0,payRoll.overTimePayment());
    }

    @Test
    void testSingleEmployees() {
        employees.add(new Employee("ID4567","Rabia Yurdakul Telef", 5));
        assertEquals(1,payRoll.overTimePayment());


    }

    @Test
    void testOnlyOneInteractionDB(){
        payRoll.overTimePayment();
        // Was the method called one time?
        verify(employeeDB,times(1)).getAllEmployees();
    }

    @Test
    void testEmployeeIsOverTimePaid(){
        employees.add(new Employee("ID4567","Rabia Yurdakul Telef",5));
        assertEquals(1,payRoll.overTimePayment());
        verify(employeeService,times(1)).makePayment("ID4567",5);
    }

    @Test
    void testEmployeeAreOverTimePaid(){
        employees.add(new Employee("ID4567", "Rabia Yurdakul Telef",5));
        employees.add(new Employee("ID3453","Lisa Simpsons",3));
        assertEquals(2,payRoll.overTimePayment());

        // Use it to capture argument values for further assertions.
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> overTimePaymentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(employeeService, times(2)).makePayment(idCaptor.capture(), overTimePaymentCaptor.capture());

        assertEquals("ID4567",idCaptor.getAllValues().get(0));
        assertEquals("ID3453",idCaptor.getAllValues().get(1));
        assertEquals(5,overTimePaymentCaptor.getAllValues().get(0).intValue());
        assertEquals(3,overTimePaymentCaptor.getAllValues().get(1).intValue());
      }

    @Test
      void testInteractionOrder(){
        String employeeId = "ID4567";
        int overTime = 5;

        employees.add(new Employee(employeeId,"Rabia Yurdakul Telef", overTime));
        assertEquals(1,payRoll.overTimePayment());

          InOrder inOrder = inOrder(employeeDB, employeeService);
          inOrder.verify(employeeDB).getAllEmployees();
          inOrder.verify(employeeService).makePayment(employeeId,overTime);
      }


}