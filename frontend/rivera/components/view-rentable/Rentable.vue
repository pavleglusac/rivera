<template>
    <div class="w-100 ml-0 mr-0 h-100">
        <div class="w-100 pl-5 pt-5 pb-0 d-flex" style="height: 100vh;">
            <div style="box-shadow: 0 8px 6px -6px #bdbdbd;" class="w-100 h-100 d-flex">
                <div class="d-flex flex-column" style="width: 35%; height: 100%; ">
                    <div style="height: 50%">
                        <MainCard :location='location' :description='description' :name='name' :score='score'/>
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
                    <img :src="pictures[1]" alt="" class="m-0 p-1" style="object-fit: cover; width: 100%; height: 100%;"  >
                </div>
                <Carousel /> 
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
                            <li>1 bedded - 3 rooms</li>
                            <li>2 bedded - 3 rooms</li>
                            <li>4 bedded - 2 rooms</li>
                        </td>
                    </tr>
                    <tr style="border-bottom: 1px solid #c9c9c9;">
                        <td style="font-size: 1.3em; font-weight: 400; width: 25%; vertical-align: top;" class="">Rules of conduct: </td>
                        <td class="w-25">
                            <li>Nullam eget felis eget nunc lobortis</li>
                            <li>Mauris ultrices eros in cursus turpis massa tincidunt</li>
                            <li>Porta non pulvinar neque laoreet suspendisse</li>
                            <li>In vitae turpis massa sed elementum tempus egestas sed</li>
                            <li>Mattis molestie a iaculis at. Tellus mauris a diam maecenas sed enim ut</li>
                        </td>
                    </tr>
                    <tr style="">
                        <td style="font-size: 1.3em; font-weight: 400; width: 15%;" class="">Additional services: </td>
                        <td class="w-25">3</td>
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
                    $380&nbsp;
                </div>
                <div class="d-inline my-auto" >
                    / hour
                </div>
                &nbsp;&nbsp;
                <div class="my-auto d-inline " style="font-size: 1.5em; font-weight: 500;">
                    $1500&nbsp;
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
                        <Review class="ml-2 mt-1 w-100 h-100" v-for="review in reviews.slice(0, 5)" :key="review.id" :review="review" />
                    </div>
                </div>
                <div class="col-6 gx-0">
                    <div>
                        <Review class="mt-1 w-100 h-100" v-for="review in reviews.slice(5)" :key="review.id" :review="review" />
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
            location: 'Cairns, Australia',
            name: 'Mountain fishing',
            score: 3.5,
            description: 'Pellentesque habitant morbi tristique senectus et netus et malesuada \
            fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum \
            maximus quam mollis consequat',
            pictures: ['https://images.pexels.com/photos/2431454/pexels-photo-2431454.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
                    'https://images.unsplash.com/photo-1493787039806-2edcbe808750?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
                    'https://images.pexels.com/photos/4822293/pexels-photo-4822293.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1',
                    'https://images.unsplash.com/photo-1493787039806-2edcbe808750?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
                    'https://images.unsplash.com/photo-1437482078695-73f5ca6c96e2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80',
                    'https://images.unsplash.com/photo-1498855926480-d98e83099315?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80'],
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
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl\
                    x diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl\
                    x diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                },  
                {
                    client: {
                        name: 'Neko drugi',
                        photo: 'https://images.unsplash.com/photo-1595152772835-219674b2a8a6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '11/5/20',
                    score: 5.0,
                    text: "Ut mollis erat non quam rhoncus sodales. Nulla facilisi. Duis sit amet pellentesque dolor. Aliquam mollis rhoncus risus vel ornare. Nulla pellentesque risus ac lorem efficitur auctor. Cras auctor turpis"
                },
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                },
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                },
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                }, 
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                }, 
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                }, 
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                }, 
                {
                    client: {
                        name: 'Mika Mikic',
                        photo: 'https://images.unsplash.com/photo-1610216705422-caa3fcb6d158?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80'
                    },
                    posted: '15/2/20',
                    score: 3.3,
                    text: "Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi erat varius magna, sit amet imperdiet tellus ex non nisl"
                }, 
            ]
      }
    },
    
    
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