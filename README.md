# MediSync - Cross-Hospital Interoperability System

![MediSync Logo](https://via.placeholder.com/200x80/667eea/ffffff?text=MediSync)

## 🏥 Overview

MediSync is a revolutionary healthcare interoperability platform that enables seamless sharing and synchronization of patient medical records across different hospitals and healthcare institutions. The system addresses the critical problem of fragmented healthcare data by creating a centralized, secure, and standardized platform for medical record management.

## ✨ Features

### 🔐 Security & Compliance
- **HIPAA-compliant** data handling and storage
- **JWT-based authentication** with role-based access control
- **End-to-end encryption** for sensitive medical data
- **Audit trails** for all data access and modifications

### 🌐 Interoperability
- **HL7 FHIR R4** standard compliance
- **Real-time synchronization** across healthcare networks
- **API-first architecture** for easy integration
- **Cross-hospital data sharing** with patient consent

### 👥 Multi-Role Support
- **Patients**: View personal medical records, track hospital visits
- **Doctors**: Access patient history, update treatment records
- **Admins**: Manage system users, hospitals, and integrations

### 🎨 Modern UI/UX
- **Glassmorphism design** with responsive layout
- **Dark/Light mode** support
- **Accessibility compliant** interface
- **Real-time notifications** and updates

## 🏗️ Architecture

### Backend (Spring Boot)
- **Java 17** with Spring Boot 3.1.5
- **MySQL 8.0** database with JPA/Hibernate
- **JWT authentication** and Spring Security
- **RESTful API** design with comprehensive endpoints
- **Docker containerization** ready

### Frontend (React)
- **React 18** with modern hooks
- **Bootstrap 5** with custom glassmorphism styling
- **React Router** for SPA navigation
- **Axios** for API communication
- **Responsive design** for all devices

### Database Schema
- **Users & Roles** management
- **Patients** with comprehensive medical profiles
- **Doctors** with hospital affiliations
- **Medical Records** with full audit trails
- **Hospitals** with API integration capabilities

## 🚀 Quick Start

### Prerequisites
- **Java 17+**
- **Node.js 18+**
- **MySQL 8.0+**
- **Docker** (optional but recommended)

### Using Docker (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MediSync
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080/api
   - Database: localhost:3306

### Manual Setup

#### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Configure database**
   - Create MySQL database: `medisync_db`
   - Update `application.properties` with your database credentials

3. **Run the backend**
   ```bash
   ./mvnw spring-boot:run
   ```

#### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm start
   ```

## 📊 API Documentation

### Authentication Endpoints
- `POST /api/auth/signin` - User login
- `POST /api/auth/signup` - User registration
- `POST /api/auth/signout` - User logout

### Patient Endpoints
- `GET /api/patients/profile` - Get patient profile
- `GET /api/patients/profile/history` - Get medical history
- `PUT /api/patients/{id}` - Update patient information

### Doctor Endpoints
- `GET /api/doctors/profile` - Get doctor profile
- `GET /api/doctors/patients` - Get doctor's patients
- `POST /api/doctors/{id}/patients/{patientId}/records` - Add medical record

### Admin Endpoints
- `GET /api/admin/stats` - Get system statistics
- `GET /api/admin/users` - Get all users
- `GET /api/admin/hospitals` - Get all hospitals

### Public Endpoints
- `GET /api/public/info` - Get system information
- `GET /api/public/hospitals` - Get public hospital directory
- `GET /api/public/health` - Health check endpoint

## 🔧 Configuration

### Environment Variables

#### Backend
```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/medisync_db
SPRING_DATASOURCE_USERNAME=medisync
SPRING_DATASOURCE_PASSWORD=password
MEDISYNC_APP_JWT_SECRET=your-jwt-secret-key
MEDISYNC_APP_JWT_EXPIRATION_MS=86400000
MEDISYNC_APP_FRONTEND_URL=http://localhost:3000
```

#### Frontend
```env
REACT_APP_API_URL=http://localhost:8080/api
```

## 🧪 Testing

### Backend Testing
```bash
cd backend
./mvnw test
```

### Frontend Testing
```bash
cd frontend
npm test
```

## 🐳 Docker Deployment

### Production Deployment
```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Scale services (if needed)
docker-compose up -d --scale medisync-backend=2
```

### Development Mode
```bash
# Start development environment
docker-compose -f docker-compose.dev.yml up -d
```

## 📈 Monitoring & Health Checks

- **Application Health**: `/api/public/health`
- **Database Health**: Built-in MySQL health checks
- **Container Health**: Docker health check configurations
- **System Metrics**: Available through admin endpoints

## 🔒 Security Features

### Authentication & Authorization
- JWT token-based authentication
- Role-based access control (RBAC)
- Session management with configurable expiration
- Password encryption using BCrypt

### Data Protection
- HTTPS enforcement for all communications
- Database connection encryption
- Sensitive data anonymization capabilities
- CORS configuration for cross-origin requests

### Compliance
- HIPAA-compliant data handling
- GDPR data protection measures
- Audit trails for all data access
- Right to be forgotten implementation

## 🤝 Contributing

1. **Fork the repository**
2. **Create feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit changes** (`git commit -m 'Add AmazingFeature'`)
4. **Push to branch** (`git push origin feature/AmazingFeature`)
5. **Open Pull Request**

## 📋 Roadmap

### Phase 1 ✅
- [x] Core backend API development
- [x] Frontend UI implementation
- [x] Authentication system
- [x] Basic CRUD operations

### Phase 2 🚧
- [ ] Advanced medical record management
- [ ] Real-time notifications
- [ ] Advanced search and filtering
- [ ] Mobile application development

### Phase 3 📅
- [ ] AI-powered diagnostics assistance
- [ ] Blockchain integration for immutable records
- [ ] IoT device integration
- [ ] Advanced analytics dashboard

## 📞 Support

For support and questions:
- **Email**: support@medisync.com
- **Documentation**: [docs.medisync.com](https://docs.medisync.com)
- **Issues**: GitHub Issues section

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Spring Boot** community for excellent framework
- **React** team for powerful frontend library
- **HL7 FHIR** consortium for healthcare standards
- **Healthcare professionals** for valuable feedback

---

<p align="center">
  <strong>MediSync - Revolutionizing Healthcare Through Technology</strong><br>
  <em>Secure • Scalable • Interoperable</em>
</p>
