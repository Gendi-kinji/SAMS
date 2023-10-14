 abstract class LoginAccount {
    abstract void getDetails();
    public static int teacherid;

    public static int studentid;
  
    public static LoginAccount createAccount(String user) {
        if (user.equals("teacher")) {
        return new TeacherAccount(teacherid);
        }
        if (user.equals("student")) {
            
        }
        else {
            System.out.println("Please input correct details");
        }


        return null;
    }
}
