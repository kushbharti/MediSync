import React from 'react';
import { Container, Card } from 'react-bootstrap';

const DoctorProfile = () => {
  return (
    <Container>
      <Card className="glass-card text-center">
        <Card.Body>
          <Card.Title className="text-white">Doctor Profile</Card.Title>
          <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
            Doctor profile management will be implemented here.
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default DoctorProfile;
