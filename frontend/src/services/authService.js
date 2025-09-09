import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

class AuthService {
  login(email, password) {
    return axios.post(`${API_URL}/signin`, {
      email,
      password
    });
  }

  register(userData) {
    return axios.post(`${API_URL}/signup`, userData);
  }

  logout() {
    return axios.post(`${API_URL}/signout`);
  }
}

export default new AuthService();
