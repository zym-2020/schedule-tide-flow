const { Pool } = require("pg");
const fs = require("fs");
const axios = require("axios");
const schedule = require("node-schedule");

const formatDate = (param = 0) => {
  // 获取当前日期时间
  const currentDate = new Date();

  // 获取一小时前的时间
  const oneHourAgo = new Date(currentDate);
  oneHourAgo.setHours(currentDate.getHours() - param);

  // 格式化一小时前的时间
  const formattedOneHourAgo = `${oneHourAgo.getFullYear()}-${padZero(oneHourAgo.getMonth() + 1)}-${padZero(oneHourAgo.getDate())} ${padZero(oneHourAgo.getHours())}:${padZero(
    oneHourAgo.getMinutes()
  )}:${padZero(oneHourAgo.getSeconds())}`;

  // 补零函数
  function padZero(value) {
    return value < 10 ? `0${value}` : value;
  }
  return formattedOneHourAgo;
};

const readJson = () => {
  const address = "./config.json";
  const data = fs.readFileSync(address, "utf-8");
  const config = JSON.parse(data);
  config.tide.body["ST_TIDE_R[]"]["ST_TIDE_R"]["TM%"] = `${formatDate()},${formatDate(1)}`;
  config.flow.body["ST_TIDE_R[]"]["ST_RIVER_R"]["TM%"] = `${formatDate()},${formatDate(1)}`;
  return config;
};

const main = () => {
  const config = readJson();

  const getData = (config) => {
    const url = config.url;
    const header = config.header;
    const params = config.params;
    const body = config.body;
    return axios
      .post(url, body, {
        headers: header,
        params: params,
      })
      .then((res) => {
        return res.data;
      });
  };
  const queryPromise = (pool, queryStr) => {
    return new Promise((resolve, reject) => {
      pool.query(queryStr, (err, result) => {
        if (err) {
          reject(err);
        } else {
          resolve(result);
        }
      });
    });
  };
  schedule.scheduleJob("0 49 * * * *", () => {
    console.log("send request");
    const pool = new Pool({
      user: "postgres",
      host: "127.0.0.1",
      database: "water",
      password: "123",
      port: 5432, // 默认 PostgreSQL 端口
    });
    Promise.all([getData(config.tide), getData(config.flow)]).then(async (res) => {
      const response1 = res[0]["ST_TIDE_R[]"];
      for (let i = 0; i < response1.length; i++) {
        const row = await queryPromise(pool, `select * from tide where stcd = '${response1[i].STCD}' and tm = '${response1[i].TM}'`);
        if (row.rows.length === 0) {
          await queryPromise(pool, `insert into tide values ('${response1[i].STCD}', '${response1[i].TM}', ${response1[i].TDZ})`);
        }
      }

      const response2 = res[1]["ST_TIDE_R[]"];
      for (let i = 0; i < response2.length; i++) {
        const row = await queryPromise(pool, `select * from flow where stcd = '${response2[i].STCD}' and tm = '${response2[i].TM}'`);
        if (row.rows.length === 0) {
          await queryPromise(pool, `insert into flow values ('${response2[i].STCD}', '${response2[i].TM}', ${response2[i].Z}, ${response2[i].Q})`);
        }
      }

      pool.end();
    });
  });
};
main();
