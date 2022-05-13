<template>
  <b-card no-body class="overflow-hidden mb-3" style="margin-top: 10px">
    <b-row no-gutters>
      <b-col md="4">
        <b-card-img id="preview" v-if="url" :src="url" alt="Profile photo" class="rounded-0"></b-card-img>
      </b-col>
      <b-col md="8" center>
        <b-card-body :title="client.fullName">
          <b-card-text>
          <p class="text-muted">username: {{client.username}}<br>email: {{client.email}}</p>
          <b-button pill class="profile-btn" size="sm" v-b-modal.changePicture><b-icon icon="person-circle"></b-icon> Change profile photo</b-button>
          <b-modal id="changePicture" title="Choose your new profile photo">
            <b-form-file id="photoFile" @change="onFileChange" accept="image/*"></b-form-file>
          </b-modal>
          <b-button pill class="profile-btn" size="sm"><b-icon icon="trash-fill"></b-icon> Delete account</b-button>
        </b-card-text>
        </b-card-body>
      </b-col>
    </b-row>
  </b-card>
</template>

<script>
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
        fullName: "",
        username: "",
        photo: "",
        email: "",
        description: "",
        name: "",
        surname: "",
      },
      url: "",
      photoData: null,
    };
  },
  mounted() {
    this.getLoggedUser();
  },
  computed: {
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
      that.client.fullName = client.name + ' ' + client.surname;
      that.client.name = client.name;
      that.client.username = client.username;
      that.client.surname = client.surname;
      that.client.email = client.email;
      that.client.photo = client.photo;
      that.url = "http://localhost:8080" + client.photo;
    },
    changeProfilePhoto(pic) {
			this.client.photo = pic;
			console.log("PARENT UPDATE PICS");
			console.log(pic);
		},
    onFileChange(e) {
      const file = e.target.files[0];
      this.photoData = file;
      this.url = URL.createObjectURL(file);
    }
  }
};
</script>

<style scoped>
#preview {
  width: 200px;
  height: 200px;
  object-fit: cover;
}
.profile-btn {
  font-size: 12px;
  background-color: var(--dark-blue-color);
  border: none;
}
.profile-btn:hover {
  opacity: 0.9;
}
</style>