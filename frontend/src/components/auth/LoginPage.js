// import React, { useState } from 'react';
// import { Container, Row, Col, Form, Button, Alert, Card } from 'react-bootstrap';
// import { Link, useNavigate } from 'react-router-dom';
// import { useAuth } from '../../context/AuthContext';

// const LoginPage = () => {
//   const [formData, setFormData] = useState({
//     email: '',
//     password: ''
//   });
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState('');
  
//   const { login } = useAuth();
//   const navigate = useNavigate();

//   const handleChange = (e) => {
//     setFormData({
//       ...formData,
//       [e.target.name]: e.target.value
//     });
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
    
//     if (!formData.email || !formData.password) {
//       setError('Please fill in all fields');
//       return;
//     }

//     setLoading(true);
//     setError('');

//     try {
//       const user = await login(formData.email, formData.password);
      
//       // Redirect based on user role
//       if (user.role === 'ROLE_ADMIN' || user.role === 'ADMIN') {
//         navigate('/admin');
//       } else if (user.role === 'ROLE_DOCTOR' || user.role === 'DOCTOR') {
//         navigate('/doctor/profile');
//       } else if (user.role === 'ROLE_PATIENT' || user.role === 'PATIENT') {
//         navigate('/patient/profile');
//       } else {
//         navigate('/dashboard');
//       }
//     } catch (error) {
//       setError(error.response?.data?.message || 'Login failed. Please check your credentials.');
//     } finally {
//       setLoading(false);
//     }
//   };

//   return (
//     <Container>
//       <Row className="justify-content-center">
//         <Col xs={12} sm={8} md={6} lg={5}>
//           <div className="text-center mb-4">
//             <h1 className="text-white mb-2">Welcome Back</h1>
//             <p style={{ color: 'rgba(255,255,255,0.8)' }}>
//               Sign in to your MediSync account
//             </p>
//           </div>

//           <Card className="glass-card">
//             <Card.Body>
//               {error && (
//                 <Alert variant="danger" className="alert-danger-glass">
//                   {error}
//                 </Alert>
//               )}

//               <Form onSubmit={handleSubmit}>
//                 <Form.Group className="mb-3">
//                   <Form.Label className="text-white">Email</Form.Label>
//                   <Form.Control
//                     type="email"
//                     name="email"
//                     value={formData.email}
//                     onChange={handleChange}
//                     className="form-control-glass"
//                     placeholder="Enter your email"
//                     required
//                   />
//                 </Form.Group>

//                 <Form.Group className="mb-3">
//                   <Form.Label className="text-white">Password</Form.Label>
//                   <Form.Control
//                     type="password"
//                     name="password"
//                     value={formData.password}
//                     onChange={handleChange}
//                     className="form-control-glass"
//                     placeholder="Enter your password"
//                     required
//                   />
//                 </Form.Group>

//                 <Button
//                   type="submit"
//                   className="btn-primary-glass w-100 mb-3"
//                   disabled={loading}
//                 >
//                   {loading ? (
//                     <>
//                       <span className="spinner-border spinner-border-sm me-2" />
//                       Signing in...
//                     </>
//                   ) : (
//                     'Sign In'
//                   )}
//                 </Button>
//               </Form>

//               <div className="text-center">
//                 <p className="text-white mb-0">
//                   Don't have an account?{' '}
//                   <Link 
//                     to="/register" 
//                     style={{ color: 'var(--accent-color)', textDecoration: 'none' }}
//                   >
//                     Sign up here
//                   </Link>
//                 </p>
//               </div>
//             </Card.Body>
//           </Card>

//           <div className="text-center mt-4">
//             <p style={{ color: 'rgba(255,255,255,0.6)', fontSize: '0.9rem' }}>
//               Demo Accounts:<br />
//               Patient: patient@demo.com / password123<br />
//               Doctor: doctor@demo.com / password123<br />
//               Admin: admin@demo.com / password123
//             </p>
//           </div>
//         </Col>
//       </Row>
//     </Container>
//   );
// };

// export default LoginPage;




import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Alert, Card } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const LoginPage = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.email || !formData.password) {
      setError('Please fill in all fields');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const user = await login(formData.email, formData.password);
      
      // Redirect based on user role
      if (user.role === 'ROLE_ADMIN' || user.role === 'ADMIN') {
        navigate('/admin');
      } else if (user.role === 'ROLE_DOCTOR' || user.role === 'DOCTOR') {
        navigate('/doctor/profile');
      } else if (user.role === 'ROLE_PATIENT' || user.role === 'PATIENT') {
        navigate('/patient/profile');
      } else {
        navigate('/dashboard');
      }
    } catch (error) {
      setError(error.response?.data?.message || 'Login failed. Please check your credentials.');
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
              <h1 style={{ color: '#007B83', fontWeight: '600' }}>Welcome Back</h1>
              <p style={{ color: '#4f5d75' }}>
                Sign in to your <span style={{ color: '#007B83', fontWeight: '500' }}>MediSync</span> account
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
                        Signing in...
                      </>
                    ) : (
                      'Sign In'
                    )}
                  </Button>
                </Form>

                <div className="text-center">
                  <p style={{ color: '#4f5d75', marginBottom: 0 }}>
                    Don't have an account?{' '}
                    <Link 
                      to="/register" 
                      style={{ color: '#007B83', fontWeight: '600', textDecoration: 'none' }}
                    >
                      Sign up here
                    </Link>
                  </p>
                </div>
              </Card.Body>
            </Card>

            <div className="text-center mt-4">
              <p style={{ color: '#4f5d75', fontSize: '0.9rem' }}>
                <strong>Demo Accounts:</strong><br />
                Patient: <span style={{ color: '#007B83' }}>patient@demo.com</span> / password123<br />
                Doctor: <span style={{ color: '#007B83' }}>doctor@demo.com</span> / password123<br />
                Admin: <span style={{ color: '#007B83' }}>admin@demo.com</span> / password123
              </p>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default LoginPage;