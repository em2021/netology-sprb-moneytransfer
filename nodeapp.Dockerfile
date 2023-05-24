FROM node:14-alpine AS builder
ENV repo=https://github.com/serp-ya/card-transfer.git
RUN apk update && apk upgrade && apk add git && git clone $repo app/
WORKDIR /app
RUN npm install

FROM node:14-alpine
COPY --from=builder app/ app/
WORKDIR /app
EXPOSE 3000
ENTRYPOINT ["npm", "run", "start"]
