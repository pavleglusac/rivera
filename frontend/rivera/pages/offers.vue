<template>
<div>
    <unauthenticated-navbar/>
    <div>
      <b-nav class="w-100 nav">
        <b-nav-item>
          <span><img src="..\static\icons\boat.png" alt="Boats">Boats</span>
        </b-nav-item>
        <b-nav-item>
          <span><img src="..\static\icons\cottage.png" alt="Cottages">Cottages</span>
        </b-nav-item>
        <b-nav-item>
          <span><img src="..\static\icons\fish.png" alt="Fishing">Fishing</span>
          </b-nav-item>
        <b-nav-item fill><b-form-input v-model="text" placeholder="Search..."></b-form-input></b-nav-item>
        <b-nav-item><b-form-datepicker id="datepicker-placeholder" placeholder="Start date"></b-form-datepicker></b-nav-item>
        <b-nav-item><b-form-datepicker id="datepicker-placeholder" placeholder="End date"></b-form-datepicker></b-nav-item>
        <b-nav-item><b-dropdown split text="Order descending">
          <b-dropdown-item-button>Price</b-dropdown-item-button>
          <b-dropdown-item-button>Score</b-dropdown-item-button>
          <b-dropdown-item-button>Name</b-dropdown-item-button>
        </b-dropdown></b-nav-item>
        <b-nav-item><b-dropdown split text="Order ascending">
          <b-dropdown-item-button>Price</b-dropdown-item-button>
          <b-dropdown-item-button>Score</b-dropdown-item-button>
          <b-dropdown-item-button>Name</b-dropdown-item-button>
        </b-dropdown></b-nav-item>
<b-nav-item>
        <b-dropdown block menu-class="w-100">
            <template #button-content>Choose tags</template>
            <b-dropdown-item-button
              v-for="option in availableOptions"
              :key="option"
              @click="onOptionClick({ option, addTag })"
            >
              {{ option }}
            </b-dropdown-item-button>
          </b-dropdown></b-nav-item>
          <b-nav-item><b-button>Clear all</b-button></b-nav-item>
      </b-nav>
    </div>
    <div>
      <ul class="list-inline d-inline-block mb-2">
        <li v-for="tag in tags" :key="tag" class="list-inline-item">
          <b-form-tag
            @remove="removeTag(tag)"
            :title="tag"
            :disabled="disabled"
            variant="info"
          >{{ tag }}</b-form-tag>
        </li>
      </ul>
    </div>
      <!--<b-nav>
          <b-form-tags input-id="tags-basic" v-model="value"></b-form-tags>
      </b-nav>-->
</div> 
</template>

<script>
import UnauthenticatedNavbar from '../components/UnauthenticatedNavbar.vue'

  export default {
      components: { UnauthenticatedNavbar },

    data() {
      return {
        options: ['Apple', 'Orange', 'Banana', 'Lime', 'Peach', 'Chocolate', 'Strawberry'],
        value: [],
        tags: [],
        text: ''
      }
    },
    computed: {
      availableOptions() {
        return this.options
      },
    },
    methods: {
      onOptionClick({ option, addTag }) {
        addTag(option)
          this.tags.append(option);
      }
    }
  }
</script>

<style>
</style>