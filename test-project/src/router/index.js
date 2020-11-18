import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Main from '@/components/Main'
import Download from '@/components/Download'

const Identity = r => require.ensure([], () => (require('@/components/Identity')));
const UserInfo = r => require.ensure([], () => (require('@/components/UserInfo')));

Vue.use(Router)

var router = new Router({
  base: '/happy/',
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/main/identity',
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/main',
      name: 'main',
      meta: { requiresAuth: true },
      component: Main,
      children: [
        {
          path: 'identity',
          name: 'identity',
          component: Identity,
        },
        {
          path: 'download',
          name: 'download',
          component: Download,
        },
        {
          path: 'userInfo',
          name: 'userInfo',
          component: UserInfo,
        }
      ]
    },
    {
      path: '*',
      redirect: '/login',
    }
  ]
})

router.beforeEach((to, from, next) => {
  /*if(to.matched.some(record => record.meta.requiresAuth)) {
    if(!store.getters.isLoggedIn) {
      next('/login');
      next();
    } else {
      next();
    }
  } else {
    next();
  }*/
  next();
})

export default router
