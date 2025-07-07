package LowLevelDesign.DesignPatterns.ProxyDesignPattern;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public void create(String client, EmployeeDo obj) throws Exception {
        // creates a new row
        System.out.println("created new row in the employee table");
    }

    @Override
    public void delete(String client, int empId) throws Exception {
        // deletes a row
        System.out.println("Deleted row with employee id:"+empId);
    }

    @Override
    public EmployeeDo get(String client, int emplId) throws Exception {
        // fetch a row
        System.out.println("fetching data from the DB");
        return new EmployeeDo(1,"Bhavesh");
    }
}
