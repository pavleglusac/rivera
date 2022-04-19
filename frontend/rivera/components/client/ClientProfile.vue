<template>
  <b-container class="bv-example-row">
    <b-card
      img-alt="Profile photo"
      :img=photo
      img-left
      class="mb-3"
      style="margin-top: 10px"
    >
      <b-card-text>
        <h4>{{fullName}}</h4>
        <h5>{{email}}</h5>
        <h5>{{photo}}</h5>
        <p v-model="description">
          Lorem ipsum, dolor sit amet consectetur adipisicing elit. Nemo ad
          consectetur similique nulla alias, doloremque itaque, nesciunt tempore
          eligendi quaerat exercitationem ducimus quas. Nesciunt, facilis.
          Officiis molestiae aliquid at dignissimos.
        </p>
        <b-button v-b-modal.changePicture variant="link"><b-icon icon="person-circle"></b-icon> Change profile photo</b-button>
        <b-modal id="changePicture" title="Choose your new profile photo">
          <b-form-file accept="image/*"></b-form-file>
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
              v-model="name"
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
              v-model="surname"
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
              v-model="phoneNumber"
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
              v-model="address"
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
                  v-model="city"
                  id="inputCity"
                  placeholder="Enter your city"
                />
              </div>
              <div class="form-group col-6">
                <label for="inputState">State</label>
                <select id="inputState" v-model="country" class="form-control">
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
            <password-change :cpasswordText="password" />

            <h4>Loyalty Program</h4>
            <p>Number of points you earned is {{numberOfPoints}}.</p>
            <p>Number of penalties you have is {{numberOfPenalties}}.</p>
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
      fullName: "",
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
      numberOfPenalties: 5,
      numberOfPoints: 5,
    };
  },
  mounted() {
    let that = this;
		this.$axios
        .get('/api/get-client?id=' + this.$route.params.client)
        .then((resp) => {
            console.log(resp.data);
			let client = resp.data;

			that.fullName = client.name + ' ' + client.surname;
			that.name = client.name;
			that.surname = client.surname;
			that.email = client.email;
      that.password = client.password;

			that.phoneNumber = client.phoneNumber;
			that.photo = client.photo;
			that.address = client.address;
			that.country = client.country;
			that.city = client.city;
			that.numberOfPenalties = client.numberOfPenalties;
			that.numberOfPoints = client.numberOfPoints;
    });
  },
  computed: {
    firstNameValidation() {
      return this.name.length != 0;
    },
    lastNameValidation() {
      return this.surname.length != 0;
    },
    phoneNumberValidation() {
      return this.phoneNumber.length >= 9;
    },
    addressValidation() {
      return this.address.length != 0;
    },
    countries() {
      const list = countries.getNames("en", { select: "official" });
      return Object.keys(list).map((key) => ({ value: key, label: list[key] }));
    },
  },
  methods: {
    update() {
			var formData = new FormData();
			formData.append("name", this.name);
			formData.append("surname", this.surname);
			formData.append("email", this.email);
			formData.append("password", this.password);
			formData.append("phoneNumber", this.phoneNumber);
			formData.append("address", this.address);
			formData.append("country", this.country);
			formData.append("city", this.city);
			formData.append("numberOfPenalties", this.numberOfPenalties);
			formData.append("numberOfPoints", this.numberOfPoints);
			formData.append("equipment", this.equipment);
			formData.append("photo", this.photo);
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
			this.photo = pic;
			console.log("PARENT UPDATE PICS");
			console.log(pics);
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