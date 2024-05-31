import { r as ref, o as onMounted, b as onUnmounted, w as watch, q as reactive, C as toRefs, l as resolveComponent, m as openBlock, n as createBlock, p as withCtx, j as createVNode, u as unref, a3 as withKeys, K as createBaseVNode, N as createTextVNode, O as toDisplayString, a as getCurrentInstance, J as createElementBlock, f as isRef, $ as pushScopeId, a0 as popScopeId } from './element-plus.f66de0e1.js';
import { _ as _export_sfc, u as useRouter, b as useRoute, f as useLang, L as Login } from './index.e65e155f.js';
import { P as PubSub, R as RegisterForm } from './RegisterForm.14c43b46.js';

var _imports_0 = "/assets/login.4a00de23.png";

var LoginForm_vue_vue_type_style_index_0_scoped_true_lang = '';

const _hoisted_1$1 = { class: "login-check" };


const _sfc_main$1 = {
  setup(__props) {

const { proxy: ctx } = getCurrentInstance();
// const store = useStore();
const router = useRouter();
const route = useRoute();
const { lang } = useLang();
const checkedPwd = ref(true); // 是否记住密码
onMounted(()=>{
  PubSub.subscribe("registerData",(eventName,data)=>{
    ruleForm.account = data.account;
    ruleForm.password = data.password;
  });
  // 是否需要加载上次登录用户的信息
  let now = localStorage.getItem("nextLoginData");
  if (now != null){
    now = JSON.parse(now);
    ruleForm.account = now.account;
    ruleForm.password = now.password;
  }
});
onUnmounted(()=>{
  PubSub.unsubscribe("registerData");
});
watch(lang, () => {
state.rules = getRules();
});

const getRules = () => ({
  account: [
    {
      required: true,
      message: ctx.$t('login.rules.username'),
      trigger: 'blur'
    }
  ],
  password: [
    {
      required: true,
      message: ctx.$t('login.rules.password'),
      trigger: 'blur'
    },
    {
      min: 6,
      max: 12,
      message: ctx.$t('login.rules-regpassword'),
      trigger: 'blur',
    },
  ],

});

const state = reactive({
  ruleForm: {
    account: '',
    password: '',
  },
  loading: false,
  validateForm: ref(null),
  rules: getRules(),

  handleLogin: () => {
  if (state.loading) {
    return
  }
  state.validateForm.validate(async valid => {
    if (valid) {
      state.loading = true;
      const response = await Login(state.ruleForm);
      const users = response.data.data;
      const errorInfo = response.data.errorMsg;
      // 登录成功
      if (errorInfo == null){
        ctx.$message.success({
          message: ctx.$t('login.loginsuccess'),
          duration: 1000,
        });
        const user = users[0];
        sessionStorage.setItem("curUser",JSON.stringify(user));
        // 是否要记住密码
        if (checkedPwd.value == true){
          localStorage.setItem("nextLoginData",JSON.stringify(user));
        }else {
          try {
            localStorage.removeItem("nextLoginData");
          }catch (e){}
        }
        const targetPath = decodeURIComponent(route.query.redirect);
        if (targetPath.startsWith('http')) {
          // 如果是一个url地址
          window.location.href = targetPath;
        } else if (targetPath.startsWith('/')) {
          // 如果是内部路由地址
          router.push(targetPath);
        } else {
          router.push('/');
        }
      }else {
        ctx.$message.error({
          message: ctx.$t('login.inexistuser'),
          duration: 1000,
        });
      }
      state.loading = false;
    }
  });
},
});
let {ruleForm,loading,validateForm,rules,handleLogin} = toRefs(state);
ruleForm = reactive(ruleForm.value);

return (_ctx, _cache) => {
  const _component_icon_user = resolveComponent("icon-user");
  const _component_el_input = resolveComponent("el-input");
  const _component_el_form_item = resolveComponent("el-form-item");
  const _component_icon_lock = resolveComponent("icon-lock");
  const _component_el_checkbox = resolveComponent("el-checkbox");
  const _component_el_button = resolveComponent("el-button");
  const _component_el_form = resolveComponent("el-form");

  return (openBlock(), createBlock(_component_el_form, {
    model: unref(ruleForm),
    rules: unref(rules),
    ref_key: "validateForm",
    ref: validateForm,
    class: "login-ruleForm"
  }, {
    default: withCtx(() => [
      createVNode(_component_el_form_item, { prop: "account" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.username'),
            modelValue: unref(ruleForm).account,
            "onUpdate:modelValue": _cache[0] || (_cache[0] = $event => ((unref(ruleForm).account) = $event))
          }, {
            prefix: withCtx(() => [
              createVNode(_component_icon_user, {
                theme: "outline",
                size: "16",
                fill: "#999"
              })
            ]),
            _: 1
          }, 8, ["placeholder", "modelValue"])
        ]),
        _: 1
      }),
      createVNode(_component_el_form_item, { prop: "password" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            onKeyup: withKeys(unref(handleLogin), ["enter"]),
            placeholder: _ctx.$t('login.password'),
            type: "password",
            modelValue: unref(ruleForm).password,
            "onUpdate:modelValue": _cache[1] || (_cache[1] = $event => ((unref(ruleForm).password) = $event))
          }, {
            prefix: withCtx(() => [
              createVNode(_component_icon_lock, {
                theme: "outline",
                size: "16",
                fill: "#999"
              })
            ]),
            _: 1
          }, 8, ["onKeyup", "placeholder", "modelValue"])
        ]),
        _: 1
      }),
      createVNode(_component_el_form_item, null, {
        default: withCtx(() => [
          createBaseVNode("div", _hoisted_1$1, [
            createVNode(_component_el_checkbox, {
              modelValue: checkedPwd.value,
              "onUpdate:modelValue": _cache[2] || (_cache[2] = $event => ((checkedPwd).value = $event))
            }, {
              default: withCtx(() => [
                createTextVNode(toDisplayString(_ctx.$t('login.rememberPwd')), 1)
              ]),
              _: 1
            }, 8, ["modelValue"]),
            createVNode(_component_el_button, {
              text: "",
              type: "primary"
            }, {
              default: withCtx(() => [
                createTextVNode(toDisplayString(_ctx.$t('login.forgotPwd')), 1)
              ]),
              _: 1
            })
          ])
        ]),
        _: 1
      }),
      createVNode(_component_el_form_item, null, {
        default: withCtx(() => [
          createVNode(_component_el_button, {
            type: "primary",
            loading: unref(loading),
            class: "login-btn",
            round: "",
            onClick: unref(handleLogin)
          }, {
            default: withCtx(() => [
              createTextVNode(toDisplayString(_ctx.$t('login.loginBtn')), 1)
            ]),
            _: 1
          }, 8, ["loading", "onClick"])
        ]),
        _: 1
      })
    ]),
    _: 1
  }, 8, ["model", "rules"]))
}
}

};
var LoginForm = /*#__PURE__*/_export_sfc(_sfc_main$1, [['__scopeId',"data-v-50330100"]]);

var enter_vue_vue_type_style_index_0_scoped_true_lang = '';

const _withScopeId = n => (pushScopeId("data-v-3e9dc8c6"),n=n(),popScopeId(),n);
const _hoisted_1 = { class: "login-wrapper" };
const _hoisted_2 = { class: "login-container" };
const _hoisted_3 = { class: "login-left hidden-sm-and-down" };
const _hoisted_4 = { class: "login-left-wrap" };
const _hoisted_5 = /*#__PURE__*/ _withScopeId(() => /*#__PURE__*/createBaseVNode("img", {
  class: "img",
  src: _imports_0,
  alt: "login-bg"
}, null, -1));
const _hoisted_6 = { class: "desc" };
const _hoisted_7 = { class: "tip" };
const _hoisted_8 = { class: "login-form" };
const _hoisted_9 = { class: "form-warp" };


const _sfc_main = {
  setup(__props) {

const handleClick = (val) => {
  console.log(val);
};
let activeName = ref('first');
const change = () =>{
  activeName.value = 'first';
};


return (_ctx, _cache) => {
  const _component_el_tab_pane = resolveComponent("el-tab-pane");
  const _component_el_tabs = resolveComponent("el-tabs");

  return (openBlock(), createElementBlock("div", _hoisted_1, [
    createBaseVNode("div", _hoisted_2, [
      createBaseVNode("div", _hoisted_3, [
        createBaseVNode("div", _hoisted_4, [
          _hoisted_5,
          createBaseVNode("h2", _hoisted_6, toDisplayString(_ctx.$t('login.desc')), 1),
          createBaseVNode("p", _hoisted_7, toDisplayString(_ctx.$t('login.tip')), 1)
        ])
      ]),
      createBaseVNode("div", _hoisted_8, [
        createBaseVNode("div", _hoisted_9, [
          createVNode(_component_el_tabs, {
            modelValue: unref(activeName),
            "onUpdate:modelValue": _cache[0] || (_cache[0] = $event => (isRef(activeName) ? (activeName).value = $event : activeName = $event)),
            onTabClick: handleClick
          }, {
            default: withCtx(() => [
              createVNode(_component_el_tab_pane, {
                label: _ctx.$t('login.login'),
                name: "first"
              }, {
                default: withCtx(() => [
                  createVNode(LoginForm)
                ]),
                _: 1
              }, 8, ["label"]),
              createVNode(_component_el_tab_pane, {
                label: _ctx.$t('login.register.title'),
                name: "second"
              }, {
                default: withCtx(() => [
                  createVNode(RegisterForm, { onMyEvent: change })
                ]),
                _: 1
              }, 8, ["label"])
            ]),
            _: 1
          }, 8, ["modelValue"])
        ])
      ])
    ])
  ]))
}
}

};
var enter = /*#__PURE__*/_export_sfc(_sfc_main, [['__scopeId',"data-v-3e9dc8c6"]]);

export { enter as default };
