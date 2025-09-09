import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

const Footer = () => {
  return (
    <footer className="footer">
      <Container>
        <Row>
          <Col md={6}>
            <h5 className="text-white">🏥 MediSync</h5>
            <p style={{ color: 'rgba(255,255,255,0.8)', fontSize: '0.9rem' }}>
              Revolutionizing healthcare through seamless cross-hospital interoperability.
            </p>
          </Col>
          <Col md={6} className="text-md-end">
            <p style={{ color: 'rgba(255,255,255,0.8)', fontSize: '0.9rem' }}>
              © 2024 MediSync. All rights reserved.
            </p>
            <p style={{ color: 'rgba(255,255,255,0.6)', fontSize: '0.8rem' }}>
              HIPAA Compliant • HL7 FHIR Compatible • Secure by Design
            </p>
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;
