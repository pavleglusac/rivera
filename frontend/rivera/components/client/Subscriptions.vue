<template>
<div>
    <div style="margin-bottom: 10px;">
        <b-form-input
            id="search-filter"
            size="sm"
            placeholder="Search..."
            v-model="search"
            v-on:input="loadSubscribed"
        ></b-form-input>
    </div>
    <b-list-group v-if="subscribed.length > 0">
      <b-list-group-item v-for="entity in subscribed" :key="entity.id" class="d-flex align-items-center">
        <b-avatar variant="info" :src='process.env.backend + entity.profilePicture' class="mr-3"></b-avatar>
        <span @click="goToProfile(entity)" style="cursor: pointer" class="mr-auto">
        <font-awesome-icon v-if="entity.kind == 'ADVENTURE'" icon="fish" />
        <font-awesome-icon v-else-if="entity.kind == 'COTTAGE'" icon="house" />
        <font-awesome-icon v-else icon="sailboat" /> {{entity.name}}</span>
        <b-button size="sm" @click="unsubscribe(entity.id)">Unsubscribe</b-button>
      </b-list-group-item>
    </b-list-group>
    <p v-else>You haven't subscribed to any entities yet.</p>
</div>
</template>

<script>
export default {
  data() {
    return {
      subscribed: [],
      clientUsername: "",
      search: ""
    };
  },
  mounted() {
    this.loadSubscribed();
  },
  methods: {
    goToProfile(entity) {
      this.$router.push({ path: "/rentable/" + entity.id });
    },
    loadSubscribed() {
        this.subscribed = [];
        this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                        }).then((resp) => {
            this.$axios.get('/api/get-subscribed-entities?username=' + resp.data + '&search=' + this.search)
            .then(response => {
                this.subscribed = response.data;
                console.log(this.subscribed);
            });
            }).catch((err) => {
                console.log(err);
            });
    },
    unsubscribe(id) {
        let that = this;
        this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
        }).then((resp) => {
            this.$axios.post('/api/unsubscribe?username=' + resp.data + '&id=' + id)
                .then((resp) => {
                    that.loadSubscribed();
                }).catch((err) => {
                    console.log(err);   
                });
            });
    }
  },
}
</script>

<style>
.subscriptions {
    font-size: 10px;
    color: var(--test-color);
    font-weight: 500;
}
</style>