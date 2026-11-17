# Note: For base images an architecture-agnostic SHA is always preferred, see https://refarch.oss.muenchen.de/templates/deploy.html#docker-images for more info.

# For documentation see https://rh-openjdk.github.io/redhat-openjdk-containers/
FROM registry.access.redhat.com/ubi10/openjdk-25-runtime:1.24-18@sha256:e898c4ad1c8cfb5afee86917906356226956813733ebc27bbf33f762bb3b8f08

# Temporarily switch to root user
USER 0

# Install fontconfig in order for JVM (JasperReport) have access to system fonts
RUN microdnf install -y \
        fontconfig \
    && microdnf clean all \
    && fc-cache -f

# Switch back to default user execution Java application
USER 185

# Copy runnable jar to deployments
COPY target/*.jar /deployments/application.jar