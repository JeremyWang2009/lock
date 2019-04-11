/**
 * Created by shanguang.wang on 2019-04-01
 */
public class StringTest {

    private String str1 = "a";

    private static String str2 = StringTest.class.getName()+"b";

    public static void main(String[] args) {
        StringTest st=new StringTest();
        String a="a";
        String d="a";
        String b=a+"b";
        String c="a"+"b";
        String e="ab";
        System.out.println(a+b+c+d+e);
    }
}
