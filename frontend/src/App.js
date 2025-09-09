import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Container } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

// Components
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import LandingPage from './components/LandingPage';
import LoginPage from './components/auth/LoginPage';
import RegisterPage from './components/auth/RegisterPage';
import Dashboard from './components/Dashboard';
import PatientProfile from './components/patient/PatientProfile';
import MedicalHistory from './components/patient/MedicalHistory';
import DoctorProfile from './components/doctor/DoctorProfile';
import DoctorPatients from './components/doctor/DoctorPatients';
import AdminPanel from './components/admin/AdminPanel';
import ProtectedRoute from './components/common/ProtectedRoute';

// Context
import { AuthProvider } from './context/AuthContext';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Navbar />
          <main className="main-content">
            <Container fluid className="p-0">
              <Routes>
                {/* Public Routes */}
                <Route path="/" element={<LandingPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />

                {/* Protected Routes */}
                <Route path="/dashboard" element={
                  
                    <Dashboard />
                  
                } />

                {/* Patient Routes */}
                <Route path="/patient/profile" element={
                  // <ProtectedRoute role="PATIENT">
                    <PatientProfile />
                  // </ProtectedRoute>
                } />
                <Route path="/patient/history" element={
                  <ProtectedRoute role="PATIENT">
                    <MedicalHistory />
                  </ProtectedRoute>
                } />

                {/* Doctor Routes */}
                <Route path="/doctor/profile" element={
                  <ProtectedRoute role="DOCTOR">
                    <DoctorProfile />
                  </ProtectedRoute>
                } />
                <Route path="/doctor/patients" element={
                  <ProtectedRoute role="DOCTOR">
                    <DoctorPatients />
                  </ProtectedRoute>
                } />

                {/* Admin Routes */}
                <Route path="/admin" element={
                  <ProtectedRoute role="ADMIN">
                    <AdminPanel />
                  </ProtectedRoute>
                } />

                {/* Fallback Route */}
                <Route path="*" element={<Navigate to="/" replace />} />
              </Routes>
            </Container>
          </main>
          <Footer />
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
