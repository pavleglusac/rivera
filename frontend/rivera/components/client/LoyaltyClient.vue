<template>
  <div>
    <h6>Penalties</h6>
    <p>
      When you skip a reservation or don't follow rules of conduct, owners can
      give you a penalty. Having 3 penatiles means that you cannot make a
      reservation. Penalties are deleted every 1st of month. Your current number of penalties is
      <b>{{ client.numberOfPenalties }}</b
      >.
    </p>
    <hr />
    <h6>Loyalty Program</h6>
    <p>
      Number of points you get when you make a reservation is
      <b>{{ client.pointsPerReservation }}</b
      >. The more points you
      have, the more discount you get on your next reservation! Your current
      number of points is <b>{{ client.numberOfPoints }}</b
      >.
    </p>
    <b-card
      v-if="clientLoyalty != null"
      class="loyaltyCard"
      :style="cssCard(clientLoyalty)"
    >
      <h6 :style="cssCard(clientLoyalty)" class="loyaltyTitle">
        <font-awesome-icon icon="gift" /> {{ clientLoyalty.name }}
      </h6>
      <b-card-text>
        Discount you have with this program is <b>{{ clientLoyalty.percentage }}%</b>.<br>
        Minimal number of points you should have is <b>
        {{ clientLoyalty.numberOfPoints }}</b>.</b-card-text
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
        v-for="loyalty in client.allLoyalties"
        :style="cssCard(loyalty)"
        class="loyaltyCard"
      >
        <h6 :style="cssCard(loyalty)" class="loyaltyTitle">
          <font-awesome-icon icon="gift" /> {{ loyalty.name }}
        </h6>
        <b-card-text>
          Discount you have with this program is {{ loyalty.percentage }}%.
          Minimal number of points you should have is
          {{ loyalty.numberOfPoints }}.</b-card-text
        >
      </b-card>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      client: {
        numberOfPenalties: 0,
        numberOfPoints: 0,
      },
      clientLoyalty: {
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
    getClientLoyalty() {
      var program = null;
      var max = 0;
      console.log(this.client.allLoyalties);
      for (const loyalty of this.client.allLoyalties) {
        if (
          this.client.numberOfPoints >= loyalty.numberOfPoints &&
          loyalty.numberOfPoints >= max
        ) {
          max = loyalty.numberOfPoints;
          program = loyalty;
        }
      }
      this.clientLoyalty = program;
      console.log(program);
    },
    loadClient() {
      this.subscribed = [];
      this.$axios
        .get("/api/auth/get-logged-username")
        .then((resp) => {
          this.$axios
            .get("/api/get-client-loyalty?username=" + resp.data)
            .then((response) => {
              this.client = response.data;
              console.log(this.client);
              this.getClientLoyalty();
            });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.loadClient();
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