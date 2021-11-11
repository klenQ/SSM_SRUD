package com.atguigu.crud.service;

import com.atguigu.crud.bean.DepartmentExample;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Jianyu Qiu (Kalen)
 * @CreateTime: 2021/11/10
 */

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getAll(){
        return employeeMapper.selectByExampleWithDept(null);
    }





    public void saveEmp(Employee employee) {

        employeeMapper.insertSelective(employee);
    }

    public Boolean checkUser(String empName) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(employeeExample);
        return count==0;

    }

    public Employee getEmp(Integer id) {

        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;

    }

    public int update(Employee employee){
        int update = employeeMapper.updateByPrimaryKeySelective(employee);
        return update;
    }

    public int deleteEmp(Integer empId) {
        int i = employeeMapper.deleteByPrimaryKey(empId);
        return i;
    }

    public void deleteBatch(List<Integer> del_ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(del_ids);
        employeeMapper.deleteByExample(example);
    }
}
