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
            Hire Date: {{ employee.hireDate }}<br />
            Birthdate: {{ employee.birthdate }}
          </div>
        </div>
      </div>
      <div class="additional-container">
        <div id="c-header">Assigned To Employee</div>
      </div>
    </div>
    <div class="skills-container">
      <h2>Skills</h2>
    </div>
  </div>
</template>

<script>
import EmpService from "@/services/EmployeeService";
// import { component } from 'vue/types/umd';

export default {
  name: "Profile",
  components: {},
  data() {
    return {
      employee: {},
      assignedEmp: {},
    };
  },
  methods: {
    getEmployee() {
      EmpService.getEmployeeInfo(this.$route.params.id)
        .then((response) => {
          if (response.status === 200) {
            this.employee = response.data;
            console.log(this.employee);
          }
        })
        .catch((err) => {
          console.log(err);
        });

      this.getAssigned();
    },
    getAssigned() {
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
  },
  mounted() {
    this.getEmployee();
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
}
.skills-container {
  border: 1px solid lightgrey;
  border-radius: 8px;
  margin-left: 10px;
  margin-right: 10px;
}
.img-container {
  margin-top: 5px;
  height: 33vh;
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
  margin-top: 20px;
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
  justify-self: start;
  margin-left: 5px;
}
.dates-container {
  align-self: start;
  justify-self: start;
}
.additional-container {
  margin-top: 10px;
}
</style>
