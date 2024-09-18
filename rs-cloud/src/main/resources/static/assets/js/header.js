var admin;
var user;
layui.use(['admin', 'setter'], function () {
    admin = layui.admin;
    var setter = layui.setter;
    user = admin.getUser();
})

function generateHeader() {
    $('#header').html(
        '<div class="layui-row" style="background-color: #ffffff; display: flex; align-items: center; height: 60px">\n' +
        '    <div class="layui-col-md1" id="title">\n' +
        '        储能演示\n' +
        '    </div>\n' +
        '    <div class="layui-col-md10" style="padding-left: 100px;">\n' +
        '        <ul>\n' +
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                ><a href="/index.html">首页</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                ><a href="/pcs.html">PCS</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                ><a href="/bms.html">BMS</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                "><a href="">收益</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                "><a href="">一次图</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                "><a href="">定值设置</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary" style="float: right" id="user-action">\n' +
        '                <span>用户操作</span>\n' +
        '                <i class="layui-icon layui-icon-down layui-font-12"></i>\n' +
        '            </li>\n'+
        '            <li class="layui-btn layui-btn-primary" style="float: right" id="change-sub">\n' +
        '                <span>切换储能站</span>\n' +
        '                <i class="layui-icon layui-icon-down layui-font-12"></i>\n' +
        '            </li>'+
        '            <li class="layui-btn layui-btn-primary" style="float: right" id="setting">\n' +
        '                <span>配置</span>\n' +
        '                <i class="layui-icon layui-icon-down layui-font-12"></i>\n' +
        '            </li>'+
        '        </ul>\n' +
        '\n' +
        '    </div>\n' +
        '    <div class="layui-col-md1" style="text-align: center; color: #666666">\n' +
        '        <div id="current-day"></div>\n' +
        '        <div id="current-time"></div>\n' +
        '    </div>\n' +
        '</div>\n'
    )
    $('#current-day').css('font-size', '16px');
    $('#current-day').css('color', '#666666');
    $('#current-time').css('font-size', '16px');
    $('#current-time').css('color', '#666666');
    $('#current-time').css('margin-top', '4px');
}

function changeBg(element) {
    element.classList.remove('layui-btn-primary');
    element.classList.add('is-choose');
}

var stamp = new Date().getTime() + 8 * 60 * 60 * 1000;
var currentDay = new Date(stamp).toISOString().replace(/T/, ' ').substring(0, 10);
var currentTime = new Date(stamp).toISOString().replace(/\..+/, '').substring(11, 19);
console.log(currentDay);
$('#current-day').html(currentDay);
$('#current-time').html(currentTime);

function updateTime() {
    stamp = new Date().getTime() + 8 * 60 * 60 * 1000;
    currentTime = new Date(stamp).toISOString().replace(/\..+/, '').substring(11, 19);
    $('#current-time').html(currentTime);
}
setInterval(updateTime, 1000);

function changePswd() {
    layer.open({
        type: 1,
        title: '修改密码',
        area: ['400px', '300px'],
        content:
            '<div>' +
            '<form class="layui-form model-form" id="form-pwd">\n' +
            '    <input name="userId" type="hidden">\n' +
            '    <div class="layui-form-item">\n' +
            '        <label class="layui-form-label" style="width: 85px;">原始密码:</label>\n' +
            '        <div class="layui-input-block" style="margin-left: 120px;">\n' +
            '            <input id="old-password" type="password" autocomplete="off" name="userPwdOld" placeholder="请输入原始密码" class="layui-input" lay-vertype="tips" lay-verify="required|h5" show-word-limit="true" style="width: 182px;">\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="layui-form-item">\n' +
            '        <label class="layui-form-label" style="width: 85px;">新密码:</label>\n' +
            '        <div class="layui-input-block" style="margin-left: 120px;">\n' +
            '            <input id="new-password-1" type="password" name="userPwdNew" placeholder="请输入新密码" class="layui-input" autocomplete="off" lay-vertype="tips" lay-verify="required|userPsw|h5" show-word-limit="true" style="width: 182px;">\n' +
            '        </div>\n' +
            '    </div>\n' +
            '    <div class="layui-form-item">\n' +
            '        <label class="layui-form-label" style="width: 85px;">确认密码:</label>\n' +
            '        <div class="layui-input-block" style="margin-left: 120px;">\n' +
            '            <input id="new-password-2" type="password" placeholder="请再次输入新密码" class="layui-input" autocomplete="off" lay-vertype="tips" lay-verify="equalTo|h5" lay-equalto="#password" show-word-limit="true" style="width: 182px;">\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</form>' +
            // '<div class="layui-btn-container" style="width: auto; float: right; margin-top: 20px;">' +
            // '<div type="button" class="layui-btn layui-btn-primary  layui-bg-blue">确定</div>' +
            // '<div type="button" class="layui-btn layui-btn-primary">取消</div>' +
            // '</div>' +
            '</div>',
        btn: ['确定', '取消'],
        btn1: function (index, layero) {
            var oldPassword = $('#old-password').val();
            var newPassword1 = $('#new-password-1').val();
            var newPassword2 = $('#new-password-2').val();

            if (!oldPassword || !newPassword1 || !newPassword2) {
                layer.msg('请填写所有字段');
                return;
            }

            if (newPassword1 !== newPassword2) {
                layer.msg('新密码和确认密码不一致');
                return;
            }
            admin.req("iac/user/pwd", {usrId: user.userId, oldPwd: oldPassword, newPwd: newPassword1}, function (response) {
                console.log(response)
            }, "PUT")
        },
        btn2: function (index, layero) {
            // 取消按钮的回调，关闭弹窗
            layer.close(index);
        }
    })
}

function logout() {
    admin.req('logout', function () {
        window.location.href = "/login.html";
    })
}


