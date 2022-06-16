<template>
	<b-card no-body class="shadow-sm p-3 mb-4" img-left max-height="500" style="cursor: pointer;">
		<div class="d-flex w-100">
			<span class="profile-img" style="height: 15vh; width: 15vh;">
								<img :src="'http://localhost:8080' + person.profilePhoto" style="height: 15vh;" />
			</span>
			<span class="name-user justify-content-center">
				<strong>{{person.name}} {{person.surname}}</strong>
				<span> {{person.username}}</span>
			</span>
			<span class="d-flex justify-content-end" style="margin-top: auto; margin-bottom: auto; flex-grow: 1"> 
				<b-button class=" book-btn" style="margin-top: auto; margin-bottom: auto;" @click="deleteConfirmation">Delete</b-button>
			</span>
		</div>
	</b-card>
</template>

<script>
export default {
	props: ["person", "adminView"],
	mounted() {
	},
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
		}
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