const fs = require("fs");
const axios = require("axios");

const readJson = () => {
  const address = "./test.json";
  const data = fs.readFileSync(address, "utf-8");
  const config = JSON.parse(data);
  return config;
};

const main = () => {
  const config = readJson();
  const url = config.url;
  const header = config.header;
  const params = config.params;
  const body = config.body;
  axios
    .post(url, body, {
      headers: header,
      params: params,
    })
    .then((res) => {
      console.log(res.data);
    });
};
main();
