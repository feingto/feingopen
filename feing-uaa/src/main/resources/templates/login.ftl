<#assign ctx = rc.contextPath />
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Feing Login</title>
  <link rel="shortcut icon" href="${ctx}/favicon.ico">
  <link rel="stylesheet" href="${ctx}/css/main.css">
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/js/ltie.js"></script>
  <![endif]-->
</head>
<body>
<div id="app">
  <div class="login-container">
    <form class="login-form" action="${ctx}/login" method="post" ref="form">
      <h3 class="title">Feing 登录</h3>
      <div class="lf-text-field" :class="{'focus-state': usernameFocused == true, error: errors.first('username')}">
        <i class="lf-text-field-icon glyphicon glyphicon-user"></i>
        <div class="lf-text-field-content">
          <div class="lf-text-field-hint" v-show="!loginForm.username">用户名</div>
          <input class="lf-text-field-input"
                 type="text"
                 name="username"
                 v-model="loginForm.username"
                 @focus="usernameFocused = true"
                 @blur="usernameFocused = false"
                 @keyup.enter.native="handleLogin"
                 data-vv-as="用户名"
                 v-validate="{ required: true, regex: /^[a-zA-Z0-9_-]{4,16}$/ }"/>
          <div>
            <hr class="lf-text-field-line">
            <hr class="lf-text-field-focus-line" :class="{focus: usernameFocused, error: errors.has('username')}">
          </div>
          <div class="lf-text-field-help" v-show="errors.has('username')">
            {{ errors.first('username') }}
          </div>
        </div>
      </div>
      <div class="lf-text-field" :class="{'focus-state': passwordFocused == true, error: errors.first('password')}">
        <i class="lf-text-field-icon glyphicon glyphicon-lock"></i>
        <div class="lf-text-field-content">
          <div class="lf-text-field-hint" v-show="!loginForm.password">密码</div>
          <input class="lf-text-field-input"
                 type="password"
                 name="password"
                 v-model="loginForm.password"
                 @focus="passwordFocused = true"
                 @blur="passwordFocused = false"
                 @keyup.enter.native="handleLogin"
                 data-vv-as="密码"
                 v-validate="{ required: true, min: 6, max: 20 }"
                 autocomplete="off"/>
          <div>
            <hr class="lf-text-field-line">
            <hr class="lf-text-field-focus-line" :class="{focus: passwordFocused, error: errors.has('password')}">
          </div>
          <div class="lf-text-field-help" v-show="errors.has('password')">
            {{ errors.first('password') }}
          </div>
        </div>
      </div>
      <div class="alert alert-danger" ref="error" v-show="hasError"></div>
    <#--<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
      <button class="lf-raised-button" @click.prevent="handleLogin">登录</button>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/js/main.js"></script>
<script>
  Vue.use(VeeValidate, {locale: 'zh_CN'});
  new Vue({
    el: '#app',
    data() {
      return {
        usernameFocused: false,
        passwordFocused: false,
        hasError: false,
        loginForm: {
          username: null,
          password: null
        }
      };
    },
    mounted() {
      this.getError();
    },
    methods: {
      handleLogin() {
        this.$validator.validateAll().then(result => {
          if (result) {
            this.$refs.form.submit();
          }
        });
      },
      getError() {
        if ('${(RequestParameters["authentication_error"])!}') {
          this.$refs.error.innerHTML = '用户名/密码验证失败.';
          this.hasError = true;
        } else if ('${(RequestParameters["authorization_error"])!}') {
          this.$refs.error.innerHTML = '不允许访问该资源.';
          this.hasError = true;
        }
        setTimeout(() => this.hasError = false, 3000);
      }
    }
  });
</script>
</body>
</html>
