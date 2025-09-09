import React from 'react';
import { Navbar as BootstrapNavbar, Nav, NavDropdown, Container } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { currentUser, logout, isAuthenticated, hasRole } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <BootstrapNavbar expand="lg" className="navbar-glass" fixed="top">
      <Container>
        <LinkContainer to="/">
          <BootstrapNavbar.Brand>
            <span className="navbar-brand">🏥 MediSync</span>
          </BootstrapNavbar.Brand>
        </LinkContainer>
        
        <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
        
        <BootstrapNavbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {isAuthenticated() && (
              <LinkContainer to="/dashboard">
                <Nav.Link>Dashboard</Nav.Link>
              </LinkContainer>
            )}
            
            {hasRole('PATIENT') && (
              <NavDropdown title="Patient" id="patient-dropdown">
                <LinkContainer to="/patient/profile">
                  <NavDropdown.Item>My Profile</NavDropdown.Item>
                </LinkContainer>
                <LinkContainer to="/patient/history">
                  <NavDropdown.Item>Medical History</NavDropdown.Item>
                </LinkContainer>
              </NavDropdown>
            )}
            
            {hasRole('DOCTOR') && (
              <NavDropdown title="Doctor" id="doctor-dropdown">
                <LinkContainer to="/doctor/profile">
                  <NavDropdown.Item>My Profile</NavDropdown.Item>
                </LinkContainer>
                <LinkContainer to="/doctor/patients">
                  <NavDropdown.Item>My Patients</NavDropdown.Item>
                </LinkContainer>
              </NavDropdown>
            )}
            
            {hasRole('ADMIN') && (
              <LinkContainer to="/admin">
                <Nav.Link>Admin Panel</Nav.Link>
              </LinkContainer>
            )}
          </Nav>
          
          <Nav>
            {!isAuthenticated() ? (
              <>
                <LinkContainer to="/login">
                  <Nav.Link>Login</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/register">
                  <Nav.Link>Register</Nav.Link>
                </LinkContainer>
              </>
            ) : (
              <NavDropdown title={`👋 ${currentUser?.name}`} id="user-dropdown" align="end">
                <NavDropdown.Item disabled>
                  {currentUser?.email}
                </NavDropdown.Item>
                <NavDropdown.Item disabled>
                  Role: {currentUser?.role?.replace('ROLE_', '')}
                </NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  Logout
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
