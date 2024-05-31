import { k as service, _ as _export_sfc, f as useLang, u as useRouter, b as useRoute } from './index.e65e155f.js';
import { D as commonjsGlobal, w as watch, q as reactive, r as ref, C as toRefs, l as resolveComponent, m as openBlock, n as createBlock, p as withCtx, j as createVNode, u as unref, N as createTextVNode, O as toDisplayString, a as getCurrentInstance } from './element-plus.f66de0e1.js';

var pubsub = {exports: {}};

/**
 * Copyright (c) 2010,2011,2012,2013,2014 Morgan Roderick http://roderick.dk
 * License: MIT - http://mrgnrdrck.mit-license.org
 *
 * https://github.com/mroderick/PubSubJS
 */

(function (module, exports) {
(function (root, factory){

    var PubSub = {};

    if (root.PubSub) {
        PubSub = root.PubSub;
        console.warn("PubSub already loaded, using existing version");
    } else {
        root.PubSub = PubSub;
        factory(PubSub);
    }
    // CommonJS and Node.js module support
    {
        if (module !== undefined && module.exports) {
            exports = module.exports = PubSub; // Node.js specific `module.exports`
        }
        exports.PubSub = PubSub; // CommonJS module 1.1.1 spec
        module.exports = exports = PubSub; // CommonJS
    }

}(( typeof window === 'object' && window ) || commonjsGlobal, function (PubSub){

    var messages = {},
        lastUid = -1,
        ALL_SUBSCRIBING_MSG = '*';

    function hasKeys(obj){
        var key;

        for (key in obj){
            if ( Object.prototype.hasOwnProperty.call(obj, key) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a function that throws the passed exception, for use as argument for setTimeout
     * @alias throwException
     * @function
     * @param { Object } ex An Error object
     */
    function throwException( ex ){
        return function reThrowException(){
            throw ex;
        };
    }

    function callSubscriberWithDelayedExceptions( subscriber, message, data ){
        try {
            subscriber( message, data );
        } catch( ex ){
            setTimeout( throwException( ex ), 0);
        }
    }

    function callSubscriberWithImmediateExceptions( subscriber, message, data ){
        subscriber( message, data );
    }

    function deliverMessage( originalMessage, matchedMessage, data, immediateExceptions ){
        var subscribers = messages[matchedMessage],
            callSubscriber = immediateExceptions ? callSubscriberWithImmediateExceptions : callSubscriberWithDelayedExceptions,
            s;

        if ( !Object.prototype.hasOwnProperty.call( messages, matchedMessage ) ) {
            return;
        }

        for (s in subscribers){
            if ( Object.prototype.hasOwnProperty.call(subscribers, s)){
                callSubscriber( subscribers[s], originalMessage, data );
            }
        }
    }

    function createDeliveryFunction( message, data, immediateExceptions ){
        return function deliverNamespaced(){
            var topic = String( message ),
                position = topic.lastIndexOf( '.' );

            // deliver the message as it is now
            deliverMessage(message, message, data, immediateExceptions);

            // trim the hierarchy and deliver message to each level
            while( position !== -1 ){
                topic = topic.substr( 0, position );
                position = topic.lastIndexOf('.');
                deliverMessage( message, topic, data, immediateExceptions );
            }

            deliverMessage(message, ALL_SUBSCRIBING_MSG, data, immediateExceptions);
        };
    }

    function hasDirectSubscribersFor( message ) {
        var topic = String( message ),
            found = Boolean(Object.prototype.hasOwnProperty.call( messages, topic ) && hasKeys(messages[topic]));

        return found;
    }

    function messageHasSubscribers( message ){
        var topic = String( message ),
            found = hasDirectSubscribersFor(topic) || hasDirectSubscribersFor(ALL_SUBSCRIBING_MSG),
            position = topic.lastIndexOf( '.' );

        while ( !found && position !== -1 ){
            topic = topic.substr( 0, position );
            position = topic.lastIndexOf( '.' );
            found = hasDirectSubscribersFor(topic);
        }

        return found;
    }

    function publish( message, data, sync, immediateExceptions ){
        message = (typeof message === 'symbol') ? message.toString() : message;

        var deliver = createDeliveryFunction( message, data, immediateExceptions ),
            hasSubscribers = messageHasSubscribers( message );

        if ( !hasSubscribers ){
            return false;
        }

        if ( sync === true ){
            deliver();
        } else {
            setTimeout( deliver, 0 );
        }
        return true;
    }

    /**
     * Publishes the message, passing the data to it's subscribers
     * @function
     * @alias publish
     * @param { String } message The message to publish
     * @param {} data The data to pass to subscribers
     * @return { Boolean }
     */
    PubSub.publish = function( message, data ){
        return publish( message, data, false, PubSub.immediateExceptions );
    };

    /**
     * Publishes the message synchronously, passing the data to it's subscribers
     * @function
     * @alias publishSync
     * @param { String } message The message to publish
     * @param {} data The data to pass to subscribers
     * @return { Boolean }
     */
    PubSub.publishSync = function( message, data ){
        return publish( message, data, true, PubSub.immediateExceptions );
    };

    /**
     * Subscribes the passed function to the passed message. Every returned token is unique and should be stored if you need to unsubscribe
     * @function
     * @alias subscribe
     * @param { String } message The message to subscribe to
     * @param { Function } func The function to call when a new message is published
     * @return { String }
     */
    PubSub.subscribe = function( message, func ){
        if ( typeof func !== 'function'){
            return false;
        }

        message = (typeof message === 'symbol') ? message.toString() : message;

        // message is not registered yet
        if ( !Object.prototype.hasOwnProperty.call( messages, message ) ){
            messages[message] = {};
        }

        // forcing token as String, to allow for future expansions without breaking usage
        // and allow for easy use as key names for the 'messages' object
        var token = 'uid_' + String(++lastUid);
        messages[message][token] = func;

        // return token for unsubscribing
        return token;
    };

    PubSub.subscribeAll = function( func ){
        return PubSub.subscribe(ALL_SUBSCRIBING_MSG, func);
    };

    /**
     * Subscribes the passed function to the passed message once
     * @function
     * @alias subscribeOnce
     * @param { String } message The message to subscribe to
     * @param { Function } func The function to call when a new message is published
     * @return { PubSub }
     */
    PubSub.subscribeOnce = function( message, func ){
        var token = PubSub.subscribe( message, function(){
            // before func apply, unsubscribe message
            PubSub.unsubscribe( token );
            func.apply( this, arguments );
        });
        return PubSub;
    };

    /**
     * Clears all subscriptions
     * @function
     * @public
     * @alias clearAllSubscriptions
     */
    PubSub.clearAllSubscriptions = function clearAllSubscriptions(){
        messages = {};
    };

    /**
     * Clear subscriptions by the topic
     * @function
     * @public
     * @alias clearAllSubscriptions
     * @return { int }
     */
    PubSub.clearSubscriptions = function clearSubscriptions(topic){
        var m;
        for (m in messages){
            if (Object.prototype.hasOwnProperty.call(messages, m) && m.indexOf(topic) === 0){
                delete messages[m];
            }
        }
    };

    /**
       Count subscriptions by the topic
     * @function
     * @public
     * @alias countSubscriptions
     * @return { Array }
    */
    PubSub.countSubscriptions = function countSubscriptions(topic){
        var m;
        // eslint-disable-next-line no-unused-vars
        var token;
        var count = 0;
        for (m in messages) {
            if (Object.prototype.hasOwnProperty.call(messages, m) && m.indexOf(topic) === 0) {
                for (token in messages[m]) {
                    count++;
                }
                break;
            }
        }
        return count;
    };


    /**
       Gets subscriptions by the topic
     * @function
     * @public
     * @alias getSubscriptions
    */
    PubSub.getSubscriptions = function getSubscriptions(topic){
        var m;
        var list = [];
        for (m in messages){
            if (Object.prototype.hasOwnProperty.call(messages, m) && m.indexOf(topic) === 0){
                list.push(m);
            }
        }
        return list;
    };

    /**
     * Removes subscriptions
     *
     * - When passed a token, removes a specific subscription.
     *
	 * - When passed a function, removes all subscriptions for that function
     *
	 * - When passed a topic, removes all subscriptions for that topic (hierarchy)
     * @function
     * @public
     * @alias subscribeOnce
     * @param { String | Function } value A token, function or topic to unsubscribe from
     * @example // Unsubscribing with a token
     * var token = PubSub.subscribe('mytopic', myFunc);
     * PubSub.unsubscribe(token);
     * @example // Unsubscribing with a function
     * PubSub.unsubscribe(myFunc);
     * @example // Unsubscribing from a topic
     * PubSub.unsubscribe('mytopic');
     */
    PubSub.unsubscribe = function(value){
        var descendantTopicExists = function(topic) {
                var m;
                for ( m in messages ){
                    if ( Object.prototype.hasOwnProperty.call(messages, m) && m.indexOf(topic) === 0 ){
                        // a descendant of the topic exists:
                        return true;
                    }
                }

                return false;
            },
            isTopic    = typeof value === 'string' && ( Object.prototype.hasOwnProperty.call(messages, value) || descendantTopicExists(value) ),
            isToken    = !isTopic && typeof value === 'string',
            isFunction = typeof value === 'function',
            result = false,
            m, message, t;

        if (isTopic){
            PubSub.clearSubscriptions(value);
            return;
        }

        for ( m in messages ){
            if ( Object.prototype.hasOwnProperty.call( messages, m ) ){
                message = messages[m];

                if ( isToken && message[value] ){
                    delete message[value];
                    result = value;
                    // tokens are unique, so we can just stop here
                    break;
                }

                if (isFunction) {
                    for ( t in message ){
                        if (Object.prototype.hasOwnProperty.call(message, t) && message[t] === value){
                            delete message[t];
                            result = true;
                        }
                    }
                }
            }
        }

        return result;
    };
}));
}(pubsub, pubsub.exports));

var PubSub = pubsub.exports;

// 注册接口
const Register = data => {
    return service({
      url: '/user/register',
      method: 'post',
      data,
    })
  };

var RegisterForm_vue_vue_type_style_index_0_scoped_true_lang = '';

const _sfc_main = {
  setup(__props) {

const { proxy: ctx } = getCurrentInstance();
const { lang } = useLang();
const router = useRouter();
const route = useRoute();

watch(lang,() => {
  state.rules = getRules();
});

const getRules = () => ({
  account:[
    {
      required:true,
      message:ctx.$t('login.register.rules.account'),
      trigger:'blur'
    },
    {
      min:10,
      max:10,
      message:ctx.$t('login.register.rules.accountLength'),
      trigger: 'blur'
    }
  ],
  name:[
    {
      required:true,
      message:ctx.$t('login.register.rules.name'),
      trigger:'blur'
    }
  ],
  lesson:[
    {
      required:true,
      message:ctx.$t('login.register.rules.lesson'),
      trigger:'blur'
    }
  ],
  nickname:[
    {
      required:true,
      message:ctx.$t('login.register.rules.nickname'),
      trigger:'blur'
    }
  ],
  major:[
    {
      required:true,
      message:ctx.$t('login.register.rules.major'),
      trigger:'blur'
    }
  ],
  phone:[
    {
      required:true,
      message:ctx.$t('login.register.rules.phone'),
      trigger:'blur'
    },
    {
      min:11,
      max:11,
      message:ctx.$t('login.register.rules.phoneLength'),
      trigger: 'blur'
    }
  ],
  mailbox:[
    {
      required:true,
      message:ctx.$t('login.register.rules.mailbox'),
      trigger:'blur'
    }
  ],
  password:[
    {
      required:true,
      message:ctx.$t('login.register.rules.password'),
      trigger:'blur'
    },
    {
      min:6,
      max:12,
      message:ctx.$t('login.register.rules.passwordLength'),
      trigger: 'blur'
    }
  ],
  // checkPassword:[
  //   {
  //     required:true,
  //     message:ctx.$t('login.register.rules.confirmPwd'),
  //     trigger:'blur'
  //   },
  // ],
});
const state = reactive({
  ruleForm: {
    account: '1931200551',
    name: 'hish',
    lesson: 'dfgab',
    nickname: 'sagw',
    major: 'dgsgag',
    phone: '12345543211',
    mailbox: 'sfagaq',
    password: '123456',
    sex:'男'
  },
  rules:getRules(),
  loading:false,
  registerForm:ref(null),

  handleRegister: () => {
    if(state.loading){
      return
    }
    state.registerForm.validate(async valid => {
      if(valid){
        const response = await Register(state.ruleForm);
        const user = response.data.data;
        const error = response.data.errorMsg; // 错误信息是否存在
        // 登录成功
        if (error == null){
          ctx.$message.success({
            message: ctx.$t('login.register.registersuccess'),
            duration: 1000,
          });
          ctx.$emit("myEvent");
          PubSub.publish("registerData",user);
        }
        else {
          ctx.$message.error({
            message: ctx.$t('login.register.userexist'),
            duration: 1000,
          });
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
        state.loading = false;
      }
    });
  }
});
let {ruleForm,rules,loading,registerForm,handleRegister} = toRefs(state);
ruleForm = reactive(ruleForm.value);

return (_ctx, _cache) => {
  const _component_icon_user = resolveComponent("icon-user");
  const _component_el_input = resolveComponent("el-input");
  const _component_el_form_item = resolveComponent("el-form-item");
  const _component_el_radio = resolveComponent("el-radio");
  const _component_icon_lock = resolveComponent("icon-lock");
  const _component_el_button = resolveComponent("el-button");
  const _component_el_form = resolveComponent("el-form");

  return (openBlock(), createBlock(_component_el_form, {
    model: unref(ruleForm),
    rules: unref(rules),
    ref_key: "registerForm",
    ref: registerForm,
    class: "register-ruleForm"
  }, {
    default: withCtx(() => [
      createVNode(_component_el_form_item, { prop: "account" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.account'),
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
      createVNode(_component_el_form_item, { prop: "name" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.name'),
            modelValue: unref(ruleForm).name,
            "onUpdate:modelValue": _cache[1] || (_cache[1] = $event => ((unref(ruleForm).name) = $event))
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
      createVNode(_component_el_form_item, { prop: "sex" }, {
        default: withCtx(() => [
          createVNode(_component_el_radio, {
            modelValue: unref(ruleForm).sex,
            "onUpdate:modelValue": _cache[2] || (_cache[2] = $event => ((unref(ruleForm).sex) = $event)),
            label: "男"
          }, {
            default: withCtx(() => [
              createTextVNode(toDisplayString(_ctx.$t('login.register.male')), 1)
            ]),
            _: 1
          }, 8, ["modelValue"]),
          createVNode(_component_el_radio, {
            modelValue: unref(ruleForm).sex,
            "onUpdate:modelValue": _cache[3] || (_cache[3] = $event => ((unref(ruleForm).sex) = $event)),
            label: "女"
          }, {
            default: withCtx(() => [
              createTextVNode(toDisplayString(_ctx.$t('login.register.female')), 1)
            ]),
            _: 1
          }, 8, ["modelValue"])
        ]),
        _: 1
      }),
      createVNode(_component_el_form_item, { prop: "lesson" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.lesson'),
            modelValue: unref(ruleForm).lesson,
            "onUpdate:modelValue": _cache[4] || (_cache[4] = $event => ((unref(ruleForm).lesson) = $event))
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
      createVNode(_component_el_form_item, { prop: "major" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.major'),
            modelValue: unref(ruleForm).major,
            "onUpdate:modelValue": _cache[5] || (_cache[5] = $event => ((unref(ruleForm).major) = $event))
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
      createVNode(_component_el_form_item, { prop: "nickname" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.nickname'),
            modelValue: unref(ruleForm).nickname,
            "onUpdate:modelValue": _cache[6] || (_cache[6] = $event => ((unref(ruleForm).nickname) = $event))
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
      createVNode(_component_el_form_item, { prop: "phone" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.phone'),
            modelValue: unref(ruleForm).phone,
            "onUpdate:modelValue": _cache[7] || (_cache[7] = $event => ((unref(ruleForm).phone) = $event))
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
      createVNode(_component_el_form_item, { prop: "mailbox" }, {
        default: withCtx(() => [
          createVNode(_component_el_input, {
            placeholder: _ctx.$t('login.register.mailbox'),
            modelValue: unref(ruleForm).mailbox,
            "onUpdate:modelValue": _cache[8] || (_cache[8] = $event => ((unref(ruleForm).mailbox) = $event))
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
            placeholder: _ctx.$t('login.register.password'),
            type: "password",
            modelValue: unref(ruleForm).password,
            "onUpdate:modelValue": _cache[9] || (_cache[9] = $event => ((unref(ruleForm).password) = $event))
          }, {
            prefix: withCtx(() => [
              createVNode(_component_icon_lock, {
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
      createVNode(_component_el_form_item, null, {
        default: withCtx(() => [
          createVNode(_component_el_button, {
            type: "primary",
            loading: unref(loading),
            class: "register-btn",
            round: "",
            onClick: unref(handleRegister)
          }, {
            default: withCtx(() => [
              createTextVNode(toDisplayString(_ctx.$t('login.register.registerBtn')), 1)
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
var RegisterForm = /*#__PURE__*/_export_sfc(_sfc_main, [['__scopeId',"data-v-41e0a6d6"]]);

var RegisterForm$1 = /*#__PURE__*/Object.freeze(/*#__PURE__*/Object.defineProperty({
    __proto__: null,
    'default': RegisterForm
}, Symbol.toStringTag, { value: 'Module' }));

export { PubSub as P, RegisterForm as R, RegisterForm$1 as a };
