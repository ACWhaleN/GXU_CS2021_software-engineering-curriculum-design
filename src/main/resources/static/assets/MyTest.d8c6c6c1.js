import { m as axios, N as NProgress, _ as _export_sfc } from './index.e65e155f.js';
import { d as onBeforeMount, m as openBlock, J as createElementBlock } from './element-plus.f66de0e1.js';

const http = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
});
// 注册请求拦截器
http.interceptors.request.use(config=>{
    NProgress.start();
    return config
});
http.interceptors.response.use(res=>{
    NProgress.done();
    return res
},(error)=>{
    NProgress.done();
});

// 测试接口
const users = (data)=>{
    return http.get(`/admin/queryAllUsers?pageSize=${data.pageSize}&pageNum=${data.pageNum}`)
};

var MyTest_vue_vue_type_style_index_0_scoped_true_lang = '';

const _sfc_main = {
  setup(__props) {

onBeforeMount(()=>{
  console.log(users({
    pageNum:1,
    pageSize:10
  }));
});

return (_ctx, _cache) => {
  return (openBlock(), createElementBlock("h1", null, "hello"))
}
}

};
var MyTest = /*#__PURE__*/_export_sfc(_sfc_main, [['__scopeId',"data-v-013ee5f0"]]);

export { MyTest as default };
