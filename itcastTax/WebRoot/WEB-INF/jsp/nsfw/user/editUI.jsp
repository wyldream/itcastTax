<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/common/header.jsp"%>
    <title>用户管理</title>
    <!-- 引入日期组件 -->
    <script type="text/javascript" src="${basePath }js/datepicker/WdatePicker.js"></script>
    <script type="text/javascript">
      	//账户验证
      	function doVerify(){
      		var account = $("#account").val();//得到文本框中的值
      		if(account != ""){
      			$.ajax({
      				url:"${basePath}nsfw/user_verifyAccount.action",//ajax要提交到的地方
      				
      				data:{//提交的数据
      					"user.account":account,
      					"user.id":"${user.id}"
      				},
      				type:"post",
      				async:false,//非异步
      				success:function(msg){//对返回结果的处理
      					if("true" != msg){//如果ajax执行结果不为true，则校验失败
      						alert("账号已经存在");
      						//定焦
      						$("#account").focus();
      						vResult = false;
      					}else{
      						vResult = true;
      					}
      				}
      			});
      		}
      	}
      	    	//校验帐号唯一
    	/* function doVerify(){
    		//1、获取帐号
    		var account = $("#account").val();
    		alert(account != "");
    		if(account != ""){
    			//2、校验 
    			$.ajax({
    				url:"${basePath}nsfw/user_verifyAccount.action",
    				data: {"user.account": account, "user.id": "${user.id}"},
    				type: "post",
    				async: false,//非异步
    				success: function(msg){
    					if("true" != msg){
    						//帐号已经存在
    						alert("帐号已经存在。请使用其它帐号！");
    						//定焦
    						$("#account").focus();
    						vResult = false;
    					} else {
    						vResult = true;
    					}
    				}
    			});
    		}
    	} */
    </script>
</head>
<body class="rightBody">
<form id="form" name="form" action="${basePath }nsfw/user_edit.action" method="post" enctype="multipart/form-data">
    <div class="p_d_1">
        <div class="p_d_1_1">
            <div class="content_info">
    <div class="c_crumbs"><div><b></b><strong>用户管理</strong>&nbsp;-&nbsp;编辑用户</div></div>
    <div class="tableH2">编辑用户</div>
    <table id="baseInfo" width="100%" align="center" class="list" border="0" cellpadding="0" cellspacing="0"  >
        <tr>
            <td class="tdBg" width="200px">所属部门：</td>
            <td><s:select name="user.dept" list="#{'部门A':'部门A','部门B':'部门B' }"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">头像：</td>
            <td>
                <s:if test="%{user.headImg != null && user.headImg != ''}">
                    <img src="${basePath }upload/<s:property value='user.headImg'/>" width="100" height="100"/>
                    <!-- 在有值的情况下将值传递给后台，将值传递给后台 -->
                    <s:hidden name="user.headImg"/>
                </s:if>
                <input type="file" name="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">用户名：</td>
            <td><s:textfield name="user.name"/> </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">帐号：</td>
            <td><s:textfield name="user.account" id="account" onchange="doVerify()"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">密码：</td>
            <td><s:textfield name="user.password"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">性别：</td>
            <td><s:radio list="#{'true':'男','false':'女'}" name="user.gender"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">角色：</td>
            <td>
            	<s:checkboxlist list="#roleList" name="userRoleIds" listKey="roleId" listValue="name"></s:checkboxlist>
            </td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">电子邮箱：</td>
            <td><s:textfield name="user.email"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">手机号：</td>
            <td><s:textfield name="user.mobile"/></td>
        </tr>        
        <tr>
            <td class="tdBg" width="200px">生日：</td>
            <td>
            <s:textfield id="birthday" name="user.birthday" readonly="true" onfocus="WdatePicker({'skin':'whyGreen','dateFmt':'yyyy-MM-dd'});" > <!-- 调用日期组件，传递参数 -->
            	<s:param name="value">
            		<s:date name="user.birthday" format="yyyy-MM-dd"/>
            	</s:param>
            </s:textfield>
            </td>
        </tr>
		<tr>
            <td class="tdBg" width="200px">状态：</td>
            <td><s:radio list="#{'1':'有效','0':'无效'}" name="user.state"/></td>
        </tr>
        <tr>
            <td class="tdBg" width="200px">备注：</td>
            <td><s:textarea name="user.memo" cols="75" rows="3"/></td>
        </tr>
    </table>
    <s:hidden name="user.id"/>
    <div class="tc mt20">
        <input type="submit" class="btnB2" value="保存" />
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button"  onclick="javascript:history.go(-1)" class="btnB2" value="返回" />
    </div>
    </div></div></div>
</form>
</body>
</html>