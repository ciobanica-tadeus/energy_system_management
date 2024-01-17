FROM node:latest as builder
WORKDIR /app
COPY . .
RUN npm link @angular/cli
RUN npm i
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=builder /app/dist/angular-project .
ENTRYPOINT ["nginx", "-g", "daemon off;"]
