<template>
  <div>
    <h6>Loyalty Program</h6>
    <p>
      Number of points you get when your reservation is successfull is
      <b>{{ owner.pointsPerReservation }}</b
      >. Rivera is taking <b>{{ owner.incomePercentage }}%</b> of your
      reservation income. The more points you have, the more income you get on
      your next reservation! Your current number of points is
      <b>{{ owner.numberOfPoints }}</b
      >.
    </p>
    <b-card
      v-if="ownerLoyalty != null"
      class="loyaltyCard"
      :style="cssCard(ownerLoyalty)"
    >
      <h6 :style="cssCard(ownerLoyalty)" class="loyaltyTitle">
        <font-awesome-icon icon="gift" /> {{ ownerLoyalty.name }}
      </h6>
      <b-card-text>
        Additional income percentage you have with this program is
        <b>{{ ownerLoyalty.percentage }}%</b>.<br />
        Minimal number of points you should have is
        <b> {{ ownerLoyalty.numberOfPoints }}</b
        >.</b-card-text
      >
    </b-card>
    <p
      @click="openLoyaltyModal"
      style="
        font-size: 12px;
        cursor: pointer;
        color: #11698e;
        font-weight: bold;
      "
    >
      <font-awesome-icon icon="gift" /> Show me all loyalty programs
    </p>
    <b-modal id="loyaltyModal" hide-footer title="All Loyalty Programs">
      <b-card
        v-for="(loyalty, index) in owner.allLoyalties"
        :style="cssCard(loyalty)"
        v-bind:key="loyalty.name + index"
        class="loyaltyCard"
      >
        <h6 :style="cssCard(loyalty)" class="loyaltyTitle">
          <font-awesome-icon icon="gift" /> {{ loyalty.name }}
        </h6>
        <b-card-text>
          Additional income percentage you have with this program is
          <b>{{ loyalty.percentage }}%</b>. Minimal number of points you should
          have is <b>{{ loyalty.numberOfPoints }}</b
          >.</b-card-text
        >
      </b-card>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      owner: {
        numberOfPenalties: 0,
        numberOfPoints: 0,
      },
      ownerLoyalty: {
        name: "",
        color: "#ffffff",
      },
    };
  },
  methods: {
    cssCard(l) {
      return { "--color": l.color };
    },
    openLoyaltyModal() {
      this.$bvModal.show("loyaltyModal");
    },
    getOwnerLoyalty() {
      var program = null;
      var max = 0;
      console.log(this.owner.allLoyalties);
      for (const loyalty of this.owner.allLoyalties) {
        if (
          this.owner.numberOfPoints >= loyalty.numberOfPoints &&
          loyalty.numberOfPoints >= max
        ) {
          max = loyalty.numberOfPoints;
          program = loyalty;
        }
      }
      this.ownerLoyalty = program;
      console.log(program);
    },
    loadOwner() {
      this.subscribed = [];
      this.$axios
        .get("/api/auth/get-logged-username")
        .then((resp) => {
          this.$axios
            .get("/api/get-owner-loyalty?username=" + resp.data)
            .then((response) => {
              this.owner = response.data;
              console.log(this.owner);
              this.getOwnerLoyalty();
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.loadOwner();
  },
};
</script>

<style>
p {
  font-weight: 400;
}
.loyaltyCard {
  border-top: 10px solid var(--color);
  margin-bottom: 10px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 5px 0 rgba(0, 0, 0, 0.19);
}
.loyaltyTitle {
  color: var(--color);
}
</style>