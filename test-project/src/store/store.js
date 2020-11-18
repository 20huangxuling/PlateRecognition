import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import { identity, login, confirm } from '@/http/api.js';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        user: window.sessionStorage.getItem('user') || '',
        token: window.sessionStorage.getItem('token') || '',
        newResult: null
    },
    mutations: {    //不能异步
        SET_TOKEN: (state, data) => {
            state.token = data;
            window.sessionStorage.setItem('token', data);
        },
        SET_USER: (state, data) => {
            state.user = data;
            window.sessionStorage.setItem('user', data);
        },
        LOGOUT: (state) => {
            state.user = null;
            state.token = null;
            window.sessionStorage.removeItem('user');
            window.sessionStorage.removeItem('token');
        },
        SET_NEW_RESULT: (state, value) => {
            state.newResult = value;
        }
    },
    actions: {  //可以异步
        /**
         * @method login
         * @param params 参数对象，键值为identitiy和password
         */
        login: (context, account) => {
            return new Promise((resolve, reject) => {
                login(account.identity, account.password)
                    .then(res => {
                        context.commit('SET_USER', res.data.user);
                        context.commit('SET_TOKEN', res.data.token);
                        resolve();
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },
        logout: (context) => {
            return new Promise(function (resolve, reject) {
                context.commit('LOGOUT');
                resolve();
            });
        },

        /**
         * @method identity
         * @param params 一个对象，包括要识别的file对象和该file用fileReader读取的base64Url
         */
        identity: (context, params) => {
            return new Promise((resolve, reject) => {
                identity(params.file)
                    .then(res => {
                        let newResult = {};
                        newResult.originImg = params.base64;
                        newResult.cabinetId = res.data['cabinetId'];
                        newResult.returnImg = res.data['path_Image_Detected'];
                        newResult.dateTime = res.data['dateTime'];
                        newResult.switchs = res.data['result'];
                        context.commit('SET_NEW_RESULT', newResult);
        
                        resolve(res);
                    })
                    .catch(err => {
                        reject(err);
                    });
            });
        },
        confirmToSave: (context, cabinetId) => {
            confirm(cabinetId)
            .then(res => {
                console.log("确认成功");
                //console.log(res.data);
            })
            .catch(err => {
                console.log(err);
            });
        }
    },
    getters: {
        isLoggedIn: state => !!state.token,
        imgMngrDlgVsbl: state => state.imgMngrDlgVsbl,
        newResult: state => state.newResult,
    }
});

export default store;
