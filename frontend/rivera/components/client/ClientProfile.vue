<template>
  <b-container class="bv-example-row">
    <b-card
      img-src="https://placekitten.com/300/300"
      img-alt="Card image"
      img-left
      class="mb-3"
    >
      <b-card-text>
        <h4>Mala maca</h4>
        <h5>malamaca@gmail.com</h5>
        <p>
          Lorem ipsum, dolor sit amet consectetur adipisicing elit. Nemo ad
          consectetur similique nulla alias, doloremque itaque, nesciunt tempore
          eligendi quaerat exercitationem ducimus quas. Nesciunt, facilis.
          Officiis molestiae aliquid at dignissimos.
        </p>
        <a href="#"
          ><b-icon icon="person-circle"></b-icon> Change profile photo</a
        ><br />
        <a href="#"><b-icon icon="trash-fill"></b-icon> Delete account</a>
      </b-card-text>
    </b-card>
    <b-card>
      <b-form @submit.stop.prevent>
        <b-row>
          <b-col>
            <h4>Profile Settings</h4>
            <label for="feedback-first-name">First Name</label>
            <b-form-input
              v-model="firstName"
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
              v-model="lastName"
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
                  <option selected>Choose...</option>
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
            <b-button block class="save-changes">Save changes</b-button>
          </b-col>
          <b-col>
            <h4>Password Settings</h4>
            <password-change />

            <h4>Loyalty Program</h4>
            <p>Number of points you earned is 235.</p>
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
      firstName: "",
      lastName: "",
      phoneNumber: "",
      address: "",
      country: "",
      city: "",
    };
  },
  methods: {},
  computed: {
    firstNameValidation() {
      return this.firstName.length != 0;
    },
    lastNameValidation() {
      return this.lastName.length != 0;
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
    buttonLabel() {
      return this.showPassword ? "Hide" : "Show";
    },
  },
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