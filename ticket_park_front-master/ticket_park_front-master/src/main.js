import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus' //全局引入
import 'element-plus/dist/index.css'
import store from './store'
// import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const app = createApp(App)
app.use(ElementPlusIconsVue);
// 将所有图标组件逐一全局注册
for (const [key, component] of Object.entries(ElementPlusIconsVue)) { 
	app.component(key, component) 
}
// 注册router store 和elementplus组件
app.use(router).use(ElementPlus).use(store).mount('#app')