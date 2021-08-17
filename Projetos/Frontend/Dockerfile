FROM node:12-alpine

WORKDIR /app

COPY . .

RUN npm install

CMD ["npm", "start"]

# docker image build --network host -t frontend:0.1.0 .