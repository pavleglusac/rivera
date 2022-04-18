<template>
  <div style="border: 1px solid #9c9a9a; border-radius: 5%;" class="p-0 card">
	<div style="background-color: #F3F9FA; border-radius: 7%;" class="p-0">
		<div class="d-flex justify-content-center pt-4">
			<span class="align-middle p-0" style="font-size: 2em; font-weight: 500;">${{discount.price}}</span>
		</div>
		<div class="d-flex justify-content-center">
			{{discount.start[2]}}/{{discount.start[1]}}/{{discount.start[0]}} - {{discount.end[2]}}/{{discount.end[1]}}/{{discount.end[0]}}
		</div>
		<div class="d-flex mt-2 justify-content-center pb-3">
			<b-button class="mt-2" style="background-color: #171F30; border-radius: 10%;">Reserve</b-button>
		</div>
	</div>
	<div class="pl-2 mb-3 mt-3">
		<b-form-tag  v-for="tag in discount.tags"
									:key="tag"
									class="mr-1 mt-2 d-flex">
									{{tag}}
		</b-form-tag>
	</div>
		
		
  </div>
</template>

<script>
export default {
	props: ['discount'],
	mounted() {
		this.discount.tags.splice(0, 0, 'capacity ' + this.discount.capacity + '🧍')
	},
	data() {
		return {

		}
	}
}
</script>

<style>
.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 4px 20px 0 rgba(0, 0, 0, 0.19);
}
</style>