<template>
  <div>
    <Navbar :currentPage="'profile'" />
    <b-card no-body class="mb-3" style="margin: 40px; margin-top: 10px">
      <div class="w-100 p-0" style="padding: 0">
        <div class="w-100">
          <img
            :src="cover"
            alt=""
            class="w-100 h-25"
            style="object-fit: cover; max-height: 30vh"
          />
        </div>
        <div class="d-flex justify-content-center mt-n5">
          <img
            ref="profilePhoto"
            :src="profilePhoto"
            alt=""
            style="
              border-radius: 50%;
              max-height: 25vh;
              object-fit: cover;
              margin-top: -10vh;
              border: 2px solid white;
            "
            @error="noProfilePhoto"
          />
        </div>
        <div class="d-flex justify-content-center">
          <div class="display-4">{{ name }} {{ surname }}</div>
        </div>
        <div
          class="d-flex justify-content-center"
          style="font-size: 1.4em; font-weight: 200"
        >
          <div style="">{{ username }}</div>
        </div>
        <div
          class="d-flex justify-content-center"
          style="font-size: 1.4em; font-weight: 400"
        >
          <span>
            <font-awesome-icon
              v-if="role == 'Fishing Instructor'"
              icon="fish"
            />
            <font-awesome-icon
              v-else-if="role == 'Cottage Owner'"
              icon="house"
            />
            <font-awesome-icon v-else-if="role == 'Boat Owner'" icon="boat" />
            <font-awesome-icon v-else icon="fish" />
            {{ role }} &nbsp;</span
          >
          <span :style="'color: ' + memberColor + ';'"
            ><font-awesome-icon icon="user" /> {{ member }}</span
          >
        </div>
        <div
          class="d-flex justify-content-center mt-4"
          style="font-size: 1.2em; font-weight: 450"
        >
          <div style="text-align: center; max-width: 33vw">
            {{ description }}
          </div>
        </div>
      </div>
      <!--  -->
      <OwnerBody v-if="role != 'Client'" :role="role" />
      <ClientBody v-else />
      <!--  -->
    </b-card>
  </div>
</template>

<script>
import Navbar from "../../components/navbar.vue";
import OwnerBody from "../../components/profile/OwnerBody.vue";
import ClientBody from "../../components/profile/ClientBody.vue";

export default {
  components: { Navbar, OwnerBody, ClientBody },
  data() {
    return {
      role: "Fishing Instructor",
      cover: "",
      profilePhoto:
        "https://images.unsplash.com/photo-1520451644838-906a72aa7c86?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=550&q=80",
      name: "Petar",
      surname: "Peric",
      username: "@pera",
      description:
        "Nam fringilla, nisi et sagittis luctus, sem velit rhoncus nisi, vel ultrices metus sem et diam. \
      Praesent velit turpis, vestibulum vitae tellus feugiat, facilisis tristique augue. Vivamus porttitor ut magna in faucibus ",
      role: "Fishing Instructor",
      member: "Gold member",
      memberColor: "#fca311",
      host: "http://localhost:8080",
    };
  },
  mounted() {
    let id = this.$route.params.profile;
    let that = this;
    this.$axios.get("/api/profile?id=" + id).then((resp) => {
      that.name = resp.data.name;
      that.username = "@" + resp.data.username;
      that.surname = resp.data.surname;
      that.role = that.prettierRoleName(resp.data.role);
      that.member = resp.data.membership;
      that.memberColor = resp.data.membershipColor;
      that.profilePhoto = that.host + resp.data.profilePhoto;
      that.description = resp.data.description;
      console.log(resp.data);
      console.log(that.member);
      console.log(that.memberColor);
      that.setCoverPhoto();
    });
  },

  methods: {
    prettierRoleName(name) {
      if (name == "ROLE_FISHING_INSTRUCTOR") {
        return "Fishing Instructor";
      } else if (name == "ROLE_COTTAGE_OWNER") {
        return "Cottage Owner";
      } else if (name == "ROLE_BOAT_OWNER") {
        return "Boat Owner";
      } else if (name == "ROLE_CLIENT") {
        return "Client";
      }
    },
    noProfilePhoto() {
      this.$refs.profilePhoto.src =
        "https://www.innovaxn.eu/wp-content/uploads/blank-profile-picture-973460_1280.png";
    },
    setCoverPhoto() {
      if (this.role == "Fishing Instructor") {
        this.cover =
          "https://images.unsplash.com/photo-1499403474843-04e72c14df8e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80";
      } else if (this.role == "Cottage Owner") {
        this.cover =
          "https://images.unsplash.com/photo-1580202313707-46a966af5c6f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=862&q=80";
      } else if (this.role == "Boat Owner") {
        this.cover =
          "https://images.unsplash.com/photo-1499403474843-04e72c14df8e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80";
      } else {
        this.cover =
          "https://images.unsplash.com/photo-1527549993586-dff825b37782?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80";
      }
    },
  },
};
</script>

<style>
.menubtn {
  font-size: 1.5em;
  font-weight: 550;
}

.nav-mn {
  font-size: 1.5em;
  font-weight: 550;
}
</style>
