const fs = require("fs");
const axios = require("axios");
const schedule = require("node-schedule");
const sqlite = require("sqlite3").verbose();

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
  schedule.scheduleJob("0 15 * * * *", () => {
    console.log("send request");
    Promise.all([getData(config.tide), getData(config.flow)]).then((res) => {
      const db = new sqlite.Database("./database.db", (err) => {
        if (err) throw err;
      });
      const response1 = res[0]["ST_TIDE_R[]"];
      response1.forEach((item) => {
        db.all(`select * from tide where stcd = '${item.STCD}' and tm = '${item.TM}'`, (err, row) => {
          if (err) throw err;
          if (row.length === 0) {
            db.run(`insert into tide values ('${item.STCD}', '${item.TM}', ${item.TDZ})`, (err) => {
              if (err) throw err;
            });
          }
        });
      });
      const response2 = res[1]["ST_TIDE_R[]"];
      response2.forEach((item) => {
        db.all(`select * from flow where stcd = '${item.STCD}' and tm = '${item.TM}'`, (err, row) => {
          if (err) throw err;
          if (row.length === 0) {
            db.run(`insert into flow values ('${item.STCD}', '${item.TM}', ${item.Z}, ${item.Q})`, (err) => {
              if (err) throw err;
            });
          }
        });
      });
      db.close();
    });
  });
};
main()
