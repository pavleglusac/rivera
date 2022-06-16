<template>
  <div id="testimonials">
    <div class="testimonial-box-container w-100 m-0 h-25">
      <div class="testimonial-box w-100">
        <div class="box-top">
          <div class="profile">
            <div class="profile-img">
              <img :src="'http://localhost:8080' + review.clientImage" />
            </div>
            <div class="name-user">
              <strong
                >{{ review.clientName }} {{ review.clientSurname }}</strong
              >
              <span>{{review.clientUsername}}</span>
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
import Rating from "../view-rentable/Rating.vue";
export default {
  props: ["review"],
  components: { Rating },
  mounted() {},
  data() {
    return {};
  },
  watch: {
	review(val, oldVal) {
		this.review.clientName = val.clientName;
		this.review.clientImage = val.clientImage;
		this.review.score = val.score;
		this.review.text = val.text;
	}
  }
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
  width: 50vw;
}

.testimonial-box-container {
  /* display: flex; */
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  width: 100%;
}
.testimonial-box {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 5px 0 rgba(0, 0, 0, 0.19);
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
  font-size: 0.8rem;
}
.box-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.client-comment p {
  font-size: 0.9rem;
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