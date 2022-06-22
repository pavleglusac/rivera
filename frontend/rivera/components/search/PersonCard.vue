<template>
	<b-list-group-item class="d-flex align-items-center">
		<b-avatar variant="info" :src="'http://localhost:8080' + person.profilePhoto" class="mr-3"></b-avatar>
		<span @click="goToProfile(person.username)" style="cursor: pointer" class="mr-auto"> {{person.name}} {{person.surname}} <span class="text-muted">@{{person.username}}</span></span>
		<b-button size="sm" @click="deleteConfirmation">Delete</b-button>
	</b-list-group-item>
</template>

<script>
export default {
	props: ["person", "adminView"],
	methods: {
		deleteConfirmation() {
			if (confirm('Are you sure you want to delete this person?')) {
				this.deletePerson();
			} else {
				console.log('Not deleted.');
			}
		},
		deletePerson() {
			this.$axios
				.delete(
					`/api/person?username=` + this.person.username
				)
				.then((response) => {
					window.location.reload();
				});
		},
		goToProfile() {
			this.$router.push({ path: "/profile/" + this.person.username });
		},
	}
	
}
</script>

<style>
.book-btn {
  background-color: var(--prime-color);
  border: none;
}
.book-btn:hover {
  background-color: #39AEA9;
}
</style>