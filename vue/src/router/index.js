import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Profile from '@/views/Profile'
import AddEmployee from '@/views/AddEmployee'
import EmployeeProfile from '@/views/EmployeeProfile'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/profile/:id',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/add',
    name: 'Add',
    component: AddEmployee
  },
  {
    path: '/employees/:id',
    name: 'EmployeeProfile',
    component: EmployeeProfile
  }
]

const router = new VueRouter({
  routes
})

export default router
