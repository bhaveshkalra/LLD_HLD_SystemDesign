package LowLevelDesign.DesignPatterns.ProxyDesignPattern;

public interface EmployeeDao {
    public void create(String client, EmployeeDo obj) throws Exception;
    public void delete(String client, int empId) throws Exception;
    public EmployeeDo get(String client, int emplId) throws Exception;
}