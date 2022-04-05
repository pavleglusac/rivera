FROM node:lts-alpine

RUN mkdir -p /home/app
WORKDIR /home/app


RUN apk update && apk upgrade
RUN apk add git


COPY . /home/app
RUN npm install
RUN npm run build

EXPOSE 3000

ENV NUXT_HOST=0.0.0.0
ENV NUXT_PORT=3000

CMD [ "npm", "start" ]