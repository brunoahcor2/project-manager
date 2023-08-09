# stage 1
FROM node:alpine3.17 as build

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# stage 2
FROM nginx:alpine3.17
COPY --from=build /app/dist/front /usr/share/nginx/html