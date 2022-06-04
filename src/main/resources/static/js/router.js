import Router from 'vue-router'

var router=new Router({
    routes: [
        {
            path: '*',
            redirect: '/'
        },
        {
            path: '/',
            name: '/',
            component: () => import('./views/Index.vue'), // vue路由懒加载  异步加载
            meta: {
                title: '首页',
                requireAuth: true // 只要此字段为true，必须做鉴权处理
            }
        },
        {
            path: '/test',
            name: 'test',
            component: () => import('./views/Test.vue'),// vue路由懒加载  异步加载
            meta: {
                title: 'test',
                requireAuth: true // 只要此字段为true，必须做鉴权处理
            }
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('./views/Login.vue'),// vue路由懒加载  异步加载
            meta: {
                noNav: true // 不显示nav
            }
        }]
})