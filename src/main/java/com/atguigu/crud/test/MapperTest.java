package com.atguigu.crud.test;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @Description:
 * @Author: Jianyu Qiu (Kalen)
 * @CreateTime: 2021/11/9
 */
@RunWith(SpringJUnit4ClassRunner.class) //配置好以后直接AutoWired
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //指定spring配置文件位置
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;
    /**
     *
     */
    @Test
    public void testCURD(){
//        //测试部门deptMapper
//        //1. 创建springIOC
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//        //2. 从容器中获取mapper
//        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);

//        可以使用spring单元测试组件

        System.out.println(employeeMapper);

        // 1. 插入几个部门

//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));

        // 2. 插入员工测试
        employeeMapper.insertSelective(new Employee(null,"Jerry","M", "Jerry@atguigu.com",1));

        //批量插入测试, 使用可以执行批量操作的sqlSession
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i <1000; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null,uid,"M",uid+"@atguigu.com",1));
        }
        System.out.println("批量执行完毕");


    }

}
