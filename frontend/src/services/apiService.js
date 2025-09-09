import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance with default config
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  }
});

// Request interceptor to add auth token
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Token expired or invalid
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

class ApiService {
  // Patient endpoints
  getPatientProfile() {
    return apiClient.get('/patients/profile');
  }

  getPatientHistory() {
    return apiClient.get('/patients/profile/history');
  }

  updatePatient(patientId, data) {
    return apiClient.put(`/patients/${patientId}`, data);
  }

  // Doctor endpoints
  getDoctorProfile() {
    return apiClient.get('/doctors/profile');
  }

  getDoctorPatients() {
    return apiClient.get('/doctors/patients');
  }

  addPatientRecord(doctorId, patientId, record) {
    return apiClient.post(`/doctors/${doctorId}/patients/${patientId}/records`, record);
  }

  updateMedicalRecord(doctorId, recordId, record) {
    return apiClient.put(`/doctors/${doctorId}/records/${recordId}`, record);
  }

  searchPatients(name) {
    return apiClient.get(`/patients/search?name=${name}`);
  }

  // Admin endpoints
  getSystemStats() {
    return apiClient.get('/admin/stats');
  }

  getAllUsers() {
    return apiClient.get('/admin/users');
  }

  getAllHospitals() {
    return apiClient.get('/admin/hospitals');
  }

  addHospital(hospital) {
    return apiClient.post('/admin/hospitals', hospital);
  }

  updateHospital(hospitalId, hospital) {
    return apiClient.put(`/admin/hospitals/${hospitalId}`, hospital);
  }

  deleteHospital(hospitalId) {
    return apiClient.delete(`/admin/hospitals/${hospitalId}`);
  }

  toggleUserStatus(userId) {
    return apiClient.patch(`/admin/users/${userId}/toggle-status`);
  }

  // Sync endpoints
  getSyncStatus() {
    return apiClient.get('/sync/status');
  }

  syncPatientData(patientId) {
    return apiClient.post(`/sync/patient/${patientId}`);
  }

  testHospitalConnection(hospitalId) {
    return apiClient.post(`/sync/test/${hospitalId}`);
  }

  // Public endpoints
  getSystemInfo() {
    return axios.get(`${API_BASE_URL}/public/info`);
  }

  getPublicHospitals() {
    return axios.get(`${API_BASE_URL}/public/hospitals`);
  }

  healthCheck() {
    return axios.get(`${API_BASE_URL}/public/health`);
  }
}

export default new ApiService();
