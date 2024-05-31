import { s as storeToRefs, j as useAccount, _ as _export_sfc } from './index.e65e155f.js';
import { k as defineComponent, m as openBlock, J as createElementBlock, F as Fragment, K as createBaseVNode, O as toDisplayString, $ as pushScopeId, a0 as popScopeId } from './element-plus.f66de0e1.js';

const useUserinfo = () => {
  const { userinfo } = storeToRefs(useAccount());
  return { userinfo }
};

var index_vue_vue_type_style_index_0_scoped_true_lang = '';

const _sfc_main = defineComponent({
  setup() {
    const { userinfo } = useUserinfo();

    return { userinfo }
  },
});

const _withScopeId = n => (pushScopeId("data-v-83ab0600"),n=n(),popScopeId(),n);
const _hoisted_1 = { class: "userinfo" };
const _hoisted_2 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("i", { class: "el-icon-user" }, null, -1));
const _hoisted_3 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("h3", null, "admin", -1));
const _hoisted_4 = ["src"];

function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return (openBlock(), createElementBlock("div", _hoisted_1, [
    (!_ctx.userinfo)
      ? (openBlock(), createElementBlock(Fragment, { key: 0 }, [
          _hoisted_2,
          _hoisted_3
        ], 64))
      : (openBlock(), createElementBlock(Fragment, { key: 1 }, [
          createBaseVNode("img", {
            class: "avatar",
            src: _ctx.userinfo.avatar
          }, null, 8, _hoisted_4),
          createBaseVNode("h3", null, toDisplayString(_ctx.userinfo.name), 1)
        ], 64))
  ]))
}
var Avatar = /*#__PURE__*/_export_sfc(_sfc_main, [['render',_sfc_render],['__scopeId',"data-v-83ab0600"]]);

export { Avatar as A, useUserinfo as u };
