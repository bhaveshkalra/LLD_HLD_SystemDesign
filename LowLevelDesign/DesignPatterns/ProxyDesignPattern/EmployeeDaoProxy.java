package LowLevelDesign.DesignPatterns.ProxyDesignPattern;

public class EmployeeDaoProxy implements EmployeeDao {
    EmployeeDao employeeDaoObj;

    EmployeeDaoProxy() {
        employeeDaoObj = new EmployeeDaoImpl();
    }

    @Override
    public void create(String client, EmployeeDo obj) throws Exception {
        // creates a new row
        if (client.equals("ADMIN")) {
            employeeDaoObj.create(client, obj);
            return;
        }
        throw new Exception("Access Denied");
    }

    @Override
    public void delete(String client, int empId) throws Exception {
        // deletes a row
        if (client.equals("ADMIN")) {
            employeeDaoObj.delete(client, empId);
            return;
        }
        throw new Exception("Access Denied");
    }

    @Override
    public EmployeeDo get(String client, int emplId) throws Exception {
        // fetch a row
        if (client.equals("ADMIN") || client.equals("USER")) {
            return employeeDaoObj.get(client, emplId);
        }
        throw new Exception("Access Denied");
    }
}