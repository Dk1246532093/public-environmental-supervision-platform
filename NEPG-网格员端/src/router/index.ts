import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginPage.vue')
  },
  {
    path: '/task-list',
    name: 'TaskList',
    component: () => import('@/views/TaskListPage.vue')
  },
  {
    path: '/task-detail/:id',
    name: 'TaskDetail',
    component: () => import('@/views/TaskDetailPage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
