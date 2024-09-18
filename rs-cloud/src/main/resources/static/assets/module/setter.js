/** EasyWeb spa v3.1.8 date:2020-05-04 License By http://easyweb.vip */

layui.define(['table'], function (exports) {
    let setter = {
        baseServer: '/', // 接口地址，实际项目请换成http形式的地址
        pageTabs: false,   // 是否开启多标签
        cacheTab: true,  // 是否记忆Tab
        defaultTheme: 'theme-blue',  // 默认主题
        openTabCtxMenu: true,   // 是否开启Tab右键菜单
        maxTabNum: 20,  // 最多打开多少个tab
        viewPath: 'components', // 视图位置
        viewSuffix: '.html',  // 视图后缀
        reqPutToPost: true,  // req请求put方法变成post
        apiNoCache: true,  // ajax请求json数据不带版本号
        tableName: 'rs-iot',  // 存储表名
        /* 获取缓存的token */
        getToken: function () {
            let cache = layui.data(setter.tableName);
            if (cache) {
                return cache.token;
            }
        },
        /* 清除token */
        removeToken: function () {
            layui.data(setter.tableName, {
                key: 'token',
                remove: true
            });
        },
        /* 缓存token */
        putToken: function (token) {
            layui.data(setter.tableName, {
                key: 'token',
                value: token
            });
        },
        /* 当前登录的用户 */
        getUser: function () {
            let cache = layui.data(setter.tableName);
            if (cache) {
                return cache.loginUser;
            }
        },
        /* 缓存user */
        putUser: function (user) {
            layui.data(setter.tableName, {
                key: 'loginUser',
                value: user
            });
        },
        /* 获取用户所有权限 */
        getUserAuths: function () {
            let auths = [];
            let authorities = setter.getUser().authorities;
            if(authorities){
                for (let i = 0; i < authorities.length; i++) {
                    auths.push(authorities[i].authority);
                }
            }
            return auths;
        },
        /* ajax请求的header */
        getAjaxHeaders: function (url) {
            let headers = [];
            let token = setter.getToken();
            if (token) {
                headers.push({
                    name: 'Authorization',
                    value: 'Bearer ' + token.access_token
                });
            }
            return headers;
        },
        getHeaders: function () {
            let headers = setter.getAjaxHeaders(), obj = {};
            for (let i = 0; i < headers.length; i++) {
                let one = headers[i];
                obj[one.name] = one.value;
            }
            return obj;
        },
        // ajax请求结束后的处理，返回false阻止代码执行
        ajaxSuccessBefore: function (res, url, obj) {
            // token过期
            if (res.code == 401 || res.code === 505) {
                setter.removeToken();
                layui.layer.msg('登录过期', {icon: 2, anim: 6, time: 1500}, function () {
                    location.reload();
                });
                return false;
            }
            // token刷新
            else if(res.code === 209) {
                console.error('token过期');
                res.code = 200;
                setter.putToken(res);
                location.reload();
                return false;
            }
            return true;
        },
        // 路由不存在处理
        routerNotFound: function (r) {
            layui.layer.alert('路由<span class="text-danger">' + r.path.join('/') + '</span>不存在', {
                title: '提示',
                skin: 'layui-layer-admin',
                btn: [],
                offset: '30px',
                anim: 6,
                shadeClose: true
            });
        }
    };

    /* table全局设置 */
    let token = setter.getToken();
    if (token && token.access_token) {
        layui.table.set({
            headers: {'Authorization': 'Bearer ' + token.access_token},
            even: true,
            page: true,
            cellMinWidth: 100,
            response: {
                statusCode: 200
            }
        });
    }

    setter.base_server = setter.baseServer;  // 兼容旧版
    exports('setter', setter);
});
