/** 透明侧边栏导航 */
.layui-layout-admin .layui-side .layui-nav {
    background-color: transparent;
}

.layui-layout-admin .layui-side .layui-nav .layui-nav-item > a:hover {
    background: rgba(255,255,255,.03);
}

/** logo部分样式 */
.layui-layout-admin .layui-header .layui-logo {
    background-color: {{logoColor}};
    color: {{logoTvColor}};
}

/** header样式 */
.layui-layout-admin .layui-header {
    background-color: {{headerColor}};
}

.layui-layout-admin .layui-header a {
    color: {{headerTvColor}};
}

.layui-layout-admin .layui-header a:hover {
    color: {{headerTvColor}};
}

.layui-layout-admin .layui-header .layui-nav .layui-nav-more {
    border-color: {{headerTvColor}} transparent transparent;
}

.layui-layout-admin .layui-header .layui-nav .layui-nav-mored {
    border-color: transparent transparent {{headerTvColor}};
}

/** 导航栏下面的线条 */
.layui-layout-admin .layui-header .layui-nav .layui-this:after, .layui-layout-admin .layui-header .layui-nav-bar {
    background-color: {{headerTvColor}};
}

/** 侧边栏样式 */
.layui-layout-admin .layui-side {
    background-color: {{sideColor}} !important;
}

.layui-nav-tree .layui-nav-child dd.layui-this, .layui-nav-tree .layui-nav-child dd.layui-this a, .layui-nav-tree .layui-this, .layui-nav-tree .layui-this > a, .layui-nav-tree .layui-this > a:hover {
    background-color: {{primaryColor}};
}

.layui-nav-tree .layui-nav-bar {
    background-color: {{primaryColor}};
}

/** 主题颜色 */

/** 按钮 */
.layui-btn:not(.layui-btn-primary):not(.layui-btn-normal):not(.layui-btn-warm):not(.layui-btn-danger):not(.layui-btn-disabled) {
    background-color: {{primaryColor}};
}

.layui-btn.layui-btn-primary:hover {
    border-color: {{primaryColor}};
}

/** 开关 */
.layui-form-onswitch {
    border-color: {{primaryColor}};
    background-color: {{primaryColor}};
}

/** 分页插件 */
.layui-laypage .layui-laypage-curr .layui-laypage-em {
    background-color: {{primaryColor}};
}

.layui-table-page .layui-laypage input:focus {
    border-color: {{primaryColor}} !important;
}

.layui-table-view select:focus {
    border-color: {{primaryColor}} !important;
}

.layui-table-page .layui-laypage a:hover {
    color: {{primaryColor}};
}

/** 单选按钮 */
.layui-form-radio > i:hover, .layui-form-radioed > i {
    color: {{primaryColor}};
}

/** 下拉条目选中 */
.layui-form-select dl dd.layui-this {
    background-color: {{primaryColor}};
}

/** 选项卡 */
.layui-tab-brief > .layui-tab-title .layui-this {
    color: {{primaryColor}};
}

.layui-tab-brief > .layui-tab-more li.layui-this:after, .layui-tab-brief > .layui-tab-title .layui-this:after {
    border-color: {{primaryColor}} !important;
}

/** 面包屑导航 */
.layui-breadcrumb a:hover {
    color: {{primaryColor}} !important;
}

/** 主体标题 */
.layui-body-header-title {
    border-left-color: {{primaryColor}};
}

/** 日期选择器按钮 */
.laydate-footer-btns span:hover {
    color: {{primaryColor}} !important;
}

/** 时间轴 */
.layui-timeline-axis {
    color: {{primaryColor}};
}

/** 主题切换 */
.btnTheme:hover, .btnTheme.active {
    border-color: {{primaryColor}};
}

/** 侧边栏文字颜色 */
.layui-side .layui-nav .layui-nav-item a {
    color: {{sideTvColor2}};
}

.layui-side .layui-nav-itemed > a, .layui-nav-tree .layui-nav-title a, .layui-nav-tree .layui-nav-title a:hover {
    color: {{sideTvColor}} !important;
}

/** header线条 */
.layui-layout-admin .layui-header .layui-nav .layui-this:after, .layui-layout-admin .layui-header .layui-nav-bar {
    background-color: {{headerLineColor}};
}

/** tab下划线 */
.layui-layout-admin .layui-body > .layui-tab > .layui-tab-title li.layui-this:after {
    background-color: {{tabLineColor}};
    top: {{tabPostion}};
}

/** 复选框 */
.layui-form-checked[lay-skin=primary] i {
    border-color: {{primaryColor}};
    background-color: {{primaryColor}};
}

.layui-form-checkbox[lay-skin=primary] i:hover {
    border-color: {{primaryColor}};
}

/** PC端折叠鼠标经过样式 */
.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item.admin-nav-hover > .layui-nav-child {
    background: {{sideColor}} !important;
}

/** 移动设备样式 */
@media screen and (max-width: 750px) {
    /** 去掉PC端折叠鼠标经过样式 */
    .layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item.admin-nav-hover > .layui-nav-child {
        background-color: rgba(0, 0, 0, .3) !important;
    }
}

/** admin风格弹窗样式 */
.layui-layer.layui-layer-admin .layui-layer-title {
    background-color: {{logoColor}};
    color: #ffffff;
}

/** 按钮颜色 */
.layui-layer.layui-layer-admin .layui-layer-setwin a {
    color: #ffffff;
}

/* 最小化按钮 */
.layui-layer.layui-layer-admin .layui-layer-setwin .layui-layer-min cite {
    background-color: #dddddd;
}

/** 弹窗确定按钮 */
 .layui-layer.layui-layer-admin .layui-layer-btn .layui-layer-btn0 {
     border-color: {{primaryColor}};
     background-color: {{primaryColor}};
 }

/* 圆形按钮 */
.btn-circle {
    background: {{primaryColor}};
}