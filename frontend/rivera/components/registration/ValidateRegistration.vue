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
				disabled
            />
            </div>
            <div class="form-group col-6">
            <label>Username</label>
            <input
                type="text"
                placeholder="Enter your username"
                class="form-control form-control-lg"
                id="username" v-model="username"
				disabled
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
				disabled
            />
            </div>
            <div class="form-group col-6 ">
            <label>Surname</label>
            <input
                type="text"
                placeholder="Enter your surname"
                class="form-control form-control-lg"
                id="surname" v-model="surname"
				disabled
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
				disabled
            />
            </div>
            <div class="form-group col-6 ">
            <label>Address</label>
            <input
                type="text"
                placeholder="Enter your address"
                class="form-control form-control-lg"
                id="address" v-model="address"
				disabled
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
				disabled
            />
            </div>
            <div class="form-group col-6 ">
            <label>City</label>
            <input
                type="text"
                placeholder="Enter your city"
                class="form-control form-control-lg"
                id="city" v-model="city"
				disabled
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
				disabled
            />
            </div>
        </div>

		<div class="form-row ">
            <div class="form-group col-3">
            </div>
            <div class="form-group col-6 ">
            <label>Status</label>
            <input
                type="text"
                placeholder="Status"
                class="form-control form-control-lg"
                id="status" v-model="status"
				disabled
            />
            </div>
        </div>
        
        <b-form-invalid-feedback>
          Wrong email or password. Please try again.
        </b-form-invalid-feedback>

		<div v-if="status=='WAITING'">
			<b-button block id="login-btn" variant="primary"  @click="accept">Accept</b-button>
			<b-button block id="signup-btn" variant="outline-primary">Decline</b-button>
		</div>
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
			status: "",
		}
	},
    mounted() {
		let that = this;
		this.$axios.get('/api/admin/get-owner?username='+ this.$route.params.registration).then((resp) => {
				let owner = resp.data;
				that.email = owner.email;
				that.username=owner.username;
				that.password=owner.password;
				that.name=owner.name;
				that.surname=owner.surname;
				that.services=owner.services;
				that.phoneNumber=owner.phoneNumber;
				that.country=owner.country;
				that.city=owner.city;
				that.address=owner.address;
				that.status=owner.status;
				that.description=owner.description;
				console.log(owner);
			}).catch((err) => {
				console.log(err);
			});
	},
	methods: {
		accept(){
            console.log("AAAAAAAAAAAAA");
			this.$axios.post('/api/admin/activate-owner?username='+ this.$route.params.registration).then((resp) => {
				console.log("Uspesno");
			}).catch((err) => {
				console.log(err);
			});
		},
		decline(){
			this.$axios.post('/api/admin/deactivate-owner?username='+ this.$route.params.registration).then((resp) => {
				console.log("Uspesno");
			}).catch((err) => {
				console.log(err);
			});
		}
	}
}
</script>