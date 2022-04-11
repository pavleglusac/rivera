<template>
  <div>
    <div v-if="!cpasswordHidden" class="bv-example-row">
      <b-row>
        <b-col cols="11">
          <input type="text" class="password-field" v-model="cpasswordText" />
        </b-col>
        <b-col cols="1">
          <b-icon
            class="password-icon"
            @click="hideCPassword"
            icon="eye-fill"
          ></b-icon>
        </b-col>
      </b-row>
    </div>
    <div v-if="cpasswordHidden" class="bv-example-row">
      <b-row>
        <b-col cols="11">
          <input
            type="password"
            class="password-field"
            v-model="cpasswordText"
          />
        </b-col>
        <b-col cols="1">
          <b-icon
            @click="showCPassword"
            class="password-icon"
            icon="eye-slash-fill"
          ></b-icon>
        </b-col>
      </b-row>
    </div>
    <b-form-invalid-feedback :state="currentPasswordValidation">
      That is not your current password. Try again.
    </b-form-invalid-feedback>

    <label>New Password</label>
    <div v-if="!passwordHidden" class="bv-example-row">
      <b-row>
        <b-col cols="11">
          <input type="text" class="password-field" v-model="passwordText" />
        </b-col>
        <b-col cols="1">
          <b-icon
            class="password-icon"
            @click="hidePassword"
            icon="eye-fill"
          ></b-icon>
        </b-col>
      </b-row>
    </div>
    <div v-if="passwordHidden" class="bv-example-row">
      <b-row>
        <b-col cols="11">
          <input
            type="password"
            class="password-field"
            v-model="passwordText"
          />
        </b-col>
        <b-col cols="1">
          <b-icon
            @click="showPassword"
            class="password-icon"
            icon="eye-slash-fill"
          ></b-icon>
        </b-col>
      </b-row>
    </div>
    <b-form-invalid-feedback :state="newPasswordValidation">
      Password must be at least 8 characters. Try again.
    </b-form-invalid-feedback>

    <label>Re-enter New Password</label>
    <div v-if="!rpasswordHidden" class="bv-example-row">
      <b-row>
        <b-col cols="11">
          <input type="text" class="password-field" v-model="rpasswordText" />
        </b-col>
        <b-col cols="1">
          <b-icon
            @click="hideRPassword"
            class="password-icon"
            icon="eye-fill"
          ></b-icon>
        </b-col>
      </b-row>
    </div>
    <div v-if="rpasswordHidden" class="bv-example-row">
      <b-row>
        <b-col cols="11">
          <input
            type="password"
            class="password-field"
            v-model="rpasswordText"
          />
        </b-col>
        <b-col cols="1">
          <b-icon
            @click="showRPassword"
            class="password-icon"
            icon="eye-slash-fill"
          ></b-icon>
        </b-col>
      </b-row>
    </div>
    <b-form-invalid-feedback :state="newPasswordAgainValidation">
      Re-entered password is not the same as your new password.
    </b-form-invalid-feedback>
    <b-button block class="change-password">Change password</b-button>
  </div>
</template>

<script>
import { BIcon, BIconEyeFill, BIconEyeSlashFill } from "bootstrap-vue";

export default {
  name: "ClientProfile",
  components: {
    BIcon,
    BIconEyeFill,
    BIconEyeSlashFill,
  },
  data() {
    return {
      passwordHidden: {
        default: false,
        type: Boolean,
      },
      rpasswordHidden: {
        default: false,
        type: Boolean,
      },
      cpasswordHidden: {
        default: false,
        type: Boolean,
      },
      passwordText: "",
      rpasswordText: "",
      cpasswordText: "",
    };
  },
  methods: {
    hidePassword() {
      this.passwordHidden = true;
    },
    showPassword() {
      this.passwordHidden = false;
    },
    hideRPassword() {
      this.rpasswordHidden = true;
    },
    showRPassword() {
      this.rpasswordHidden = false;
    },
    hideCPassword() {
      this.cpasswordHidden = true;
    },
    showCPassword() {
      this.cpasswordHidden = false;
    },
  },
  computed: {
    currentPasswordValidation() {
      return this.cpasswordText == "current";
    },
    newPasswordValidation() {
      return this.passwordText.length != 0;
    },
    newPasswordAgainValidation() {
      return this.rpasswordText == this.passwordText;
    },
  },
};
</script>

<style scoped>
.password-field {
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
}

.password-field {
  width: 100%;
}

.password-icon {
  align-self: center;
  vertical-align: middle;
  text-align: center;
  font-size: 20px;
}

.change-password {
  margin-top: 8px;
  background-color: #fff;
  border: 1px solid #16c79a;
  color: #16c79a;
}
</style>