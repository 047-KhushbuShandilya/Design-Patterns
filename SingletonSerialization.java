import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SingletonSerialization {
    public static class Employee implements Serializable{
        private volatile static Employee emp; //null
          
        private Employee() { // private constructors
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

        // will be called during deserialization and will return class instance
        protected Object readResolve() {
            return getInstance();
        }
    }

    
    
    public static void main(String []args) {
        Employee e1= Employee.getInstance();
        e1.display();
        
        try {
            // serialization
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("emp.json"));
            out.writeObject(e1);
            out.close();

            // Deserialization
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("emp.json"));
            Employee e2 = (Employee) in.readObject();
            in.close();

            System.out.println("e1 hash-code: " + e1.hashCode());
            System.out.println("e2 hash-code: " + e2.hashCode());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
