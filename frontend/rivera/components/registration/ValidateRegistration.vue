<template>
    <div>
        <form id="registrationForm">
            email: <input type="text" id="email" v-model="email" disabled/>
            <br>
            username: <input type="text" id="username"  v-model="username" disabled/>
            <br>
            name: <input type="text" id="name"  v-model="name" disabled/>
            <br>
            surname: <input type="text" id="surname"  v-model="surname" disabled/>
            <br>
            phoneNumber: <input type="text" id="phoneNumber"  v-model="phoneNumber" disabled/>
            <br>
            address: <input type="text" id="address"  v-model="address" disabled/>
            <br>
            city: <input type="text" id="city"  v-model="city" disabled/>
            <br>
            country: <input type="text" id="country" v-model="country" disabled/>
            <br>
            status: <input type="text" id="status" v-model="status" disabled/>
            <br>
			<div v-if="status=='WAITING'">
				<button @click="accept" > ACCEPT </button>
				<button @click="decline" > DECLINE </button>
			</div>
        </form>
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
				console.log(owner);
			}).catch((err) => {
				console.log(err);
			});
	},
	methods: {
		accept(){
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