<template>
	<div class="mt-5 border-top border-bottom">
		<div class="mt-2 mb-2">
			<b-form>
				<div class="container-fluid p-2">
					<ul class="nav nav-pills nav-fill" role="tablist">
						<li class="nav-item">
							<p
								class="nav-link nav-mn"
								v-bind:class="{ active: activeSection == 0 }"
								id="adventures-btn"
								v-on:click="activeSection = 0"
							>
								<span><font-awesome-icon icon="star" /> Reviews</span>
							</p>
						</li>
					</ul>
				</div>
			</b-form>
			<div v-if="reviews.length > 0">
					<div v-for="review in reviews" class="testimonial-box-container w-100 m-1 h-25">
					<div class="testimonial-box w-100">
						<div class="box-top">
						<div class="profile">
							<div class="profile-img">
							<img :src="review.ownerPicture" />
							</div>
							<div class="name-user" style="cursor: pointer">
							<strong
								>{{ review.ownerName }} {{ review.ownerSurname }}</strong
							>
							</div>
						</div>
						</div>
						<div class="client-comment w-100">
						<p class="w-100">{{ review.text }}</p>
						</div>
					</div>
				</div>
			</div>
            <div v-else class="text-center">
                <p style="font-size: 20px">No one reviewed {{name}} yet.</p>
            </div>
		</div>
	</div>
</template>

<script>

export default {
    components: {},
	props: ["role", "name"],
	data() {
		return {
			id: "",
			selected: 0,
			activeSection: 0,
			reviews: []
		};
	},
	mounted() {
		this.id = this.$route.params.profile;
		let that = this;
		console.log("DANIUFBIUAEB")
		this.$axios
        .get("/api/getClientReviews?username=" + this.id)
        .then((resp) => {
			console.log(resp.data);
			for(let i=0; i<resp.data.length; i++)
				resp.data[i].ownerPicture = process.env.backend + resp.data[i].ownerPicture;
			that.reviews = resp.data;
		});
		
	},
	watch: {
		activeSection(val, oldVal) {
			if (val == oldVal) return;
		},
	},
	methods: {

	},
};
</script>

<style></style>
