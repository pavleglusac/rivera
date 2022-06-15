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
            <div class="text-center">
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
		};
	},
	mounted() {
		this.id = this.$route.params.profile;
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
