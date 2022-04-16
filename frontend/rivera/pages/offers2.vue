<template>
  <div>
    <unauthenticated-navbar />
    <div class="p-3 mt-1">
      <form>
        <div class="form-row">
          <div class="form-group col-md-1">
            <span
              ><img src="..\static\icons\boat.png" alt="Boats" /> Boats</span
            >
          </div>
          <div class="form-group col-md-1">
            <span
              ><img src="..\static\icons\cottage.png" alt="Boats" />
              Cottages</span
            >
          </div>
          <div class="form-group col-md-1">
            <span
              ><img src="..\static\icons\fish.png" alt="Boats" /> Fishing</span
            >
          </div>

          <div class="form-group col-md-4">
            <b-form-input v-model="text" placeholder="Search..."></b-form-input>
          </div>

          <div class="form-group col-md-2">
            <b-form-datepicker placeholder="Start date"></b-form-datepicker>
          </div>
          <div class="form-group col-md-2">
            <b-form-datepicker placeholder="End date"></b-form-datepicker>
          </div>

          <div class="form-group col-md-1">
            <b-dropdown text="Order by">
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-group id="asc" header="Ascending">
                <b-dropdown-item-button>Price</b-dropdown-item-button>
                <b-dropdown-item-button>Name</b-dropdown-item-button>
                <b-dropdown-item-button>Score</b-dropdown-item-button>
              </b-dropdown-group>
              <b-dropdown-group id="desc" header="Descending">
                <b-dropdown-item-button>Price</b-dropdown-item-button>
                <b-dropdown-item-button>Name</b-dropdown-item-button>
                <b-dropdown-item-button>Score</b-dropdown-item-button>
              </b-dropdown-group>
            </b-dropdown>
          </div>
        </div>
        <div class="form-row">
            <b-form-tags input-id="tags-basic" v-model="value"></b-form-tags>
        </div>
      </form>
    </div>
  </div>
</template>


<script>
import UnauthenticatedNavbar from "../components/UnauthenticatedNavbar.vue";

export default {
  components: { UnauthenticatedNavbar },

  data() {
    return {
      options: [
        "Apple",
        "Orange",
        "Banana",
        "Lime",
        "Peach",
        "Chocolate",
        "Strawberry",
      ],
      value: [],
      tags: [],
      text: "",
    };
  },
  computed: {
    availableOptions() {
      return this.options;
    },
  },
  methods: {
    onOptionClick({ option, addTag }) {
      addTag(option);
      this.tags.append(option);
    },
  },
};
</script>

<style>
</style>