// import React from 'react';
// import { Container, Card } from 'react-bootstrap';

// const RegisterPage = () => {
//   return (
//     <Container>
//       <Card className="glass-card text-center">
//         <Card.Body>
//           <Card.Title className="text-white">Registration Page</Card.Title>
//           <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
//             Registration functionality will be implemented here.
//           </Card.Text>
//         </Card.Body>
//       </Card>
//     </Container>
//   );
// };

// export default RegisterPage;





// import React from 'react';
// import { Container, Card } from 'react-bootstrap';
// import { FiUserPlus } from "react-icons/fi"; // 👤 Registration Icon

// const RegisterPage = () => {
//   return (
//     <Container 
//       fluid 
//       className="d-flex justify-content-center align-items-center vh-100"
//       style={{
//         background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
//         padding: "20px"
//       }}
//     >
//       <Card 
//         className="text-center shadow-lg glass-card"
//         style={{
//           backdropFilter: "blur(15px)",
//           background: "rgba(255, 255, 255, 0.15)",
//           borderRadius: "25px",
//           padding: "40px 30px",
//           maxWidth: "420px",
//           width: "100%",
//           border: "1px solid rgba(255,255,255,0.3)",
//           boxShadow: "0 12px 40px rgba(0,0,0,0.25)",
//           transition: "all 0.3s ease-in-out",
//           transform: "scale(1)",
//           cursor: "pointer"
//         }}
//         onMouseEnter={(e) => e.currentTarget.style.transform = "scale(1.03)"}
//         onMouseLeave={(e) => e.currentTarget.style.transform = "scale(1)"}
//       >
//         <Card.Body>
//           <div style={{ fontSize: "3rem", color: "#fff", marginBottom: "15px" }}>
//             <FiUserPlus />
//           </div>
//           <Card.Title 
//             className="fw-bold"
//             style={{ 
//               color: "#fff", 
//               fontSize: "2rem", 
//               marginBottom: "15px",
//               letterSpacing: "1px"
//             }}
//           >
//             Registration Page
//           </Card.Title>
//           <Card.Text 
//             style={{ 
//               color: "rgba(255,255,255,0.85)", 
//               fontSize: "1.05rem",
//               lineHeight: "1.6"
//             }}
//           >
//             Registration functionality will be implemented here.  
//             <br />
//             ✨ Stay tuned for an amazing experience!
//           </Card.Text>
//         </Card.Body>
//       </Card>
//     </Container>
//   );
// };

// export default RegisterPage;






import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Alert, Card } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.name || !formData.email || !formData.password || !formData.confirmPassword) {
      setError('Please fill in all fields');
      return;
    }
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setLoading(true);
    setError('');

    try {
      await register(formData.name, formData.email, formData.password);
      navigate('/login');
    } catch (error) {
      setError(error.response?.data?.message || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div 
      style={{
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #e6f7ff, #f2fcfb)',
        display: 'flex',
        alignItems: 'center'
      }}
    >
      <Container>
        <Row className="justify-content-center">
          <Col xs={12} sm={8} md={6} lg={5}>
            <div className="text-center mb-4">
              <h1 style={{ color: '#007B83', fontWeight: '600' }}>Create Account</h1>
              <p style={{ color: '#4f5d75' }}>
                Register for your <span style={{ color: '#007B83', fontWeight: '500' }}>MediSync</span> account
              </p>
            </div>

            <Card 
              style={{ 
                borderRadius: '16px', 
                boxShadow: '0 6px 18px rgba(0,0,0,0.1)',
                border: 'none'
              }}
            >
              <Card.Body style={{ padding: '2rem' }}>
                {error && (
                  <Alert 
                    variant="danger" 
                    style={{
                      borderRadius: '12px',
                      backgroundColor: '#ffe6e6',
                      color: '#d9534f'
                    }}
                  >
                    {error}
                  </Alert>
                )}

                <Form onSubmit={handleSubmit}>
                  <Form.Group className="mb-3">
                    <Form.Label style={{ color: '#007B83', fontWeight: '500' }}>Full Name</Form.Label>
                    <Form.Control
                      type="text"
                      name="name"
                      value={formData.name}
                      onChange={handleChange}
                      style={{
                        borderRadius: '10px',
                        border: '1px solid #cce7e8',
                        padding: '0.8rem'
                      }}
                      placeholder="Enter your full name"
                      required
                    />
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label style={{ color: '#007B83', fontWeight: '500' }}>Email</Form.Label>
                    <Form.Control
                      type="email"
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                      style={{
                        borderRadius: '10px',
                        border: '1px solid #cce7e8',
                        padding: '0.8rem'
                      }}
                      placeholder="Enter your email"
                      required
                    />
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label style={{ color: '#007B83', fontWeight: '500' }}>Password</Form.Label>
                    <Form.Control
                      type="password"
                      name="password"
                      value={formData.password}
                      onChange={handleChange}
                      style={{
                        borderRadius: '10px',
                        border: '1px solid #cce7e8',
                        padding: '0.8rem'
                      }}
                      placeholder="Enter your password"
                      required
                    />
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label style={{ color: '#007B83', fontWeight: '500' }}>Confirm Password</Form.Label>
                    <Form.Control
                      type="password"
                      name="confirmPassword"
                      value={formData.confirmPassword}
                      onChange={handleChange}
                      style={{
                        borderRadius: '10px',
                        border: '1px solid #cce7e8',
                        padding: '0.8rem'
                      }}
                      placeholder="Re-enter your password"
                      required
                    />
                  </Form.Group>

                  <Button
                    type="submit"
                    className="w-100 mb-3"
                    disabled={loading}
                    style={{
                      backgroundColor: '#007B83',
                      border: 'none',
                      borderRadius: '12px',
                      padding: '0.75rem',
                      fontWeight: '500',
                      fontSize: '1rem'
                    }}
                  >
                    {loading ? (
                      <>
                        <span className="spinner-border spinner-border-sm me-2" />
                        Creating Account...
                      </>
                    ) : (
                      'Register'
                    )}
                  </Button>
                </Form>

                <div className="text-center">
                  <p style={{ color: '#4f5d75', marginBottom: 0 }}>
                    Already have an account?{' '}
                    <Link 
                      to="/login" 
                      style={{ color: '#007B83', fontWeight: '600', textDecoration: 'none' }}
                    >
                      Sign in here
                    </Link>
                  </p>
                </div>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default RegisterPage;









