import java.io.*;
import java.util.*;

public class StudentUtils {
    // 存放学生对象的集合;
    private static List<Student> students = new ArrayList<>();
    // 键盘录入
    private final static Scanner SCANNER = new Scanner(System.in);

    //初始化程序
    static {
        File file = new File(StudentConstant.FILE);
        if (file.exists() && file.length() > 0) {
            try {
                init(file);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动程序执行方法;
     */
    public static void doWork() throws IOException {
        showHandle();
        int chooseId;
        chooseId = getInt("请输入你的选择：", StudentConstant.INPUT_CHOOSE_TYPE);
        switch (chooseId) {
            case 1:
                addStudent();
                break;
            case 2:
                removeStudent();
                break;
            case 3:
                modifyStudent();
                break;
            case 4:
                lookStudent();
                break;
            case 5: {
                System.out.println("谢谢使用！");
                store();
                System.exit(0); //JVM退出
                break;
            }
            default:
                break;
        }
    }

    /**
     * 显示主菜单
     */
    private static void showHandle() {
        System.out.println("--------".repeat(10));
        System.out.println("**********根据提示选择对应的功能*********");
        System.out.println("1. \t 新增加学生信息!");
        System.out.println("2. \t 根据学生编号删除学生信息!");
        System.out.println("3. \t 根据学生编号修改学生信息!");
        System.out.println("4. \t 查看所有学生信息!");
        System.out.println("5. \t 退出程序!");
        System.out.println("--------".repeat(10));
    }

    /**
     * 添加学生对象
     */
    public static void addStudent() {
        //输入学号
        int id;
        while (true) {
            boolean flag = true;
            id = getInt("请输入学生学号：", StudentConstant.INPUT_ID_TYPE);
            for (Student stu : students) {
                if (stu.getId() == id) {
                    System.err.println("学号已被使用，请重新输入！");
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        //输入姓名
        System.out.println("请输入学生姓名：");
        String name = SCANNER.next();
        //输入年龄
        int age = getInt("请输入学生年龄：", StudentConstant.INPUT_AGE_TYPE);
        //输入成绩
        int score = getInt("请输入学生成绩：", "成绩");
        //创建学生对象
        Student student = new Student(id, name, age, score);
        students.add(student);
        System.out.println("添加学生成功");
        System.out.println("请按任意键返回主菜单...");
        SCANNER.nextLine();
        SCANNER.nextLine();
    }

    /**
     * 删除学生对象
     * 1. 判断编号是否存在于集合当中
     * 2. 存在,则删除
     * 3. 不存在,抛出异常;
     */
    public static void removeStudent() {
        if (students.isEmpty()) {
            System.err.println("没有可以删除的学生对象！");
            System.out.println("请按任意键返回主菜单...");
            SCANNER.nextLine();
            SCANNER.nextLine();
            return;
        }
        System.out.println("所有的学生信息是: ");
        for (Student student : students) {
            System.out.println(student);
        }
        // 参数校验
        int id;
        while (true) {
            id = getInt("请输入要删除的学生学号：", StudentConstant.INPUT_ID_TYPE);
            if (checkStudent(id) == -1) {
                System.err.println("没有此id的学生对象，请重新输入");
            } else {
                break;
            }
        }
        // 对象存在,则删除
        students.remove(checkStudent(id));
        System.out.println("删除学生对象成功");
        System.out.println("请按任意键返回主菜单...");
        SCANNER.nextLine();
        SCANNER.nextLine();
    }

    /**
     * 修改学生对象
     */
    public static void modifyStudent() {
        if (students.isEmpty()) {
            System.err.println("没有可以删除的学生对象！");
            System.out.println("请按任意键继续...");
            SCANNER.nextLine();
            SCANNER.nextLine();
            return;
        }
        int id;
        int index;
        while (true) {
            id = getInt("请输入要修改学生的id：", StudentConstant.INPUT_ID_TYPE);
            index = checkStudent(id);
            if (index == -1) {
                System.err.println("没有这个学生，请重新输入！");
            } else {
                break;
            }
        }
        System.out.println("找到该学生");
        System.out.println(students.get(index));
        students.remove(index);
        //输入学号
        while (true) {
            boolean flag = true;
            id = getInt("请输入新的学生学号：", StudentConstant.INPUT_ID_TYPE);
            for (Student stu : students) {
                if (stu.getId() == id) {
                    System.err.println("学号已被使用，请重新输入！");
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        //输入姓名
        System.out.println("请输入新的学生姓名：");
        String name = SCANNER.next();
        //输入年龄
        int age = getInt("请输入新的学生年龄：", StudentConstant.INPUT_AGE_TYPE);
        //输入成绩
        int score = getInt("请输入新的学生成绩：", StudentConstant.INPUT_SCORE_TYPE);
        //创建学生对象
        Student s = new Student(id, name, age, score);
        students.add(index, s);
        System.out.println("修改学生信息成功");
        System.out.println("请按任意键返回主菜单...");
        SCANNER.nextLine();
        SCANNER.nextLine();
    }

    /**
     * 查看所有学生信息
     */
    public static void lookStudent() {
        if (students.isEmpty()) {
            System.err.println("没有任何学生对象");
            System.out.println("请按任意键继续...");
            SCANNER.nextLine();
            SCANNER.nextLine();
            return;
        }
        System.out.println("所有的学生信息是: ");
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("请按任意键返回主菜单...");
        SCANNER.nextLine();
        SCANNER.nextLine();
    }

    /**
     * 判断学生编号对应的对象是否存在于集合当中
     *
     * @param id 学生学号
     * @return 返回Student对象
     */
    private static int checkStudent(int id) {
        // 判断出,这个id在学生集合当中;
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                return i;
            }
        }
        return index;
    }

    /**
     * 检查输入是否为数字
     *
     * @param s 输入字符串
     * @return 判断字符串是否由数字组成
     */
    private static boolean checkInputInt(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * 输入合法int值
     *
     * @param inputMsg  提示信息
     * @param inputType 输入类型
     * @return int
     */
    private static int getInt(String inputMsg, String inputType) {
        System.out.println(inputMsg);
        String n = SCANNER.next();
        if (!checkInputInt(n)) {
            System.err.println("请输入正确的数字！");
            return getInt(inputMsg, inputType);
        }
        int x = Integer.parseInt(n);
        if (("年龄".equals(inputType)) && (x < StudentConstant.MIN_AGE || x > StudentConstant.MAX_AGE)) {
            System.err.println("请输入正确的年龄范围(0~130)！");
            return getInt(inputMsg, inputType);
        }
        if (("成绩".equals(inputType)) && (x < StudentConstant.MIN_SCORE || x > StudentConstant.MAX_SCORE)) {
            System.err.println("请输入正确的成绩范围(0~100)！");
            return getInt(inputMsg, inputType);
        }
        if (("选项".equals(inputType)) && (x < StudentConstant.MIN_CHOOSE || x > StudentConstant.MAX_CHOOSE)) {
            System.err.println("请输入正确的选项范围(1~5)！");
            return getInt(inputMsg, inputType);
        }
        return x;
    }

    /**
     * 存储输入的学生信息
     * @throws IOException 抛出
     */
    private static void store() throws IOException {
        if(students.isEmpty()) {
            return;
        }
        Collections.sort(students);
        ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(StudentConstant.FILE));
        oo.writeObject(students);
        oo.close();
        System.out.println("保存数据成功！！！");
    }

    /**
     * 读取学生信息
     * @param file 文件路径
     * @throws IOException IO
     * @throws ClassNotFoundException 错误
     */
    public static void init(File file) throws IOException, ClassNotFoundException  {
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(file))) {
            students = (List<Student>) oi.readObject();
        } catch (EOFException ignore) {

        }
        System.out.println("成功读取 " + students.size() + " 条学生记录！！！");
    }
}