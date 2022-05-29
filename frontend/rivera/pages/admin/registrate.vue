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

};
</script>

<style>
</style>