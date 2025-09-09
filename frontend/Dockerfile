# MediSync Frontend Dockerfile
# Build stage
FROM node:18-alpine as build

# Set working directory
WORKDIR /app

# Copy package files
COPY package.json package-lock.json* ./

# Install dependencies
RUN npm ci --only=production

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy custom nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built app from build stage
COPY --from=build /app/build /usr/share/nginx/html

# Create non-root user for security
RUN addgroup -g 1001 -S medisync && \
    adduser -S medisync -u 1001

# Change ownership of nginx directories
RUN chown -R medisync:medisync /usr/share/nginx/html && \
    chown -R medisync:medisync /var/cache/nginx && \
    chown -R medisync:medisync /var/log/nginx && \
    chown -R medisync:medisync /etc/nginx/conf.d

# Switch to non-root user
USER medisync

# Expose port
EXPOSE 80

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:80 || exit 1

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
