import { _ as _export_sfc } from './index.e65e155f.js';
import { k as defineComponent, l as resolveComponent, m as openBlock, J as createElementBlock, F as Fragment, j as createVNode, K as createBaseVNode, O as toDisplayString, P as createCommentVNode, p as withCtx, N as createTextVNode, $ as pushScopeId, a0 as popScopeId } from './element-plus.f66de0e1.js';

var index_vue_vue_type_style_index_0_scoped_true_lang = '';

const _sfc_main = defineComponent({
  props: ['error'],
});

const _withScopeId = n => (pushScopeId("data-v-4285be92"),n=n(),popScopeId(),n);
const _hoisted_1 = { class: "error" };
const _hoisted_2 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("span", { class: "code-403" }, "403", -1));
const _hoisted_3 = { class: "title" };
const _hoisted_4 = { class: "title" };
const _hoisted_5 = { class: "title" };

function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_svg_icon = resolveComponent("svg-icon");
  const _component_el_button = resolveComponent("el-button");
  const _component_router_link = resolveComponent("router-link");

  return (openBlock(), createElementBlock("div", _hoisted_1, [
    (_ctx.error === '403')
      ? (openBlock(), createElementBlock(Fragment, { key: 0 }, [
          _hoisted_2,
          createVNode(_component_svg_icon, {
            name: "error-icons-403",
            class: "error-img"
          }),
          createBaseVNode("h2", _hoisted_3, toDisplayString(_ctx.$t('error.noauth')), 1)
        ], 64))
      : (_ctx.error === '500')
        ? (openBlock(), createElementBlock(Fragment, { key: 1 }, [
            createVNode(_component_svg_icon, {
              name: "error-icons-500",
              class: "error-img"
            }),
            createBaseVNode("h2", _hoisted_4, toDisplayString(_ctx.$t('error.servererror')), 1)
          ], 64))
        : (_ctx.error === '404')
          ? (openBlock(), createElementBlock(Fragment, { key: 2 }, [
              createVNode(_component_svg_icon, {
                name: "error-icons-404",
                class: "error-img"
              }),
              createBaseVNode("h2", _hoisted_5, toDisplayString(_ctx.$t('error.notfound')), 1)
            ], 64))
          : createCommentVNode("", true),
    createVNode(_component_router_link, { to: "/" }, {
      default: withCtx(() => [
        createVNode(_component_el_button, { type: "primary" }, {
          default: withCtx(() => [
            createTextVNode(toDisplayString(_ctx.$t('error.backhome')), 1)
          ]),
          _: 1
        })
      ]),
      _: 1
    })
  ]))
}
var index = /*#__PURE__*/_export_sfc(_sfc_main, [['render',_sfc_render],['__scopeId',"data-v-4285be92"]]);

export { index as default };
