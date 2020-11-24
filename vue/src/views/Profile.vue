<template>
  <div class="main-container">
    <div class="personal-container">
      <h2>{{ employee.firstName }} {{ employee.lastName }}</h2>
      <div class="img-container">
        <img
          id="prof-pic"
          src="https://www.oseyo.co.uk/wp-content/uploads/2020/05/empty-profile-picture-png-2.png"
          alt="No Pic"
        />
      </div>
      <div class="role-container">
        <h5>{{ employee.role }}</h5>
        <h6>{{ employee.companyEmail }}</h6>
      </div>

      <div class="contact-container">
        <div id="c-header">Personal Information</div>
        <div class="info-container">
          <div class="address-container">
            {{ employee.address.street }} <br />
            <div v-if="employee.address.suit != ''">
              {{ employee.address.suite }}
            </div>
            {{ employee.address.city }} , {{ employee.address.region }}
            {{ employee.address.postal }}<br />
            {{ employee.address.country }}
          </div>
          <div class="dates-container">
            Hire Date: {{ hired }} <br />
            Birthdate: {{ birthdate }}
          </div>
        </div>
      </div>
      <div class="additional-container">
        <div id="c-header">Assigned To Employee</div>
        <router-link v-on:click.prevent="refresh()" to>
          {{ assignedEmp.firstName }} {{ assignedEmp.lastName }}
        </router-link>
      </div>
    </div>
    <div class="skills-container">
      <h2>Skills</h2>
      <div
        class="skills"
        v-for="empSkill in employee.skills"
        :key="empSkill.id"
      >
        <Skill :skill="empSkill" />
      </div>
      <NewSkill :employee="employee" v-on:childToParent="onSkillSave" /> 
    </div>
  </div>
</template>

<script>
import EmpService from "@/services/EmployeeService";
import Skill from "@/components/Skill.vue";
import NewSkill from "@/components/NewSkillModal.vue";

// import { component } from 'vue/types/umd';

export default {
  name: "Profile",
  components: {
    Skill,
    NewSkill,
  },
  data() {
    return {
      employee: {},
      assignedEmp: {},
      hired: "",
      birthdate: "",
      newSkill: {},
    };
  },
  methods: {
    getEmployee() {
      EmpService.getEmployeeInfo(this.$route.params.id)
        .then((response) => {
          if (response.status === 200) {
            this.employee = response.data;

            var hDate = this.employee.hireDate.split("-");
            var bDate = this.employee.birthdate.split("-");

            this.hired = `${hDate[1]}-${hDate[2]}-${hDate[0]}`;
            this.birthdate = `${bDate[1]}-${bDate[2]}-${bDate[0]}`;

            this.getAssigned();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getAssigned() {
      console.log(this.employee.assignedTo);
      if (this.employee.assignedTo != null) {
        EmpService.getEmployeeInfo(this.employee.assignedTo)
          .then((response) => {
            if (response.status == 200) {
              this.assignedEmp = response.data;
              console.log(this.assignedEmp);
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    onSkillSave(newSkills) {
      this.employee.skills = newSkills;
    },
    flip() {
      this.modalOn = !this.modalOn;
      console.log("clicked!")
    },
  },
  created() {
    this.getEmployee();
  },
  mounted() {
    // this.getAssigned();
  },
};
</script>

<style>
.main-container {
  width: 100%;
  height: 100vh;
  /* background-color: lightgray; */
  display: grid;
  grid-template-columns: 1fr 2fr;
}
.personal-container {
  border: 1px solid lightgrey;
  border-radius: 8px;
  margin-left: 10px;
  margin-right: 10px;
  height: 75%;
}
.skills-container {
  border: 1px solid lightgrey;
  background-color: whitesmoke;
  border-radius: 8px;
  margin-left: 10px;
  margin-right: 10px;
}
.img-container {
  margin-top: 5px;
  height: 22vh;
}
#prof-pic {
  border: 1px solid black;
  border-radius: 50%;
  max-width: 100%;
  max-height: 100%;
  background-color: white;
}
.contact-container {
  display: grid;
  margin-top: 5%;
}
.role-container {
  margin-top: 9px;
}
#c-header {
  font-weight: bold;
  font-size: 1.1em;
  /* text-align: left;
  margin-left: 3px; */
}
.info-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  font-size: 16px;
}
.address-container {
  align-self: center;
  justify-self: center;
  margin-left: 5px;
}
.dates-container {
  align-self: start;
  justify-self: start;
}
.additional-container {
  margin-top: 10px;
}
.skills {
  margin: 4px;
}
</style>
