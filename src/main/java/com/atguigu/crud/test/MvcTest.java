package com.atguigu.crud.test;

import com.atguigu.crud.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Arg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * @Description: 使用spring测试模块提供的测试请求功能, 测试crud请求正确性
 * @Author: Jianyu Qiu (Kalen)
 * @CreateTime: 2021/11/10
 */
@RunWith(SpringJUnit4ClassRunner.class) //配置好以后直接AutoWired
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:springMVC.xml"}) //指定spring配置文件位置
public class MvcTest {

    //传入SpringMvc的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟mvc请求, 获取处理结果
    MockMvc mockMvc;

    @Before
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testPage() throws Exception{
        //模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();
        //请求成功以后, 请求域中会有pageInfo, 可以取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("current pageNo"+pi.getPageNum());
        System.out.println("total pageNo"+pi.getPages());
        System.out.println("total dataCount"+pi.getTotal());
        System.out.println("页面需要连续显示的页码");
        int[] nums = pi.getNavigatepageNums();

        for (int i:
             nums) {
            System.out.print(" "+i);
        }
        
        //获取员工数据

        List<Employee> list = pi.getList();
        for (Employee employee :
                list) {
            System.out.println("ID:"+employee.getEmpId()+"==>Name"+employee.getEmpName());

        }

    }

}
