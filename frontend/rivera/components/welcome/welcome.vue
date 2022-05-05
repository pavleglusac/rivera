<template>
  <div>
    <b-card class="text-center mx-auto my-auto card">
      <img src="..\..\static\rivera_logo_ver.png" />

        <form>
        <text>
            WELCOME TO RIVERA
        </text>
        <div class="form-group">
          WELCOME TO RIVERA
          <br><br><br><label>Your username:</label>
          <input
            type="username"
            placeholder="Your username"
            class="form-control form-control-lg"
            v-model="username"
            disabled
          />
        </div>
        <button type="button" @click="cowner"> COWNER </button>
        <button type="button" @click="bowner"> BOWNER </button>
        <button type="button" @click="idk"> IDK </button>
        </form>
    </b-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
			username: "",
      };
  },
  mounted(){
      let that = this;
      console.log(window.localStorage.getItem("JWT"));
      this.$axios.get('/api/auth/get-logged-user',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
        that.username = resp.data.username;
        console.log(window.localStorage.getItem("JWT"));
				console.log(resp.data);
			}).catch((err) => {
				console.log(err);
			});
  },
  methods:{
    cowner(){
      let that = this;
      console.log(window.localStorage.getItem("JWT"));
      this.$axios.get('/api/helloCowner',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
				console.log(resp.data);
			}).catch((err) => {
				console.log(err);
			});
    },
    bowner(){
      let that = this;
      console.log(window.localStorage.getItem("JWT"));
      this.$axios.get('/api/helloBowner',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
				console.log(resp.data);
			}).catch((err) => {
				console.log(err);
			});
    },
    idk(){
      let that = this;
      console.log(window.localStorage.getItem("JWT"));
      this.$axios.get('/api/auth/getRole',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
				console.log(resp.data);
			}).catch((err) => {
				console.log(err);
			});

    },
  }
};
</script>
<style>

html,
body {
  background: linear-gradient(
    90deg,
    var(--light-blue-color) 0%,
    var(--prime-color) 100%
  );
}

.card {
    border-radius: 20;
    filter: drop-shadow(0px 2px 3px);
    padding: 10px;
    margin: 0 auto; 
    float: none;
    margin-bottom: 10px;
    max-width: 40rem;
    padding: 50px;
}

.card img {
    height: 200px;
    margin-bottom: 30px;
}

#login-btn {
    background-color: var(--prime-color);
    border: none;
}

#signup-btn {
    border: 2px solid var(--prime-color);
    color: var(--prime-color);
}

#signup-btn:hover {
    background-color: var(--prime-color);
    color: white;
}

#just-looking {
    border: none;
    background: white;
    color: var(--dark-blue-color);
    font-weight: 500;
}

#just-looking:hover {
    border: 2px solid var(--dark-blue-color);
}

label {
    float: left;
    font-size: 18px;
}

input[type="email"],
input[type="password"] {
    font-size: 18px;
}
</style>