<template>
    <div>
        <form id="registrationForm">
            email: <input type="text" id="email" v-model="email"/>
            <br>
            username: <input type="text" id="username"  v-model="username"/>
            <br>
            password: <input type="text" id="password"  v-model="password"/>
            <br>
            name: <input type="text" id="name"  v-model="name"/>
            <br>
            surname: <input type="text" id="surname"  v-model="surname"/>
            <br>
            phoneNumber: <input type="text" id="phoneNumber"  v-model="phoneNumber"/>
            <br>
            address: <input type="text" id="address"  v-model="address"/>
            <br>
            city: <input type="text" id="city"  v-model="city"/>
            <br>
            country: <input type="text" id="country" v-model="country"/>
            <br>
			<button type="button" @click="register">Register</button>
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

</style>