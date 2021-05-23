import com.atbj.crowd.domain.Admin;
import com.atbj.crowd.mapper.AdminMapper;
import com.atbj.crowd.service.api.AdminService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//在类上标记必要的注解，Spring整合Junit
@RunWith(SpringJUnit4ClassRunner.class)
//使用spring-text 读取配置文件，创建容器对象
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml",
        "classpath:spring-persist-tx.xml"})

public class Test {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminService adminService;

    @org.junit.Test
    public void testAdminService(){
        Admin admin = new Admin(null, "jerry", "123456",
                "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }


    @org.junit.Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @org.junit.Test
    public void testMapper(){
        Admin admin = new Admin(null,"tom","123321",
                "汤姆","tom@qq.com",null);
        int count = adminMapper.insert(admin);
        System.out.println("受影响的行数→_→"+count);
    }

    @org.junit.Test
    public void textint(){
        int i = 3;
        i = ++i;
        int j = i++ +4;
        int a = j*i;
        System.out.println("222222222222222");
        System.out.println(a);
    }


    @org.junit.Test
    public void texx(){
        an a = new cat();
        a.run();
        System.out.println(22222222);
    }
}
