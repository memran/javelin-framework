# syntax=docker/dockerfile:1
# Build from the repository root:
#   docker compose -f demo-app/docker-compose.yml up --build

# Stage 1: static assets through the same build.sh used locally.
FROM node:22-alpine AS assets
WORKDIR /assets
COPY demo-app/build.sh demo-app/package.json ./
COPY demo-app/resources/assets ./resources/assets
# Tailwind's @source rules scan these templates for utility classes.
COPY demo-app/resources/views ./resources/views
COPY demo-app/app/views ./app/views
COPY demo-app/scripts ./scripts
RUN sh build.sh assets

# Stage 2: build the demo jar from the Maven reactor via build.sh.
FROM maven:3.9-eclipse-temurin-25 AS app-build
WORKDIR /workspace
COPY . .
RUN sh demo-app/build.sh java

# Stage 3: runtime with built assets and a non-filtered container config.
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app
COPY --from=app-build /workspace/demo-app/target/javelin-demo-app-0.1.0-SNAPSHOT.jar app.jar
COPY --from=app-build /workspace/demo-app/config ./config
COPY demo-app/docker-app.yaml ./config/app.yaml
COPY --from=assets /assets/public ./public
ENV APP_ENV=prod SERVER_HOST=0.0.0.0 SERVER_PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar", "serve"]
