<template>
<div class="container">
  <div class="row mt-5">
    <div class="col-12 text-center">
      <img id="logo" src="@/static/rivera_logo_ver.png" />
      <h3 class="mt-1">Account successfully verified.</h3>
      <div class="divider"></div>
      <b-button class="get-started mt-5" @click="changeToLogin">Login</b-button>
    </div>
  </div> 
</div>
</template>

<script>

export default {
  components: { },
  mounted() {
      console.log("avliasf");
      this.$axios
        .post("/api/activate-client?username=" + this.$route.params.clientValidation)
        .then((resp) => {
          console.log("avliasf");
          console.log(resp.data);
        })
        .catch((err) => {
          console.log(err);
        });
  },
  methods: {
    changeToLogin() {
      this.$router.push({ path: "/login" });
    },
  }
}

</script>

<style scoped>
.get-started {
  background: linear-gradient(
    90deg,
    var(--light-blue-color) 0%,
    var(--prime-color) 100%
  );
  border: none;
  font-size: 23px;
}
.divider {
  height: 10px;
  width: 150px;
  background: linear-gradient(
    90deg,
    var(--light-blue-color) 0%,
    var(--prime-color) 100%
  );
  margin: 16px auto;
}
#logo {
  width: 20rem;
}
</style>