<template>
  <div id="testimonials">
    <div class="testimonial-box-container w-100 m-1 h-25">
      <div class="testimonial-box w-100">
        <div class="box-top">
          <div class="profile">
            <div class="profile-img">
              <img :src="review.client.photo" />
            </div>
            <div class="name-user" style="cursor: pointer" @click="goToProfile">
              <strong
                >{{ review.client.name }} {{ review.client.surname }}</strong
              >
              <span>@{{review.client.username}}</span>
            </div>
          </div>
          <div class="reviews">
            <Rating :score="review.score" />
          </div>
        </div>
        <div class="client-comment w-100">
          <p class="w-100">{{ review.text }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Rating from "./Rating.vue";
export default {
  props: ["review"],
  components: { Rating },
  mounted() {
    console.log(this.review);
    if(!this.review.client.photo.startsWith(process.env.backend))
      this.review.client.photo = process.env.backend + this.review.client.photo;
  },
  methods: {
        goToProfile() {
      this.$router.push({ path: "/profile/" + this.review.client.username });
    },
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
a {
  text-decoration: none;
}
#testimonials {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 50%;
}

.testimonial-box-container {
  /* display: flex; */
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  width: 100%;
}
.testimonial-box {
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.2), 0 3px 5px 0 rgba(0, 0, 0, 0.19);
  background-color: #ffffff;
  padding: 20px;
  cursor: pointer;
}
.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 10px;
}
.profile-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}
.profile {
  display: flex;
  align-items: center;
}
.name-user {
  display: flex;
  flex-direction: column;
}
.name-user strong {
  color: #3d3d3d;
  font-size: 1.1rem;
  letter-spacing: 0.5px;
}
.name-user span {
  color: #979797;
  font-size: 1rem;
}
.box-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.client-comment p {
  font-size: 1rem;
  color: #4b4b4b;
  width: 90%;
  text-align: left;
}
.testimonial-box:hover {
  transform: translateY(-10px);
  transition: all ease 0.3s;
}
::selection {
  color: #ffffff;
  background-color: #252525;
}
</style>