<template>
	<div>
		<Navbar :currentPage="'registrations'" />
		<div
			class="d-flex flex align-items-start mt-1 parent"
			style="width: 70vw; margin-left: auto; margin-right: auto"
		>
			<b-card
				class=" card w-25 mt-2 mb-5"
				style="margin-top: 0; box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px"
			>
                <p>
                    Total requests: {{totalRequests}}
                </p>
                <p>
                    Active owner accounts: {{activeAccounts}}
                </p>
                <p>
                    Terminated accounts: {{terminatedAccounts}}
                </p>
			</b-card>

            <b-card
				class=" card  mt-2 mb-5 ml-2"
				style="margin-top: 0; box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;"
			>
                <validate-registration class="h-100 child" :usernameToRegister="currentUsername"></validate-registration>
                
                <div class="d-flex flex w-100 justify-content-end mt-3">
                    <b-button @click="nextUsername">Next</b-button>
                </div>
			</b-card>
		</div>
	</div>
</template>

<script>
import ValidateRegistration from '../../components/registration/ValidateRegistration.vue';
export default {
	components: { ValidateRegistration },
    data() {
        return {
            totalRequests: 1389,
            activeAccounts: 28,
            terminatedAccounts: 6,
            unregisteredUsernames: [],
            currentUsername: ''
        }
    },
    mounted() {
        this.redirectAdmin();
        this.getUnregisteredUsernames();
        this.getStats();
    },
    methods: {
        getUnregisteredUsernames() {
            let that = this;
            this.$axios.get('/api/admin/unregistered-usernames')
            .then(resp => {
                console.log(resp.data);
                that.unregisteredUsernames.push(...resp.data);
                that.currentUsername = that.unregisteredUsernames[0];
            })
        },
        nextUsername() {
            if(this.unregisteredUsernames.length > 0) {
                let ko = this.unregisteredUsernames.shift();
                this.unregisteredUsernames.push(ko);
                this.currentUsername = this.unregisteredUsernames[0];
            }
        },
        getStats() {
            let that = this;
            this.$axios.get('/api/admin/registered-stats')
            .then(resp => {
                console.log(resp.data);
                that.activeAccounts = typeof resp.data.ACTIVE !== 'undefined' ? resp.data.ACTIVE : 0;
                that.totalRequests = typeof resp.data.WAITING !== 'undefined' ? resp.data.WAITING : 0;
                that.terminatedAccounts = typeof resp.data.TERMINATED !== 'undefined' ? resp.data.TERMINATED : 0;
            });
        },
        redirectAdmin() {
			this.$axios.get('/api/auth/logged-user-info').then((resp) => {
				console.log(resp.data);
				if(resp.data != null) {
					if(resp.data.role == 'ROLE_ADMIN' && resp.data.lastPasswordChange == null) {
					this.$router.push({ path: "/admin/profile" });
					}
				}
			}).catch((err) => {
				console.log(err);
			});
		}
    }

};
</script>

<style scoped>
.parent >>> .child label {
    float: left;
    font-size: 16px;
}

</style>