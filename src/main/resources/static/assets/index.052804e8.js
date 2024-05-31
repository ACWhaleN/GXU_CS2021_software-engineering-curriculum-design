import { u as useRouter, b as useRoute } from './index.e65e155f.js';
import { h } from './element-plus.f66de0e1.js';

const _sfc_main = {
  setup() {
    const router = useRouter();
    const route = useRoute();
    router.replace(route.fullPath.replace(/^\/redirect/, ''));
  },
  render() {
    return h('div')
  },
};

export { _sfc_main as default };
