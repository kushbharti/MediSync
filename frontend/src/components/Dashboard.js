import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Alert } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import { Link } from 'react-router-dom';
import apiService from '../services/apiService';

const Dashboard = () => {
  const { currentUser, hasRole } = useAuth();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadDashboardData = async () => {
      try {
        if (hasRole('ADMIN')) {
          const response = await apiService.getSystemStats();
          setStats(response.data);
        }
      } catch (error) {
        console.error('Error loading dashboard data:', error);
        setError('Failed to load dashboard data');
      } finally {
        setLoading(false);
      }
    };

    loadDashboardData();
  }, [hasRole]);

  const getRoleSpecificContent = () => {
    if (hasRole('PATIENT')) {
      return (
        <Row className="g-4">
          <Col md={6}>
            <Card className="glass-card h-100">
              <Card.Body>
                <Card.Title className="text-white">👤 My Profile</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  View and update your personal information and medical details.
                </Card.Text>
                <Link to="/patient/profile" className="btn btn-primary-glass">
                  View Profile
                </Link>
              </Card.Body>
            </Card>
          </Col>
          <Col md={6}>
            <Card className="glass-card h-100">
              <Card.Body>
                <Card.Title className="text-white">📋 Medical History</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  Access your complete medical records across all hospitals.
                </Card.Text>
                <Link to="/patient/history" className="btn btn-primary-glass">
                  View History
                </Link>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      );
    }

    if (hasRole('DOCTOR')) {
      return (
        <Row className="g-4">
          <Col md={6}>
            <Card className="glass-card h-100">
              <Card.Body>
                <Card.Title className="text-white">👨‍⚕️ My Profile</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  Manage your professional profile and hospital affiliation.
                </Card.Text>
                <Link to="/doctor/profile" className="btn btn-primary-glass">
                  View Profile
                </Link>
              </Card.Body>
            </Card>
          </Col>
          <Col md={6}>
            <Card className="glass-card h-100">
              <Card.Body>
                <Card.Title className="text-white">👥 My Patients</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  Access and manage your patients' medical records.
                </Card.Text>
                <Link to="/doctor/patients" className="btn btn-primary-glass">
                  View Patients
                </Link>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      );
    }

    if (hasRole('ADMIN')) {
      return (
        <>
          {stats && (
            <div className="dashboard-grid">
              <div className="stat-card">
                <div className="stat-number">{stats.totalUsers || 0}</div>
                <div className="stat-label">Total Users</div>
              </div>
              <div className="stat-card">
                <div className="stat-number">{stats.totalPatients || 0}</div>
                <div className="stat-label">Patients</div>
              </div>
              <div className="stat-card">
                <div className="stat-number">{stats.totalDoctors || 0}</div>
                <div className="stat-label">Doctors</div>
              </div>
              <div className="stat-card">
                <div className="stat-number">{stats.totalHospitals || 0}</div>
                <div className="stat-label">Hospitals</div>
              </div>
            </div>
          )}
          <Row className="g-4 mt-4">
            <Col md={4}>
              <Card className="glass-card h-100">
                <Card.Body>
                  <Card.Title className="text-white">⚙️ System Management</Card.Title>
                  <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                    Manage users, hospitals, and system settings.
                  </Card.Text>
                  <Link to="/admin" className="btn btn-primary-glass">
                    Admin Panel
                  </Link>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </>
      );
    }

    return (
      <Row>
        <Col>
          <Card className="glass-card text-center">
            <Card.Body>
              <Card.Title className="text-white">Welcome to MediSync!</Card.Title>
              <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                Your dashboard will be populated based on your role and permissions.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    );
  };

  if (loading) {
    return (
      <Container className="py-5">
        <div className="d-flex justify-content-center">
          <div className="spinner-glass"></div>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row>
        <Col>
          <div className="text-center mb-5">
            <h1 className="text-white mb-2">
              Welcome back, {currentUser?.name}! 👋
            </h1>
            <p style={{ color: 'rgba(255,255,255,0.8)' }}>
              Role: {currentUser?.role?.replace('ROLE_', '') || 'User'}
            </p>
          </div>

          {error && (
            <Alert variant="danger" className="alert-danger-glass mb-4">
              {error}
            </Alert>
          )}

          {getRoleSpecificContent()}
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;
