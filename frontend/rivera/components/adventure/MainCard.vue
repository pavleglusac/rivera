<template>
    <div class="mt-5">
        <p style="font-size: 0.9em; font-weight: 500;" class="p-0 mb-1">
            {{location}}
        </p>
        <h2 id="name" class="mb-0">
            {{name}}
            <div class="float-right mr-1">
                <b-button type="button" class="btn btn-light rounded-pill" :disabled='!canBeChanged' @click="updateRentable">
                    <font-awesome-icon icon="pen-to-square"  />
                    Update
                </b-button>
                <b-button v-if="isSubscribed" type="button" class="btn btn-light rounded-pill" @click="subscribe">
                    + Subscribe
                </b-button>
                <b-button v-else type="button" class="btn btn-light rounded-pill" @click="unsubscribe">
                    - Unsubscribe
                </b-button>
                <button type="button" class="btn rounded-pill btn-light "  :disabled='!canBeChanged' @click="deleteRentable">
                    <font-awesome-icon icon="trash"  />
                    Delete
                </button>
            </div>
        </h2>
        <div class="text-secondary" style="font-size: 0.8em;">
            <font-awesome-icon icon="star" /> &nbsp; {{ score }}
        </div>
        <div id="desc" class="mt-4 w-75">
            {{description}}
        </div>

        <b-modal id="update_modal" title="Update cottage" ref="update_modal" size="xl" hide-footer>
            <EditAdventure />
        </b-modal>


        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Confirm delete dialog</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					</div>
					<div class="modal-body">
					Are you sure you want to proceed?
					</div>
					<div class="modal-footer">
					    <button type="button" class="btn btn-primary" id="roomUpdate" @click="confirmDeletion()">Yes</button>
					    <button type="button" class="btn btn-secondary" id="modalClosure"  @click="closeModal('deleteModal')">No</button>
					</div>
				</div>
            </div>
        </div>
    </div>   
</template>

<script>
import EditAdventure from './../add-adventure/EditAdventure.vue'
export default {
    components: { EditAdventure },
    data() {
        return {
            isSubscribed: false,
            username: ""
        }
    },
    mounted() {
        this.getUsername();
        this.subscribed();
    }, 
    methods: {
        deleteRentable() {
			$("#deleteModal").modal('show');		
        },
        confirmDeletion() {
            this.$axios
            .post('/api/delete-adventure?id=' + this.$route.params.adventure)
            .then((resp) => {
                console.log(resp);
            }).catch((err) => {
                console.log(err);   
            });
            this.closeModal("deleteModal");
        },
        closeModal(id) {
			$("#"+id).modal('hide');
		},
        updateRentable() {
            this.$refs.update_modal.show();
        },
        getUsername() {
            let that = this;
            this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                }).then((resp) => {
                    console.log("HEEEEEi");
                    console.log(resp.data);
                    that.username = resp.data;
                    console.log(that.username);

                }).catch((err) => {
                    console.log(err);
                });
        },
        subscribed() {
            let that = this;
            this.$axios.post('/api/is-subscribed?username=' + that.username + 'id=' + this.$route.params.adventure)
                .then((resp) => {
                    that.isSubscribed = resp.data;
                }).catch((err) => {
                    console.log(err);   
                });
        },
        subscribe() {
            let that = this;
            this.$axios.post('/api/subscribe?username=' + that.username + 'id=' + this.$route.params.adventure)
                .then((resp) => {
                    console.log(resp);
                }).catch((err) => {
                    console.log(err);   
                });
        },
        unsubscribe() {
            let that = this;
            this.$axios.post('/api/unsubscribe?username=' + that.username + 'id=' + this.$route.params.adventure)
                .then((resp) => {
                    console.log(resp);
                }).catch((err) => {
                    console.log(err);   
                });
        }
    },
    data() {
        return {


        }
    },
    props: ['location', 'name', 'score', 'description', 'canBeChanged']
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans:wght@400&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rubik:wght@600&display=swap');
#desc {
    font-family: 'Noto Sans', sans-serif;
}

#name {
    font-family: 'Rubik', sans-serif;
}
</style>