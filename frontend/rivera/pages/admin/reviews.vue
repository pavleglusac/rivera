<template>
	<div>
		<Navbar :currentPage="'reviews'" />
		<div
			class="d-flex flex align-items-start mt-1 parent"
			style="width: 60vw; margin-left: auto; margin-right: auto"
		>
			<b-card
				class="card mt-2 mb-5 w-100"
				style="
					margin-top: 0;
					box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
				"
				v-if="reviews.length > 0"
			>
                <span>
                    <h4 class="d-inline">
                        {{currentReview.complaint ? 'Complaint' : 'Review'}} on:
                    </h4>
                    <img
                        :src="'http://localhost:8080' + currentReview.reviewedImage"
                        alt=""
                        class="z-depth-2"
                        style="border-radius: 50%; object-fit: cover; width: 3vw; height: 3vw;"
				    />

                    {{currentReview.reviewedName}}
                </span>
				<ReviewApproval :review="currentReview" class="w-100 mt-3" style="">
				</ReviewApproval>

				<div class="w-100 ml-1 mt-5">
					<b-button variant="success" @click="respondModal"
						>Respond</b-button
					>
				</div>

				<b-modal ref="response_modal" size="lg" hide-footer>
					<h3>Enter response text</h3>
					<b-form-textarea
						id="textarea-large"
						size="lg"
						placeholder=""
						v-model="responseText"
					>
					</b-form-textarea>

                    <b-form-checkbox v-model="allowReview" class="mt-2" v-if="!currentReview.complaint">
                        Allow review
                    </b-form-checkbox>

					<b-button class="mt-5 float-right" @click="sendResponse"
						>Send</b-button
					>
				</b-modal>

				<div class="d-flex flex w-100 mt-3">
					<span class="w-50 ml-1">
						<b-button @click="prevReview">&lt; Previous</b-button>
					</span>
					<span class="w-50 mr-1 d-flex flex-row-reverse">
						<b-button @click="nextReview">Next &gt;</b-button>
					</span>
				</div>
			</b-card>
			<div v-else class="mt-4 w-100 d-flex justify-content-center">
				<h4>Currently there are no pending reviews.</h4>
			</div>
		</div>
	</div>
</template>

<script>
import ReviewApproval from "@/components/admin/ReviewApproval.vue";
import Navbar from "@/components/navbar.vue";
export default {
	components: { ReviewApproval, Navbar },
	data() {
		return {
			reviews: [],
			currentReview: {
				text: "",
				responseText: "",
				clientName: "",
				clientSurname: "",
				clientImage: "",
				reviewedName: "",
				reviewedImage: "",
				score: 0,
                complaint: false,
                id: -1,
			},
            allowReview: false,
            responseText: '',
		};
	},
	mounted() {
		this.redirectAdmin();
		this.getPendingReviews();
	},
	methods: {
        respondModal() {
            this.$refs.response_modal.show();
        },
        sendResponse() {

        },
		getPendingReviews() {
			let that = this;
			this.$axios.get("/api/admin/pending-reviews").then((resp) => {
				for (let index = 0; index < response.data.length; index++) {
					response.data[index].clientImage = process.env.backend + response.data[index].clientImage;
				}
				console.log(resp.data);
				that.reviews.push(...resp.data);
				if (that.reviews.length > 0)
					that.currentReview = that.reviews[0];
			});
		},
		prevReview() {
			if (this.reviews.length > 0) {
				let review = this.reviews.pop();
				this.reviews.splice(0, 0, review);
				this.currentReview = review;
			}
		},
		nextReview() {
			if (this.reviews.length > 0) {
				let review = this.reviews.shift();
				this.reviews.push(review);
				this.currentReview = review;
			}
		},
        sendResponse() {
			let that = this;
			console.log(`${that.id}  ${that.responseText}  ${that.assignPenalty}`);
			this.$axios.post('/api/admin/resolve-review/', {
				reviewId: that.currentReview.id,
				responseText: that.responseText,
				allowed: that.allowReview,
			}, {
				headers: {
					'Content-Type': 'application/json'
				}
			}).then(resp => {
				window.location.reload();
			});
		},
		redirectAdmin() {
			this.$axios
				.get("/api/auth/logged-user-info")
				.then((resp) => {
					console.log(resp.data);
					if (resp.data != null) {
						if (
							resp.data.role == "ROLE_ADMIN" &&
							resp.data.lastPasswordChange == null
						) {
							this.$router.push({ path: "/admin/profile" });
						}
					}
				})
				.catch((err) => {
					console.log(err);
				});
		},
	},
};
</script>

<style>
</style>