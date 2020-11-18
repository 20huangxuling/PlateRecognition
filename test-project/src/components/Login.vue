<template>
  <div>
    <div class="background"></div>
    <el-card shadow="always" style="margin:0 auto;width:80%;margin-top:38.2%;">
      <span style="float:left;color:#409EFF">用户登陆</span>
      <div style="margin-top:15%">
        <el-divider></el-divider>
      </div>
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="50px"
        class="demo-ruleForm"
      >
        <el-form-item prop="identity" style="margin-top:10%">
          <el-input prefix-icon="el-icon-user" v-model="ruleForm.identity" style="width:100%; left:-12.5%"></el-input>
        </el-form-item>
        <el-form-item prop="password" style="margin-top:12%">
          <div style="position:relative">
            <el-input prefix-icon="el-icon-key" :type="[ hidePassword ? 'password' : 'text' ]" v-model="ruleForm.password" style="width:100%; left:-12.5%"></el-input>
            <i class="el-icon-view" style="position:absolute; right:17%; top:32.5%" @click="hidePassword = !hidePassword"></i> 
          </div>
        </el-form-item>
        <el-form-item style="margin-top:12%;">
          <el-button
            type="primary"
            @click="submitForm('ruleForm')"
            style="position:relative;width:100%;left:-12.5%"
          >登陆</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      hidePassword: true,
      ruleForm: {
        identity: "",
        password: ""
      },
      rules: {
        identity: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            pattern: /^(?=.*[a-zA-Z])(?=.*\d)[^]{6,11}$/,
            message: "长度在6到10个字符，必须包含字母和数字",
            trigger: "blur"
          }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            pattern: /^(?=.*[a-zA-Z])(?=.*\d)[^]{6,11}$/,
            message: "长度在6到10个字符，必须包含字母和数字",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submitForm(loginForm) {
      this.$refs[loginForm].validate(valid => {
        if (valid) {
          let identity = this[loginForm].identity;
          let password = this[loginForm].password;

          this.$store
            .dispatch("login", { identity, password })
            .then(() => {
              this.$router.push("/main/identity");
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          console.log("error submit!!");
        }
      });
    }
  }
};
</script>

<style scoped>
.background {
  position: absolute;
  background-image: linear-gradient(#00BFFF, #87CEFA);
  height: 100%;
  width: 100%;
  z-index: -1;
  left: 0px;
  top: 0px;
}
</style>