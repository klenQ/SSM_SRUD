<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kalen
  Date: 2021/11/10
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
  <title>员工列表</title>
  <%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
  %>

  <%--    不以斜线开始的相对路径, 以当前资源都路径为基准, 经常出问题--%>
  <%--    以斜线开始的相对路径, 找资源, 以服务器的路径为标准(https://localhost:3306), 需要加上项目名--%>
  <%--  https://localhost:3306/crud/  --%>
  <script src="${APP_PATH}/static/js/jquery-1.12.4.min.js"></script>
  <link rel="stylesheet" href="${APP_PATH}/static/bootstrap-3.4.1-dist/css/bootstrap.min.css">
  <script src="${APP_PATH}/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
</head>
<body>
<%--搭建显示页面--%>

<div class="container">
  <%--        标题--%>
  <div class="row">
    <div class="col-md-12">
      <h1>SSM-CURD</h1>
    </div>
  </div>
  <%--    按钮--%>
  <div class="row">
    <div class="col-md-4 col-md-offset-8">
      <button class="btn btn-primary">新增</button>
      <button class="btn btn-danger">删除</button>
    </div>

  </div>
  <%--    表格数据--%>
  <div class="row">
    <div class="col-md-12">
      <table class="table table-hover" id="emps_table">
        <thead>
          <tr>
            <th>#</th>
            <th>empName</th>
            <th>gender</th>
            <th>email</th>
            <th>deptName</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>

        </tbody>


      </table>
    </div>
  </div>
  <%--    分页信息--%>
  <div class="row">
    <%--            显示分页文字信息--%>
    <div class="col-md-6" id="page_info_area">


    </div>
    <%--            分页条信息--%>
    <div class="col-md-6" id="page_nav_area">

    </div>
  </div>

</div>
<script type="text/javascript">
  //页面加载完成后, 直接发送ajax请求
  $(function (){
    to_page(1);
  });

  function to_page(pn){
    $.ajax({
      url:"${APP_PATH}/emps",
      data:"pn="+pn,
      type:"get",
      success:function (result){
        // console.log(result);
        //解析并显示分页信息
        build_emps_table(result);

        build_page_info(result);

        build_page_nav(result);
      }
    });
  }


  function build_emps_table(result){
    //清空
    $("#emps_table tbody").empty();

    var emps = result.extend.pageInfo.list;
    $.each(emps, function (index, item){
      // alert(item.empName);
      //原生dom
      // const tdElement = document.createElement("td");
      // const trElement = document.createElement("tr");
      // tdElement.innerText = item.empName;
      // trElement.appendChild(tdElement);
      // const tbodyElement = document.getElementsByTagName("tbody")[0];
      // tbodyElement.appendChild(trElement);

      var empIdTd = $("<td></td>").append(item.empId);
      var empNameTd = $("<td></td>").append(item.empName);
      var gender = item.gender=='M'?'男':'女';
      var empGenderTd = $("<td></td>").append(gender);
      var empEmailTd = $("<td></td>").append(item.email);
      var empDeptTd = $("<td></td>").append(item.department.deptName);
      // <button class="btn btn-primary btn-sm">
      //   <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
      //   编辑
      // </button>
      var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                        .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
      // <button class="btn btn-danger btn-sm">
      //   <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
      //   删除
      // </button>
      var deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
              .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("编辑");

      var btnTd = $("<td></td>").append(editBtn).append(" ").append(deleteBtn);
      $("<tr></tr>").append(empIdTd)
              .append(empNameTd)
              .append(empGenderTd)
              .append(empEmailTd)
              .append(empDeptTd)
              .append(btnTd)
              .appendTo($("#emps_table tbody"));


    });
  }
  //解析显示分页信息
  function build_page_info(result){
    $("#page_info_area").empty();
    $("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页, 总"+result.extend.pageInfo.pages+"页, 总记录"+result.extend.pageInfo.total+"条记录")
  }


  //解析显示分页条
  function build_page_nav(result){

    $("#page_nav_area").empty();

    var ul = $("<ul></ul>").addClass("pagination");
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"));
    var prevPageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

    if(result.extend.pageInfo.hasPreviousPage == false){
      firstPageLi.addClass("disabled");
      prevPageLi.addClass("disabled");
    }else{
      firstPageLi.click(function (){
        to_page(1);
      });

      prevPageLi.click(function(){
        to_page(result.extend.pageInfo.pageNum-1)
      });
    }



    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页"));

    if(result.extend.pageInfo.hasNextPage == false){
      nextPageLi.addClass("disabled");
      lastPageLi.addClass("disabled");
    }else{
      nextPageLi.click(function(){
        to_page(result.extend.pageInfo.pageNum+1)
      });
      lastPageLi.click(function (){
        to_page(result.extend.pageInfo.pages);
      });
    }




    ul.append(firstPageLi).append(prevPageLi);

    $.each(result.extend.pageInfo.navigatepageNums,function (index,item){

      var numLi = $("<li></li>").append($("<a></a>").append(item));
      if(result.extend.pageInfo.pageNum == item){
        numLi.addClass("active");
      }
      numLi.click(function (){
        to_page(item);
      });
      ul.append(numLi);
    });

    ul.append(nextPageLi).append(lastPageLi);

    var navEle = $("<nav></nav>").append(ul);
    navEle.appendTo("#page_nav_area");
  }

</script>

</body>
</html>
