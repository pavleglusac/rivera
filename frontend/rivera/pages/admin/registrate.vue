<template>
	<div>
		<Navbar :currentPage="'registrate'" />
		<div class="">
			<AdminRegistrationForm class="h-100"></AdminRegistrationForm>
		</div>
	</div>
</template>

<script>
import AdminRegistrationForm from '../../components/registration/AdminRegistrationForm.vue';
export default {
	components: {AdminRegistrationForm, },
	mounted() {
		this.redirectAdmin();
	},
	methods: {
		redirectAdmin() {
			this.$axios.get('/api/auth/logged-user-info').then((resp) => {
				console.log(resp.data);
				if(resp.data != null) {
					if(resp.data.role == 'ROLE_ADMIN' && resp.data.lastPasswordChange == null) {
					this.$router.push({ path: "/admin/profile" });
					}
				}
			}).catch((err) => {
				console.log(err);
			});
		}
	}

};
</script>

<style>
</style>