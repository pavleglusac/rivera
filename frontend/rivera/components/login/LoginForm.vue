<template>
  <div class="register-photo">
    <div class="form-container">
      <div class="image-holder"></div>
      <form v-on:keyup.enter="login">
        <label style="font-size: 20px; margin: 10px; font-weight: 400">Welcome to</label>
        <img
          class="text-center"
          style="height: 100px; margin-top: -20px"
          src="@/static/rivera_logo_hor.png"
        />
        <div class="form-group">
          <input
            class="form-control"
            v-model="username"
            placeholder="Username"
          />
        </div>
        <div class="form-group">
          <input
            class="form-control"
            type="password"
            v-model="password"
            placeholder="Password"
          />
        </div>
        <div class="form-group">
          <b-button class="prime-btn btn-block" @click="login">Log In</b-button>
        </div>
        <span v-if="nonExistent" class="already" style="font-size: 15px; color: #dc143c">
          Wrong email or password. Please try again.
        </span>
        <span v-else><br></span>
        <a class="already" style="font-size: 15px"
          >You don't have an account?
          <b @click="changeToRegistration" style="cursor: pointer"
            >Register here.</b
          ></a
        >
      </form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      nonExistent: false,
    };
  },
  methods: {
    changeToRegistration() {
      this.$router.push({ path: "/registration" });
    },
    login() {
      var formData = new FormData();
      formData.append("username", this.username);
      formData.append("password", this.password);
      this.$axios
        .post("/api/auth/login", formData)
        .then((resp) => {
          if (resp.data) {
            window.localStorage.setItem("JWT", resp.data["accessToken"]);
            this.$router.push("/");
          } else {
            this.nonExistent = true;
          }
        })
        .catch((err) => {
          this.$bvModal.show("wrong-login-data");
          console.log(err);
        });
    },
  },
};
</script>
<style>
.register-photo {
  padding: 40px 0;
}

.register-photo .image-holder {
  display: table-cell;
  width: auto;
  background: url(https://images.unsplash.com/photo-1508705152659-209db714f16f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1523&q=80);
  background-size: cover;
}

.register-photo .form-container {
  display: table;
  max-width: 900px;
  width: 90%;
  margin: 0 auto;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 3px 5px 0 rgba(0, 0, 0, 0.19);
}

.register-photo form {
  display: table-cell;
  width: 400px;
  background-color: #ffffff;
  padding: 40px 60px;
  color: #505e6c;
}

@media (max-width: 991px) {
  .register-photo form {
    padding: 40px;
  }
}

.register-photo form .form-control {
  background: transparent;
  border: none;
  border-bottom: 1px solid #dfe7f1;
  border-radius: 0;
  box-shadow: none;
  outline: none;
  color: inherit;
  text-indent: 0px;
  height: 40px;
}

.register-photo form .form-check {
  font-size: 13px;
  line-height: 20px;
}

.register-photo form .already {
  display: block;
  text-align: center;
  font-size: 12px;
  color: #6f7a85;
  opacity: 0.9;
  text-decoration: none;
}
</style>