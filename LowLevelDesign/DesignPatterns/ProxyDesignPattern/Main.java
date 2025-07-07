package LowLevelDesign.DesignPatterns.ProxyDesignPattern;
public class Main {

    public static void Main(String args[]) {
        try {
            EmployeeDao empTableObj = new EmployeeDaoImpl();
            empTableObj.create("USER", new EmployeeDo(1,"BHAVESH"));
            System.out.println("Success created");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
