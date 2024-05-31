import { i as useTags, u as useRouter, b as useRoute, _ as _export_sfc } from './index.e65e155f.js';
import { q as reactive, C as toRefs, a as getCurrentInstance, k as defineComponent, l as resolveComponent, m as openBlock, J as createElementBlock, K as createBaseVNode, O as toDisplayString, j as createVNode, p as withCtx, F as Fragment, N as createTextVNode } from './element-plus.f66de0e1.js';

// 关闭当前标签
var useCloseTag = () => {
  const instance = getCurrentInstance();
  const router = useRouter();
  const route = useRoute();
  const { delTag } = useTags();
  const state = reactive({
    /**
     * @param {String} fullPath 要跳转到那个页面的地址
     * @param {Boolean} reload 是否在跳转后重新渲染页面组件
     * @param {Boolean} f5 是否在跳转后刷新页面
     * @return {*}
     */
    closeTag({ fullPath, reload, f5 } = {}) {
      delTag(route);
      fullPath ? router.push(fullPath) : router.back();
      reload &&
        setTimeout(() => {
          instance.appContext.config.globalProperties.$tagsbar.refreshSelectedTag(
            route
          );
        }, 500);

      f5 && setTimeout(() => window.location.reload(), 500);
    },
  });

  return toRefs(state)
};

const _sfc_main = defineComponent({
  setup() {
    const { closeTag } = useCloseTag();
    return {
      closeTag,
    }
  },
});

const _hoisted_1 = /*#__PURE__*/createTextVNode("关闭");
const _hoisted_2 = /*#__PURE__*/createTextVNode(" 关闭当前页面，更新上级页面（组件重新渲染） ");
const _hoisted_3 = /*#__PURE__*/createTextVNode(" 关闭当前页面，刷新上级页面（浏览器刷新） ");

function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_el_button = resolveComponent("el-button");

  return (openBlock(), createElementBlock(Fragment, null, [
    createBaseVNode("h2", null, "编辑页面 id:" + toDisplayString(_ctx.$route.params.id), 1),
    createVNode(_component_el_button, {
      type: "primary",
      onClick: _ctx.closeTag
    }, {
      default: withCtx(() => [
        _hoisted_1
      ]),
      _: 1
    }, 8, ["onClick"]),
    createVNode(_component_el_button, {
      type: "success",
      onClick: _cache[0] || (_cache[0] = $event => (_ctx.closeTag({ reload: true })))
    }, {
      default: withCtx(() => [
        _hoisted_2
      ]),
      _: 1
    }),
    createVNode(_component_el_button, {
      type: "warning",
      onClick: _cache[1] || (_cache[1] = $event => (_ctx.closeTag({ f5: true })))
    }, {
      default: withCtx(() => [
        _hoisted_3
      ]),
      _: 1
    })
  ], 64))
}
var Edit = /*#__PURE__*/_export_sfc(_sfc_main, [['render',_sfc_render]]);

export { Edit as default };
