<template>
<div data-bs-spy="scroll">

  <!--landing-->
    <section id="home" class="bg-cover landing cover1">
        <div class="overlay"></div>
        <b-container>
          <h1 class="title" data-aos="zoom-in">We help you find the adventure</h1>
          <p class="description" data-aos="fade-up">
            If you are looking for a nice place to stay, boat to ride or some fish to catch, we got you covered!<br>
            Also, our job is to help instructors and owners manage their reservations and calendars.</p>
          <b-button class="get-started">Get started</b-button>
        </b-container>
    </section>

  <!--services-->
    <section id="services" class="text-center">
        <b-container>
            <div>
                <div class="col-12 section-intro text-center" data-aos="fade-up">
                    <h1>Our Services</h1>
                    <div class="divider"></div>
                </div>
            </div>
            <div class="row g-4">
                <div class="col-md-4" data-aos="zoom-in">
                    <div class="service">
                        <div class="service-img">
                            <img src="../assets/fishing.jpg" alt="">
                        </div>
                        <h5 class="mt-3">Fishing classes</h5>
                        <p>Whether you have fished before or for the first time, our instructions are here to help you improve.</p>
                    </div>
                </div>
                <div class="col-md-4" data-aos="zoom-in">
                    <div class="service">
                        <div class="service-img">
                            <img src="../assets/boat.jpg" alt="">
                        </div>
                        <h5 class="mt-3">Boats renting</h5>
                        <p>If you are ready to embark on a river adventure or a relaxing voyage, take a look at our offer of boats.</p>
                    </div>
                </div>
                <div class="col-md-4" data-aos="zoom-in">
                    <div class="service"> 
                        <div class="service-img">
                            <img src="../assets/cottage.jpg" alt="">
                        </div>
                        <h5 class="mt-3">Cottage booking</h5>
                        <p>Find the perfect vacation spot with a beautiful view, in the fresh air and away from the city.</p>
                    </div>
                </div>
            </div>
        </b-container>
    </section>

   <!--owners-->
    <section id="about" class="bg-cover cover2">
        <div class="overlay" style="opacity: 0.5;"></div>
        <div class="container text-white text-center">
            <div class="row">
                <div class="col-12 section-intro text-center" data-aos="fade-up">
                    <h1 style="color:#fff;">Grow your business</h1>
                    <div class="divider"></div>
                    <p style="color:#fff;">We can offer you opportunity to grow your buisness by adding your entities to our system. 
                    <br>Your time management will be much easier with our application. You can go throw calendar and manage all your obligations.
                    Don't worry about paying. We got you covered with that! </p>
                </div>
            </div>
        </div>
    </section>

    <!--features-->
    <section id="features" class="text-center">
        <div class="container">
            <div class="row">
                <div class="col-12 section-intro text-center" data-aos="fade-up">
                    <h1>Our Features</h1>
                    <div class="divider"></div>
                </div>
            </div>
            <div class="row gx-4 gy-5">
                <div class="col-md-6 feature" data-aos="fade-up">
                    <div class="icon"><i class="icon-tools"></i></div>
                    <h5 class="mt-4 mb-3">Managing your calendar</h5>
                    <p>You will have full picture of your occupations right in front of you. Managing your calendar was never this easy!</p>
                </div>
                <div class="col-md-6 feature" data-aos="fade-up">
                    <div class="icon"><i class="icon-browser"></i></div>
                    <h5 class="mt-4 mb-3">Managing finances</h5>
                    <p>With our services, you do not need to worry about payments. Clients will be paying online, and your money will go right to you.</p>
                </div>
                <div class="col-md-6 feature" data-aos="fade-up">
                    <div class="icon"><i class="icon-calendar"></i></div>
                    <h5 class="mt-4 mb-3">Trustworthy clients</h5>
                    <p>Before you meet your client in person, you will have access to their data. This way, you can be sure that you can trust someone.</p>
                </div>
                <div class="col-md-6 feature" data-aos="fade-up">
                    <div class="icon"><i class="icon-camera"></i></div>
                    <h5 class="mt-4 mb-3">Review everything</h5>
                    <p>You and your entites will get reviews and scores after each reservation. This help us promote the best services!</p>
                </div>
            </div>
        </div>
    </section>

    <!--blog-->
    <section id="blog" class="bg-light">
        <div class="container">
            <div class="row">
                <div class="col-12 section-intro text-center" data-aos="fade-up">
                    <h1>Trending</h1>
                    <div class="divider"></div>
                    <p>Check out some of our most popular entites this month!</p>
                </div>
            </div>
            <div class="row text-start">
                <div class="col-md-4" data-aos="fade-up">
                    <img src="../assets/fishing.jpg" alt="">
                    <div class="mt-4">
                        <div class="text-secondary" style="font-size: 0.8em;">
                            <font-awesome-icon icon="star" /> &nbsp;{{ boat.averageScore }}
                        </div>
                        <span>{{boat.city}}, {{boat.country}}</span>
                        <h5 class="mt-1 mb-2"><a href="#">{{boat.name}}</a></h5>
                        <p v-if="boat.description.length<200" style="font-size: 0.8em;">{{boat.description}}</p>
                        <p v-else style="font-size: 0.8em;">{{boat.description.substring(0,200)}}...</p>
                    </div>
                </div>
                <div class="col-md-4" data-aos="fade-up">
                    <img src="../assets/fishing.jpg" alt="">
                    <div class="mt-4">
                        <div class="text-secondary" style="font-size: 0.8em;">
                            <font-awesome-icon icon="star" /> &nbsp;{{ adventure.averageScore }}
                        </div>
                        <span>{{adventure.city}}, {{adventure.country}}</span>
                        <h5 class="mt-1 mb-2"><a href="#">{{adventure.name}}</a></h5>
                        <p v-if="adventure.description.length<200" style="font-size: 0.8em;">{{adventure.description}}</p>
                        <p v-else style="font-size: 0.8em;">{{adventure.description.substring(0,200)}}...</p>
                    </div>
                </div>
                <div class="col-md-4" data-aos="fade-up">
                    <img src="../assets/fishing.jpg" alt="">
                    <div class="mt-4">
                        <div class="text-secondary" style="font-size: 0.8em;">
                            <font-awesome-icon icon="star" /> &nbsp;{{ cottage.averageScore }}
                        </div>
                        <span>{{cottage.city}}, {{cottage.country}}</span>
                        <h5 class="mt-1 mb-2"><a href="#">{{cottage.name}}</a></h5>
                        <p v-if="cottage.description.length<200" style="font-size: 0.8em;">{{cottage.description}}</p>
                        <p v-else style="font-size: 0.8em;">{{cottage.description.substring(0,200)}}...</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!--footer-->
    <footer class="py-4">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <p class="mb-0">Rivera Copyright © 2022. Designed By Pavle, Dejan and Andjela</p>
                </div>
            </div>
        </div>
    </footer>

</div>
</template>

<script>
import AOS from 'aos'
import 'aos/dist/aos.css'

export default {
  components: {  },
  mounted() {
    AOS.init();
    let that = this;
    console.log("hello")
    /*this.$axios.post(`/api/search-adventures?&numberOfResults=1&orderBy=${score-d}`)
    .then(response => {
      console.log(response);
      that.adventure = response.data;
      that.boat = response.data;
      that.cottage = response.data;
    });
    this.$axios.post(`/api/search-boats?&numberOfResults=1&orderBy=${score-d}`)
    .then(response => {
      that.adventure = response.data;
      console.log(response);
    });
    this.$axios.post(`/api/search-cottages?&numberOfResults=1&orderBy=${score-d}`)
    .then(response => {
      that.adventure = response.data;
      console.log(response);
    });*/
  },
  data() {
    return {
      boat: {
        pictures: [".\assets\fishing.jpg"],
        description: "This is the best thing ever.",
        name: "Fishing - Duvan",
        country: "Serbia",
        city: "Novi Sad",
        averageScore: 5.0,
      },
      adventure: {
        pictures: ["../assets/fishing.jpg"],
        description: "This is the best thing ever.",
        name: "Fishing - Duvan",
        country: "Serbia",
        city: "Novi Sad",
        averageScore: 5.0,
      },
      cottage: {
        pictures: ["../assets/fishing.jpg"],
        description: "This is the best thing ever.",
        name: "Fishing - Duvan",
        country: "Serbia",
        city: "Novi Sad",
        averageScore: 5.0,
      },
    }
  }

}
</script>

<style>
.get-started {
  background: linear-gradient(90deg, var(--light-blue-color) 0%, var(--prime-color) 100%);
  border: none;
  font-size: 23px;
}
.title {
  font-size: 60px;
  color: #fff;
}
.description {
  font-size: 20px;
  color: #fff;
}
section {
    padding-top: 80px;
    padding-bottom: 80px;
    z-index: 2;
    position: relative;
}
.overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, var(--light-blue-color) 0%, var(--prime-color) 100%);
    z-index: -1;
    opacity: 0.25;
}
.bg-cover {
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    background-attachment: fixed;
    color: white;
}
.landing {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}
.cover1 {
    background-image: url(./assets/landing.jpg);
}
.cover2 {
    background-image: url(./assets/buisness.png);
}
h1,h2,h3,h4,h5,h6 {
    font-weight: 900 !important;
}
section img {
  width: 100%;
}
.section-intro {
    margin-bottom: 40px;
}
.section-intro .divider {
    height: 10px;
    width: 150px;
    background: linear-gradient(90deg, var(--light-blue-color) 0%, var(--prime-color) 100%);
    margin: 16px auto;
}
.service .service-img {
    position: relative;
}
</style>