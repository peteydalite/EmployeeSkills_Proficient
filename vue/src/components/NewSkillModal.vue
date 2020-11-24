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
              v-on:click.prevent="close()"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body NewSkill-container">
            <div class="fields">
              <label> Field </label>
              <select
                placeholder="Skill"
                class="form-control"
                v-model="fieldName"
              >
                <option v-for="field in fields" v-bind:key="field.id">
                  {{ field.name }}
                </option>
              </select>
              <label>Exp. in months</label>
              <input
                type="number"
                class="form-control"
                v-model="skill.experience"
              />
            </div>
            <div class="description">
              <label>Description</label>
              <textarea class="form-control desc-box" v-model="skill.summary" />
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-dismiss="modal"
              v-on:click.prevent="close()"
            >
              Close
            </button>
            <button
              type="button"
              class="btn btn-primary"
              v-on:click.prevent="save()"
            >
              Save changes
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import EmpService from "@/services/EmployeeService";
export default {
  name: "NewSkill",
  props: {
    employee: {},
  },
  data() {
    return {
      modalOn: false,
      fields: [],
      fieldName: "",
      field: {},
      skill: {},
      skills: [],
    };
  },
  methods: {
    flip() {
      this.modalOn = !this.modalOn;
    },
    close() {
      this.field = {};
      this.flip();
    },
    getFields() {
      EmpService.getFields()
        .then((response) => {
          if (response.status === 200) {
            this.fields = response.data;
            console.log(response.data);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    save() {
      this.fields.forEach((f) => {
        if (f.name === this.fieldName) {
          this.field = f;
          this.skill.field = this.field;
        }
      });

      EmpService.addEmployeeSkill(this.employee.id, this.skill)
        .then((response) => {
          if (response.status === 201) {
            this.skills = response.data;
            console.log(this.skills);

            //Sending new skills array back to profile
            this.$emit("childToParent", this.skills);

            this.close();
          }
        })
        .catch((err) => {
          console.log(err);
        });
      // console.log(this.skill);
    },
  },
  mounted() {
    this.getFields();
  },
};
</script>

<style>
.NewSkill-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
}
.NewSkill-container > div {
  margin: 4px;
}
.desc-box {
  width: 100%;
  height: 80%;
}

</style>