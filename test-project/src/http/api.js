import {get, post} from './axios';
import {Message} from 'element-ui'

const baseURL = process.env.BASE_URL;

export const identity = file => {
  return new Promise((resolve, reject) => {
    let params = new FormData();
    // params.append("cabinetId", "215P");
    params.append('file', file, file.name);

    let api = baseURL + '/web/image/ident/215P';
    post(api, params)
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        Message(`${fileName}识别失败`);
        reject(err);
      });
  });
};

export const login = (identity, password) => {
  return new Promise((resolve, reject) => {
    let params = new FormData();
    params.append('identity', identity);
    params.append('password', password);

    post('/login', params)
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        reject(err);
      });
  });
}

export const confirm = (cabinetId) => {
  return new Promise((resolve, reject) => {
    let api = baseURL + '/web/image/confirm/';
    get(api, {
      params: {
        cabinetId
      }
    })
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        reject(err);
      });
  });

}

export const getAllRecordsURI = _ => {
  return new Promise((resolve, reject) => {
    let api = baseURL + '/web/download/searchAllRecords/1';
    get(api)
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        reject(err);
      })
  })
}
