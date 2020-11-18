<template>
  <div class="hello">
    <el-container>
      <div class="header"><NavMenu @logout="logout"></NavMenu></div>
      <div class="main"><router-view></router-view></div>
    </el-container>
  </div>
</template>

<script>
import NavMenu from "@/components/NavMenu";

export default {
  name: "helloWorld",
  components: { NavMenu },
  methods: {
    logout: function() {
      this.$store
        .dispatch("logout")
        .then(() => {
          this.$router.push("/login");
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
