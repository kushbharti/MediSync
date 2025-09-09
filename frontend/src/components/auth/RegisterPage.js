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





import React from 'react';
import { Container, Card } from 'react-bootstrap';

const RegisterPage = () => {
  return (
    <Container 
      fluid 
      className="d-flex justify-content-center align-items-center vh-100"
      style={{
        background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
        padding: "20px"
      }}
    >
      <Card 
        className="text-center shadow-lg glass-card"
        style={{
          backdropFilter: "blur(12px)",
          background: "rgba(255, 255, 255, 0.1)",
          borderRadius: "20px",
          padding: "30px",
          maxWidth: "400px",
          width: "100%",
          border: "1px solid rgba(255,255,255,0.2)",
          boxShadow: "0 8px 32px rgba(0,0,0,0.2)",
          transition: "transform 0.3s ease-in-out"
        }}
      >
        <Card.Body>
          <Card.Title 
            className="fw-bold"
            style={{ 
              color: "#fff", 
              fontSize: "1.8rem", 
              marginBottom: "15px" 
            }}
          >
            🚀 Registration Page
          </Card.Title>
          <Card.Text 
            style={{ 
              color: "rgba(255,255,255,0.9)", 
              fontSize: "1rem" 
            }}
          >
            Registration functionality will be implemented here.
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default RegisterPage;
















