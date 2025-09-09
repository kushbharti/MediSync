import React, { createContext, useState, useContext, useEffect } from 'react';
import authService from '../services/authService';

const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');
    
    if (token && userData) {
      try {
        const user = JSON.parse(userData);
        setCurrentUser(user);
      } catch (error) {
        console.error('Error parsing user data:', error);
        localStorage.removeItem('token');
        localStorage.removeItem('user');
      }
    }
    
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      setError('');
      setLoading(true);
      
      const response = await authService.login(email, password);
      
      if (response.data.accessToken) {
        const userData = {
          id: response.data.id,
          name: response.data.name,
          email: response.data.email,
          role: response.data.role
        };
        
        localStorage.setItem('token', response.data.accessToken);
        localStorage.setItem('user', JSON.stringify(userData));
        
        setCurrentUser(userData);
        return userData;
      }
    } catch (error) {
      console.error('Login error:', error);
      setError(error.response?.data?.message || 'Login failed');
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const register = async (userData) => {
    try {
      setError('');
      setLoading(true);
      
      await authService.register(userData);
      // After successful registration, user needs to login
      
    } catch (error) {
      console.error('Registration error:', error);
      setError(error.response?.data?.message || 'Registration failed');
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setCurrentUser(null);
    authService.logout();
  };

  const hasRole = (role) => {
    if (!currentUser) return false;
    return currentUser.role === `ROLE_${role}` || currentUser.role === role;
  };

  const isAuthenticated = () => {
    return currentUser !== null;
  };

  const value = {
    currentUser,
    login,
    register,
    logout,
    hasRole,
    isAuthenticated,
    loading,
    error,
    setError
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};
