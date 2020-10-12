import axios from 'axios';
import { v4 as uuidv4 } from 'uuid';

const http = axios.create({
    baseURL: 'http://localhost:8080'
});

export default{
    getAllEmployee(){
        return http.get('/employees');
    },
    addEmployee(employee){
        employee.id = uuidv4();
        employee.address.id = uuidv4();
        
        return http.post('/employees', employee);
    },
    getEmployeeInfo(id){
        return http.get('/employees/' + id)
    }
}