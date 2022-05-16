<template>
    <b-form @submit.stop.prevent>
        <b-row>
          <b-col>
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
                <label for="inputState">Country</label>
                <select id="inputState" v-model="client.country" class="form-control">
                  <option selected>{{client.country}}</option>
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
        </b-row>
    </b-form>  
</template>

<script>
const countries = require("i18n-iso-countries");
countries.registerLocale(require("i18n-iso-countries/langs/en.json"));
import { BIcon, BIconTrashFill, BIconPersonCircle } from "bootstrap-vue";
export default {
  name: "ClientProfile",
  components: {
    BIcon,
    BIconTrashFill,
    BIconPersonCircle,
  },
  data() {
    return {
      client: {
        name: "",
        surname: "",
        phoneNumber: "",
        address: "",
        country: "",
        city: "",
      },
    };
  },
  mounted() {
    this.getLoggedUser();
  },
  computed: {
    firstNameValidation() {
      return this.client.name.length != 0;
    },
    lastNameValidation() {
      return this.client.surname.length != 0;
    },
    phoneNumberValidation() {
      return this.client.phoneNumber.length >= 3;
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
      this.$axios.get('/api/auth/get-logged-username',{
								headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
							}).then((resp) => {
              this.$axios.get('/api/get-client-by-username?username=' + resp.data) 
							.then((resp) => {
                  that.setClientData(resp.data);
              });
			}).catch((err) => {
				console.log(err);
			});
    },
    setClientData(client) {
      let that = this;
      that.client.name = client.name;
      that.client.surname = client.surname;
      that.client.phoneNumber = client.phoneNumber;
      that.client.address = client.address;
      that.client.country = client.country;
      that.client.city = client.city;
    },
    update() {
        var formData = new FormData();
        formData.append("name", this.client.name);
        formData.append("surname", this.client.surname);
        formData.append("email", this.client.email);
        formData.append("username", this.client.username);
        formData.append("password", this.client.password);
        formData.append("phoneNumber", this.client.phoneNumber);
        formData.append("address", this.client.address);
        formData.append("country", this.client.country);
        formData.append("city", this.client.city);
        formData.append("numberOfPenalties", 0);
        formData.append("numberOfPoints", 0);
        formData.append("equipment", this.client.equipment);
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