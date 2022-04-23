<template>
    <div class="p-5 mt-3">  
		<form id="addCottageForm">
			<div class="form-row">
				<div class="form-group col-md-4">
				<label for="name">Name</label>
				<input type="text" class="form-control" v-model="name" id="inputEmail4" placeholder="Name">
				</div>

				<div class="form-group col-md-3">
				<label for="price">Price per hour(€)</label>
				<input type="number" class="form-control" v-model="perHour" id="perHour" name="perHour" min="0" placeholder="Price per hour">
				</div>

				<div class="form-group col-md-3">
				<label for="price">Price per day(€)</label>
				<input type="number" class="form-control"  v-model="perDay" id="perDay" name="perDay" min="0" placeholder="Price per day">
				</div>

				<div class="form-group col-md-2">
					<label for="price">Cancellation terms</label>
					<input type="number" step="0.01" class="form-control" v-model="cancellationTerms"  id="cancellationTerms" name="cancellationTerms" min="0" max="100" placeholder="Cancellation terms">
					</div>
			</div>
			<div class="form-group">
    			<label for="exampleFormControlTextarea1">Description</label>
				<textarea class="form-control" id="exampleFormControlTextarea1" v-model="description" rows="3" placeholder="Description"></textarea>
  			</div>
			<div class="form-row">
				<div class="form-group col-4">
					<label for="inputAddress">Tags</label>
					<b-form-tags input-id="tags-basic" v-model="tags" placeholder="Tags"></b-form-tags>
				</div>
				<div class="form-group col-4">
					<label for="inputAddress">Rules of conduct</label>
					<b-form-tags input-id="tags-basic" v-model="rulesOfConduct" placeholder="Rules of conduct"></b-form-tags>
				</div>
				<div class="form-group col-4">
					<label for="inputAddress">Additional services</label>
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
					<input type="text" class="form-control" v-model="city" id="inputCity" placeholder="City">
				</div>
				<div class="form-group col-4">
					<label for="inputState">State</label>
					<select id="inputState" class="form-control" v-model="country" >
						<option selected>Choose...</option>
						<option v-for="country in countries" :key="country.label" :value="country.value">
						{{ country.label }}
						</option>
					</select>
				</div>
			</div>
			<div class="form-row">
				
				<div class="form-group col-md-2">
					<label for="name">Number of rooms</label>
					<input type="number" class="form-control" id="numberOfRooms" name="numberOfRooms" min="1" placeholder="Number of rooms">
					</div>
	
					<div class="form-group col-md-2">
					<label for="name">Number of beds</label>
					<input type="number" class="form-control"  id="numberOfBeds" name="numberOfBeds" min="1" placeholder="Number of beds">
					</div>
					<div class="form-group col-md-0.5">
						<label for="name" ><br></label>
					<button type="button" id="addRoom" class="form-control"  >Add</button>
					</div>
					<div class="form-group col-md-1">
					</div>
	
				<div class="form-group col-3">
				<table class="table" id="addedRooms">
					<thead>
					  <tr>
						<th scope="col">Number of rooms</th>
						<th scope="col">Number of beds</th>
						<th scope="col">Remove entry</th>	
					  </tr>
					</thead>
					<tbody>
					</tbody>
				  </table>
				</div>
			</div>
			<div class="d-flex">
				<PictureEdit :pictures='pictures' @picturesChange='picturesChange' />
			</div>
			<div class="d-flex">
				<PictureUpload />
			</div>
			<button type="button" @click="upload" class="btn btn-primary float-right">Update cottage</button>





			<!-- Modal -->
			<div class="modal fade" id="roomsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Entry already exists</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					</div>
					<div class="modal-body">
					Would you like to overwrite it?
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="roomUpdate" >Yes</button>
					<button type="button" class="btn btn-secondary" id="modalClosure" >No</button>
					</div>
				</div>
				</div>
			</div>

			<div class="modal fade" id="emptyErrorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Error</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					</div>
					<div class="modal-body">
					Fields must not be empty! Please fill in empty fields and try again!
					</div>
					<div class="modal-footer">
					<button class="btn btn-primary" id="errorModalClosure" >Okay</button>
					</div>
				</div>
				</div>
			</div>
		</form>
	</div>
</template>

<script>
		
const countries = require('i18n-iso-countries')
countries.registerLocale(require('i18n-iso-countries/langs/en.json'))
import PictureUpload from './PictureUpload.vue';
import PictureEdit from './../add-adventure/PictureEdit.vue';
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
			pictures: [],
			services: [],
			name: "",
			country: "",
			city: "",
			address: "",
			perHour: 0,
			perDay: 0,
			description: "",
			cancellationTerms: "",
			id: ""
		}
	},
	
	mounted() {
		let that = this;
		this.$axios
        .get('/api/cottage/get-cottage?id=' + this.$route.params.cottage)
        .then((resp) => {
			let cottage = resp.data;
			that.name = cottage.name;
			that.country = cottage.country;
			that.equipment = cottage.equipment;
			that.services = cottage.services;
			that.address = cottage.address;
			that.perHour = cottage.perHour;
			that.perDay = cottage.perDay;
			that.description = cottage.description;
			that.rulesOfConduct = cottage.rulesOfConduct;
			that.cancellationTerms = cottage.cancellationTerms;
			that.services = cottage.services;
			that.equipment = cottage.equipment;
			that.city = cottage.city;
			that.country = cottage.country;
			that.tags = cottage.tags;
			that.pictures.push(...cottage.pictures);
			that.id = cottage.id;

			var stringRooms = cottage.rooms.split(';');
			var tbodyRef = document.getElementById('addedRooms').getElementsByTagName('tbody')[0];
			for(var room of stringRooms){
				if(!room.split(',')[0]){
					break;
				}
				var numberOfRoomsText = document.createTextNode(room.split(',')[0]);
				var numberOfBedsText = document.createTextNode(room.split(',')[1]);
				var removeText = document.createTextNode("Remove");				

				var newRow = tbodyRef.insertRow();

				var numberOfRooms = newRow.insertCell();
				var numberOfBeds = newRow.insertCell();
				var remove = newRow.insertCell();


				numberOfRooms.appendChild(numberOfRoomsText);
				numberOfBeds.appendChild(numberOfBedsText);
				remove.appendChild(removeText);
				remove.onclick = function(){
					tbodyRef.removeChild(newRow);
				}
			}
        })
        .catch((err) => {
            console.log(err);
        });


		document.getElementById('addRoom').onclick = function() {addRoom();}
		document.getElementById('roomUpdate').onclick = function() {updateRoom();}
		document.getElementById('modalClosure').onclick = function() {closeModal("roomsModal");}
		document.getElementById('errorModalClosure').onclick = function() {closeModal("emptyErrorModal");}
				
		function addRoom(){
			var tbodyRef = document.getElementById('addedRooms').getElementsByTagName('tbody')[0];
			var numberOfRoomsText = document.createTextNode(document.getElementById('numberOfRooms').value);
			var numberOfBedsText = document.createTextNode(document.getElementById('numberOfBeds').value);
			var removeText = document.createTextNode("Remove");

			var table = document.getElementById("addedRooms");
			for (var i = 1, row; row = table.rows[i]; i++) {
				if(numberOfBedsText.textContent == row.cells[1].innerText){
					$('#roomsModal').modal('show');
					return;
				}
			}

			var newRow = tbodyRef.insertRow();

			var numberOfRooms = newRow.insertCell();
			var numberOfBeds = newRow.insertCell();
			var remove = newRow.insertCell();


			numberOfRooms.appendChild(numberOfRoomsText);
			numberOfBeds.appendChild(numberOfBedsText);
			remove.appendChild(removeText);
			remove.onclick = function(){
				tbodyRef.removeChild(newRow);
			}
		}

		function updateRoom(){
			var tbodyRef = document.getElementById('addedRooms').getElementsByTagName('tbody')[0];
			var numberOfRoomsText = document.createTextNode(document.getElementById('numberOfRooms').value);
			var numberOfBedsText = document.createTextNode(document.getElementById('numberOfBeds').value);
			var removeText = document.createTextNode("Remove");

			var table = document.getElementById("addedRooms");
			for (var i = 1, row; row = table.rows[i]; i++) {
				if(numberOfBedsText.textContent == row.cells[1].innerText){
					row.cells[0].innerText = numberOfRoomsText.textContent; 
					$('#roomsModal').modal('hide');
					return;
				}
			}
		}

		function closeModal(id){
			$("#"+id).modal('hide');
		}
	},
	methods: {
		upload() {
			var formData = new FormData();
			var images = document.getElementById('files');
			
			if(!(this.name && this.perDay && this.perHour && this.tags.length>0 && this.cancellationTerms && this.rulesOfConduct.length>0 && this.services.length>0 && this.country && this.city && this.address && document.getElementById("addedRooms").rows.length>1 && images.files.length+this.pictures.length>=5)){
					$('#emptyErrorModal').modal('show');
					$("#addCottageForm").submit(function(e) {
    					e.preventDefault();
					});
					return;
			}
			formData.append("name", this.name);
			formData.append("description", this.description);
			formData.append("perHour", this.perHour);
			formData.append("perDay", this.perDay);
			formData.append("tags", this.tags);
			formData.append("country", this.country);
			formData.append("city", this.city);
			formData.append("address", this.address);
			formData.append("cancellationTerms", this.cancellationTerms);
			formData.append("rulesOfConduct", this.rulesOfConduct);
			formData.append("services", this.services);
			formData.append("id", this.id);
			formData.append("pictures", this.pictures);
			if(images.files.length == 0) {
				formData.append("images", []);
			}
			console.log(formData);
			for (let index = 0; index < images.files.length; index++) {
				formData.append("images", images.files[index]);
			}
			var roomsString = "";
			var table = document.getElementById("addedRooms");
			for (var i = 1, row; row = table.rows[i]; i++) {
				roomsString += row.cells[1].innerText+","+row.cells[0].innerText + ";";					
			}
			formData.append("rooms", roomsString);
			this.$axios.post('/api/cottage/update-cottage', formData, {
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
	}}
</script>