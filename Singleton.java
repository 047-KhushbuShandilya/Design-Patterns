import java.lang.reflect.Constructor;

public class Singleton{
    public static class Employee {
        private volatile static Employee emp; //null // volatile ensures proper read operation for multithreads
          
        private Employee() { // private constructor
            if(emp != null) {
                throw new IllegalStateException("Object already exists"); //handles Reflection api attack
            }
        } 

        public static Employee getInstance() {
            if(emp == null) {
                synchronized (Employee.class) { //thread safety double check
                    if(emp==null) {
                        emp = new Employee(); 
                    }
                }
            }
            return emp;
        }

        public void display() {
            System.out.println("Employee details:" + this.hashCode()); //hashcode gives unique identifier
        }
    }

    public static class ThreadCheck{
        public void test() {
            Runnable task = () -> {
            Employee.getInstance().display();
            };
            
            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);
            Thread t3 = new Thread(task);

            t1.start();
            t2.start();
            t3.start();

            try {
                t1.join();
                t2.join();
                t3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void main(String []args) throws Exception{
        Employee e1= Employee.getInstance();
        e1.display();
        Employee e2= null;

        ThreadCheck checker = new ThreadCheck();
        checker.test();

        // Reflection Attack
        // Constructor<Employee> cons = Employee.class.getDeclaredConstructor();
        // cons.setAccessible(true);
        // e2=cons.newInstance();

        // e2.display();
    }

}
