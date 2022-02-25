FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

FROM nginx:latest
COPY --from=node /app/dist/atp-tour-angular /usr/share/nginx/html
COPY --from=node /app/nginx-custom.conf /etc/nginx/conf.d/default.conf
