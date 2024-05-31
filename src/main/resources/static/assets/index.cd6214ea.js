import { _ as _export_sfc } from './index.e65e155f.js';
import { m as openBlock, J as createElementBlock, O as toDisplayString, k as defineComponent, l as resolveComponent, j as createVNode, o as onMounted, $ as pushScopeId, a0 as popScopeId, K as createBaseVNode } from './element-plus.f66de0e1.js';
import { T as TestError } from './test.02d5562b.js';

const _sfc_main$2 = {
  name: 'ErrorTestA',
};

function _sfc_render$2(_ctx, _cache, $props, $setup, $data, $options) {
  return (openBlock(), createElementBlock("div", null, toDisplayString(_ctx.a.a), 1))
}
var ErrorA = /*#__PURE__*/_export_sfc(_sfc_main$2, [['render',_sfc_render$2]]);

const _sfc_main$1 = {
  created() {
    this.b = b; // eslint-disable-line
  },
};

function _sfc_render$1(_ctx, _cache, $props, $setup, $data, $options) {
  return (openBlock(), createElementBlock("div"))
}
var ErrorB = /*#__PURE__*/_export_sfc(_sfc_main$1, [['render',_sfc_render$1]]);

var index_vue_vue_type_style_index_0_scoped_true_lang = '';

const _sfc_main = defineComponent({
  name: 'test-error-log',
  components: { ErrorA, ErrorB },
  setup() {
    onMounted(async () => {
      await TestError(); // 该接口抛出500错误
    });
  },
});

const _withScopeId = n => (pushScopeId("data-v-296272f6"),n=n(),popScopeId(),n);
const _hoisted_1 = { class: "errPage-container" };
const _hoisted_2 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("h2", null, "这个页面是测试错误日志功能的", -1));
const _hoisted_3 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("h4", null, " 本页面主动抛出了错误（任何页面报错都会记录到错误日志中），现在你可以点击右上角的`debugger`图标查看错误日志 ", -1));
const _hoisted_4 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("div", null, " 你可以在`store/modules/errorLog.js`的`addErrorLog`方法中将错误上报到服务器 ", -1));
const _hoisted_5 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("br", null, null, -1));
const _hoisted_6 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("div", null, " 如果你的项目不需要错误日志功能，你需要把`src/error-log.js`中的flag变量设为false ", -1));

function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_ErrorA = resolveComponent("ErrorA");
  const _component_ErrorB = resolveComponent("ErrorB");

  return (openBlock(), createElementBlock("div", _hoisted_1, [
    createVNode(_component_ErrorA),
    createVNode(_component_ErrorB),
    _hoisted_2,
    _hoisted_3,
    _hoisted_4,
    _hoisted_5,
    _hoisted_6
  ]))
}
var index = /*#__PURE__*/_export_sfc(_sfc_main, [['render',_sfc_render],['__scopeId',"data-v-296272f6"]]);

export { index as default };
