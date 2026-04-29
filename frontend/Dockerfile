# For documentation see https://github.com/sclorg/nginx-container
FROM registry.access.redhat.com/ubi10/nginx-126:10.1-1777466069@sha256:25224c392d8dbac439dc9072560ddf5a8869ca5cde7fbb3af2ccc67343445a60

# Copy built web application
COPY dist .

# Copy custom nginx configurations
COPY docker/nginx/*.conf "${NGINX_DEFAULT_CONF_PATH}"

# Start the web server
CMD ["nginx", "-g", "daemon off;"]