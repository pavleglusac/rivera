<template>
    <div class="mt-5">
        <p style="font-size: 1em; font-weight: 500;" class="p-0 mb-1">
            {{location}}&nbsp;&nbsp;&nbsp; <font-awesome-icon icon="star" />&nbsp;{{ score }}
        </p>
        <h2 id="name" class="mb-4">{{name}}</h2>
        <div class="float-left mr-1">
            <b-button size="sm" class="btn btn-light" style="font-weight: 500" :disabled='!canBeChanged' @click="updateRentable">
                <font-awesome-icon icon="pen-to-square" /> Update</b-button>
            <b-button size="sm" class="btn btn-light" style="font-weight: 500" :disabled='!canBeChanged' @click="deleteRentable">
                <font-awesome-icon icon="trash" /> Delete</b-button>
            <b-button size="sm" v-if="!isSubscribed" class="subscribe-btn" @click="subscribe">
                + Subscribe
            </b-button>
            <b-button size="sm" v-else class="subscribe-btn" @click="unsubscribe">
                - Unsubscribe
            </b-button>
        </div><br>

        <div id="desc" class="mt-4 mb-4">
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
        };
    },
    mounted() {
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
        subscribed() {
            let that = this;
            this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                }).then((resp) => {
                this.$axios.post('/api/is-subscribed?username=' + resp.data + '&id=' + this.$route.params.adventure)
                    .then((resp) => {
                        that.isSubscribed = resp.data;
                    }).catch((err) => {
                        console.log(err);   
                    });
            });
        },
        subscribe() {
            let that = this;
            this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                }).then((resp) => {
                this.$axios.post('/api/subscribe?username=' + resp.data + '&id=' + this.$route.params.adventure)
                    .then((resp) => {
                        that.isSubscribed = true;
                    }).catch((err) => {
                        console.log(err);   
                    });
                });
        },
        unsubscribe() {
            let that = this;
            this.$axios.get('/api/auth/get-logged-username', { headers: { 'Authorization' : 'Bearer ' + window.localStorage.getItem("JWT") } 
                }).then((resp) => {
                this.$axios.post('/api/unsubscribe?username=' + resp.data + '&id=' + this.$route.params.adventure)
                    .then((resp) => {
                        that.isSubscribed = false;
                    }).catch((err) => {
                        console.log(err);   
                    });
                });
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

.subscribe-btn {
    background-color: var(--prime-color);
    color: #fff;
    border: none;
    font-weight: 500;
}
.subscribe-btn:hover {
    opacity: 0.9;
    background-color: var(--prime-color);
}
</style>