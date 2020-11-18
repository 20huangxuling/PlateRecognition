import Mock from 'mockjs'
import qs from 'qs'

const token = '1234567';

Mock.mock('/login', (options) => {
    var optionsBody = qs.parse(options.body);
    var user = { identity: optionsBody.identity, password: optionsBody.password };
    return { user, token };
})