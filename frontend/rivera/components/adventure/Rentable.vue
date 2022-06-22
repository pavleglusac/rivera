<template>
  <div class="w-100 ml-0 mr-0 h-100">
    <div class="w-100 pb-0 mb-5">
      <MainCard
        :location="location"
        :description="description"
        :name="name"
        :score="score"
        :canBeChanged="!canBeChanged"
        :isOwner="canChange"
        :currentType="currentType"
        :openReservationsModal="openReservationsModal"
      />
      <h2 id="name">{{ name }}</h2>
      <div>
        <p style="font-size: 17px; font-weight: 600" class="p-0 mb-1">
          <font-awesome-icon icon="star" />&nbsp;{{ score }}
          <span class="text-muted"> ({{ reviews.length }} reviews)</span>
          &nbsp;&nbsp;&nbsp;
          <font-awesome-icon icon="location-dot" />&nbsp;{{ location }}
          &nbsp;&nbsp;&nbsp;
          <font-awesome-icon icon="person" /> Capacity: {{ capacity }}
          <span class="text-muted"> people </span>
          &nbsp;&nbsp;&nbsp;
          <font-awesome-icon icon="money-bill-wave" /> Price:

          {{ perHour }}$ <span class="text-muted">per hour</span> /
          {{ perDay }}$ <span class="text-muted">per day</span>
        </p>
      </div>
      <hr class="w-100" style="margin-bottom: -20px" />
      <b-row class="mt-5">
        <b-col md="6">
          <Gallery
            style="margin-left: -5px"
            :openGalleryModal="openGalerry"
            :pictures="pictures"
          />
          <hr class="w-100" style="margin-top: -20px" />
          <div style="display: inline" class="w-100">
            <h4 class="subtitle">Special Offers
              <font-awesome-icon
                icon="calendar-plus"
                v-if="canChange"
                style="
                  float: right;
                  width: 30px;
                  height: 30px;
                  cursor: pointer;
                  color: #16c79a;
                "
                @click="showAddDiscountModal"
              />
            </h4>
          </div>
          <div v-if="discounts.length > 0" class="w-100">
            <Discount
              v-for="discount in discounts"
              :key="discount.id"
              :discount="discount"
              :isOwner="canChange"
              :openCantReserveModal="openCantReserve"
              :openDeletedDiscountModal="openDeletedDiscount"
              :openModal="openModal"
            />
          </div>
          <div v-else-if="canChange" class="d-flex flex-column">
            <p>
              There are no offers on discount right now. You can add them by
              clicking on the green calendar button.
            </p>
          </div>
          <div v-else class="d-flex flex-column">
            <p>
              There are no offers on discount right now. If you want to receive
              email notification when owner creates a special offer, please
              subscribe to this entity.
            </p>
          </div>
        </b-col>

        <b-col md="6">
          <div class="description mb-4">
            <h4 class="subtitle">About</h4>
            <p>{{ description }}</p>
            <div
              @click="goToOwnerProfile"
              style="display: flex; cursor: pointer"
            >
              <b-avatar
                style="height: 40px; width: 40px; margin-right: 5px"
                :src="owner.picture"
              ></b-avatar>
              <p>{{ owner.name }} {{ owner.surname }}</p>
            </div>
            <li v-for="tag in tags" :key="tag">
              <span class="tag">{{ tag }}</span>
            </li>
          </div>
          <hr class="w-100" />
          <div v-if="currentType == 'adventure'">
            <h6 class="mt-4"><font-awesome-icon icon="plus" /> Equipment:</h6>
            <div class="flex">
              <span class="tagish" v-for="e in equipment" :key="e">
                {{ e }}
              </span>
            </div>
          </div>
          <h6 class="mt-2">
            <font-awesome-icon icon="book" /> Rules of conduct:
          </h6>
          <div class="flex">
            <span class="tagish" v-for="rule in rulesOfConduct" :key="rule">
              {{ rule }}
            </span>
          </div>
          <h6 class="mt-2">
            <font-awesome-icon icon="bell-concierge" /> Additional services:
          </h6>
          <div class="flex">
            <span class="tagish" v-for="service in services" :key="service">
              {{ service }}
            </span>
          </div>
          <div v-if="currentType == 'cottage'">
            <h6 class="mt-2">
              <font-awesome-icon icon="door-closed" /> Rooms:
            </h6>
            <div class="flex">
              <span class="tagish" v-for="room in rooms" :key="room">
                {{ room }}
              </span>
            </div>
          </div>
          <div v-if="currentType == 'boat'">
            <div class="form-row">
              <div class="form-group col-md-4">
                <h6 class="mt-2"><font-awesome-icon icon="ruler" /> Length:</h6>
                <div class="flex">
                  <span class="tagish"> {{ boatLength }} m </span>
                </div>
              </div>
              <div class="form-group col-md-4">
                <h6 class="mt-2">
                  <font-awesome-icon icon="gauge" /> Max speed:
                </h6>
                <div class="flex">
                  <span class="tagish"> {{ maxSpeed }} knots </span>
                </div>
              </div>
              <div class="form-group col-md-4">
                <h6 class="mt-2"><font-awesome-icon icon="anchor" /> Type:</h6>
                <div class="flex">
                  <span class="tagish">
                    {{ type }}
                  </span>
                </div>
              </div>
            </div>
            <div class="form-row">
              <div class="form-group col-md-4">
                <h6>
                  <font-awesome-icon icon="bell-concierge" /> Engine Power:
                </h6>
                <div class="flex">
                  <span class="tagish"> {{ enginePower }} kW </span>
                </div>
              </div>
              <div class="form-group col-md-4">
                <h6>
                  <font-awesome-icon icon="bell-concierge" /> Number of engines:
                </h6>
                <div class="flex">
                  <span class="tagish">
                    {{ enginesNumber }}
                  </span>
                </div>
              </div>
            </div>
          </div>
          <div class="d-flex flex-column mt-3">
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
          <hr class="w-100" />
          <h4 class="subtitle">Reviews</h4>
          <div v-if="reviews.length > 0">
            <Review
              class="ml-2 mt-1 w-100 h-100"
              v-for="review in reviews"
              :key="review.id"
              :review="review"
            />
          </div>
          <div v-else>
            <p>No one reviewed this entity yet.</p>
          </div>
        </b-col>
      </b-row>
    </div>

    <b-modal id="gallery" size="xl" hide-footer title="Gallery">
      <div class="gallery-row">
        <div class="gallery-column">
          <img v-for="pic in firstColumn" style="width: 100%" :src="pic" />
        </div>
        <div class="gallery-column">
          <img v-for="pic in secondColumn" style="width: 100%" :src="pic" />
        </div>
      </div>
    </b-modal>

    <div class="w-100 pb-0 d-flex mt-5">
      <div class="d-flex flex-column" style="width: 100%; height: 100%">
        <hr class="w-100" />
        <h3 class="subtitle">Calendar</h3>
        <p style="font-size: 20px">
          <b>You are wondering how to reserve your appointment?</b> <br />
          Just click on the available appointment on calendar in order to book
          your reservation. You will be able to book whole appointment or just a
          part of it! Check additional services you want and your reservation is
          ready! Remember to check your email for your reservation details. You
          can cancel reservation 3 days in advance, so be sure to cancell it on
          time. Otherwise, you will get a penalty.
        </p>
        <div
          class="pl-5 w-100 justify-content-center d-flex mb-5"
          style="height: 80vh"
        >
          <div class="w-75">
            <Calendar
              :reservations="reservations"
              :currentType="currentType"
              :additionalServices="services"
              :isOwner="canChange"
            />
          </div>
        </div>
      </div>
    </div>

    <Popup
      id="reservedDiscountModal"
      ref="reservedDiscountModal"
      title="Congratulations!"
      type="success"
      text="You have successfully reserved an appointment."
    />

    <b-modal id="reservationModal" size="xl" hide-header hide-footer>
      <reservation-history
        :reportModal="reportModal"
        :viewReportModal="viewReportModal"
      />
    </b-modal>

    <Popup id="cantReserve" ref="cantReserve" title="You can't make reservations."
        text="Because of your 3 penalties, we have to stop you from making a
        reservation. You will have to wait till the end of month to be able to
        reserve something again." type="error" />

    <b-modal id="add_modal" size="xl" hide-header hide-footer>
      <ReservationReport :selectedId="selectedId" />
    </b-modal>
    <b-modal id="add_modal2" size="xl" hide-header hide-footer>
      <ViewReservationReport :selectedId="selectedId" />
    </b-modal>
    <b-modal id="addDiscountModal" size="lg" hide-header hide-footer>
      <AddDiscount
        :openModal="openAddedDiscountModal"
        :additionalServices="services"
      />
    </b-modal>
    <Popup
      title="Discount added successfully!"
      type="success"
      ref="discountAdded"
      id="discountAdded"
    />
    <Popup
      title="Discount deleted successfully!"
      type="success"
      ref="deletedDiscountModal"
      id="deletedDiscountModal"
    />
  </div>
</template>

<script>
import MainCard from "./MainCard.vue";
import Carousel2 from "./Carousel2.vue";
import Calendar from "./Calendar.vue";
import Discount from "./Discount.vue";
import Review from "./Review.vue";
import Popup from "../popup.vue";
import Gallery from "./Gallery.vue";
import ReservationHistory from "../owner_reservations/reservation_history.vue";
import ReservationReport from "./../owner_reservations/reservation_report";
import ViewReservationReport from "./../owner_reservations/view_reservation_report.vue";
import AddDiscount from "./../owner_reservations/add_discount.vue";

import { BIcon, BIconCalendarPlus } from "bootstrap-vue";

export default {
  components: {
    MainCard,
    Carousel2,
    Calendar,
    Discount,
    Review,
    ReservationHistory,
    AddDiscount,
    ReservationReport,
    ViewReservationReport,
    BIcon,
    BIconCalendarPlus,
    Popup,
    Gallery,
  },
  data() {
    return {
      selectedId: "",
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
        username: "",
      },
      discounts: [],
      reviews: [],
      services: [],
      rulesOfConduct: [],
      cancellationTerms: [],
      firstColumn: [],
      secondColumn: [],
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
      maxSpeed: "",
      canChange: false,
    };
  },
  mounted() {
    let that = this;
    this.$axios
      .get("/api/get-type-of-rentable?id=" + this.$route.params.rentable)
      .then((resp) => {
        that.currentType = resp.data;
        switch (resp.data) {
          case "cottage": {
            this.loadCottage();
            break;
          }
          case "adventure": {
            this.loadAdventure();
            break;
          }
          case "boat": {
            this.loadBoat();
            break;
          }
        }
      });
    this.$axios
      .get(
        `/api/check-if-rentable-from-logged-user?&rentableId=${that.$route.params.rentable}`
      )
      .then((resp) => {
        that.canChange = resp.data;
      });
  },
  methods: {
    goToOwnerProfile() {
      this.$router.push({ path: "/profile/" + this.owner.username });
    },
    openModal() {
      this.$refs.reservedDiscountModal.show();
    },
    openDeletedDiscount() {
      this.$refs.deletedDiscountModal.show();
    },
    openAddedDiscountModal() {
      this.$refs.discountAdded.show();
    },
    openCantReserve() {
      this.$refs.cantReserve.show();
    },
    openGalerry() {
      this.$bvModal.show("gallery");
    },
    openReservationsModal() {
      this.$bvModal.show("reservationModal");
    },
    showAddDiscountModal() {
      this.$bvModal.show("addDiscountModal");
    },
    isDateAfterToday(date) {
      return (
        new Date(date.toDateString()) > new Date(new Date().toDateString())
      );
    },
    loadAdventure() {
      let that = this;
      this.$axios
        .get("/api/get-full-adventure?id=" + this.$route.params.rentable)
        .then((resp) => {
          let adventure = resp.data;
          console.log(adventure.reviews);
          that.reviews = adventure.reviews.filter(x => x.status === "ACCEPTED");
          console.log(that.reviews);
          that.name = adventure.name;
          that.location =
            adventure.address +
            ", " +
            adventure.city +
            ", " +
            adventure.country;
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
          console.log("canBeChangedAdventure"+that.canBeChanged);
          for (var disc in adventure.discounts) {
            var startDate = adventure.discounts[disc].start;
            if (
              !adventure.discounts[disc].reserved &&
              that.isDateAfterToday(new Date(startDate))
            )
              that.discounts.push(adventure.discounts[disc]);
          }
          that.discounts.forEach(
            (x) => (
              (x.start = x.start.replaceAll("T", " ")),
              (x.start = x.start.replaceAll("-", "/")),
              (x.start = x.start.split(":")[0] + ":" + x.start.split(":")[1])
            )
          );
          that.discounts.forEach(
            (x) => (
              (x.end = x.end.replaceAll("T", " ")),
              (x.end = x.end.replaceAll("-", "/")),
              (x.end = x.end.split(":")[0] + ":" + x.end.split(":")[1])
            )
          );
          that.score = adventure.averageScore;
          that.pictures.push(
            ...adventure.pictures.map((x) => process.env.backend + x)
          );
          var half = Math.ceil(that.pictures.length / 2);
          var first = that.pictures.length % 2 == 1 ? half - 1 : half;
          console.log(half);
          that.firstColumn = that.pictures.slice(0, first);
          console.log(that.firstColumn);
          that.secondColumn = that.pictures.slice(-half);
          that.id = adventure.id;
          that.equipment = adventure.equipment;
          that.owner = adventure.owner;
          that.capacity = adventure.capacity;
          that.owner.picture = process.env.backend + that.owner.picture;
          that.reservations = adventure.reservations;
        });
    },
    loadCottage() {
      let that = this;
      this.$axios
        .get("/api/cottage/get-full-cottage?id=" + this.$route.params.rentable)
        .then((resp) => {
          let cottage = resp.data;
          that.reviews = cottage.reviews.filter(x => x.status === "ACCEPTED");
          that.reviews.forEach(
            (x) => (x.client.photo = process.env.backend + x.client.photo)
          );
          that.name = cottage.name;
          that.location =
            cottage.address + ", " + cottage.city + ", " + cottage.country;
          that.services = cottage.services;
          that.name = cottage.name;
          that.perHour = cottage.perHour;
          that.perDay = cottage.perDay;
          that.capacity = cottage.capacity;
          that.address = cottage.address;
          that.description = cottage.description;
          that.rulesOfConduct = cottage.rulesOfConduct;
          that.cancellationTerms = cottage.cancellationTerms;
          that.services = cottage.services;
          that.tags = cottage.tags;
          that.canBeChanged = !cottage.canBeChanged;
          console.log("canBeChangedCottage"+that.canBeChanged);
          for (var disc in cottage.discounts) {
            var startDate = cottage.discounts[disc].start;
            if (
              !cottage.discounts[disc].reserved &&
              that.isDateAfterToday(new Date(startDate))
            )
              that.discounts.push(cottage.discounts[disc]);
          }
          for (var x of that.discounts) {
            console.log("---------------" + x.start);
          }
          that.discounts.forEach(
            (x) => (
              (x.start = x.start.replaceAll("T", " ")),
              (x.start = x.start.replaceAll("-", "/")),
              (x.start = x.start.split(":")[0] + ":" + x.start.split(":")[1])
            )
          );
          that.discounts.forEach(
            (x) => (
              (x.end = x.end.replaceAll("T", " ")),
              (x.end = x.end.replaceAll("-", "/")),
              (x.end = x.end.split(":")[0] + ":" + x.end.split(":")[1])
            )
          );
          that.pictures.push(
            ...cottage.pictures.map((x) => process.env.backend + x)
          );
          var half = Math.ceil(that.pictures.length / 2);
          var first = that.pictures.length % 2 == 1 ? half - 1 : half;
          console.log(half);
          that.firstColumn = that.pictures.slice(0, first);
          console.log(that.firstColumn);
          that.secondColumn = that.pictures.slice(-half);
          that.id = cottage.id;
          that.owner = cottage.owner;
          that.owner.picture = process.env.backend + that.owner.picture;
          var stringRooms = cottage.rooms.split(";");
          for (var room of stringRooms) {
            if (!room.split(",")[0]) {
              break;
            }
            var roomString =
              room.split(",")[0] + " bedded - " + room.split(",")[1];
            parseInt(room.split(",")[1]) > 1
              ? (roomString += " rooms")
              : (roomString += " room");
            that.rooms.push(roomString);
          }
        });
    },
    loadBoat() {
      let that = this;
      this.$axios
        .get("/api/boat/get-full-boat?id=" + this.$route.params.rentable)
        .then((resp) => {
          let boat = resp.data;
          that.reviews = boat.reviews.filter(x => x.status === "ACCEPTED");
          that.name = boat.name;
          that.location = boat.address + ", " + boat.city + ", " + boat.country;
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
          that.type = boat.type;
          that.maxSpeed = boat.maxSpeed;
          that.boatLength = boat.length;
          that.capacity = boat.capacity;
          that.canBeChanged = !boat.canBeChanged;
          for (var disc in boat.discounts) {
            var startDate = boat.discounts[disc].start;
            if (
              !boat.discounts[disc].reserved &&
              that.isDateAfterToday(new Date(startDate))
            )
              that.discounts.push(boat.discounts[disc]);
          }
          that.discounts.forEach(
            (x) => (
              (x.start = x.start.replaceAll("T", " ")),
              (x.start = x.start.replaceAll("-", "/")),
              (x.start = x.start.split(":")[0] + ":" + x.start.split(":")[1])
            )
          );
          that.discounts.forEach(
            (x) => (
              (x.end = x.end.replaceAll("T", " ")),
              (x.end = x.end.replaceAll("-", "/")),
              (x.end = x.end.split(":")[0] + ":" + x.end.split(":")[1])
            )
          );
          that.pictures.push(
            ...boat.pictures.map((x) => process.env.backend + x)
          );
          var half = Math.ceil(that.pictures.length / 2);
          var first = that.pictures.length % 2 == 1 ? half - 1 : half;
          that.firstColumn = that.pictures.slice(0, first);
          that.secondColumn = that.pictures.slice(-half);
          that.id = boat.id;
          that.owner = boat.owner;
          that.owner.picture = process.env.backend + that.owner.picture;
        });
    },
    reportModal(param) {
      this.selectedId = param;
      this.$bvModal.show("add_modal");
    },
    viewReportModal(param) {
      this.selectedId = param;
      this.$bvModal.show("add_modal2");
    },
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
  padding: 7px;
  font-size: 15px;
  background-color: #ebecf0;
  border: none;
  border-radius: 7px;
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

.gallery-row {
  display: -ms-flexbox;
  /* IE 10 */
  display: flex;
  -ms-flex-wrap: wrap;
  /* IE 10 */
  flex-wrap: wrap;
  padding: 0 4px;
}

/* Create two equal columns that sits next to each other */
.gallery-column {
  -ms-flex: 50%;
  /* IE 10 */
  flex: 50%;
  padding: 0 4px;
}

.gallery-column img {
  margin-top: 8px;
  vertical-align: middle;
}
</style>