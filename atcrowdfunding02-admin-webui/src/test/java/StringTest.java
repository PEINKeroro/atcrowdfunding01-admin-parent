import com.atbj.crowd.domain.Admin;
import com.atbj.crowd.util.MD5CodeUtil;

public class StringTest {

    @org.junit.Test
    public void testMD5(){
        String md5 = MD5CodeUtil.toMD5("123321");
        System.out.println(md5);
    }
}
