<template>
  <div class="regForm">
    <b-card class="text-center mx-auto my-auto card">
      <img src="..\..\static\rivera_logo_ver.png" />
      <form>
        <div class="form-group">
          <label>Username</label>
          <input
            type="username"
            placeholder="Enter your username"
            class="form-control form-control-lg"
            v-model="username"
          />
        </div>
        <div class="form-group">
          <label>Password</label>
          <input
            type="password"
            placeholder="Enter your password"
            class="form-control form-control-lg"
            v-model="password"
          />
        </div>
        <b-form-invalid-feedback>
          Wrong email or password. Please try again.
        </b-form-invalid-feedback>

        <b-button block id="login-btn" variant="primary" @click="login()">Log In</b-button>
        <b-button block id="signup-btn" variant="outline-primary" @click="changeToRegistration()">Create an account</b-button>
        <b-button block id="just-looking">Just looking...</b-button>
      </form>
    </b-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
			username: "",
			password: ""
      };
  },
  methods:{
    changeToRegistration(){
      this.$router.push({path: '/registration'});
    },
    login(){      
      var formData = new FormData();
      formData.append("username",this.username);
      formData.append("password",this.password);
			this.$axios.post('/api/auth/login', formData).then((resp) => {
				if(resp.data){
          window.localStorage.setItem("JWT", resp.data['accessToken']);
          this.$router.push("welcome");
        }
			}).catch((err) => {
				console.log(err);
			});
    }
  }
};
</script>