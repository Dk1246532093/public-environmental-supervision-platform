import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

// Vant 组件库
import { 
  Button, 
  Cell, 
  CellGroup, 
  Field, 
  Form, 
  NavBar, 
  Tabbar, 
  TabbarItem,
  Popup,
  Picker,
  Toast,
  Dialog,
  Icon,
  Radio,
  RadioGroup,
  Checkbox,
  CheckboxGroup,
  Uploader,
  DropdownMenu,
  DropdownItem,
  List,
  PullRefresh
} from 'vant'
import 'vant/lib/index.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// 注册 Vant 组件
app.use(Button)
app.use(Cell)
app.use(CellGroup)
app.use(Field)
app.use(Form)
app.use(NavBar)
app.use(Tabbar)
app.use(TabbarItem)
app.use(Popup)
app.use(Picker)
app.use(Toast)
app.use(Dialog)
app.use(Icon)
app.use(Radio)
app.use(RadioGroup)
app.use(Checkbox)
app.use(CheckboxGroup)
app.use(Uploader)
app.use(DropdownMenu)
app.use(DropdownItem)
app.use(List)
app.use(PullRefresh)

app.mount('#app')
