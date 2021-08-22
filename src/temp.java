import java.io.*;

public class temp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        init();
    }
    ///asfaf
    public static void init() throws IOException, ClassNotFoundException {
        File file = new File(StudentConstant.FILE);
        if (file.length() == 0) {
            return;
        }
        //
        //
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file))) {
            Student s = null;
            while ((s = (Student) oi.readObject()) != null) {
                System.out.println(s);
            }
        } catch (EOFException ignore) {

        }
    }
}
