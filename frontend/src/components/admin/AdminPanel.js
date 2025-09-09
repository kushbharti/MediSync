import React from 'react';
import { Container, Card } from 'react-bootstrap';

const AdminPanel = () => {
  return (
    <Container>
      <Card className="glass-card text-center">
        <Card.Body>
          <Card.Title className="text-white">Admin Panel</Card.Title>
          <Card.Text style={{ color: 'rgba(255,255,255,0.8)' }}>
            System administration features will be implemented here.
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default AdminPanel;
