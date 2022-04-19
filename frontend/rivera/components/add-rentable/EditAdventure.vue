<template>
    <div class="p-5 mt-3">
		<form>
			<div class="form-row">
				<div class="form-group col-md-4">
				<label for="name">Name</label>
				<input type="text" class="form-control" id="inputEmail4" placeholder="Name" v-model="name">
				</div>

				<div class="form-group col-md-4">
				<label for="name">Capacity</label>
				<input type="number" class="form-control" v-model="capacity"  id="capacity" name="capacity" min="1" max="100" placeholder="Capacity">
				</div>

				<div class="form-group col-md-1">
				<label for="name">$ per hour</label>
				<input type="number" class="form-control" v-model="perHour"  id="perHour" name="perHour" min="0" placeholder="Price per hour">
				</div>

				<div class="form-group col-md-1">
				<label for="name">$ per day</label>
				<input type="number" class="form-control" v-model="perDay"  id="capacity" name="perDay" min="0" placeholder="Price per day">
				</div>

				<div class="form-group col-md-2">
				<label for="name">Cancellation terms</label>
				<input type="text" class="form-control" id="terms" placeholder="Cancellation terms" v-model="cancellationTerms">
				</div>

			</div>
			<div class="form-group">
    			<label for="exampleFormControlTextarea1">Description</label>
				<textarea class="form-control"  v-model="description" id="exampleFormControlTextarea1" rows="3"></textarea>
  			</div>
			<div class="form-row">
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="tags" placeholder="Tags"></b-form-tags>
				</div>
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="rulesOfConduct" placeholder="Rules of conduct"></b-form-tags>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="equipment" placeholder="Equipment"></b-form-tags>
				</div>
				<div class="form-group col-6">
					<b-form-tags input-id="tags-basic" v-model="services" placeholder="Additional services"></b-form-tags>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-4">
					<label for="inputAddress">Address</label>
					<input type="text" class="form-control" v-model="address" id="inputAddress" placeholder="1234 Main St">
				</div>
				<div class="form-group col-4">
					<label for="inputCity">City</label>
					<input type="text" class="form-control" v-model="city" id="inputCity">
				</div>
				<div class="form-group col-4">
					<label for="inputState">State</label>
					<select id="inputState" v-model="country" class="form-control">
						<option selected>{{country}}</option>
						<option v-for="country in countries" :key="country.label" :value="country.value">
						{{ country.label }}
						</option>
					</select>
				</div>
			</div>
			<div class="d-flex">
				<PictureEdit :pictures='pictures' @picturesChange='picturesChange' />
			</div>
			<div class="d-flex">
				<PictureUpload />
			</div>
		</form>
		<button @click="upload" class="btn btn-primary float-right">Update adventure</button>
	</div>
</template>

<script>
const countries = require('i18n-iso-countries')
countries.registerLocale(require('i18n-iso-countries/langs/en.json'))
import PictureUpload from './PictureUpload.vue';
import PictureEdit from './PictureEdit.vue'
import Vue from 'vue'
export default {
	components: {PictureUpload, PictureEdit},
	computed: {
    countries () {
      const list = countries.getNames('en', { select: 'official' })
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }))
    }
  },
  data () {
    	return {
			selected: null,
			result: null,
			tags: [],
			rulesOfConduct: [],
			equipment: [],
			services: [],
			name: "",
			country: "",
			city: "",
			address: "",
			perHour: 0,
			perDay: 0,
			description: "",
			capacity: 0,
			cancellationTerms: "",
			pictures: [],
			id: null
		}
	},
	mounted() {
		let that = this;
		this.$axios
        .get('/api/get-adventure?id=' + this.$route.params.adventure)
        .then((resp) => {
            console.log(resp.data);
			let adventure = resp.data;
			that.name = adventure.name;
			that.country = adventure.country;
			that.equipment = adventure.equipment;
			that.services = adventure.services;
			that.address = adventure.address;
			that.perHour = adventure.perHour;
			that.perDay = adventure.perDay;
			that.description = adventure.description;
			that.capacity = adventure.capacity;
			that.rulesOfConduct = adventure.rulesOfConduct;
			that.cancellationTerms = adventure.cancellationTerms;
			that.services = adventure.services;
			that.equipment = adventure.equipment;
			that.city = adventure.city;
			that.country = adventure.country;
			that.pictures.push(...adventure.pictures);
			that.id = adventure.id;
			that.tags = adventure.tags;
			that.cancellationTerms = adventure.cancellationTerms;
			console.log(that.pictures);
        })
        .catch((err) => {
            console.log(err);
        });
	},
	methods: {
		upload() {
			var formData = new FormData();
			var images = document.getElementById('files');
			formData.append("name", this.name);
			formData.append("description", this.description);
			formData.append("perHour", this.perHour);
			formData.append("perDay", this.perDay);
			formData.append("services", this.services);
			formData.append("tags", this.tags);
			formData.append("country", this.country);
			formData.append("city", this.city);
			formData.append("address", this.address);
			formData.append("capacity", this.capacity);
			formData.append("equipment", this.equipment);
			formData.append("cancellationTerms", this.cancellationTerms);
			formData.append("rulesOfConduct", this.rulesOfConduct);
			formData.append("pictures", this.pictures);
			formData.append("id", this.id);
			if(images.files.length == 0) {
				formData.append("images", []);
			}
			for (let index = 0; index < images.files.length; index++) {
				formData.append("images", images.files[index]);
			}
			this.$axios.post('/api/update-adventure', formData, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			}).then((resp) => {
				console.log(resp);
			}).catch((err) => {
				console.log(err);
			});
		},
		picturesChange(pics) {
			this.pictures = pics;
			console.log("PARENT UPDATE PICS");
			console.log(pics);
		}
	}
}
</script>

<style>

</style>