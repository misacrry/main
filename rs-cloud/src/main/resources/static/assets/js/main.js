layui.config({
    version: '318',   // 更新组件缓存，设为true不缓存，也可以设一个固定值
    base: 'assets/module/'
}).extend({
    fileChoose: 'fileChoose/fileChoose',
    zTree: 'zTree/zTree',
    steps: 'steps/steps',
    treeTable: 'treeTable',
    treeSelect: 'treeSelect/treeSelect',
    xmSelect: 'xmSelect',
    treeGrid: 'treeGrid/treeGrid',
    tableEdit: 'tableEdit/tableEdit'
}).use(['layer', 'index'], function () {
    var $ = layui.jquery;
    var index = layui.index;

    var year = new Date().getFullYear();
    var copyright = 'Copyright © 2012 - ' + year + ' pinggaogroup.com 版权所有';
    $('.layui-footer').html(copyright);
});
