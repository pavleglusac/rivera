<template>
    <div id="full-stars">
    <div class="rating-group">
            <input class="rating__input rating__input--none" name="rating" id="rating-none" value="0" type="radio">
            <label aria-label="No rating" class="rating__label" for="rating-none"><i class="rating__icon rating__icon--none fa fa-ban"></i></label>
            <label aria-label="1 star" class="rating__label" for="rating-1"><i class="rating__icon rating__icon--star fa fa-star"></i></label>
            <input class="rating__input" name="rating" id="rating-1" value="1" type="radio">
            <label aria-label="2 stars" class="rating__label" for="rating-2"><i class="rating__icon rating__icon--star fa fa-star"></i></label>
            <input class="rating__input" name="rating" id="rating-2" value="2" type="radio">
            <label aria-label="3 stars" class="rating__label" for="rating-3"><i class="rating__icon rating__icon--star fa fa-star"></i></label>
            <input class="rating__input" name="rating" id="rating-3" value="3" type="radio" checked>
            <label aria-label="4 stars" class="rating__label" for="rating-4"><i class="rating__icon rating__icon--star fa fa-star"></i></label>
            <input class="rating__input" name="rating" id="rating-4" value="4" type="radio">
            <label aria-label="5 stars" class="rating__label" for="rating-5"><i class="rating__icon rating__icon--star fa fa-star"></i></label>
            <input class="rating__input" name="rating" id="rating-5" value="5" type="radio">
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style>


  .rating-group {
    display: inline-flex;
  }
  
  .rating__icon {
    pointer-events: none;
  }
  
  .rating__input {
   position: absolute !important;
   left: -9999px !important;
  }

  .rating__label {
    cursor: pointer;
    padding: 0 0.1em;
    font-size: 2rem;
  }
  
  .rating__icon--star {
    color: orange;
  }
  
  .rating__icon--none {
    color: #eee;
  }

  .rating__input--none:checked + .rating__label .rating__icon--none {
    color: red;
  }

  .rating__input:checked ~ .rating__label .rating__icon--star {
    color: #ddd;
  }

  .rating-group:hover .rating__label .rating__icon--star {
    color: orange;
  }

  .rating__input:hover ~ .rating__label .rating__icon--star {
    color: #ddd;
  }
  
  .rating-group:hover .rating__input--none:not(:hover) + .rating__label .rating__icon--none {
     color: #eee;
  }

  .rating__input--none:hover + .rating__label .rating__icon--none {
    color: red;
  }


</style>