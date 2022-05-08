<template>
  <b-container class="bv-example-row">
    <b-card
      img-alt="Profile photo"
      :img-src='"http://localhost:8080" + client.photo'
      img-left
      class="mb-3"
      style="margin-top: 10px"
    >
      <b-card-text>
        <h4>{{client.fullName}}</h4>
        <h5>{{client.email}}</h5>
        <h5>{{client.photo}}</h5>
        <b-button v-b-modal.changePicture variant="link"><b-icon icon="person-circle"></b-icon> Change profile photo</b-button>
        <b-modal id="changePicture" title="Choose your new profile photo">
          <b-form-file id="photoFile" v-model="client.photo" accept="image/*"></b-form-file>
        </b-modal>
        <b-button variant="link"><b-icon icon="trash-fill"></b-icon> Delete account</b-button>
      </b-card-text>
    </b-card>
    <b-card style="margin-bottom: 10px">
      <b-form @submit.stop.prevent>
        <b-row>
          <b-col>
            <h4>Profile Settings</h4>
            <label for="feedback-first-name">First Name</label>
            <b-form-input
              v-model="client.name"
              :state="firstNameValidation"
              id="feedback-first-name"
              placeholder="Enter your first name"
              required
            ></b-form-input>
            <b-form-invalid-feedback :state="firstNameValidation">
              First name cannot be empty.
            </b-form-invalid-feedback>

            <label for="feedback-last-name">Last Name</label>
            <b-form-input
              v-model="client.surname"
              :state="lastNameValidation"
              id="feedback-last-name"
              placeholder="Enter your last name"
              required
            ></b-form-input>
            <b-form-invalid-feedback :state="lastNameValidation">
              Last name cannot be empty.
            </b-form-invalid-feedback>

            <label for="feedback-phone-number">Phone Number</label>
            <b-form-input
              v-model="client.phoneNumber"
              :state="phoneNumberValidation"
              id="feedback-phone-number"
              placeholder="Enter your phone number"
              required
            ></b-form-input>
            <b-form-invalid-feedback :state="phoneNumberValidation">
              Phone number cannot be empty.
            </b-form-invalid-feedback>

            <label for="feedback-address">Address</label>
            <b-form-input
              v-model="client.address"
              :state="addressValidation"
              id="feedback-address"
              placeholder="Enter your address"
              required
            ></b-form-input>
            <b-form-invalid-feedback :state="addressValidation">
              Address cannot be empty.
            </b-form-invalid-feedback>

            <div class="form-row">
              <div class="form-group col-6">
                <label for="inputCity">City</label>
                <input
                  type="text"
                  class="form-control"
                  v-model="client.city"
                  id="inputCity"
                  placeholder="Enter your city"
                />
              </div>
              <div class="form-group col-6">
                <label for="inputState">State</label>
                <select id="inputState" v-model="client.country" class="form-control">
                  <option selected>{{country}}</option>
                  <option
                    v-for="country in countries"
                    :key="country.label"
                    :value="country.value"
                  >
                    {{ country.label }}
                  </option>
                </select>
              </div>
            </div>
            <b-button @click="update" block class="save-changes">Save changes</b-button>
          </b-col>
          <b-col>
            <h4>Password Settings</h4>
            <password-change :cpasswordText="client.password" />

            <h4>Loyalty Program</h4>
            <p>Number of points you earned is {{client.numberOfPoints}}.</p>
            <p>Number of penalties you have is {{client.numberOfPenalties}}.</p>
            <b-card
              bg-variant="light"
              header="Gold Program"
              class="text-center"
            >
              <b-card-text>With gold program, you can ...</b-card-text>
            </b-card>
          </b-col>
        </b-row>
      </b-form>
    </b-card>
  </b-container>
</template>

<script>
const countries = require("i18n-iso-countries");
countries.registerLocale(require("i18n-iso-countries/langs/en.json"));
import { BIcon, BIconTrashFill, BIconPersonCircle } from "bootstrap-vue";
import PasswordChange from "./PasswordChange.vue";
export default {
  name: "ClientProfile",
  components: {
    BIcon,
    BIconTrashFill,
    BIconPersonCircle,
    PasswordChange,
  },
  data() {
    return {
      client: {
        fullName: "",
        username: "",
        photo: "",
        email: "",
        description: "",
        password: "",
        name: "",
        surname: "",
        phoneNumber: "",
        address: "",
        country: "",
        city: "",
        numberOfPenalties: 0,
        numberOfPoints: 0,
      }
    };
  },
  mounted() {
    
  },
  computed: {
    firstNameValidation() {
      return this.client.name.length != 0;
    },
    lastNameValidation() {
      return this.client.surname.length != 0;
    },
    phoneNumberValidation() {
      return this.client.phoneNumber.length >= 9;
    },
    addressValidation() {
      return this.client.address.length != 0;
    },
    countries() {
      const list = countries.getNames("en", { select: "official" });
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
    },
  },
  methods: {
    getLoggedUser() {
      let that = this;
      console.log(window.localStorage.getItem("JWT"));
      this.$axios.get('/api/auth/get-logged-user',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
        that.client = resp.data;
        console.log(window.localStorage.getItem("JWT"));
				console.log(resp.data);
			}).catch((err) => {
				console.log(err);
			});
    },
    setClientData(client) {
      let that = this;
      that.client.fullName = client.name + ' ' + client.surname;
      that.client.name = client.name;
      that.client.username = client.username;
      that.client.surname = client.surname;
      that.client.email = client.email;
      that.client.password = client.password;
      that.client.phoneNumber = client.phoneNumber;
      that.client.photo = client.photo;
      that.client.address = client.address;
      that.client.country = client.country;
      that.client.city = client.city;
      that.client.numberOfPenalties = client.numberOfPenalties;
      that.client.numberOfPoints = client.numberOfPoints;
    },
    update() {
			var formData = new FormData();
			formData.append("name", this.client.name);
			formData.append("surname", this.client.surname);
			formData.append("email", this.client.email);
			formData.append("password", this.client.password);
			formData.append("phoneNumber", this.client.phoneNumber);
			formData.append("address", this.client.address);
			formData.append("country", this.client.country);
			formData.append("city", this.client.city);
			formData.append("numberOfPenalties", this.client.numberOfPenalties);
			formData.append("numberOfPoints", this.client.numberOfPoints);
			formData.append("equipment", this.client.equipment);
			formData.append("photo", this.client.photo);
			this.$axios.post('/api/update-client', formData, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			}).then((resp) => {
				console.log(resp);
			}).catch((err) => {
				console.log(err);
			});
		},
    changeProfilePhoto(pic) {
			this.client.photo = pic;
			console.log("PARENT UPDATE PICS");
			console.log(pic);
		}
  }
};
</script>

<style scoped>
.form-control {
  height: calc(1.5em + 0.75rem + 2px);
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  display: block;
  width: 100%;
}
.save-changes {
  margin-top: 8px;
  margin-bottom: 8px;
  background-color: #16c79a;
  border: none;
}
</style>