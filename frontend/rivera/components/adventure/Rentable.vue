<template>
  <div class="w-100 ml-0 mr-0 h-100">
    <div class="w-100 pb-0 d-flex mb-5">
      <div class="d-flex flex-column" style="width: 50%; height: 100%">
        <MainCard
          :location="location"
          :description="description"
          :name="name"
          :score="score"
          :canBeChanged="!canBeChanged"
          :currentType = "currentType"
        />
        <div class="mt-10 h-25">
          <h6 class="mb-3">
            <font-awesome-icon icon="person" /> Capacity:
            <span class="text-muted"> {{ capacity }} people </span>
          </h6>
          <h6 class="mb-3">
            <font-awesome-icon icon="money-bill-wave" /> Price:
            <span class="text-muted">
              {{ perHour }}$ per hour / {{ perDay }}$ per day
            </span>
          </h6>
          <li v-for="tag in tags" :key="tag">
            <span class="tag">{{ tag }}</span>
          </li>
          <br />
          <div
            class="mt-3"
            @click="goToOwnerProfile"
            style="cursor: pointer"
            >
            <b-avatar
              variant="info"
              :src="owner.picture"
            ></b-avatar>
            <span class="mr-auto" style="font-weight: 500"
              >{{ owner.name }} {{ owner.surname }}</span
            >
          </div>
        </div>
      </div>
      <div class="d-flex flex-column mt-5" style="width: 50%; height: 100%">
        <img
          :src="pictures[0]"
          alt=""
          style="
            object-fit: cover;
            width: 100%;
            height: 100%;
            max-height: 500px;
          "
        />
      </div>
    </div>

    <h3 class="subtitle">Details</h3>

    <div class="w-100 pb-0 d-flex" style="height: 50vh">
      <div class="d-flex flex-column" style="width: 50%; height: 100%">
        <iframe
          :src="
            'https://maps.google.com/maps?q=' +
            encodeURIComponent(location + ',' + address) +
            '&t=&z=13&ie=UTF8&iwloc=&output=embed'
          "
          frameborder="0"
          style="border: none; border-radius: 10px; height: 100%"
          allowfullscreen
        ></iframe>
      </div>
      <div class="d-flex flex-column ml-5" style="width: 50%; height: 100%">
        <div v-if="currentType=='adventure'">
          <h6 class="mt-4"><font-awesome-icon icon="plus" /> Equipment:</h6>
          <div class="flex">
            <span class="tagish" v-for="e in equipment" :key="e"> {{ e }} </span>
          </div>
        </div>
        <h6 class="mt-4">
          <font-awesome-icon icon="book" /> Rules of conduct:
        </h6>
        <div class="flex">
          <span class="tagish" v-for="rule in rulesOfConduct" :key="rule">
            {{ rule }}
          </span>
        </div>
        <h6 class="mt-4">
          <font-awesome-icon icon="bell-concierge" /> Additional services:
        </h6>
        <div class="flex">
          <span class="tagish" v-for="service in services" :key="service">
            {{ service }}
          </span>
        </div>
        <div v-if="currentType=='cottage'">
        <h6 class="mt-4">
          <font-awesome-icon icon="bell-concierge" /> Rooms:
        </h6>
        <div class="flex">
          <span class="tagish" v-for="room in rooms" :key="room">
            {{ room }}
          </span>
        </div>
        </div>
        <div v-if="currentType=='boat'">
			  <div class="form-row">
				  <div class="form-group col-md-4">
            <h6 class="mt-4">
              <font-awesome-icon icon="bell-concierge" /> Length:
            </h6>
            <div class="flex">
              <span class="tagish">
              {{boatLength}}
              </span>
            </div>
            </div>
				<div class="form-group col-md-4">
            <h6 class="mt-4">
              <font-awesome-icon icon="bell-concierge" /> Capacity:
            </h6>
            <div class="flex">
              <span class="tagish">
              {{capacity}}
              </span>
            </div>
            </div>
				<div class="form-group col-md-4">
            <h6 class="mt-4">
              <font-awesome-icon icon="bell-concierge" /> Max speed:
            </h6>
            <div class="flex">
              <span class="tagish">
              {{maxSpeed}}
              </span>
            </div>
        </div>
            </div>
			  <div class="form-row">
				<div class="form-group col-md-4">
            <h6 class="mt-4">
              <font-awesome-icon icon="bell-concierge" /> Engine Power:
            </h6>
            <div class="flex">
              <span class="tagish">
              {{enginePower}}
              </span>
            </div>
            </div>
				<div class="form-group col-md-4">
            <h6 class="mt-4">
              <font-awesome-icon icon="bell-concierge" /> Number of engines:
            </h6>
            <div class="flex">
              <span class="tagish">
              {{enginesNumber}}
              </span>
            </div>
            </div>
				<div class="form-group col-md-4">
            <h6 class="mt-4">
              <font-awesome-icon icon="bell-concierge" /> Type:
            </h6>
            <div class="flex">
              <span class="tagish">
              {{type}}
              </span>
            </div>
            </div>
        </div>
        </div>
      </div>
    </div>

    <div class="w-100 pb-0 d-flex mt-5" style="max-height: 50vh">
      <div class="d-flex flex-column" style="width: 100%; height: 100%">
        <h3 class="subtitle">Gallery</h3>
        <div class="d-flex flex-column h-100">
          <Carousel :pictures="pictures.slice(1, pictures.lenght)" />
        </div>
      </div>
    </div>

    <div class="w-100 pb-0 d-flex mt-5">
      <div class="d-flex flex-column" style="width: 100%; height: 100%">
        <h3 class="subtitle">Calendar</h3>
        <div
          class="pl-5 w-100 justify-content-center d-flex mb-5"
          style="height: 80vh"
        >
          <div class="w-75">
            <Calendar :reservations="reservations" :currentType='currentType'/>
          </div>
        </div>
      </div>
    </div>

    <div class="w-100 pb-0 d-flex mt-5">
      <div class="d-flex flex-column" style="width: 100%; height: 100%">
        <h3 class="subtitle">Offers on discount</h3>
        <div v-if="discounts.length > 0" class="d-flex flex-column h-100">
          <div class="row ml-2">
            <Discount
              class="m-1 col-2"
              v-for="discount in discounts"
              :key="discount.id"
              :discount="discount"
              :openModal="openModal"
            />
          </div>
        </div>
        <div v-else class="d-flex flex-column h-100">
          <p>There are no offers on discount right now. If you want to receive email notification when owner creates a special offer, please subscribe to this entity.</p>
        </div>
            <b-modal id="reservedDiscountModal" title="Congratulations!">
      <p class="my-4">You have successfully reserved your appointment.</p>
    </b-modal>
      </div>
    </div>

    <div class="mt-5 w-100 mb-5">
    <h3 class="subtitle">Reviews</h3>
      <div v-if="reviews.length > 0" class="row h-100 gx-0">
        <div class="col-6 gx-0 pr-0">
          <div>
            <Review
              class="ml-2 mt-1 w-100 h-100"
              v-for="review in reviews
                .slice()
                .splice(0, Math.ceil(reviews.length / 2))"
              :key="review.id"
              :review="review"
            />
          </div>
        </div>
        <div class="col-6 gx-0">
          <div>
            <Review
              class="mt-1 w-100 h-100"
              v-for="review in reviews
                .slice()
                .splice(-Math.ceil(reviews.length / 2))"
              :key="review.id"
              :review="review"
            />
          </div>
        </div>
      </div>
      <div v-else>
        <p>No one reviewed this entity yet.</p>
      </div>
    </div>
  </div>
</template>

<script>
import MainCard from "./MainCard.vue";
import Carousel from "./Carousel.vue";
import Calendar from "./Calendar.vue";
import Discount from "./Discount.vue";
import Review from "./Review.vue";

export default {
  components: { MainCard, Carousel, Calendar, Discount, Review },
  data() {
    return {
      id: "",
      location: "",
      name: "",
      score: 0,
      capacity: 0,
      description: "",
      pictures: [],
      tags: [],
      owner: {
        picture: "",
        name: "",
        surname: "",
        biography: "",
      },
      discounts: [],
      reviews: [],
      services: [],
      rulesOfConduct: [],
      cancellationTerms: [],
      equipment: [],
      address: "",
      perHour: 0,
      perDay: 0,
      biography: "",
      reservations: [],
      canBeChanged: false,
      currentType: "",
      rooms: [],
      type: "",
      boatLength: "",
      enginesNumber: "",
      enginePower: "",
      maxSpeed : "",
    };
  },
  mounted() {
    let that = this;
		this.$axios
        .get('/api/get-type-of-rentable?id=' + this.$route.params.rentable)
        .then((resp) => {

          that.currentType = resp.data;
          switch(resp.data){
            case "cottage":  {this.loadCottage(); break;}
            case "adventure": {this.loadAdventure(); break;}
            case "boat": {this.loadBoat(); break;}
          }
          console.log(this.$route.params.rentable);
          console.log(resp.data);
          console.log(that.currentType);
        })

  },
  methods: {
    goToOwnerProfile() {
      //this.$router.push({ path: "/owner/" + owner.id });
    },
    openModal() {
      this.$bvModal.show('reservedDiscountModal');
    },
    loadAdventure(){
    console.log("AAAAAAAAAAAAABRUH");
    let that = this;
    this.$axios
      .get("/api/get-full-adventure?id=" + this.$route.params.rentable)
      .then((resp) => {
        let adventure = resp.data;
        console.log(adventure);
        that.reviews = adventure.reviews;
        that.reviews.forEach(
          (x) => (x.client.photo = "http://localhost:8080" + x.client.photo)
        );
        that.name = adventure.name;
        that.location = adventure.city + ", " + adventure.country;
        that.address = adventure.address;
        that.services = adventure.services;
        that.name = adventure.name;
        that.perHour = adventure.perHour;
        that.perDay = adventure.perDay;
        that.description = adventure.description;
        that.rulesOfConduct = adventure.rulesOfConduct;
        that.cancellationTerms = adventure.cancellationTerms;
        that.services = adventure.services;
        that.tags = adventure.tags;
        that.canBeChanged = !adventure.canBeChanged;
        that.discounts = adventure.discounts;
        that.discounts.forEach(
          (x) => (x.start = moment(x.start).format("DD/MM/YY HH:MM"))
        );
        that.discounts.forEach(
          (x) => (x.end = moment(x.end).format("DD/MM/YY HH:mm"))
        );
        that.score = adventure.averageScore;
        that.pictures.push(
          ...adventure.pictures.map((x) => "http://localhost:8080" + x)
        );
        that.id = adventure.id;
        that.equipment = adventure.equipment;
        that.owner = adventure.owner;
        that.capacity = adventure.capacity;
        that.owner.picture = "http://localhost:8080" + that.owner.picture;
        that.reservations = adventure.reservations;
      });
    },
    loadCottage(){
    console.log("AAAAAAAAAAAAAKRUH");      
		let that = this;
		this.$axios
        .get('/api/cottage/get-full-cottage?id=' + this.$route.params.rentable)
        .then((resp) => {
            let cottage = resp.data;
            console.log("TWETEWETWTETWTEW\n\n"+cottage+"\n\nTWETEWETWTETWTEW");
            that.reviews = cottage.reviews;
            that.reviews.forEach(x => x.client.photo = "http://localhost:8080" + x.client.photo)
            that.name = cottage.name;
            that.location = cottage.address+","+cottage.city+","+cottage.country;
            that.services = cottage.services;
            that.name = cottage.name;
            that.perHour = cottage.perHour;
            that.perDay = cottage.perDay;
            that.address = cottage.address;
            that.description = cottage.description;
            that.rulesOfConduct = cottage.rulesOfConduct;
            that.cancellationTerms = cottage.cancellationTerms;
            that.services = cottage.services;
            that.tags = cottage.tags;
            that.canBeChanged = !cottage.canBeChanged;
            console.log(that.canBeChanged +"------");
            that.discounts = cottage.discounts;
            that.discounts.forEach(x => x.start = moment(x.start).format("DD/MM/YY HH:MM"))
            that.discounts.forEach(x => x.end = moment(x.end).format("DD/MM/YY HH:mm"))
            that.pictures.push(...cottage.pictures.map(x => "http://localhost:8080" + x));
			      that.id = cottage.id;
            that.owner = cottage.owner;
            that.owner.picture = "http://localhost:8080" + that.owner.picture;
            var stringRooms = cottage.rooms.split(';');
            for(var room of stringRooms){
              if(!room.split(',')[0]){
                break;
              }
                var roomString = room.split(',')[0]+" bedded - " + room.split(',')[1];
                (parseInt(room.split(',')[1])>1) ? roomString += " rooms" : roomString += " room"; 
                that.rooms.push(roomString);
              console.log(roomString+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            }
        });
    },
    loadBoat(){
    console.log("AAAAAAAAAAAAAKRUH");      
		let that = this;
		this.$axios
        .get('/api/boat/get-full-boat?id=' + this.$route.params.rentable)
        .then((resp) => {
            let boat = resp.data;
            console.log(resp.data.maxSpeed+"AUISDHIUASDHAISUDH");
            that.reviews = boat.reviews;
            that.reviews.forEach(x => x.client.photo = "http://localhost:8080" + x.client.photo)
            that.name = boat.name;
            that.location = boat.address+","+boat.city+","+boat.country;
            that.services = boat.services;
            that.name = boat.name;
            that.perHour = boat.perHour;
            that.perDay = boat.perDay;
            that.address = boat.address;
            that.description = boat.description;
            that.rulesOfConduct = boat.rulesOfConduct;
            that.cancellationTerms = boat.cancellationTerms;
            that.services = boat.services;
            that.tags = boat.tags;
            that.enginesNumber = boat.enginesNumber;
            that.enginePower = boat.enginePower;
            that.type= boat.type;
            that.maxSpeed = boat.maxSpeed;
            that.boatLength= boat.length;
            that.capacity = boat.capacity
            that.canBeChanged = !boat.canBeChanged;
            console.log(that.canBeChanged +"------");
            that.discounts = boat.discounts;
            that.discounts.forEach(x => x.start = moment(x.start).format("DD/MM/YY HH:MM"))
            that.discounts.forEach(x => x.end = moment(x.end).format("DD/MM/YY HH:mm"))
            that.pictures.push(...boat.pictures.map(x => "http://localhost:8080" + x));
			      that.id = boat.id;
            that.owner = boat.owner;
            that.owner.picture = "http://localhost:8080" + that.owner.picture;
        });
    }
  },
};
</script>

<style>
#review_container {
  -moz-column-count: 1;
  -webkit-column-count: 1;
  column-count: 1;
  -moz-column-gap: 5px;
  -webkit-column-gap: 5px;
  column-gap: 5px;
  margin: 0 auto;
  -webkit-column-break-inside: avoid;
  -moz-column-break-inside: avoid;
  -moz-page-break-inside: avoid;
  page-break-inside: avoid;
  break-inside: avoid-column;
}

.tagish {
  padding: 10px;
  background-color: #ebecf0;
  border: none;
  border-radius: 10px;
  margin: 0;
  display: inline-block;
  list-style-type: none;
  margin: 5px;
  font-weight: 500;
}

ul {
  margin: 0;
  padding: 0;
  list-style-position: inside;
}
li {
  padding: 0;
  display: inline-block;
  list-style-type: none;
}

.subtitle {
  font-family: "Rubik", sans-serif;
  margin-bottom: 15px;
}

.tag {
  background-color: var(--light-blue-color);
  border: none;
  border-radius: 5px;
  padding: 5px;
  font-weight: 500;
  margin: 3px;
  text-align: center;
  color: #fff;
  display: inline;
  font-size: 15px;
}
</style>