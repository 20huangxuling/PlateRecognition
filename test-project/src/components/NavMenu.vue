<template>
  <div>
    <div class="mobile-bar">
      <el-button
        type="primary"
        class="my-button menu-button"
        :icon="functionButtonIcon"
        @click="functionMenuShowed = !functionMenuShowed"
      ></el-button>
      <el-dropdown class="customer-dropdown" trigger="click" @command="handleCommand">
        <el-button type="primary" class="my-button customer-button" icon="el-icon-s-custom"></el-button>
        <el-dropdown-menu slot="dropdown" style="position:absolute; width: 30%">
          <el-dropdown-item icon="el-icon-setting" command="settings">设置</el-dropdown-item>
          <el-dropdown-item icon="el-icon-error" command="logout">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <transition name="el-zoom-in-top">
      <div v-show="functionMenuShowed" class="function-menu">
        <el-menu default-active="this.$router.path" router>
          <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.path">
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </el-menu>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "navMenu",
  data() {
    return {
      activeIndex: "identity",
      activeIndex2: "",
      functionMenuShowed: false,
      functionButtonIcon: "el-icon-s-unfold",
      navList: [
        { path: "/main/identity", title: "识别" },
        { path: "/main/download", title: "下载" }
      ]
    };
  },
  watch: {
    functionMenuShowed: function(newVal, oldVal) {
      if (newVal === false) {
        this.functionButtonIcon = "el-icon-s-unfold";
      } else {
        this.functionButtonIcon = "el-icon-s-fold";
      }
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      switch (key) {
        case "identity":
          this.$router.push("/main/identity");
          break;
        case "userInfo":
          this.$router.push("/main/userInfo");
          break;
        case "logout":
          this.$emit("logout");
          break;
      }
      console.log(key, keyPath);
    },
    handleCommand(command) {
      console.log(command);
      switch (command) {
        case "settings":
          break;
        case "logout":
          this.logout();
          break;
        default:
          break;
      }
    },
    logout() {
      this.$store.dispatch("logout").then(_ => {
        this.$router.push("/login");
      });
    }
  }
};
</script>

<style scoped>
.mobile-bar {
  display: block;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 8%;
  background-color: #fff;
  z-index: 20;
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.25);
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}

.my-button {
  position: relative;
  border: none;
  background-color: transparent;
  color: black;
  font-size: 35px;
  top: 80%;
}

.menu-button {
  position: absolute;
  color: #666;
  left: 0;
  top: 0;
}

.customer-dropdown {
  position: absolute;
  top: 0;
  right: 0;
}

.customer-button {
  position: relative;
  top: 0;
  left: 0;
  color: #666;
}

.function-menu {
  position: absolute;
  width: 30%;
  left: 0;
  z-index: 999;
}
</style>

<style>
.el-tabs__item {
  padding: 0 20px;
  height: 40px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 17px;
  font-weight: 500;
  color: #303133;
  position: relative;
}
</style>