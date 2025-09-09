import React from 'react';
import { Container, Card } from 'react-bootstrap';

const DoctorPatients = () => {
  return (
    <Container>
      <Card className="glass-card text-center">
        <Card.Body>
          <Card.Title className="text-white">My Patients</Card.Title>
          <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
            Patient management for doctors will be implemented here.
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default DoctorPatients;
