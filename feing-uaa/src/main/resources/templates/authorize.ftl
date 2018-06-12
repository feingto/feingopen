<#assign ctx = rc.contextPath />
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>用户授权</title>
  <link rel="shortcut icon" href="${ctx}/favicon.ico">
  <link rel="stylesheet" href="${ctx}/css/authorize.css">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/js/ltie.js"></script>
  <![endif]-->
</head>
<body>
<div class="row" style="margin-top: 50px">
  <div class="col-sm-8 col-sm-offset-2">
    <div class="panel panel-primary" style="border: 0">
      <div class="panel-heading">确认授权</div>
      <div class="panel-body">
        <form id="confirmationForm" name="confirmationForm" action="${ctx}/oauth/authorize" method="post">
          <p>
            是否允许 "${authorizationRequest.clientId}" 使用你的账号访问 "${authorizationRequest.redirectUri}"，
            并允许以下操作：<#--${authorizationRequest.scope?join(", ")}-->
          </p>
          <#if scopes??>
              <#list scopes?keys as key>
                <div class="checkbox">
                  <label class="control-label">${key}</label>
                  <label><input type="radio" name="scope.${key}" value="true" <#if scopes[key]=='true'>checked</#if>/><i class="fa fa-unlock"></i>许可</label>
                  <label><input type="radio" name="scope.${key}" value="false" <#if scopes[key]=='false'>checked</#if>/><i class="fa fa-lock"></i>拒绝</label>
                </div>
                <div class="hr-line-dashed"></div>
              </#list>
          </#if>
          <div class="form-group text-center">
            <input type="hidden" name="user_oauth_approval" value="true"/>
          <#--<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
            <button class="btn btn-primary">确认并授权</button>
          <#--<button type="button" class="btn btn-primary" onclick="doDeny()">取消</button>-->
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/js/authorize.js"></script>
<script type="text/javascript">
  function doDeny() {
    $('input[name="user_oauth_approval"]').val(false);
    $('#confirmationForm').submit();
  }
</script>
</body>
</html>
