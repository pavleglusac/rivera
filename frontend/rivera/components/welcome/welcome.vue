<template>
  <div class="login-form">
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
        <button type="button"  class="btn btn-primary"  @click="redirect">Add {{addType}} </button>
        </form>
    <button type='button' @click="test"> TEST </button>
    </b-card>
    
  </div>
</template>
<script>
export default {
  data() {
    return {
			username: "",
			addType: "",
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
      this.$axios.get('/api/auth/getRole',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
                console.log(resp.data);
                if(resp.data=="ROLE_COTTAGE_OWNER"){
                  this.addType="cottage";
                } else if(resp.data=="ROLE_FISHING_INSTRUCTOR"){
                  this.addType="adventure";
                }
			}).catch((err) => {
				console.log(err);
			});
  },
  methods:{
    test(){
      this.loadingRentables = false;
      let that = this;
      this.$axios.post(`/api/helloEj`,{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							})
                .then(response => {
                  console.log("A");
                });
    },
    redirect(){
      this.$router.push({ path: "/" + this.addType });
    },
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

</style>