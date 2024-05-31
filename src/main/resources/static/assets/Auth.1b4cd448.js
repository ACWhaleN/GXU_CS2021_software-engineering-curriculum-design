import { s as storeToRefs, j as useAccount } from './index.e65e155f.js';
import { l as resolveComponent, m as openBlock, J as createElementBlock, K as createBaseVNode, O as toDisplayString, u as unref, j as createVNode, p as withCtx, F as Fragment, N as createTextVNode } from './element-plus.f66de0e1.js';

const _hoisted_1 = /*#__PURE__*/createBaseVNode("h4", null, [
  /*#__PURE__*/createBaseVNode("mark", null, "刷新页面可切换随机角色")
], -1);
const _hoisted_2 = /*#__PURE__*/createTextVNode("点击进入只有admin才能访问的页面");


const _sfc_main = {
  setup(__props) {

const { userinfo } = storeToRefs(useAccount());

return (_ctx, _cache) => {
  const _component_router_link = resolveComponent("router-link");

  return (openBlock(), createElementBlock(Fragment, null, [
    createBaseVNode("h2", null, "当前用户角色:" + toDisplayString(unref(userinfo) && unref(userinfo).role), 1),
    _hoisted_1,
    createVNode(_component_router_link, { to: "/test/noauth" }, {
      default: withCtx(() => [
        _hoisted_2
      ]),
      _: 1
    })
  ], 64))
}
}

};

export { _sfc_main as default };
