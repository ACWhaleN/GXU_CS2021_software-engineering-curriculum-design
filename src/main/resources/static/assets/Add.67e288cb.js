import { _ as _export_sfc, k as service } from './index.e65e155f.js';
import { k as defineComponent, r as ref, l as resolveComponent, m as openBlock, n as createBlock, p as withCtx, j as createVNode, u as unref, N as createTextVNode, aa as genFileId } from './element-plus.f66de0e1.js';

var Add_vue_vue_type_style_index_0_scoped_true_lang = '';

var Add_vue_vue_type_style_index_1_lang = '';

const _hoisted_1 = /* @__PURE__ */ createTextVNode("select file");
const _hoisted_2 = /* @__PURE__ */ createTextVNode("\u63D0\u4EA4");
const _sfc_main = /* @__PURE__ */ defineComponent({
  setup(__props) {
    const name = ref("");
    const address = ref("");
    const description = ref("");
    const time = ref();
    const category = ref();
    ref("");
    let imagefile = ref([]);
    const submitForm = () => {
      const itemName = name.value;
      const address_value = address.value;
      const description_value = description.value;
      const time_value = time.value;
      const category_value = category.value;
      const item = sessionStorage.getItem("curUser");
      const losterId = JSON.parse(item)["id"];
      const formData = new FormData();
      formData.append("itemName", itemName);
      formData.append("address_value", address_value);
      formData.append("description_value", description_value);
      formData.append("time_value", time_value);
      formData.append("category_value", category_value);
      formData.append("losterId", losterId);
      formData.append("upload", imagefile);
      service.post("/user/addLostSpe", formData).then((res) => {
        console.log(res);
      }).catch(function(error) {
        alert(error);
      });
    };
    const upload = ref();
    const handleExceed = (files) => {
      upload.value.clearFiles();
      const file2 = files[0];
      file2.uid = genFileId();
      upload.value.handleStart(file2);
    };
    function handleImg(param) {
      param.file;
    }
    const onUploadChange = (file2) => {
      imagefile = file2.raw;
    };
    return (_ctx, _cache) => {
      const _component_el_input = resolveComponent("el-input");
      const _component_el_form_item = resolveComponent("el-form-item");
      const _component_el_date_picker = resolveComponent("el-date-picker");
      const _component_el_button = resolveComponent("el-button");
      const _component_el_upload = resolveComponent("el-upload");
      const _component_el_option = resolveComponent("el-option");
      const _component_el_select = resolveComponent("el-select");
      const _component_el_form = resolveComponent("el-form");
      return openBlock(), createBlock(_component_el_form, {
        "label-width": "100px",
        ref: "form",
        model: _ctx.form,
        rules: _ctx.rules
      }, {
        default: withCtx(() => [
          createVNode(_component_el_form_item, {
            label: "\u7269\u54C1\u540D\u79F0",
            prop: "name"
          }, {
            default: withCtx(() => [
              createVNode(_component_el_input, {
                type: "text",
                size: "large",
                modelValue: name.value,
                "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => name.value = $event),
                placeholder: "\u8BF7\u8F93\u5165\u4E22\u5931\u7269\u54C1\u540D\u79F0"
              }, null, 8, ["modelValue"])
            ]),
            _: 1
          }),
          createVNode(_component_el_form_item, {
            label: "\u4E22\u5931\u5730\u5740",
            prop: "address"
          }, {
            default: withCtx(() => [
              createVNode(_component_el_input, {
                type: "text",
                modelValue: address.value,
                "onUpdate:modelValue": _cache[1] || (_cache[1] = ($event) => address.value = $event),
                placeholder: "\u8BF7\u8F93\u5165\u7269\u54C1\u4E22\u5931\u5730\u5740"
              }, null, 8, ["modelValue"])
            ]),
            _: 1
          }),
          createVNode(_component_el_form_item, {
            label: "\u7269\u54C1\u63CF\u8FF0",
            prop: "description"
          }, {
            default: withCtx(() => [
              createVNode(_component_el_input, {
                type: "textarea",
                modelValue: description.value,
                "onUpdate:modelValue": _cache[2] || (_cache[2] = ($event) => description.value = $event),
                placeholder: "\u8BF7\u8F93\u5165\u4E22\u5931\u7269\u54C1\u63CF\u8FF0",
                rows: 10
              }, null, 8, ["modelValue"])
            ]),
            _: 1
          }),
          createVNode(_component_el_form_item, {
            label: "\u4E22\u5931\u65F6\u95F4",
            prop: "lostTime"
          }, {
            default: withCtx(() => [
              createVNode(_component_el_date_picker, {
                type: "datetime",
                modelValue: time.value,
                "onUpdate:modelValue": _cache[3] || (_cache[3] = ($event) => time.value = $event)
              }, null, 8, ["modelValue"])
            ]),
            _: 1
          }),
          createVNode(_component_el_form_item, {
            label: "\u7269\u54C1\u56FE\u7247",
            prop: "image"
          }, {
            default: withCtx(() => [
              createVNode(_component_el_upload, {
                ref_key: "upload",
                ref: upload,
                class: "upload-demo",
                limit: 1,
                "on-exceed": handleExceed,
                "on-change": onUploadChange,
                "auto-upload": false,
                "http-request": handleImg,
                "file-list": unref(imagefile),
                "list-type": "text"
              }, {
                trigger: withCtx(() => [
                  createVNode(_component_el_button, { type: "primary" }, {
                    default: withCtx(() => [
                      _hoisted_1
                    ]),
                    _: 1
                  })
                ]),
                _: 1
              }, 8, ["file-list"])
            ]),
            _: 1
          }),
          createVNode(_component_el_form_item, {
            label: "\u7269\u54C1\u7C7B\u522B",
            prop: "category"
          }, {
            default: withCtx(() => [
              createVNode(_component_el_select, {
                modelValue: category.value,
                "onUpdate:modelValue": _cache[4] || (_cache[4] = ($event) => category.value = $event)
              }, {
                default: withCtx(() => [
                  createVNode(_component_el_option, {
                    label: "\u65E5\u5E38\u7528\u54C1",
                    value: "\u65E5\u5E38\u7528\u54C1"
                  }),
                  createVNode(_component_el_option, {
                    label: "\u8D35\u91CD\u7269\u54C1",
                    value: "\u8D35\u91CD\u7269\u54C1"
                  }),
                  createVNode(_component_el_option, {
                    label: "\u8BC1\u4EF6",
                    value: "\u8BC1\u4EF6"
                  }),
                  createVNode(_component_el_option, {
                    label: "\u7535\u5B50\u8BBE\u5907",
                    value: "\u7535\u5B50\u8BBE\u5907"
                  }),
                  createVNode(_component_el_option, {
                    label: "\u4E66\u7C4D",
                    value: "\u4E66\u7C4D"
                  }),
                  createVNode(_component_el_option, {
                    label: "\u7279\u6B8A\u7269\u54C1",
                    value: "\u7279\u6B8A\u7269\u54C1"
                  })
                ]),
                _: 1
              }, 8, ["modelValue"])
            ]),
            _: 1
          }),
          createVNode(_component_el_form_item, null, {
            default: withCtx(() => [
              createVNode(_component_el_button, {
                type: "primary",
                onClick: submitForm
              }, {
                default: withCtx(() => [
                  _hoisted_2
                ]),
                _: 1
              })
            ]),
            _: 1
          })
        ]),
        _: 1
      }, 8, ["model", "rules"]);
    };
  }
});
var Add = /* @__PURE__ */ _export_sfc(_sfc_main, [["__scopeId", "data-v-5cdf23bc"]]);

export { Add as default };
