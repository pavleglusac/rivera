<template>
    <div class="w-100 ml-0 mr-0 h-100">
        <div class="w-100 pl-5 pt-5 pb-0 d-flex" style="height: 100vh;">
            <div style="box-shadow: 0 8px 6px -6px #bdbdbd;" class="w-100 h-100 d-flex">
                <div class="d-flex flex-column" style="width: 35%; height: 100%; ">
                    <div style="height: 50%">
                        <MainCard :location='location' :description='description' :name='name' :score='score' :canBeChanged='canBeChanged'/>
                    </div>
                    <div style="height: 40%">
                        <img :src="pictures[0]" alt="" class="m-0 pr-1" style="object-fit: cover; min-height: 100%; max-height: 100%; min-width:100%; max-width: 100%;" >
                    </div>
                    <div class="mt-0 h-25">
                        <p class="p-0 m-0 display-4 mt-3" style="font-size: 1.2em; font-weight: 500;">
                            Tags
                        </p>
                        <b-form-tag  v-for="tag in tags"
                                    :key="tag"
                                    class="mr-1 mt-3">
                                    {{tag}}
                        </b-form-tag>
                    </div>
                </div>
            <div class="d-flex flex-column h-100" style="width: 64%;">
                    <div class="w-100 mt-5" style="height: 60%;">
                        <img :src="pictures[1]" alt="" style="object-fit: cover; width: 100%; height: 100%;"  >
                    </div>
                    <Carousel :pictures="pictures.slice(2, pictures.lenght)" /> 
                </div>
            </div>
        </div>

        <div class="pl-5 mt-5 d-flex">
            <div class="d-flex flex-column w-50">
                <table style="font-size: 1.2em;" class="w-100">
                    <tr style="border-bottom: 1px solid #c9c9c9;">
                        <td style="font-size: 1.3em; font-weight: 400; width: 25%;" class="">Cancellation terms: </td>
                        <td class="w-25">10%</td>
                    </tr>
                    <tr style="border-bottom: 1px solid #c9c9c9;">
                        <td style="font-size: 1.3em; font-weight: 400; width: 25%; vertical-align: top;" class="">Rooms: </td>
                        <td class="w-25">
                            <li v-for="room in rooms"  :key="room" >
                                {{room}}
                            </li>
                        </td>
                    </tr>
                    <tr style="border-bottom: 1px solid #c9c9c9;">
                        <td style="font-size: 1.3em; font-weight: 400; width: 25%; vertical-align: top;" class="" >Rules of conduct: </td>
                        <td class="w-25">
                            <li v-for="rule in rulesOfConduct"  :key="rule" >
                                {{rule}}
                            </li>
                        </td>
                    </tr>
                    <tr style="">
                        <td style="font-size: 1.3em; font-weight: 400; width: 15%;" class="">Additional services: </td>
                        <td class="w-25">
                            <li v-for="service in services"  :key="service" >
                                {{service}}
                            </li>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="d-flex w-50 ml-auto justify-content-center align-items-center">
                <iframe src="https://maps.google.com/maps?q=novisad&t=&z=13&ie=UTF8&iwloc=&output=embed" frameborder="0"
                style="border:0; width: 80%; height: 80%;" allowfullscreen></iframe>
            </div>
        </div>


        <b-row style="height: 10vh;" class="pl-5 mt-5">
            <b-col cols="8" class="h-100">
                <img :src="person" alt="" class="z-depth-2 h-100" style="border-radius:50%; object-fit: cover;">
                <div class="d-inline ml-3" style="font-size: 1.3em; font-weight: 500;">
                    Petar Peric&nbsp;
                    <div class="d-inline" style="font-size: 0.8em; font-weight: 400;">
                        <a role="button" class="popover-test" title="Title" data-toggle="popover" data-content="Donec nibh massa, pharetra non enim sed, efficitur viverra lectus. Morbi molestie, ante et sollicitudin blandit, ex nisl tristique mauris, sed blandit quam arcu a lorem. Fusce nisi augue, cursus eget tellus non, varius congue enim. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce vestibulum eros maximus, tincidunt velit accumsan, maximus lorem. Suspendisse luctus ante ac nulla dignissim porta. Phasellus porttitor diam at dapibus semper. Vivamus accumsan blandit lectus, vel ullamcorper arcu tincidunt et"
                        style="color: gray;">about me</a>
                    </div>
                </div>
            </b-col>
            <b-col cols="4" class="align-items-center justify-content-center my-auto pr-2">
                <div class="my-auto d-inline " style="font-size: 1.5em; font-weight: 500;">
                    ${{perHour}}
                </div>
                <div class="d-inline my-auto" >
                    / hour
                </div>
                &nbsp;&nbsp;
                <div class="my-auto d-inline " style="font-size: 1.5em; font-weight: 500;">
                    ${{perDay}}&nbsp;
                </div>
                <div class="d-inline my-auto" >
                    / day
                </div>
                <b-button style="font-size: 1em; background-color: #D25034; color: white; border: 0;" class="rounded-pill d-inline w-25 ml-4">See dates</b-button>
            </b-col>
        </b-row>

        <div class="pl-5 w-100 mt-5 justify-content-center d-flex mb-5" style="height: 75vh;">
            <div class="w-75">
                <Calendar />
            </div>
        </div>

        <div class="w-100 pl-5 mt-5">
            <div style="font-size: 1.8em; font-weight: 500;" class="mt-4">Discount offers!</div>
            <div class="row mt-5 ">
                <Discount class="ml-2 col-2" v-for="discount in discounts" :key="discount.id" :discount="discount" />
            </div>
        </div>

        <div class="mt-5 pl-5 w-100 mb-5">
            <div style="font-size: 1.8em; font-weight: 500;">
                Client reviews
            </div>
            <div class="row h-100 gx-0">
                <div class="col-6 gx-0 pr-0">
                    <div>
                        <Review class="ml-2 mt-1 w-100 h-100" v-for="review in reviews.slice().splice(0, Math.ceil(reviews.length / 2))" :key="review.id" :review="review" />
                    </div>
                </div>
                <div class="col-6 gx-0">
                    <div>
                        <Review class="mt-1 w-100 h-100" v-for="review in reviews.slice().splice(-Math.ceil(reviews.length / 2))" :key="review.id" :review="review" />
                    </div>
                </div>
            </div>
            
        </div>
        
    </div>
</template>

<script>
import MainCard from './MainCard.vue'
import Carousel from './Carousel.vue'
import Calendar from './Calendar.vue'
import Discount from './Discount.vue'
import Review from './Review.vue'

export default {
  components: { MainCard, Carousel, Calendar, Discount, Review },
  data() {
      return {
            id: '',
            location: '',
            name: '',
            cancellationTerms: '',
            perHour: '',
            perDay: '',
            rulesOfConduct: '',
            score: 3.5,
            description: '',
            pictures: [],
            rooms: [],
            services: "",
            canBeChanged: false,
            owner: {
                picture: '',
                name: '',
                surname: '',
            },
            tags: [
                'pet firendly',
                'canoe',
                'mountains'
            ],
            person: [
                'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
            ],
            discounts: [
                {
                    id: 1,
                    start: '12/5/22',
                    end: '12/5/22',
                    capacity: 2,
                    price: 300,
                    tags: ['river', 'kayak']
                },
                {
                    id: 2,
                    start: '12/5/22',
                    end: '12/5/22',
                    capacity: 4,
                    price: 450,
                    tags: ['mountain', 'extreme', 'lorem', 'ipsum']
                }
            ],
            reviews: [
            ]
      }
    },
  mounted() {      
		let that = this;
		this.$axios
        .get('/api/cottage/get-full-cottage?id=' + this.$route.params.cottage)
        .then((resp) => {
            let cottage = resp.data;
            console.log("TWETEWETWTETWTEW\n\n"+cottage+"\n\nTWETEWETWTETWTEW");
            that.reviews = cottage.reviews;
            that.reviews.forEach(x => x.client.photo = "http://localhost:8080/" + x.client.photo)
			that.name = cottage.name;
			that.location = cottage.address+","+cottage.city+","+cottage.country;
			that.services = cottage.services;
			that.name = cottage.name;
			that.perHour = cottage.perHour;
			that.perDay = cottage.perDay;
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
                var roomString = room.split(',')[1]+" bedded - " + room.split(',')[1];
                (parseInt(room.split(',')[1])>1) ? roomString += " rooms" : roomString += " room"; 
                that.rooms.push(roomString);
            }
            console.log("AAAAAAAAAA"+that.rooms);
        });
  }    
    
}
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
    -moz-column-break-inside:avoid;
    -moz-page-break-inside:avoid;
    page-break-inside: avoid;
    break-inside: avoid-column;
}
</style>