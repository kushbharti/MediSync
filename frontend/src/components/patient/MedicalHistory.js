import React from 'react';
import { Container, Card } from 'react-bootstrap';

const MedicalHistory = () => {
  return (
    <Container>
      <Card className="glass-card text-center">
        <Card.Body>
          <Card.Title className="text-white">Medical History</Card.Title>
          <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
            Medical history viewing will be implemented here.
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default MedicalHistory;
