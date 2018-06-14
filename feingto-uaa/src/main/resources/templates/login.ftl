<#assign ctx = rc.contextPath />
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>登录认证中心</title>
  <link rel="shortcut icon" href="${ctx}/favicon.ico">
  <link rel="stylesheet" href="${ctx}/css/main.css">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/js/ltie.js"></script>
  <![endif]-->
</head>
<body>
<div id="mainWrapper">
  <div class="login-container">
    <div class="login-card">
      <div class="login-form">
        <form action="${ctx}/login" method="post" class="form-horizontal">
          <h1 class="text-center">UAA认证中心</h1>
        <#if RequestParameters.authentication_error??>
          <div class="alert alert-danger">
            <p>用户名/密码验证失败.</p>
          </div>
        </#if>
        <#if RequestParameters.authorization_error??>
          <div class="alert alert-danger">
            <p>不允许访问该资源.</p>
          </div>
        </#if>
        <#if RequestParameters.logout??>
          <div class="alert alert-success">
            <p>已成功注销.</p>
          </div>
        </#if>
          <div class="input-group input-sm">
            <label class="input-group-addon" for="username"><i class="glyphicon glyphicon-user"></i></label>
            <input class="form-control" id="username" name="username" placeholder="输入用户名" required>
          </div>
          <div class="input-group input-sm">
            <label class="input-group-addon" for="password"><i class="glyphicon glyphicon-lock"></i></label>
            <input type="password" class="form-control" id="password" name="password" placeholder="输入密码" required>
          </div>
          <div class="form-group input-sm text-center">
            <input type="submit" class="btn btn-block btn-primary btn-default" value="登录">
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
</body>
</html>
