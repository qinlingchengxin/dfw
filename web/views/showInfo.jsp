<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-5-27
  Time: 上午12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <link href="${rootPath}/css/frame_table.css" type="text/css" rel="stylesheet"/>
    <style type="text/css">
        #tab {
            border-left: 1px solid #b5d6e6;
            border-top: 1px solid #b5d6e6;
            font-size: 12px;
        }

        #tab td {
            border-right: 1px solid #b5d6e6;
            border-bottom: 1px solid #b5d6e6;
            padding: 3px 4px 3px 4px;
            max-width: 120px;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }

        #tab th {
            border-right: 1px solid #b5d6e6;
            border-bottom: 1px solid #b5d6e6;
            width: 120px;
            background-color: #0081C1;
            color: white;
            font-weight: bold;
            font-size: 16px;
        }

        #content {
            width: 100%;
            height: 95%;
            overflow: auto;
        }
    </style>


    <script type="text/javascript">
        $(function () {
            $("#tab tr:gt(0):odd").css("background-color", "#CED3D7");
        });

        //第一页
        function firstPage() {
            if ($("#currentPage").text() != "1") {
                var url = "queryUsersByPage.do";
                pageing(1, url);
            }
        }

        //上一页
        function prePage() {
            if ($("#currentPage").text() != "1") {
                var curPage = parseInt($("#currentPage").text()) - 1;
                var url = "queryUsersByPage.do";
                pageing(curPage, url);
            }
        }
        //下一页
        function nextPage() {
            if ($("#currentPage").text() != $("#totalPage").text()) {
                var curPage = parseInt($("#currentPage").text()) + 1;
                var url = "queryUsersByPage.do";
                pageing(curPage, url);
            }
        }

        //最后一页
        function lastPage() {
            if ($("#currentPage").text() != $("#totalPage").text()) {
                var lastPage = $("#totalPage").text();
                var url = "queryUsersByPage.do";
                pageing(lastPage, url);
            }
        }

        //跳转到第几页
        function btnGo() {
            var go_page = $("#go_page").val();
            var totalPage = $("#totalPage").text();
            if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
                if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                    var url = "queryUsersByPage.do";
                    pageing(go_page, url);
                }
            }
        }

        function page_callback(data) {
            $("#tab tr:gt(0)").remove();
            var tabTag = $("#tab");
            var users = data.users;
            var trTag;
            for (var i = 0, j = users.length; i < j; i++) {
                trTag = ' <tr>'
                        + '<td>' + users[i].MC + '</td>'
                        + '<td>' + users[i].id + '</td>'
                        + '<td>' + users[i].typ + '</td>'
                        + '<td>' + users[i].account + '</td>'
                        + '<td>' + users[i].nickname + '</td>'
                        + '<td>' + users[i].school + '</td>'
                        + '<td>' + users[i].cls + '</td>'
                        + '<td>' + users[i].sex + '</td>'
                        + '<td>' + users[i].age + '</td>'
                        + '<td>' + users[i].bestScore + '</td>'
                        + '<td>' + users[i].lastScore + '</td>'
                        + '<td>' + users[i].allScore + '</td>'
                        + '<td>' + users[i].name + '</td>'
                        + '<td>' + users[i].tel + '</td>'
                        + '<td>' + users[i].pro1 + '</td>'
                        + '<td>' + users[i].pro2 + '</td>'
                        + '<td>' + users[i].regTime + '</td>'
                        + '<td>' + users[i].city + '</td>'
                        + '<td>' + users[i].grade + '</td>'
                        + '</tr>';
                tabTag.append($(trTag));
            }

            $("#tab tr:gt(0):odd").css("background-color", "#CED3D7");
        }

        //分页
        function pageing(pageNum, url) {
            $.ajax({
                url: url,
                type: "POST",
                data: {"curPage": pageNum},
                dataType: "json",
                success: function (data) {
                    $("#currentPage").text(pageNum);
                    page_callback(data);
                }
            });
        }

        function exportReport() {
            window.open("exportReport.do");
        }
    </script>
</head>
<body>
<div id="content">
    <table id="tab" cellspacing=0 cellpadding=5>
        <caption style="font-size: 20px; font-weight: bold;">用户列表</caption>
        <tr>
            <th>名次</th>
            <th>ID</th>
            <th>类别</th>
            <th>账号</th>
            <th>昵称</th>
            <th>学校</th>
            <th>班级</th>
            <th>性别</th>
            <th>年龄</th>
            <th>最好成绩</th>
            <th>当前成绩</th>
            <th>总成绩</th>
            <th>真实姓名</th>
            <th>电话</th>
            <th>第一题</th>
            <th>第二题</th>
            <th>注册时间</th>
            <th>城市</th>
            <th>级部</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.MC}</td>
                <td>${user.id}</td>
                <td>${user.typ}</td>
                <td>${user.account}</td>
                <td>${user.nickname}</td>
                <td>${user.school}</td>
                <td>${user.cls}</td>
                <td>${user.sex}</td>
                <td>${user.age}</td>
                <td>${user.bestScore}</td>
                <td>${user.lastScore}</td>
                <td>${user.allScore}</td>
                <td>${user.name}</td>
                <td>${user.tel}</td>
                <td>${user.pro1}</td>
                <td>${user.pro2}</td>
                <td>${user.regTime}</td>
                <td>${user.city}</td>
                <td>${user.grade}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${userCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
        </span>
        <span class="footer_text_align_two">
            <button class="footer_text_two" onclick="exportReport();">导出到Excel文件中</button>
            <button class="footer_text_two" onclick="firstPage();">首页</button>
            <button class="footer_text_two" onclick="prePage();">上一页</button>
            <button class="footer_text_two" onclick="nextPage();">下一页</button>
            <button class="footer_text_two" onclick="lastPage();">尾页</button>
            转到第<input id="go_page" type="text" size="8"/>页
            <button class="footer_text_two" onclick="btnGo();">跳转</button>
        </span>
</div>
</body>
</html>