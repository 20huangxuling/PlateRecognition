import axios from 'axios'
import qs from 'qs'
import { Message } from 'element-ui';

//跨域请求携带cookie
axios.defaults.withCredentials = true;

// 设置请求超时时间
axios.defaults.timeout = 10000;

// 设置post请求头
axios.defaults.headers.post['Context-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

axios.defaults.transformRequest = [function(data) {
    if(data instanceof FormData)
        return data;
    data = qs.stringify(data);
    return data;
}];

// 请求拦截
axios.interceptors.request.use(config => {
    //在发送请求前做点什么，验证token之类的
    return config;
}, err => {
    //对请求错误做些什么
    return Promise.error(err);
});

axios.interceptors.response.use(res => {
    //对响应数据做点什么
    return res;
}, err => {
    //对响应错误做点什么
    return Promise.reject(err);
});

//封装get方法和post方法
export function get(url, params) {
    return new Promise((resolve, reject) => {
        axios.get(url, params)
        .then(res => {
            resolve(res);
        })
        .catch(err => {
            reject(err);
        });
    });
}

export function post(url, params) {
    return new Promise((resolve, reject) => {
        axios.post(url, params)
        .then(res => {
            resolve(res);
        })
        .catch(err => {
            reject(err);
            Message({message: '加载失败', type: 'error'});
        });
    });
}
