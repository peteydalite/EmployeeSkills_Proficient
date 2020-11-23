<template>
  <div>
    <button
      type="button"
      v-on:click.prevent="flip()"
      class="btn btn-outline-dark"
    >
      Add Skills
    </button>
    <div
      v-show="modalOn == true"
      id="exampleModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">New Skill</h5>
            <button
              type="button"
              class="close"
              v-on:click.prevent="flip()"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">

          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-dismiss="modal"
              v-on:click.prevent="flip()"
            >
              Close
            </button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewSkill",
  data() {
    return {
      modalOn: false,
      fields: [],
    };
  },
  methods: {
    flip() {
      this.modalOn = !this.modalOn;
    },
  },
};
</script>

<style>
</style>