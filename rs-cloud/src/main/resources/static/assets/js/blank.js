layui.config({
    version: '318',   // 更新组件缓存，设为true不缓存，也可以设一个固定值
    base: 'assets/module/'
}).extend({
}).use(['layer', 'admin'], function () {
    var $ = layui.jquery;
    var admin = layui.admin;

    // 检查是否登录
    if (!admin.getToken() || !admin.getUser()) {
        return location.replace('login.html');
    } else {
        let params = [];
        switch (admin.getUser().loginType) {
            case 'SYS_ADMIN':
                params = {loginType: 'SYS_ADMIN'};
                break;
            case 'IOT_CORP':
                params = {loginType: 'IOT_CORP'};
                break;
            case 'IAC_USR_WEB':
                params = {loginType: 'IAC_USR_WEB'};
                break;
            default:
                break;
        }

        if(params.length != 0) {
            admin.openForm('/', '_self', 'POST', params);
        }
    }
});
