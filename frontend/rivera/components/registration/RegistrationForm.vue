<template>
  <div>
    <b-card class="text-center mx-auto my-auto card">
      <img src="..\..\static\rivera_logo_ver.png" />
      <form id="registrationForm">
          
		<div class="form-row ">
            <div class="form-group col-6">
            <label>Email</label>
            <input
                type="email"
                placeholder="Enter your email address"
                class="form-control form-control-lg"
                id="email" v-model="email"
            />
            </div>
            <div class="form-group col-6">
            <label>Username</label>
            <input
                type="text"
                placeholder="Enter your username"
                class="form-control form-control-lg"
                id="username" v-model="username"
            />
            
            </div>
        </div>
        <div class="form-row ">
            <div class="form-group col-6 ">
            <label>Password</label>
            <input
                type="password"
                placeholder="Enter your password"
                class="form-control form-control-lg"
                id="password" v-model="password"
            />
            </div>
            <div class="form-group col-6 ">
            <label>Reenter password</label>
            <input
                type="password"
                placeholder="Reenter your password"
                class="form-control form-control-lg"
            />
            </div>
        </div>
        <div class="form-row ">
            <div class="form-group col-6">
            <label>Name</label>
            <input
                type="text"
                placeholder="Enter your surname"
                class="form-control form-control-lg"
                v-model="name"
            />
            </div>
            <div class="form-group col-6 ">
            <label>Surname</label>
            <input
                type="text"
                placeholder="Enter your surname"
                class="form-control form-control-lg"
                id="surname" v-model="surname"
            />
            </div>
        </div>
        
        <div class="form-row ">
            <div class="form-group col-6">
            <label>Phone number</label>
            <input
                type="text"
                placeholder="Enter your phone number"
                class="form-control form-control-lg"
                id="phoneNumber" v-model="phoneNumber"
            />
            </div>
            <div class="form-group col-6 ">
            <label>Address</label>
            <input
                type="text"
                placeholder="Enter your address"
                class="form-control form-control-lg"
                id="address" v-model="address"
            />
            </div>
        </div>
        <div class="form-row ">
            <div class="form-group col-6">
            <label>Country</label>
            <input
                type="text"
                placeholder="Enter your country"
                class="form-control form-control-lg"
                id="country" v-model="country"
            />
            </div>
            <div class="form-group col-6 ">
            <label>City</label>
            <input
                type="text"
                placeholder="Enter your city"
                class="form-control form-control-lg"
                id="city" v-model="city"
            />
            </div>
        </div>
        <div class="form-row ">
            <div class="form-group col-12">
            <label>Description</label>
            <textarea
                placeholder="Enter description"
                class="form-control form-control-lg"
                id="description" v-model="description"
            />
            </div>
        </div>
        <div class="form-row ">
            <div class="form-group col-3"></div>
            <div class="form-group col-6 ">
            <label>Type</label>
            <input
                type="text"
                placeholder="Enter your type"
                class="form-control form-control-lg"
                id="type" v-model="type"
            />
            </div>
        </div>
        
        <b-form-invalid-feedback>
          Wrong email or password. Please try again.
        </b-form-invalid-feedback>

        <b-button block id="login-btn" variant="primary"  @click="register">Register</b-button>
        <b-button block id="signup-btn" variant="outline-primary">Already have an accont? Log in</b-button>
        <b-button block id="just-looking">Just looking...</b-button>
      </form>
    </b-card>
  </div>
</template>


<script>

export default {
    data () {
    	return {
			email: "",
			username: "",
			password: "",
			name: "",
			surname: "",
			services: "",
			phoneNumber: "",
			country: "",
			city: "",
			address: "",
			type: "",
			description: "",
		}
	},
    methods: {
        register(){
            let that = this;
            console.log("ok");
			var formData = new FormData();
            formData.append("email",this.email);
            formData.append("username",this.username);
            formData.append("password",this.password);
            formData.append("name",this.name);
            formData.append("surname",this.surname);
            formData.append("services",this.services);
            formData.append("phoneNumber",this.phoneNumber);
            formData.append("country",this.country);
            formData.append("city",this.city);
            formData.append("address",this.address);
            formData.append("description",this.description);
            formData.append("type","Cottage owner");
            this.$axios.post('/api/auth/signup', formData).then((resp) => {
				console.log(resp);
			}).catch((err) => {
				console.log(err);
			});
        }
    }
}
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
input::-webkit-input-placeholder {
    font-size: 15px;
    line-height: 3;
}
</style>