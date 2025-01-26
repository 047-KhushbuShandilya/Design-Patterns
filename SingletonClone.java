public class SingletonClone {
    public static class Employee implements Cloneable{
        private volatile static Employee emp; //null
          
        private Employee() {} // private constructors
        

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


        @Override
        protected Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Singleton instances cannot be cloned");
        }
    }

    
    
    public static void main(String []args) {
        Employee e1= Employee.getInstance();
        e1.display();
        
        try {
            Employee e2 = (Employee) e1.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
