import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import apiService from '../services/apiService';

const LandingPage = () => {
  const { isAuthenticated } = useAuth();
  const [systemInfo, setSystemInfo] = useState(null);
  const [hospitals, setHospitals] = useState([]);

  useEffect(() => {
    // Load system information
    apiService.getSystemInfo()
      .then(response => setSystemInfo(response.data))
      .catch(error => console.error('Error loading system info:', error));

    // Load public hospitals
    apiService.getPublicHospitals()
      .then(response => setHospitals(response.data.slice(0, 3))) // Show first 3
      .catch(error => console.error('Error loading hospitals:', error));
  }, []);

  return (
    <div>
      {/* Hero Section */}
      <section className="hero-section">
        <Container>
          <Row>
            <Col lg={8} className="mx-auto text-center">
              <h1 className="hero-title floating">
                MediSync
              </h1>
              <p className="hero-subtitle fade-in-up">
                Revolutionizing Healthcare Through Seamless Cross-Hospital Interoperability
              </p>
              <p className="lead mb-4 fade-in-up" style={{ color: 'rgba(255,255,255,0.9)' }}>
                Secure, standardized, and efficient sharing of patient medical records 
                across healthcare institutions worldwide.
              </p>
              <div className="fade-in-up">
                {!isAuthenticated() ? (
                  <div>
                    <Button 
                      as={Link} 
                      to="/register" 
                      className="btn-primary-glass me-3 mb-2"
                      size="lg"
                    >
                      Get Started
                    </Button>
                    <Button 
                      as={Link} 
                      to="/login" 
                      className="btn-glass mb-2"
                      size="lg"
                    >
                      Sign In
                    </Button>
                  </div>
                ) : (
                  <Button 
                    as={Link} 
                    to="/dashboard" 
                    className="btn-primary-glass"
                    size="lg"
                  >
                    Go to Dashboard
                  </Button>
                )}
              </div>
            </Col>
          </Row>
        </Container>
      </section>

      {/* Features Section */}
      <Container className="py-5">
        <Row>
          <Col lg={8} className="mx-auto text-center mb-5">
            <h2 className="text-white mb-4">Why Choose MediSync?</h2>
          </Col>
        </Row>
        
        <Row className="g-4">
          <Col md={4}>
            <Card className="glass-card text-center h-100">
              <Card.Body>
                <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🔒</div>
                <Card.Title className="text-white">Secure & Compliant</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  HIPAA-compliant with end-to-end encryption, ensuring patient data 
                  security across all healthcare networks.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
          
          <Col md={4}>
            <Card className="glass-card text-center h-100">
              <Card.Body>
                <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>⚡</div>
                <Card.Title className="text-white">Real-Time Sync</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  Instant synchronization of medical records across all connected 
                  hospitals and healthcare institutions.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
          
          <Col md={4}>
            <Card className="glass-card text-center h-100">
              <Card.Body>
                <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🌐</div>
                <Card.Title className="text-white">Universal Access</Card.Title>
                <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                  HL7 FHIR standard compliance ensures seamless integration 
                  with existing hospital management systems.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>

      {/* Connected Hospitals Section */}
      {hospitals.length > 0 && (
        <Container className="py-5">
          <Row>
            <Col lg={8} className="mx-auto text-center mb-5">
              <h2 className="text-white mb-4">Connected Healthcare Network</h2>
              <p style={{ color: 'rgba(255,255,255,0.8)' }}>
                Join our growing network of healthcare institutions
              </p>
            </Col>
          </Row>
          
          <Row className="g-4">
            {hospitals.map((hospital, index) => (
              <Col md={4} key={hospital.hospitalId}>
                <Card className="glass-card h-100">
                  <Card.Body>
                    <Card.Title className="text-white">{hospital.name}</Card.Title>
                    <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
                      {hospital.address}
                    </Card.Text>
                    <Card.Text style={{ color: 'rgba(255,255,255,0.6)', fontSize: '0.9rem' }}>
                      {hospital.contactInfo}
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </Container>
      )}

      {/* System Info Section */}
      {systemInfo && (
        <Container className="py-5">
          <Row>
            <Col lg={6} className="mx-auto">
              <Card className="glass-card text-center">
                <Card.Body>
                  <Card.Title className="text-white mb-3">System Status</Card.Title>
                  <div className="d-flex justify-content-around">
                    <div>
                      <h5 className="text-success">✅ Operational</h5>
                      <small style={{ color: 'rgba(255,255,255,0.7)' }}>
                        Version {systemInfo.version}
                      </small>
                    </div>
                    <div>
                      <h5 className="text-white">🏥 Healthcare</h5>
                      <small style={{ color: 'rgba(255,255,255,0.7)' }}>
                        Interoperability Platform
                      </small>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      )}
    </div>
  );
};

export default LandingPage;
