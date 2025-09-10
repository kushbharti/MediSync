// import React from 'react';
// import { Navbar as BootstrapNavbar, Nav, NavDropdown, Container } from 'react-bootstrap';
// import { LinkContainer } from 'react-router-bootstrap';
// import { useAuth } from '../context/AuthContext';

// const Navbar = () => {
//   const { currentUser, logout, isAuthenticated, hasRole } = useAuth();

//   const handleLogout = () => {
//     logout();
//   };

//   return (
//     <BootstrapNavbar expand="lg" className="navbar-glass" fixed="top">
//       <Container>
//         <LinkContainer to="/">
//           <BootstrapNavbar.Brand>
//             <span className="navbar-brand">🏥 MediSync</span>
//           </BootstrapNavbar.Brand>
//         </LinkContainer>
        
//         <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
        
//         <BootstrapNavbar.Collapse id="basic-navbar-nav">
//           <Nav className="me-auto">
//             {isAuthenticated() && (
//               <LinkContainer to="/dashboard">
//                 <Nav.Link>Dashboard</Nav.Link>
//               </LinkContainer>
//             )}
            
//             {hasRole('PATIENT') && (
//               <NavDropdown title="Patient" id="patient-dropdown">
//                 <LinkContainer to="/patient/profile">
//                   <NavDropdown.Item>My Profile</NavDropdown.Item>
//                 </LinkContainer>
//                 <LinkContainer to="/patient/history">
//                   <NavDropdown.Item>Medical History</NavDropdown.Item>
//                 </LinkContainer>
//               </NavDropdown>
//             )}
            
//             {hasRole('DOCTOR') && (
//               <NavDropdown title="Doctor" id="doctor-dropdown">
//                 <LinkContainer to="/doctor/profile">
//                   <NavDropdown.Item>My Profile</NavDropdown.Item>
//                 </LinkContainer>
//                 <LinkContainer to="/doctor/patients">
//                   <NavDropdown.Item>My Patients</NavDropdown.Item>
//                 </LinkContainer>
//               </NavDropdown>
//             )}
            
//             {hasRole('ADMIN') && (
//               <LinkContainer to="/admin">
//                 <Nav.Link>Admin Panel</Nav.Link>
//               </LinkContainer>
//             )}
//           </Nav>
          
//           <Nav>
//             {!isAuthenticated() ? (
//               <>
//                 <LinkContainer to="/login">
//                   <Nav.Link>Login</Nav.Link>
//                 </LinkContainer>
//                 <LinkContainer to="/register">
//                   <Nav.Link>Register</Nav.Link>
//                 </LinkContainer>
//               </>
//             ) : (
//               <NavDropdown title={`👋 ${currentUser?.name}`} id="user-dropdown" align="end">
//                 <NavDropdown.Item disabled>
//                   {currentUser?.email}
//                 </NavDropdown.Item>
//                 <NavDropdown.Item disabled>
//                   Role: {currentUser?.role?.replace('ROLE_', '')}
//                 </NavDropdown.Item>
//                 <NavDropdown.Divider />
//                 <NavDropdown.Item onClick={handleLogout}>
//                   Logout
//                 </NavDropdown.Item>
//               </NavDropdown>
//             )}
//           </Nav>
//         </BootstrapNavbar.Collapse>
//       </Container>
//     </BootstrapNavbar>
//   );
// };

// export default Navbar;







import React from 'react';
import { Navbar as BootstrapNavbar, Nav, NavDropdown, Container } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import { useAuth } from '../context/AuthContext';
import { FiHome, FiUser, FiUsers, FiSettings, FiLogIn, FiUserPlus, FiLogOut, FiUserCheck, FiFileText } from 'react-icons/fi';

const Navbar = () => {
  const { currentUser, logout, isAuthenticated, hasRole } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <BootstrapNavbar
      expand="lg"
      fixed="top"
      style={{
        background: 'linear-gradient(90deg, #007B83, #009ca8)',
        padding: '0.8rem 1rem',
        boxShadow: '0 3px 8px rgba(0,0,0,0.1)',
      }}
    >
      <Container>
        <LinkContainer to="/">
          <BootstrapNavbar.Brand
            style={{
              fontWeight: '600',
              color: '#fff',
              fontSize: '1.3rem',
              display: 'flex',
              alignItems: 'center',
              gap: '0.5rem',
            }}
          >
            🏥 <span>MediSync</span>
          </BootstrapNavbar.Brand>
        </LinkContainer>

        <BootstrapNavbar.Toggle
          aria-controls="basic-navbar-nav"
          style={{ border: 'none', backgroundColor: '#ffffffaa' }}
        />

        <BootstrapNavbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {isAuthenticated() && (
              <LinkContainer to="/dashboard">
                <Nav.Link style={{ color: '#fff', fontWeight: 500 }}>
                  <FiHome className="me-1" /> Dashboard
                </Nav.Link>
              </LinkContainer>
            )}

            {hasRole('PATIENT') && (
              <NavDropdown
                title={
                  <span style={{ display: 'flex', alignItems: 'center' }}>
                    <FiUserCheck className="me-1" /> Patient
                  </span>
                }
                id="patient-dropdown"
                menuVariant="light"
              >
                <LinkContainer to="/patient/profile">
                  <NavDropdown.Item>
                    <FiUser className="me-2" /> My Profile
                  </NavDropdown.Item>
                </LinkContainer>
                <LinkContainer to="/patient/history">
                  <NavDropdown.Item>
                    <FiFileText className="me-2" /> Medical History
                  </NavDropdown.Item>
                </LinkContainer>
              </NavDropdown>
            )}

            {hasRole('DOCTOR') && (
              <NavDropdown
                title={
                  <span style={{ display: 'flex', alignItems: 'center' }}>
                    <FiUsers className="me-1" /> Doctor
                  </span>
                }
                id="doctor-dropdown"
                menuVariant="light"
              >
                <LinkContainer to="/doctor/profile">
                  <NavDropdown.Item>
                    <FiUser className="me-2" /> My Profile
                  </NavDropdown.Item>
                </LinkContainer>
                <LinkContainer to="/doctor/patients">
                  <NavDropdown.Item>
                    <FiUsers className="me-2" /> My Patients
                  </NavDropdown.Item>
                </LinkContainer>
              </NavDropdown>
            )}

            {hasRole('ADMIN') && (
              <LinkContainer to="/admin">
                <Nav.Link style={{ color: '#fff', fontWeight: 500 }}>
                  <FiSettings className="me-1" /> Admin Panel
                </Nav.Link>
              </LinkContainer>
            )}
          </Nav>

          <Nav>
            {!isAuthenticated() ? (
              <>
                <LinkContainer to="/login">
                  <Nav.Link style={{ color: '#fff', fontWeight: 500 }}>
                    <FiLogIn className="me-1" /> Login
                  </Nav.Link>
                </LinkContainer>
                <LinkContainer to="/register">
                  <Nav.Link style={{ color: '#fff', fontWeight: 500 }}>
                    <FiUserPlus className="me-1" /> Register
                  </Nav.Link>
                </LinkContainer>
              </>
            ) : (
              <NavDropdown
                title={
                  <span style={{ display: 'flex', alignItems: 'center' }}>
                    👋 {currentUser?.name}
                  </span>
                }
                id="user-dropdown"
                align="end"
                menuVariant="light"
              >
                <NavDropdown.Item disabled>
                  {currentUser?.email}
                </NavDropdown.Item>
                <NavDropdown.Item disabled>
                  Role: {currentUser?.role?.replace('ROLE_', '')}
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  <FiLogOut className="me-2" /> Logout
                </NavDropdown.Item>
              </NavDropdown>
            )}
          </Nav>
        </BootstrapNavbar.Collapse>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;
