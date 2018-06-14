<#assign ctx = rc.contextPath />
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${httpErrorCode!'400'} - 授权失败</title>
  <link rel="shortcut icon" href="${ctx}/favicon.ico">
  <link rel="stylesheet" href="${ctx}/css/main.css">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script type="text/javascript" src="${ctx}/js/ltie.js"></script>
  <![endif]-->
</head>
<body>
<div class="center-wrapper">
  <div class="center-content text-center">
    <div class="row no-m">
      <div class="col-xs-10 col-xs-offset-2 col-sm-10 col-sm-offset-6 col-md-6 col-md-offset-3">
        <section class="panel bg-white no-b fadeIn animated">
          <div class="p15">
            <div class="pb15">
              <div class="error-number animated bounceIn">${httpErrorCode!'400'}</div>
              <div class="alert alert-danger text-left">
                <ul>
                  <li>${oAuth2ErrorCode!'未知错误'}</li>
                  <li>${message!'授权失败'}</li>
                </ul>
              </div>
            </div>
            <div class="pb15">
              <a href="../" class="btn btn-default btn-outline"><i class="fa fa-home"></i> 返回首页</a>
            </div>
            <p class="text-center">
              Copyright &copy;<span id="year" class="mr5"></span>
              <span>Feing Co.,Ltd. All rights reserved 保留所有权利</span>
            </p>
          </div>
        </section>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
</body>
</html>
