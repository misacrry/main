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
        '            <li class="layui-btn layui-btn-primary layui-btn-radius page-button" style="background-color: #e2e2e2"\n' +
        '                "><a href="backManage.html">后台管理</a></li>\n' +
        '            <li class="layui-btn layui-btn-primary  layui-btn-danger" style="float: right" id="logout"><a\n' +
        '                    href="">登出</a></li>\n' +
        '        </ul>\n' +
        '\n' +
        '    </div>\n' +
        '    <div class="layui-col-md1" style="text-align: center; color: #666666">\n' +
        '        <div id="current-day"></div>\n' +
        '        <div id="current-time"></div>\n' +
        '    </div>\n' +
        '</div>\n'
    )
}

function changeBg(element) {
    element.classList.remove('layui-btn-primary');
    element.classList.add('is-choose');
}
