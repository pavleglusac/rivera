<template>
<div>
    <div style="margin-bottom: 10px;">
        <b-form-input
            id="search-filter"
            size="sm"
            placeholder="Search..."
            v-model="search"
        ></b-form-input>
    </div>
    <b-list-group>
      <b-list-group-item v-for="entity in subscribed" :key="entity.id" class="d-flex align-items-center">
        <b-avatar variant="info" :src='"http://localhost:8080" + entity.picture' class="mr-3"></b-avatar>
        <span class="mr-auto">{{entity.name}}</span>
        <b-button>Unsubscribe</b-button>
      </b-list-group-item>
    </b-list-group>
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
      loadSubscribed() {
        let that = this;
        this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                        }).then((resp) => {
            that.subscribed = [];
            this.$axios.get('/api/get-subscribed-entities?username=' + resp.data + 'search=' + that.search)
            .then(response => {
                that.subscribed = response.data;
            });
        }).catch((err) => {
            console.log(err);
        });
    },
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