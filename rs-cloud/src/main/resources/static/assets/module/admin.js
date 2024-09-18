/** EasyWeb spa v3.1.8 date:2020-05-04 License By http://easyweb.vip */

layui.define(['layer', 'setter', 'layRouter', 'form', 'formX', 'xmSelect', 'table'], function (exports) {
    let $ = layui.jquery;
    let layer = layui.layer;
    let setter = layui.setter;
    let layRouter = layui.layRouter;
    let form = layui.form;
    let formX = layui.formX;
    let xmSelect = layui.xmSelect;
    let table = layui.table;
    let bodyDOM = '.layui-layout-admin>.layui-body';
    let tabDOM = bodyDOM + '>.layui-tab';
    let sideDOM = '.layui-layout-admin>.layui-side>.layui-side-scroll';
    let headerDOM = '.layui-layout-admin>.layui-header';
    let navFilter = 'admin-side-nav';
    let admin = {version: '3.1.8', layerData: {}};

    /** 设置侧栏折叠 */
    admin.flexible = function (expand) {
        let $layout = $('.layui-layout-admin');
        let isExapnd = $layout.hasClass('admin-nav-mini');
        if (expand === undefined) expand = isExapnd;
        if (isExapnd === expand) {
            if (expand) {
                admin.hideTableScrollBar();
                $layout.removeClass('admin-nav-mini');
            } else {
                $layout.addClass('admin-nav-mini');
            }
            layui.event.call(this, 'admin', 'flexible({*})', {expand: expand});
            admin.resizeTable(600);
        }
    };

    /** 设置导航栏选中 */
    admin.activeNav = function (url, anim) {
        if (anim === undefined) anim = true;
        if (!url) url = location.hash;
        if (!url) return console.warn('active url is null');
        $(sideDOM + '>.layui-nav .layui-nav-item .layui-nav-child dd.layui-this').removeClass('layui-this');
        $(sideDOM + '>.layui-nav .layui-nav-item.layui-this').removeClass('layui-this');
        let $a = $(sideDOM + '>.layui-nav a[href="#' + url + '"]');
        if ($a.length === 0) return console.warn(url + ' not found');
        let isMini = $('.layui-layout-admin').hasClass('admin-nav-mini');
        if ($(sideDOM + '>.layui-nav').attr('lay-shrink') === '_all') {  // 手风琴效果
            let $pChilds = $a.parent('dd').parents('.layui-nav-child');
            if (!isMini) {
                $(sideDOM + '>.layui-nav .layui-nav-itemed>.layui-nav-child').not($pChilds)
                    .css('display', 'block').slideUp(anim ? 'fast' : 0, function () {
                    $(this).css('display', '');
                });
            }
            $(sideDOM + '>.layui-nav .layui-nav-itemed').not($pChilds.parent()).removeClass('layui-nav-itemed');
        }
        $a.parent().addClass('layui-this');  // 选中当前
        // 展开所有父级
        let $asParents = $a.parent('dd').parents('.layui-nav-child').parent();
        if (!isMini) {
            let $childs = $asParents.not('.layui-nav-itemed').children('.layui-nav-child');
            $childs.slideDown(anim ? 'fast' : 0, function () {
                if ($(this).is($childs.last())) {
                    $childs.css('display', '');
                    // 菜单超出屏幕自动滚动
                    let topBeyond = $a.offset().top + $a.outerHeight() + 30 - admin.getPageHeight();
                    let topDisparity = 50 + 65 - $a.offset().top;
                    if (topBeyond > 0) {
                        $(sideDOM).animate({'scrollTop': $(sideDOM).scrollTop() + topBeyond}, anim ? 300 : 0);
                    } else if (topDisparity > 0) {
                        $(sideDOM).animate({'scrollTop': $(sideDOM).scrollTop() - topDisparity}, anim ? 300 : 0);
                    }
                }
            });
        }
        $asParents.addClass('layui-nav-itemed');
        // 适配多系统模式
        $('ul[lay-filter="' + navFilter + '"]').addClass('layui-hide');
        let $aUl = $a.parents('.layui-nav');
        $aUl.removeClass('layui-hide');
        $(headerDOM + '>.layui-nav>.layui-nav-item').removeClass('layui-this');
        $(headerDOM + '>.layui-nav>.layui-nav-item>a[nav-bind="' + $aUl.attr('nav-id') + '"]').parent().addClass('layui-this');
    };

    /**
     * 下拉框
     * @param options
     * @returns {*}
     */
    admin.select = function (options) {
        let url = options.url;
        let jsonUrl = options.jsonUrl;
        let value = options.value;
        let valueArr = options.valueArr;
        if (options.param === undefined) options.param = {};
        // 设备默认宽度
        if (options.style === undefined) options.style = {width: '180px'};
        if (options.data === undefined) options.data = [];

        // width
        if (options.width != undefined) options.style = {width: options.width + 'px'};
        if (options.required != undefined) {
            options.layVerify = 'required';
            options.layVerType = 'tips';
        }

        // 无单选、复选
        if (options.radio != undefined && options.multiple === undefined && !options.radio) {
            options = $.extend({
                radio: false,
                clickClose: true,
                model: {
                    icon: 'hidden',
                    label: {
                        type: 'text'
                    }
                }
            }, options);
        }
        // 单选
        else if (options.radio === undefined && options.multiple === undefined) {
            options = $.extend({
                radio: true,
                toolbar: {
                    show: true,
                    list: ['CLEAR']
                },
                clickClose: true
            }, options);
        }
        // 多选
        else if (options.radio === undefined && options.multiple !== undefined && options.multiple) {
            options = $.extend({
                toolbar: {
                    show: true,
                    list: ['ALL', 'REVERSE', 'CLEAR']
                }
            }, options);
        }

        let $xmSelect = xmSelect.render(options);

        if(jsonUrl) {
            $.getJSON(jsonUrl, options.param, function (res) {
                if(options.cascader && options.cascader.show) {
                    res = options.cascaderVal.call(this, res);
                }

                $xmSelect.update({
                    data: res
                });
            });
        }

        if (url) {
            admin.req(url, options.param, function (res) {
                if (res.code === 200) {
                    $xmSelect.update({
                        data: res.data
                    });

                    // 赋值
                    if (value !== undefined) {
                        $xmSelect.setValue([value]);
                    }

                    // 赋值
                    if (valueArr !== undefined) {
                        $xmSelect.setValue(valueArr);
                    }

                    // 执行事件
                    if (options.hasOwnProperty('success')) {
                        options.success.call(this, $xmSelect);
                    }
                } else {
                    layer.msg(res.msg, {icon: 2});
                }
            }, 'GET');
        } else {
            if(!options.cascader || !options.cascader.show) {
                // 赋值
                if (value !== undefined) {
                    $xmSelect.setValue([value]);
                }

                // 赋值
                if (valueArr !== undefined) {
                    $xmSelect.setValue(valueArr);
                }
            }

            // 执行事件
            if (options.hasOwnProperty('success')) {
                options.success.call(this, $xmSelect);
            }
        }

        return $xmSelect;
    };

    /**
     * 获取xmSelect对象
     * @param filter el
     * @param single 是否返回单实例
     * @returns {*} 符合条件的实例数组
     */
    admin.getSelect = function (filter, single) {
        return xmSelect.get(filter, single);
    }

    /**
     * select查询
     * @param filter el
     * @param url
     * @param param
     */
    admin.searchSelect = function (filter, url, param) {
        let $xmSelect = xmSelect.get(filter, true);
        admin.req(url, param, function (res) {
            if (res.code == 200) {
                $xmSelect.update({
                    data: res.data
                });
            } else {
                layer.msg(res.msg, {icon: 2});
            }
        }, 'GET');
    }

    /**
     * 删除
     * @param options
     */
    admin.del = function (options) {
        if (options.url === undefined) return console.warn('del url is null');
        if (options.data === undefined) options.data = {};
        admin.confirm(options.message === undefined ? '确定删除吗？' : options.message, function (i) {
            layer.close(i);
            layer.load(2);
            admin.req(options.url, options.data, function (res) {
                layer.closeAll('loading');
                if (res.code == 200) {
                    layer.msg(res.msg, {icon: 1});

                    if (options.obj != undefined) {
                        options.obj.del();
                    }

                    if (options.hasOwnProperty('success')) {
                        options.success.call(this);
                    }
                } else {
                    layer.msg(res.msg, {icon: 2});
                }
            }, 'DELETE');
        });
    };

    /**
     * 表单提交
     * @param options
     */
    admin.formSubmit = function(options) {
        if (options.btnFilter === undefined) return console.warn('submit btnFilter is null');
        if (options.url === undefined) return console.warn('submit url is null');
        // 表单提交事件
        form.on('submit(' + options.btnFilter + ')', function (d) {
            let isSubmit = true;

            if(options.hasOwnProperty('submitBefore')) {
                isSubmit = options.submitBefore.call(this, d);
            }

            if(isSubmit) {
                layer.load(2);
                let param = d.field;
                // dataStr: 布尔值
                if(options.dataStr) param = JSON.stringify(param);
                // 后台实体对象含数组对象，需要加此属性，并且后台参数需要加@RequestBody
                admin.req(options.url, param, function (res) {
                    layer.closeAll('loading');
                    if (res.code === 200) {
                        layer.msg(res.msg, {icon: 1});
                        if (!options.unclosed){
                            layer.closeAll('page');
                        }

                        if(options.hasOwnProperty('success')) {
                            options.success.call(this, res);
                        }
                    } else {
                        layer.msg(res.msg, {icon: 2});
                    }
                }, options.data ? 'PUT' : 'POST', options.option);
            }

            return false;
        });
    }

    /** 右侧弹出 */
    admin.popupRight = function (param) {
        param.anim = -1;
        param.offset = 'r';
        param.move = false;
        param.fixed = true;
        if (param.area === undefined) param.area = '336px';
        if (param.title === undefined) param.title = false;
        if (param.closeBtn === undefined) param.closeBtn = false;
        if (param.shadeClose === undefined) param.shadeClose = true;
        if (param.skin === undefined) param.skin = 'layui-anim layui-anim-rl layui-layer-adminRight';
        return admin.open(param);
    };

    /** 封装layer.open */
    admin.open = function (param) {
        if (param.content && param.type === 2) param.url = undefined;  // 参数纠正
        if (param.url && (param.type === 2 || param.type === undefined)) param.type = 1;  // 参数纠正
        if (param.area === undefined) param.area = param.type === 2 ? ['360px', '300px'] : '360px';
        if (param.offset === undefined) param.offset = '70px';
        if (param.shade === undefined) param.shade = .1;
        if (param.fixed === undefined) param.fixed = false;
        if (param.resize === undefined) param.resize = false;
        if (param.skin === undefined) param.skin = 'layui-layer-admin';
        let eCallBack = param.end;
        param.end = function () {
            layer.closeAll('tips');  // 关闭表单验证的tips
            eCallBack && eCallBack();
        };
        if (param.url) {
            let sCallBack = param.success;
            param.success = function (layero, index) {
                $(layero).data('tpl', param.tpl || '');
                admin.reloadLayer(index, param.url, sCallBack);
            };
        } else if (param.tpl && param.content) {
            param.content = admin.util.tpl(param.content, param.data, setter.tplOpen, setter.tplClose);
        }
        let layIndex = layer.open(param);
        if (param.data) admin.layerData['d' + layIndex] = param.data;

        return layIndex;
    };

    /** 获取弹窗数据 */
    admin.getLayerData = function (index, key) {
        if (index === undefined) {
            index = parent.layer.getFrameIndex(window.name);
            if (index === undefined) return null;
            else return parent.layui.admin.getLayerData(parseInt(index), key);
        } else if (isNaN(index)) {
            index = admin.getLayerIndex(index);
        }
        if (index === undefined) return;
        let layerData = admin.layerData['d' + index];
        if (key && layerData) return layerData[key];
        return layerData;
    };

    /** 放入弹窗数据 */
    admin.putLayerData = function (key, value, index) {
        if (index === undefined) {
            index = parent.layer.getFrameIndex(window.name);
            if (index === undefined) return;
            else return parent.layui.admin.putLayerData(key, value, parseInt(index));
        } else if (isNaN(index)) {
            index = admin.getLayerIndex(index);
        }
        if (index === undefined) return;
        let layerData = admin.getLayerData(index);
        if (!layerData) layerData = {};
        layerData[key] = value;
        admin.layerData['d' + index] = layerData;
    };

    /** 刷新url方式的layer */
    admin.reloadLayer = function (index, url, success) {
        if (typeof url === 'function') {
            success = url;
            url = undefined;
        }
        if (isNaN(index)) index = admin.getLayerIndex(index);
        if (index === undefined) return;
        let $layero = $('#layui-layer' + index);
        if (url === undefined) url = $layero.data('url');
        if (!url) return;
        $layero.data('url', url);
        admin.showLoading($layero);
        admin.ajax({
            url: url,
            dataType: 'html',
            success: function (res) {
                admin.removeLoading($layero, false);
                if (typeof res !== 'string') res = JSON.stringify(res);
                let tpl = $layero.data('tpl');
                // 模板解析
                if (tpl === true || tpl === 'true') {
                    let data = admin.getLayerData(index) || {};
                    data.layerIndex = index;
                    // 模板里面有动态模板处理
                    let $html = $('<div>' + res.replace(/src=/g, 'e_src=') + '</div>'), tplAll = {};
                    $html.find('script,[tpl-ignore]').each(function (i) {
                        let $this = $(this);
                        tplAll['temp_' + i] = $this[0].outerHTML;
                        $this.after('${temp_' + i + '}').remove();
                    });
                    res = admin.util.tpl($html.html().replace(/e_src=/g, 'src='), data, setter.tplOpen, setter.tplClose);
                    for (let f in tplAll) res = res.replace('${' + f + '}', tplAll[f]);
                }
                $layero.children('.layui-layer-content').html(res);
                admin.renderTpl('#layui-layer' + index + ' [ew-tpl]');
                success && success($layero[0], index);
            }
        });
    };

    /** 封装layer.alert */
    admin.alert = function (content, options, yes) {
        if (typeof options === 'function') {
            yes = options;
            options = {};
        }
        if (options.skin === undefined) options.skin = 'layui-layer-admin';
        if (options.shade === undefined) options.shade = .1;
        return layer.alert(content, options, yes);
    };

    /** 封装layer.confirm */
    admin.confirm = function (content, options, yes, cancel) {
        if (typeof options === 'function') {
            cancel = yes;
            yes = options;
            options = {};
        }
        if (options.skin === undefined) options.skin = 'layui-layer-admin';
        if (options.shade === undefined) options.shade = .1;
        return layer.confirm(content, options, yes, cancel);
    };

    /** 封装layer.prompt */
    admin.prompt = function (options, yes) {
        if (typeof options === 'function') {
            yes = options;
            options = {};
        }
        if (options.skin === undefined) options.skin = 'layui-layer-admin layui-layer-prompt';
        if (options.shade === undefined) options.shade = .1;
        return layer.prompt(options, yes);
    };

    /** 封装ajax请求，返回数据类型为json */
    admin.req = function (url, data, success, method, option) {
        if (typeof data === 'function') {
            option = method;
            method = success;
            success = data;
            data = {};
        }
        if (method !== undefined && typeof method !== 'string') {
            option = method;
            method = undefined;
        }
        if (!method) method = 'GET';
        if (typeof data === 'string') {
            if (!option) option = {};
            if (!option.contentType) option.contentType = 'application/json;charset=UTF-8';
        } else if (setter.reqPutToPost) {
            if ('put' === method.toLowerCase()) {
                method = 'POST';
                data._method = 'PUT';
            } else if ('delete' === method.toLowerCase()) {
                method = 'POST';
                data._method = 'DELETE';
            }
        }
        if (url && url.indexOf('http://') !== 0 && url.indexOf('https://') !== 0 && url.indexOf('//') !== 0) {
            url = (setter.baseServer || '') + url;
        }
        return admin.ajax($.extend({
            url: url, data: data, type: method, dataType: 'json', success: success
        }, option));
    };

    /** 封装ajax请求 */
    admin.ajax = function (param) {
        let oldParam = admin.util.deepClone(param);
        if (!param.dataType) param.dataType = 'json';
        if (!param.headers) param.headers = {};
        // 统一设置header
        let headers = setter.getAjaxHeaders(param.url);
        if (headers) {
            for (let i = 0; i < headers.length; i++) {
                if (param.headers[headers[i].name] === undefined) param.headers[headers[i].name] = headers[i].value;
            }
        }
        // success预处理
        let success = param.success;
        param.success = function (result, status, xhr) {
            let before = setter.ajaxSuccessBefore(admin.parseJSON(result), param.url, {
                param: oldParam, reload: function (p) {
                    admin.ajax($.extend(true, oldParam, p));
                }, update: function (r) {
                    result = r;
                }, xhr: xhr
            });
            if (before !== false) success && success(result, status, xhr);
            else param.cancel && param.cancel();
        };
        param.error = function (xhr, status) {
            param.success({code: xhr.status, msg: xhr.statusText}, status, xhr);
        };
        // 解决缓存问题
        if (layui.cache.version && (!setter.apiNoCache || param.dataType.toLowerCase() !== 'json')) {
            if (param.url.indexOf('?') === -1) param.url += '?v=';
            else param.url += '&v=';
            if (layui.cache.version === true) param.url += new Date().getTime();
            else param.url += layui.cache.version;
        }
        return $.ajax(param);
    };

    /** 解析json */
    admin.parseJSON = function (str) {
        if (typeof str === 'string') {
            try {
                return JSON.parse(str);
            } catch (e) {
            }
        }
        return str;
    };

    /** 显示加载动画 */
    admin.showLoading = function (elem, type, opacity, size) {
        if (elem !== undefined && (typeof elem !== 'string') && !(elem instanceof $)) {
            type = elem.type;
            opacity = elem.opacity;
            size = elem.size;
            elem = elem.elem;
        }
        if (type === undefined) type = setter.defaultLoading || 1;
        if (size === undefined) size = 'sm';
        if (elem === undefined) elem = 'body';
        let loader = [
            '<div class="ball-loader ' + size + '"><span></span><span></span><span></span><span></span></div>',
            '<div class="rubik-loader ' + size + '"></div>',
            '<div class="signal-loader ' + size + '"><span></span><span></span><span></span><span></span></div>',
            '<div class="layui-loader ' + size + '"><i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"></i></div>'
        ];
        $(elem).addClass('page-no-scroll');  // 禁用滚动条
        $(elem).scrollTop(0);
        let $loading = $(elem).children('.page-loading');
        if ($loading.length <= 0) {
            $(elem).append('<div class="page-loading">' + loader[type - 1] + '</div>');
            $loading = $(elem).children('.page-loading');
        }
        if (opacity !== undefined) $loading.css('background-color', 'rgba(255,255,255,' + opacity + ')');
        $loading.show();
    };

    /** 移除加载动画 */
    admin.removeLoading = function (elem, fade, del) {
        if (elem === undefined) elem = 'body';
        if (fade === undefined) fade = true;
        let $loading = $(elem).children('.page-loading');
        if (del) $loading.remove();
        else if (fade) $loading.fadeOut('fast');
        else $loading.hide();
        $(elem).removeClass('page-no-scroll');
    };

    /** 缓存临时数据 */
    admin.putTempData = function (key, value, local) {
        let tableName = local ? setter.tableName : setter.tableName + '_tempData';
        if (value === undefined || value === null) {
            if (local) layui.data(tableName, {key: key, remove: true});
            else layui.sessionData(tableName, {key: key, remove: true});
        } else {
            if (local) layui.data(tableName, {key: key, value: value});
            else layui.sessionData(tableName, {key: key, value: value});
        }
    };

    /** 获取缓存临时数据 */
    admin.getTempData = function (key, local) {
        if (typeof key === 'boolean') {
            local = key;
            key = undefined;
        }
        let tableName = local ? setter.tableName : setter.tableName + '_tempData';
        let tempData = local ? layui.data(tableName) : layui.sessionData(tableName);
        if (!key) return tempData;
        return tempData ? tempData[key] : undefined;
    };

    /** 滑动选项卡 */
    admin.rollPage = function (d) {
        let $tabTitle = $(tabDOM + '>.layui-tab-title');
        let left = $tabTitle.scrollLeft();
        if ('left' === d) {
            $tabTitle.animate({'scrollLeft': left - 120}, 100);
        } else if ('auto' === d) {
            let autoLeft = 0;
            $tabTitle.children("li").each(function () {
                if ($(this).hasClass('layui-this')) return false;
                else autoLeft += $(this).outerWidth();
            });
            $tabTitle.animate({'scrollLeft': autoLeft - 120}, 100);
        } else {
            $tabTitle.animate({'scrollLeft': left + 120}, 100);
        }
    };

    /** 刷新当前选项卡 */
    admin.refresh = function (url) {
        layRouter.refresh(url);
    };

    /** 关闭当前选项卡 */
    admin.closeThisTabs = function (url) {
        admin.closeTabOperNav();
        let $title = $(tabDOM + '>.layui-tab-title');
        if (!url) {
            if ($title.find('li').first().hasClass('layui-this')) return layer.msg('主页不能关闭', {icon: 2});
            $title.find('li.layui-this').find('.layui-tab-close').trigger('click');
        } else {
            if (url === $title.find('li').first().attr('lay-id')) return layer.msg('主页不能关闭', {icon: 2});
            $title.find('li[lay-id="' + url + '"]').find('.layui-tab-close').trigger('click');
        }
    };

    /** 关闭其他选项卡 */
    admin.closeOtherTabs = function (url) {
        if (!url) {
            $(tabDOM + '>.layui-tab-title li:gt(0):not(.layui-this)').find('.layui-tab-close').trigger('click');
        } else {
            $(tabDOM + '>.layui-tab-title li:gt(0)').each(function () {
                if (url !== $(this).attr('lay-id')) $(this).find('.layui-tab-close').trigger('click');
            });
        }
        admin.closeTabOperNav();
    };

    /** 关闭所有选项卡 */
    admin.closeAllTabs = function () {
        $(tabDOM + '>.layui-tab-title li:gt(0)').find('.layui-tab-close').trigger('click');
        $(tabDOM + '>.layui-tab-title li:eq(0)').trigger('click');
        admin.closeTabOperNav();
    };

    /** 关闭选项卡操作菜单 */
    admin.closeTabOperNav = function () {
        $('.layui-icon-down .layui-nav .layui-nav-child').removeClass('layui-show');
    };

    /** 设置主题 */
    admin.changeTheme = function (theme, win, noCache, noChild) {
        if (!noCache) admin.putSetting('defaultTheme', theme);
        if (!win) win = top;
        admin.removeTheme(win);
        if (theme) {
            try {
                let $body = win.layui.jquery('body');
                $body.addClass(theme);
                $body.data('theme', theme);
            } catch (e) {
            }
        }
        if (noChild) return;
        let ifs = win.frames;
        for (let i = 0; i < ifs.length; i++) admin.changeTheme(theme, ifs[i], true, false);
    };

    /** 移除主题 */
    admin.removeTheme = function (w) {
        if (!w) w = window;
        try {
            let $body = w.layui.jquery('body');
            let theme = $body.data('theme');
            if (theme) $body.removeClass(theme);
            $body.removeData('theme');
        } catch (e) {
        }
    };

    /** 关闭当前iframe层弹窗 */
    admin.closeThisDialog = function () {
        return admin.closeDialog();
    };

    /** 关闭elem所在的页面层弹窗 */
    admin.closeDialog = function (elem) {
        if (elem) layer.close(admin.getLayerIndex(elem));
        else parent.layer.close(parent.layer.getFrameIndex(window.name));
    };

    /** 获取页面层弹窗的index */
    admin.getLayerIndex = function (elem) {
        if (!elem) return parent.layer.getFrameIndex(window.name);
        let id = $(elem).parents('.layui-layer').first().attr('id');
        if (id && id.length >= 11) return id.substring(11);
    };

    /** 让当前的iframe弹层自适应高度 */
    admin.iframeAuto = function () {
        return parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
    };

    /** 获取浏览器高度 */
    admin.getPageHeight = function () {
        return document.documentElement.clientHeight || document.body.clientHeight;
    };

    /** 获取浏览器宽度 */
    admin.getPageWidth = function () {
        return document.documentElement.clientWidth || document.body.clientWidth;
    };

    /**
     * 绑定表单弹窗
     * @param layero
     * @param btnFilter 提交按钮ID(不需要加#)
     * @param formFilter form表单ID(不需要加#)
     * @param options
     */
    admin.modelForm = function (layero, btnFilter, formFilter, options) {
        let $layero = admin.renderModel(layero, options);

        $layero.addClass('layui-form');
        if (formFilter) $layero.attr('lay-filter', formFilter);

        // 确定按钮绑定submit
        let $btnSubmit = $layero.find('.layui-layer-btn .layui-layer-btn0');
        $btnSubmit.attr('lay-submit', '');
        $btnSubmit.attr('lay-filter', btnFilter);

        form.render();
    };

    /**
     * 渲染弹窗
     * @param layero
     * @param options
     */
    admin.renderModel = function (layero, options) {
        let $layero = $(layero);

        if(!$layero.hasClass('layui-layer')) {
            $layero = $layero.parent().parent();
        }

        // input-block样式
        $layero.find('.layui-input-block').each(function () {
            if (options && options.blockStyle) {
                $(this).css(options.blockStyle);
            } else {
                if (!$(this).attr("style") || $(this).attr("style").indexOf("margin-left") === -1) {
                    $(this).css({'margin-left': '120px'});
                }
            }
        });

        // form-label 宽度
        $layero.find('.layui-form-label').each(function () {
            if (options && options.labelStyle) {
                $(this).css(options.labelStyle);
            } else {
                if (!$(this).attr("style") || $(this).attr("style").indexOf("width") === -1) {
                    $(this).css({'width': '85px'});
                }
            }
        });

        // input 宽度和禁用缓存
        $layero.find('.layui-input').each(function () {
            if (options && options.inputStyle) {
                $(this).css(options.inputStyle);
            } else {
                if (!$(this).attr("style") || $(this).attr("style").indexOf("width") === -1) {
                    $(this).css({'width': '182px'});
                }
            }

            $(this).attr('autocomplete', 'off');
        });

        // select 宽度和禁用缓存
        $layero.find('.layui-treeSelect').each(function () {
            if (options && options.inputStyle) {
                $(this).css(options.inputStyle);
            } else {
                if (!$(this).attr("style") || $(this).attr("style").indexOf("width") === -1) {
                    $(this).css({'width': '182px'});
                }
            }
        });

        $layero.find('.layui-textarea').each(function () {
            if (options && options.inputStyle) {
                $(this).css(options.inputStyle);
            } else {
                if (!$(this).attr("style") || $(this).attr("style").indexOf("width") === -1) {
                    $(this).css({'width': '182px'});
                }
            }
        });

        // 滚动条 默认无滚动条
        if (options && options.overflow != undefined) {
            $(layero).children('.layui-layer-content').css({'overflow': options.overflow});
        } else {
            $(layero).children('.layui-layer-content').css({'overflow': 'visible'});
        }

        if(options && options.hasOwnProperty('success')) {
            options.success.call(this, $layero);
        }

        return $layero;
    };

    /** loading按钮 */
    admin.btnLoading = function (elem, text, loading) {
        if (text !== undefined && (typeof text === 'boolean')) {
            loading = text;
            text = undefined;
        }
        if (text === undefined) text = '&nbsp;加载中';
        if (loading === undefined) loading = true;
        let $elem = $(elem);
        if (loading) {
            $elem.addClass('ew-btn-loading');
            $elem.prepend('<span class="ew-btn-loading-text"><i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop"></i>' + text + '</span>');
            $elem.attr('disabled', 'disabled').prop('disabled', true);
        } else {
            $elem.removeClass('ew-btn-loading');
            $elem.children('.ew-btn-loading-text').remove();
            $elem.removeProp('disabled').removeAttr('disabled');
        }
    };

    /** 鼠标移入侧边栏自动展开 */
    admin.openSideAutoExpand = function () {
        let $side = $('.layui-layout-admin>.layui-side');
        $side.off('mouseenter.openSideAutoExpand').on("mouseenter.openSideAutoExpand", function () {
            if (!$(this).parent().hasClass('admin-nav-mini')) return;
            admin.flexible(true);
            $(this).addClass('side-mini-hover');
        });
        $side.off('mouseleave.openSideAutoExpand').on("mouseleave.openSideAutoExpand", function () {
            if (!$(this).hasClass('side-mini-hover')) return;
            admin.flexible(false);
            $(this).removeClass('side-mini-hover');
        });
    };

    /** 表格单元格超出内容自动展开 */
    admin.openCellAutoExpand = function () {
        let $body = $('body');
        $body.off('mouseenter.openCellAutoExpand').on('mouseenter.openCellAutoExpand', '.layui-table-view td', function () {
            $(this).find('.layui-table-grid-down').trigger('click');
        });
        $body.off('mouseleave.openCellAutoExpand').on('mouseleave.openCellAutoExpand', '.layui-table-tips>.layui-layer-content', function () {
            $('.layui-table-tips-c').trigger('click');
        });
    };

    /** open事件解析layer参数 */
    admin.parseLayerOption = function (option) {
        // 数组类型进行转换
        for (let f in option) {
            if (!option.hasOwnProperty(f)) continue;
            if (option[f] && option[f].toString().indexOf(',') !== -1) option[f] = option[f].toString().split(',');
        }
        // function类型参数转换
        let fs = {'success': 'layero,index', 'cancel': 'index,layero', 'end': '', 'full': '', 'min': '', 'restore': ''};
        for (let k in fs) {
            if (!fs.hasOwnProperty(k) || !option[k]) continue;
            try {
                if (/^[a-zA-Z_]+[a-zA-Z0-9_]+$/.test(option[k])) option[k] += '()';
                option[k] = new Function(fs[k], option[k]);
            } catch (e) {
                option[k] = undefined;
            }
        }
        // content取内容
        if (option.content && (typeof option.content === 'string') && option.content.indexOf('#') === 0) {
            if ($(option.content).is('script')) option.content = $(option.content).html();
            else option.content = $(option.content);
        }
        if (option.type === undefined && option.url === undefined) option.type = 2;  // 默认为iframe类型
        return option;
    };

    /** 字符串形式的parent.parent转window对象 */
    admin.strToWin = function (str) {
        let win = window;
        if (!str) return win;
        let ws = str.split('.');
        for (let i = 0; i < ws.length; i++) win = win[ws[i]];
        return win;
    };

    /** 解决折叠侧边栏表格滚动条闪现 */
    admin.hideTableScrollBar = function () {
        if (admin.getPageWidth() <= 768) return;
        let $tbView = setter.pageTabs ? $(tabDOM + '>.layui-tab-content>.layui-tab-item.layui-show') : $(bodyDOM);
        if (window.hsbTimer) clearTimeout(window.hsbTimer);
        $tbView.find('.layui-table-body.layui-table-main').addClass('no-scrollbar');
        window.hsbTimer = setTimeout(function () {
            $tbView.find('.layui-table-body.layui-table-main').removeClass('no-scrollbar');
        }, 600);
    };

    /** 重置表格尺寸 */
    admin.resizeTable = function (time) {
        setTimeout(function () {
            let $tbView = setter.pageTabs ? $(tabDOM + '>.layui-tab-content>.layui-tab-item.layui-show') : $(bodyDOM);
            $tbView.find('.layui-table-view').each(function () {
                let tbId = $(this).attr('lay-id');
                layui.table && layui.table.resize(tbId);
            });
        }, time === undefined ? 0 : time);
    };

    /** 判断是否有权限 */
    admin.hasPerm = function (r) {
        let auth = setter.getUserAuths();
        if (auth) for (let i = 0; i < auth.length; i++) if (r == auth[i]) return true;
        return false;
    };

    /** 移除没有权限的元素 */
    admin.renderPerm = function () {
        $('[perm-show]').each(function () {
            if (!admin.hasPerm($(this).attr('perm-show'))) $(this).remove();
        });
    };

    /** admin提供的事件 */
    admin.events = {
        /* 折叠侧导航 */
        flexible: function () {
            admin.strToWin($(this).data('window')).layui.admin.flexible();
        },
        /* 刷新主体部分 */
        refresh: function () {
            admin.strToWin($(this).data('window')).layui.admin.refresh();
        },
        /* 后退 */
        back: function () {
            admin.strToWin($(this).data('window')).history.back();
        },
        /* 设置主题 */
        theme: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'layer-theme',
                url: option.url || 'components/tpl/theme.html'
            }, admin.parseLayerOption(option)));
        },
        /* 打开便签 */
        note: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'layer-note',
                url: option.url || 'components/tpl/note.html'
            }, admin.parseLayerOption(option)));
        },
        /* iot配置 */
        config: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'iot-config',
                url: option.url || 'components/tpl/config.html',
            }, admin.parseLayerOption(option)));
        },
        /* 打开消息 */
        message: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight($.extend({
                id: 'layer-message',
                url: option.url || 'components/tpl/message.html',
                area: '450px'
            }, admin.parseLayerOption(option)));
        },
        /* 打开修改密码弹窗 */
        psw: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.open($.extend({
                id: 'layer-psw', title: '修改密码', shade: 0, url: option.url || 'components/tpl/password.html'
            }, admin.parseLayerOption(option)));
        },
        /* 退出登录 */
        logout: function () {
            // 将缓存中的projId置空
            admin.putTempData('corpToProjId');

            let option = admin.util.deepClone($(this).data());
            option = $.extend(option, {
                ajax: 'logout',
                method: 'GET',
                code: 200,
                confirm: true
            });
            admin.unlockScreen();

            function doLogout() {
                if (option.ajax) {
                    let loadIndex = layer.load(2);
                    admin.req(option.ajax, function (res) {
                        layer.close(loadIndex);
                        if (option.parseData) {
                            try {
                                let parseData = new Function('res', option.parseData);
                                res = parseData(res);
                            } catch (e) {
                                console.error(e);
                            }
                        }
                        if (res.code == (option.code || 0)) {
                            setter.removeToken && setter.removeToken();
                            location.replace(option.url || '/');
                        } else {
                            layer.msg(res.msg, {icon: 2});
                        }
                    }, option.method || 'delete');
                } else {
                    setter.removeToken && setter.removeToken();
                    location.replace(option.url || '/');
                }
            }

            if (false === option.confirm || 'false' === option.confirm) return doLogout();
            admin.strToWin(option.window).layui.layer.confirm(option.content || '确定要退出登录吗？', $.extend({
                title: '温馨提示', skin: 'layui-layer-admin', shade: .1
            }, admin.parseLayerOption(option)), function () {
                doLogout();
            });
        },
        /* 打开弹窗 */
        open: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.open(admin.parseLayerOption(option));
        },
        /* 打开右侧弹窗 */
        popupRight: function () {
            let option = admin.util.deepClone($(this).data());
            admin.strToWin(option.window).layui.admin.popupRight(admin.parseLayerOption(option));
        },
        /* 全屏 */
        fullScreen: function () {
            let ac = 'layui-icon-screen-full', ic = 'layui-icon-screen-restore';
            let $ti = $(this).find('i');
            let isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
            if (isFullscreen) {
                let efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
                if (efs) {
                    efs.call(document);
                } else if (window.ActiveXObject) {
                    let ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                $ti.addClass(ac).removeClass(ic);
            } else {
                let el = document.documentElement;
                let rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
                if (rfs) {
                    rfs.call(el);
                } else if (window.ActiveXObject) {
                    let wss = new ActiveXObject('WScript.Shell');
                    wss && wss.SendKeys('{F11}');
                }
                $ti.addClass(ic).removeClass(ac);
            }
        },
        /* 左滑动tab */
        leftPage: function () {
            admin.strToWin($(this).data('window')).layui.admin.rollPage('left');
        },
        /* 右滑动tab */
        rightPage: function () {
            admin.strToWin($(this).data('window')).layui.admin.rollPage();
        },
        /* 关闭当前选项卡 */
        closeThisTabs: function () {
            let url = $(this).data('url');
            admin.strToWin($(this).data('window')).layui.admin.closeThisTabs(url);
        },
        /* 关闭其他选项卡 */
        closeOtherTabs: function () {
            admin.strToWin($(this).data('window')).layui.admin.closeOtherTabs();
        },
        /* 关闭所有选项卡 */
        closeAllTabs: function () {
            admin.strToWin($(this).data('window')).layui.admin.closeAllTabs();
        },
        /* 关闭当前弹窗(智能) */
        closeDialog: function () {
            if ($(this).parents('.layui-layer').length > 0) admin.closeDialog(this);
            else admin.closeDialog();
        },
        /* 关闭当前iframe弹窗 */
        closeIframeDialog: function () {
            admin.closeDialog();
        },
        /* 关闭当前页面层弹窗 */
        closePageDialog: function () {
            admin.closeDialog(this);
        },
        /* 锁屏 */
        lockScreen: function () {
            admin.strToWin($(this).data('window')).layui.admin.lockScreen($(this).data('url'));
        }
    };

    /** 选择位置 */
    admin.chooseLocation = function (param) {
        let dialogTitle = param.title;  // 弹窗标题
        let onSelect = param.onSelect;  // 选择回调
        let needCity = param.needCity;  // 是否返回行政区
        let mapCenter = param.center;  // 地图中心
        let defaultZoom = param.defaultZoom;  // 地图默认缩放级别
        let pointZoom = param.pointZoom;  // 选中时地图缩放级别
        let searchKeywords = param.keywords;  // poi检索关键字
        let searchPageSize = param.pageSize;  // poi检索最大数量
        let mapJsUrl = param.mapJsUrl;  // 高德地图js的url
        if (dialogTitle === undefined) dialogTitle = '选择位置';
        if (defaultZoom === undefined) defaultZoom = 11;
        if (pointZoom === undefined) pointZoom = 17;
        if (searchKeywords === undefined) searchKeywords = '';
        if (searchPageSize === undefined) searchPageSize = 30;
        if (mapJsUrl === undefined) mapJsUrl = 'https://webapi.amap.com/maps?v=1.4.14&key=006d995d433058322319fa797f2876f5';
        let isSelMove = false, selLocation;
        // 搜索附近
        let searchNearBy = function (lat, lng) {
            AMap.service(['AMap.PlaceSearch'], function () {
                let placeSearch = new AMap.PlaceSearch({
                    type: '', pageSize: searchPageSize, pageIndex: 1
                });
                let cpoint = [lng, lat];
                placeSearch.searchNearBy(searchKeywords, cpoint, 1000, function (status, result) {
                    if (status === 'complete') {
                        let pois = result.poiList.pois;
                        let htmlList = '';
                        for (let i = 0; i < pois.length; i++) {
                            let poiItem = pois[i];
                            if (poiItem.location !== undefined) {
                                htmlList += '<div data-lng="' + poiItem.location.lng + '" data-lat="' + poiItem.location.lat + '" class="ew-map-select-search-list-item">';
                                htmlList += '     <div class="ew-map-select-search-list-item-title">' + poiItem.name + '</div>';
                                htmlList += '     <div class="ew-map-select-search-list-item-address">' + poiItem.address + '</div>';
                                htmlList += '     <div class="ew-map-select-search-list-item-icon-ok layui-hide"><i class="layui-icon layui-icon-ok-circle"></i></div>';
                                htmlList += '</div>';
                            }
                        }
                        $('#ew-map-select-pois').html(htmlList);
                    }
                });
            });
        };
        // 渲染地图
        let renderMap = function () {
            let mapOption = {
                resizeEnable: true, // 监控地图容器尺寸变化
                zoom: defaultZoom  // 初缩放级别
            };
            mapCenter && (mapOption.center = mapCenter);
            let map = new AMap.Map('ew-map-select-map', mapOption);
            // 地图加载完成
            map.on('complete', function () {
                let center = map.getCenter();
                searchNearBy(center.lat, center.lng);
            });
            // 地图移动结束事件
            map.on('moveend', function () {
                if (isSelMove) {
                    isSelMove = false;
                } else {
                    $('#ew-map-select-tips').addClass('layui-hide');
                    $('#ew-map-select-center-img').removeClass('bounceInDown');
                    setTimeout(function () {
                        $('#ew-map-select-center-img').addClass('bounceInDown');
                    });
                    let center = map.getCenter();
                    searchNearBy(center.lat, center.lng);
                }
            });
            // poi列表点击事件
            $('#ew-map-select-pois').off('click').on('click', '.ew-map-select-search-list-item', function () {
                $('#ew-map-select-tips').addClass('layui-hide');
                $('#ew-map-select-pois .ew-map-select-search-list-item-icon-ok').addClass('layui-hide');
                $(this).find('.ew-map-select-search-list-item-icon-ok').removeClass('layui-hide');
                $('#ew-map-select-center-img').removeClass('bounceInDown');
                setTimeout(function () {
                    $('#ew-map-select-center-img').addClass('bounceInDown');
                });
                let lng = $(this).data('lng');
                let lat = $(this).data('lat');
                let name = $(this).find('.ew-map-select-search-list-item-title').text();
                let address = $(this).find('.ew-map-select-search-list-item-address').text();
                selLocation = {name: name, address: address, lat: lat, lng: lng};
                isSelMove = true;
                map.setZoomAndCenter(pointZoom, [lng, lat]);
            });
            // 确定按钮点击事件
            $('#ew-map-select-btn-ok').click(function () {
                if (selLocation === undefined) {
                    layer.msg('请点击位置列表选择', {icon: 2, anim: 6});
                } else if (onSelect) {
                    if (needCity) {
                        let loadIndex = layer.load(2);
                        map.setCenter([selLocation.lng, selLocation.lat]);
                        map.getCity(function (result) {
                            layer.close(loadIndex);
                            selLocation.city = result;
                            admin.closeDialog('#ew-map-select-btn-ok');
                            onSelect(selLocation);
                        });
                    } else {
                        admin.closeDialog('#ew-map-select-btn-ok');
                        onSelect(selLocation);
                    }
                } else {
                    admin.closeDialog('#ew-map-select-btn-ok');
                }
            });
            // 搜索提示
            let $inputSearch = $('#ew-map-select-input-search');
            $inputSearch.off('input').on('input', function () {
                let keywords = $(this).val();
                let $selectTips = $('#ew-map-select-tips');
                if (!keywords) {
                    $selectTips.html('');
                    $selectTips.addClass('layui-hide');
                }
                AMap.plugin('AMap.Autocomplete', function () {
                    let autoComplete = new AMap.Autocomplete({city: '全国'});
                    autoComplete.search(keywords, function (status, result) {
                        if (result.tips) {
                            let tips = result.tips;
                            let htmlList = '';
                            for (let i = 0; i < tips.length; i++) {
                                let tipItem = tips[i];
                                if (tipItem.location !== undefined) {
                                    htmlList += '<div data-lng="' + tipItem.location.lng + '" data-lat="' + tipItem.location.lat + '" class="ew-map-select-search-list-item">';
                                    htmlList += '     <div class="ew-map-select-search-list-item-icon-search"><i class="layui-icon layui-icon-search"></i></div>';
                                    htmlList += '     <div class="ew-map-select-search-list-item-title">' + tipItem.name + '</div>';
                                    htmlList += '     <div class="ew-map-select-search-list-item-address">' + tipItem.address + '</div>';
                                    htmlList += '</div>';
                                }
                            }
                            $selectTips.html(htmlList);
                            if (tips.length === 0) $('#ew-map-select-tips').addClass('layui-hide');
                            else $('#ew-map-select-tips').removeClass('layui-hide');
                        } else {
                            $selectTips.html('');
                            $selectTips.addClass('layui-hide');
                        }
                    });
                });
            });
            $inputSearch.off('blur').on('blur', function () {
                let keywords = $(this).val();
                let $selectTips = $('#ew-map-select-tips');
                if (!keywords) {
                    $selectTips.html('');
                    $selectTips.addClass('layui-hide');
                }
            });
            $inputSearch.off('focus').on('focus', function () {
                let keywords = $(this).val();
                if (keywords) $('#ew-map-select-tips').removeClass('layui-hide');
            });
            // tips列表点击事件
            $('#ew-map-select-tips').off('click').on('click', '.ew-map-select-search-list-item', function () {
                $('#ew-map-select-tips').addClass('layui-hide');
                let lng = $(this).data('lng');
                let lat = $(this).data('lat');
                selLocation = undefined;
                map.setZoomAndCenter(pointZoom, [lng, lat]);
            });
        };
        // 显示弹窗
        let htmlStr = [
            '<div class="ew-map-select-tool" style="position: relative;">',
            '     搜索：<input id="ew-map-select-input-search" class="layui-input icon-search inline-block" style="width: 190px;" placeholder="输入关键字搜索" autocomplete="off" />',
            '     <button id="ew-map-select-btn-ok" class="layui-btn icon-btn pull-right" type="button"><i class="layui-icon">&#xe605;</i>确定</button>',
            '     <div id="ew-map-select-tips" class="ew-map-select-search-list layui-hide">',
            '     </div>',
            '</div>',
            '<div class="layui-row ew-map-select">',
            '     <div class="layui-col-sm7 ew-map-select-map-group" style="position: relative;">',
            '          <div id="ew-map-select-map"></div>',
            '          <i id="ew-map-select-center-img2" class="layui-icon layui-icon-add-1"></i>',
            '          <img id="ew-map-select-center-img" src="https://3gimg.qq.com/lightmap/components/locationPicker2/image/marker.png" alt=""/>',
            '     </div>',
            '     <div id="ew-map-select-pois" class="layui-col-sm5 ew-map-select-search-list">',
            '     </div>',
            '</div>'].join('');
        admin.open({
            id: 'ew-map-select', type: 1, title: dialogTitle, area: '750px', content: htmlStr,
            success: function (layero, dIndex) {
                let $content = $(layero).children('.layui-layer-content');
                $content.css('overflow', 'visible');
                admin.showLoading($content);
                if (undefined === window.AMap) {
                    $.getScript(mapJsUrl, function () {
                        renderMap();
                        admin.removeLoading($content);
                    });
                } else {
                    renderMap();
                    admin.removeLoading($content);
                }
            }
        });
    };

    /** 裁剪图片 */
    admin.cropImg = function (param) {
        let uploadedImageType = 'image/jpeg';  // 当前图片的类型
        let aspectRatio = param.aspectRatio;  // 裁剪比例
        let imgSrc = param.imgSrc;  // 裁剪图片
        let imgType = param.imgType;  // 图片类型
        let onCrop = param.onCrop;  // 裁剪完成回调
        let limitSize = param.limitSize;  // 限制选择的图片大小
        let acceptMime = param.acceptMime;  // 限制选择的图片类型
        let imgExts = param.exts;  // 限制选择的图片类型
        let dialogTitle = param.title;  // 弹窗的标题
        if (aspectRatio === undefined) aspectRatio = 1;
        if (dialogTitle === undefined) dialogTitle = '裁剪图片';
        if (imgType) uploadedImageType = imgType;
        layui.use(['Cropper', 'upload'], function () {
            let Cropper = layui.Cropper, upload = layui.upload;

            // 渲染组件
            function renderElem() {
                let imgCropper, $cropImg = $('#ew-crop-img');
                // 上传文件按钮绑定事件
                let uploadOptions = {
                    elem: '#ew-crop-img-upload', auto: false, drag: false,
                    choose: function (obj) {
                        obj.preview(function (index, file, result) {
                            uploadedImageType = file.type;
                            $cropImg.attr('src', result);
                            if (!imgSrc || !imgCropper) {
                                imgSrc = result;
                                renderElem();
                            } else {
                                imgCropper.destroy();
                                imgCropper = new Cropper($cropImg[0], options);
                            }
                        });
                    }
                };
                if (limitSize !== undefined) uploadOptions.size = limitSize;
                if (acceptMime !== undefined) uploadOptions.acceptMime = acceptMime;
                if (imgExts !== undefined) uploadOptions.exts = imgExts;
                upload.render(uploadOptions);
                // 没有传图片触发上传图片
                if (!imgSrc) return $('#ew-crop-img-upload').trigger('click');
                // 渲染裁剪组件
                let options = {aspectRatio: aspectRatio, preview: '#ew-crop-img-preview'};
                imgCropper = new Cropper($cropImg[0], options);
                // 操作按钮绑定事件
                $('.ew-crop-tool').on('click', '[data-method]', function () {
                    let data = $(this).data(), cropped, result;
                    if (!imgCropper || !data.method) return;
                    data = $.extend({}, data);
                    cropped = imgCropper.cropped;
                    switch (data.method) {
                        case 'rotate':
                            if (cropped && options.viewMode > 0) imgCropper.clear();
                            break;
                        case 'getCroppedCanvas':
                            if (uploadedImageType === 'image/jpeg') {
                                if (!data.option) data.option = {};
                                data.option.fillColor = '#fff';
                            }
                            break;
                    }
                    result = imgCropper[data.method](data.option, data.secondOption);
                    switch (data.method) {
                        case 'rotate':
                            if (cropped && options.viewMode > 0) imgCropper.crop();
                            break;
                        case 'scaleX':
                        case 'scaleY':
                            $(this).data('option', -data.option);
                            break;
                        case 'getCroppedCanvas':
                            if (result) {
                                onCrop && onCrop(result.toDataURL(uploadedImageType));
                                admin.closeDialog('#ew-crop-img');
                            } else {
                                layer.msg('裁剪失败', {icon: 2, anim: 6});
                            }
                            break;
                    }
                });
            }

            // 显示弹窗
            let htmlStr = [
                '<div class="layui-row">',
                '     <div class="layui-col-sm8" style="min-height: 9rem;">',
                '          <img id="ew-crop-img" src="', imgSrc || '', '" style="max-width:100%;" alt=""/>',
                '     </div>',
                '     <div class="layui-col-sm4 layui-hide-xs" style="padding: 15px;text-align: center;">',
                '          <div id="ew-crop-img-preview" style="width: 100%;height: 9rem;overflow: hidden;display: inline-block;border: 1px solid #dddddd;"></div>',
                '     </div>',
                '</div>',
                '<div class="text-center ew-crop-tool" style="padding: 15px 10px 5px 0;">',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;margin-left: 10px;">',
                '          <button title="放大" data-method="zoom" data-option="0.1" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-add-1"></i></button>',
                '          <button title="缩小" data-method="zoom" data-option="-0.1" class="layui-btn icon-btn" type="button"><span style="display: inline-block;width: 12px;height: 2.5px;background: rgba(255, 255, 255, 0.9);vertical-align: middle;margin: 0 4px;"></span></button>',
                '     </div>',
                '     <div class="layui-btn-group layui-hide-xs" style="margin-bottom: 10px;">',
                '          <button title="向左旋转" data-method="rotate" data-option="-45" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh-1" style="transform: rotateY(180deg) rotate(40deg);display: inline-block;"></i></button>',
                '          <button title="向右旋转" data-method="rotate" data-option="45" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh-1" style="transform: rotate(30deg);display: inline-block;"></i></button>',
                '     </div>',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;">',
                '          <button title="左移" data-method="move" data-option="-10" data-second-option="0" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-left"></i></button>',
                '          <button title="右移" data-method="move" data-option="10" data-second-option="0" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-right"></i></button>',
                '          <button title="上移" data-method="move" data-option="0" data-second-option="-10" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-up"></i></button>',
                '          <button title="下移" data-method="move" data-option="0" data-second-option="10" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-down"></i></button>',
                '     </div>',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;">',
                '          <button title="左右翻转" data-method="scaleX" data-option="-1" class="layui-btn icon-btn" type="button" style="position: relative;width: 41px;"><i class="layui-icon layui-icon-triangle-r" style="position: absolute;left: 9px;top: 0;transform: rotateY(180deg);font-size: 16px;"></i><i class="layui-icon layui-icon-triangle-r" style="position: absolute; right: 3px; top: 0;font-size: 16px;"></i></button>',
                '          <button title="上下翻转" data-method="scaleY" data-option="-1" class="layui-btn icon-btn" type="button" style="position: relative;width: 41px;"><i class="layui-icon layui-icon-triangle-d" style="position: absolute;left: 11px;top: 6px;transform: rotateX(180deg);line-height: normal;font-size: 16px;"></i><i class="layui-icon layui-icon-triangle-d" style="position: absolute; left: 11px; top: 14px;line-height: normal;font-size: 16px;"></i></button>',
                '     </div>',
                '     <div class="layui-btn-group" style="margin-bottom: 10px;">',
                '          <button title="重新开始" data-method="reset" class="layui-btn icon-btn" type="button"><i class="layui-icon layui-icon-refresh"></i></button>',
                '          <button title="选择图片" id="ew-crop-img-upload" class="layui-btn icon-btn" type="button" style="border-radius: 0 2px 2px 0;"><i class="layui-icon layui-icon-upload-drag"></i></button>',
                '     </div>',
                '     <button data-method="getCroppedCanvas" data-option="{ &quot;maxWidth&quot;: 4096, &quot;maxHeight&quot;: 4096 }" class="layui-btn icon-btn" type="button" style="margin-left: 10px;margin-bottom: 10px;"><i class="layui-icon">&#xe605;</i>完成</button>',
                '</div>'].join('');
            admin.open({
                title: dialogTitle, area: '665px', type: 1, content: htmlStr,
                success: function (layero, dIndex) {
                    $(layero).children('.layui-layer-content').css('overflow', 'visible');
                    renderElem();
                }
            });
        });
    };

    /** 工具类 */
    admin.util = {
        /* 百度地图坐标转高德地图坐标 */
        Convert_BD09_To_GCJ02: function (point) {
            let x_pi = (3.14159265358979324 * 3000.0) / 180.0;
            let x = point.lng - 0.0065, y = point.lat - 0.006;
            let z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
            let theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
            return {lng: z * Math.cos(theta), lat: z * Math.sin(theta)};
        },
        /* 高德地图坐标转百度地图坐标 */
        Convert_GCJ02_To_BD09: function (point) {
            let x_pi = (3.14159265358979324 * 3000.0) / 180.0;
            let x = point.lng, y = point.lat;
            let z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
            let theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
            return {lng: z * Math.cos(theta) + 0.0065, lat: z * Math.sin(theta) + 0.006};
        },
        /* 动态数字 */
        animateNum: function (elem, isThd, delay, grain) {
            isThd = isThd === null || isThd === undefined || isThd === true || isThd === 'true';  // 是否是千分位
            delay = isNaN(delay) ? 500 : delay;   // 动画延迟
            grain = isNaN(grain) ? 100 : grain;   // 动画粒度
            let getPref = function (str) {
                let pref = '';
                for (let i = 0; i < str.length; i++) if (!isNaN(str.charAt(i))) return pref; else pref += str.charAt(i);
            }, getSuf = function (str) {
                let suf = '';
                for (let i = str.length - 1; i >= 0; i--) if (!isNaN(str.charAt(i))) return suf; else suf = str.charAt(i) + suf;
            }, toThd = function (num, isThd) {
                if (!isThd) return num;
                if (!/^[0-9]+.?[0-9]*$/.test(num)) return num;
                num = num.toString();
                return num.replace(num.indexOf('.') > 0 ? /(\d)(?=(\d{3})+(?:\.))/g : /(\d)(?=(\d{3})+(?:$))/g, '$1,');
            };
            $(elem).each(function () {
                let $this = $(this);
                let num = $this.data('num');
                if (!num) {
                    num = $this.text().replace(/,/g, '');  // 内容
                    $this.data('num', num);
                }
                let flag = 'INPUT,TEXTAREA'.indexOf($this.get(0).tagName) >= 0;  // 是否是输入框
                let pref = getPref(num.toString()), suf = getSuf(num.toString());
                let strNum = num.toString().replace(pref, '').replace(suf, '');
                if (isNaN(strNum * 1) || strNum === '0') {
                    flag ? $this.val(num) : $this.html(num);
                    return console.error('not a number');
                }
                let int_dec = strNum.split('.');
                let deciLen = int_dec[1] ? int_dec[1].length : 0;
                let startNum = 0.0, endNum = strNum;
                if (Math.abs(endNum * 1) > 10) startNum = parseFloat(int_dec[0].substring(0, int_dec[0].length - 1) + (int_dec[1] ? '.0' + int_dec[1] : ''));
                let oft = (endNum - startNum) / grain, temp = 0;
                let mTime = setInterval(function () {
                    let str = pref + toThd(startNum.toFixed(deciLen), isThd) + suf;
                    flag ? $this.val(str) : $this.html(str);
                    startNum += oft;
                    temp++;
                    if (Math.abs(startNum) >= Math.abs(endNum * 1) || temp > 5000) {
                        str = pref + toThd(endNum, isThd) + suf;
                        flag ? $this.val(str) : $this.html(str);
                        clearInterval(mTime);
                    }
                }, delay / grain);
            });
        },
        /* 深度克隆对象 */
        deepClone: function (obj) {
            let result;
            let oClass = admin.util.isClass(obj);
            if (oClass === 'Object') result = {};
            else if (oClass === 'Array') result = [];
            else return obj;
            for (let key in obj) {
                if (!obj.hasOwnProperty(key)) continue;
                let copy = obj[key], cClass = admin.util.isClass(copy);
                if (cClass === 'Object') result[key] = arguments.callee(copy); // 递归调用
                else if (cClass === 'Array') result[key] = arguments.callee(copy);
                else result[key] = obj[key];
            }
            return result;
        },
        /* 获取变量类型 */
        isClass: function (o) {
            if (o === null) return 'Null';
            if (o === undefined) return 'Undefined';
            return Object.prototype.toString.call(o).slice(8, -1);
        },
        /* 判断富文本是否为空 */
        fullTextIsEmpty: function (text) {
            if (!text) return true;
            let noTexts = ['img', 'audio', 'video', 'iframe', 'object'];
            for (let i = 0; i < noTexts.length; i++) {
                if (text.indexOf('<' + noTexts[i]) > -1) return false;
            }
            let str = text.replace(/\s*/g, '');  // 去掉所有空格
            if (!str) return true;
            str = str.replace(/&nbsp;/ig, '');  // 去掉所有&nbsp;
            if (!str) return true;
            str = str.replace(/<[^>]+>/g, '');   // 去掉所有html标签
            return !str;
        },
        /* 移除元素的style */
        removeStyle: function (elem, names) {
            if (typeof names === 'string') names = [names];
            for (let i = 0; i < names.length; i++) $(elem).css(names[i], '');
        },
        /* 滚动到顶部 */
        scrollTop: function (elem) {
            if (!elem) {
                let $layBody = $('.layui-layout-admin>.layui-body');
                elem = $layBody.children('.layui-tab').children('.layui-tab-content').children('.layui-tab-item.layui-show');
                if (elem.length === 0) {
                    elem = $layBody.children('.layui-body-header.show+div');
                    if (elem.length === 0) elem = $layBody;
                }
            } else {
                elem = $(elem);
            }
            elem.animate({scrollTop: 0}, 300);
        },
        /* 模板解析 */
        tpl: function (html, data, openCode, closeCode) {
            if (html === undefined || html === null || typeof html !== 'string') return html;
            if (!data) data = {};
            if (!openCode) openCode = '{{';
            if (!closeCode) closeCode = '}}';
            let tool = {
                exp: function (str) {
                    return new RegExp(str, 'g');
                },
                // 匹配满足规则内容
                query: function (type, _, __) {
                    let types = ['#([\\s\\S])+?', '([^{#}])*?'][type || 0];
                    return tool.exp((_ || '') + openCode + types + closeCode + (__ || ''));
                },
                escape: function (str) {
                    return String(str || '').replace(/&(?!#?[a-zA-Z0-9]+;)/g, '&amp;')
                        .replace(/</g, '&lt;').replace(/>/g, '&gt;')
                        .replace(/'/g, '&#39;').replace(/"/g, '&quot;');
                },
                error: function (e, tplog) {
                    console.error('Laytpl Error：' + e + '\n' + (tplog || ''));
                },
                parse: function (tpl, data) {
                    let tplog = tpl;
                    try {
                        let jss = tool.exp('^' + openCode + '#'), jsse = tool.exp(closeCode + '$');
                        tpl = tpl.replace(tool.exp(openCode + '#'), openCode + '# ')
                            .replace(tool.exp(closeCode + '}'), '} ' + closeCode).replace(/\\/g, '\\\\')
                            // 不匹配指定区域的内容
                            .replace(tool.exp(openCode + '!(.+?)!' + closeCode), function (str) {
                                str = str.replace(tool.exp('^' + openCode + '!'), '')
                                    .replace(tool.exp('!' + closeCode), '')
                                    .replace(tool.exp(openCode + '|' + closeCode), function (tag) {
                                        return tag.replace(/(.)/g, '\\$1')
                                    });
                                return str
                            })
                            // 匹配JS规则内容
                            .replace(/(?="|')/g, '\\').replace(tool.query(), function (str) {
                                str = str.replace(jss, '').replace(jsse, '');
                                return '";' + str.replace(/\\/g, '') + ';view+="';
                            })
                            // 匹配普通字段
                            .replace(tool.query(1), function (str) {
                                let start = '"+(';
                                if (str.replace(/\s/g, '') === openCode + closeCode) return '';
                                str = str.replace(tool.exp(openCode + '|' + closeCode), '');
                                if (/^=/.test(str)) {
                                    str = str.replace(/^=/, '');
                                    start = '"+_escape_(';
                                }
                                return start + str.replace(/\\/g, '') + ')+"';
                            })
                            // 换行符处理
                            .replace(/\r\n/g, '\\r\\n" + "').replace(/\n/g, '\\n" + "').replace(/\r/g, '\\r" + "');
                        tpl = '"use strict";let view = "' + tpl + '";return view;';
                        tpl = new Function('d, _escape_', tpl);
                        return tpl(data, tool.escape);
                    } catch (e) {
                        tool.error(e, tplog);
                        return tplog;
                    }
                }
            };
            return tool.parse(html, data);
        },
        /* 渲染动态模板 */
        render: function (option) {
            if (typeof option.url === 'string') {
                option.success = function (res) {
                    admin.util.render($.extend({}, option, {url: res}));
                };
                if (option.ajax === 'ajax') admin.ajax(option);
                else admin.req(option.url, option.where, option.success, option.method, option);
                return;
            }
            let html = admin.util.tpl(option.tpl, option.url,
                option.open || setter.tplOpen, option.close || setter.tplClose);
            $(option.elem).next('[ew-tpl-rs]').remove();
            $(option.elem).after(html);
            $(option.elem).next().attr('ew-tpl-rs', '');
            option.done && option.done(option.url);
        }
    };

    /** 锁屏功能 */
    admin.lockScreen = function (url) {
        if (!url) url = 'components/tpl/lock-screen.html';
        let $lock = $('#ew-lock-screen-group');
        if ($lock.length > 0) {
            $lock.fadeIn('fast');
            admin.isLockScreen = true;
            admin.putTempData('isLockScreen', admin.isLockScreen, true);
        } else {
            let loadIndex = layer.load(2);
            admin.ajax({
                url: url, dataType: 'html',
                success: function (res) {
                    layer.close(loadIndex);
                    if (typeof res === 'string') {
                        $('body').append('<div id="ew-lock-screen-group">' + res + '</div>');
                        admin.isLockScreen = true;
                        admin.putTempData('isLockScreen', admin.isLockScreen, true);
                        admin.putTempData('lockScreenUrl', url, true);
                    } else {
                        console.error(res);
                        layer.msg(JSON.stringify(res), {icon: 2, anim: 6});
                    }
                }
            });
        }
    };

    /** 解除锁屏 */
    admin.unlockScreen = function (isRemove) {
        let $lock = $('#ew-lock-screen-group');
        isRemove ? $lock.remove() : $lock.fadeOut('fast');
        admin.isLockScreen = false;
        admin.putTempData('isLockScreen', null, true);
    };

    /** tips方法封装 */
    admin.tips = function (option) {
        return layer.tips(option.text, option.elem, {
            tips: [option.direction || 1, option.bg || '#191a23'],
            tipsMore: option.tipsMore, time: option.time || -1,
            success: function (layero) {
                let $content = $(layero).children('.layui-layer-content');
                if (option.padding || option.padding === 0) $content.css('padding', option.padding);
                if (option.color) $content.css('color', option.color);
                if (option.bgImg) $content.css('background-image', option.bgImg).children('.layui-layer-TipsG').css('z-index', '-1');
                if (option.fontSize) $content.css('font-size', option.fontSize);
                if (!option.offset) return;
                let offset = option.offset.split(',');
                let top = offset[0], left = offset.length > 1 ? offset[1] : undefined;
                if (top) $(layero).css('margin-top', top);
                if (left) $(layero).css('margin-left', left);
            }
        });
    };

    /** 渲染动态模板 */
    admin.renderTpl = function (elem) {
        if (!layui.admin) layui.admin = admin;

        // 解析数据
        function parseData(data) {
            if (!data) return;
            try {
                return new Function('return ' + data + ';')();
            } catch (e) {
                console.error(e + '\nlay-data: ' + data);
            }
        }

        $(elem || '[ew-tpl]').each(function () {
            let $this = $(this);
            let option = $(this).data();
            option.elem = $this;
            option.tpl = $this.html();
            option.url = parseData($this.attr('ew-tpl'));
            option.headers = parseData(option.headers);
            option.where = parseData(option.where);
            if (option.done) {
                try {
                    option.done = new Function('res', option.done);
                } catch (e) {
                    console.error(e + '\nlay-data:' + option.done);
                    option.done = undefined;
                }
            }
            admin.util.render(option);
        });
    };

    /** 事件监听 */
    admin.on = function (events, callback) {
        return layui.onevent.call(this, 'admin', events, callback);
    };

    /** 修改配置信息 */
    admin.putSetting = function (key, value) {
        setter[key] = value;
        admin.putTempData(key, value, true);
    };

    /** 恢复配置信息 */
    admin.recoverState = function () {
        // 恢复锁屏状态
        if (admin.getTempData('isLockScreen', true)) admin.lockScreen(admin.getTempData('lockScreenUrl', true));
        // 恢复配置的主题
        if (setter.defaultTheme) admin.changeTheme(setter.defaultTheme, window, true, true);
        // 恢复页脚状态、导航箭头
        if (setter.closeFooter) $('body').addClass('close-footer');
        if (setter.navArrow !== undefined) {
            let $nav = $(sideDOM + '>.layui-nav-tree');
            $nav.removeClass('arrow2 arrow3');
            if (setter.navArrow) $nav.addClass(setter.navArrow);
        }
        // 恢复tab自动刷新
        if (setter.pageTabs && setter.tabAutoRefresh == 'true') $(tabDOM).attr('lay-autoRefresh', 'true');
    };

    /* 事件监听 */
    admin.on = function (events, callback) {
        return layui.onevent.call(this, 'admin', events, callback);
    };

    /** 侧导航折叠状态下鼠标经过无限悬浮效果 */
    let navItemDOM = '.layui-layout-admin.admin-nav-mini>.layui-side .layui-nav .layui-nav-item';
    $(document).on('mouseenter', navItemDOM + ',' + navItemDOM + ' .layui-nav-child>dd', function () {
        if (admin.getPageWidth() > 768) {
            let $that = $(this), $navChild = $that.find('>.layui-nav-child');
            if ($navChild.length > 0) {
                $that.addClass('admin-nav-hover');
                $navChild.css('left', $that.offset().left + $that.outerWidth());
                let top = $that.offset().top;
                if (top + $navChild.outerHeight() > admin.getPageHeight()) {
                    top = top - $navChild.outerHeight() + $that.outerHeight();
                    if (top < 60) top = 60;
                    $navChild.addClass('show-top');
                }
                $navChild.css('top', top);
                $navChild.addClass('ew-anim-drop-in');
            } else if ($that.hasClass('layui-nav-item')) {
                admin.tips({elem: $that, text: $that.find('cite').text(), direction: 2, offset: '12px'});
            }
        }
    }).on('mouseleave', navItemDOM + ',' + navItemDOM + ' .layui-nav-child>dd', function () {
        layer.closeAll('tips');
        let $this = $(this);
        $this.removeClass('admin-nav-hover');
        let $child = $this.find('>.layui-nav-child');
        $child.removeClass('show-top ew-anim-drop-in');
        $child.css({'left': 'auto', 'top': 'auto'});
    });

    /** 所有ew-event */
    $(document).on('click', '*[ew-event]', function () {
        let te = admin.events[$(this).attr('ew-event')];
        te && te.call(this, $(this));
    });

    /** 所有lay-tips处理 */
    $(document).on('mouseenter', '*[lay-tips]', function () {
        let $this = $(this);
        admin.tips({
            elem: $this, text: $this.attr('lay-tips'), direction: $this.attr('lay-direction'),
            bg: $this.attr('lay-bg'), offset: $this.attr('lay-offset'),
            padding: $this.attr('lay-padding'), color: $this.attr('lay-color'),
            bgImg: $this.attr('lay-bgImg'), fontSize: $this.attr('lay-fontSize')
        });
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.closeAll('tips');
    });

    /** 表单搜索展开更多 */
    $(document).on('click', '.form-search-expand,[search-expand]', function () {
        let $this = $(this);
        let $form = $this.parents('.layui-form').first();
        let expand = $this.data('expand');
        let change = $this.attr('search-expand');
        if (expand === undefined || expand === true) {
            expand = true;
            $this.data('expand', false);
            $this.html('收起 <i class="layui-icon layui-icon-up"></i>');
            let $elem = $form.find('.form-search-show-expand');
            $elem.attr('expand-show', '');
            $elem.removeClass('form-search-show-expand');
        } else {
            expand = false;
            $this.data('expand', true);
            $this.html('展开 <i class="layui-icon layui-icon-down"></i>');
            $form.find('[expand-show]').addClass('form-search-show-expand');
        }
        if (!change) return;
        new Function('d', change)({expand: expand, elem: $this});
    });

    /** select使用fixed定位显示 */
    $(document).on('click.ew-sel-fixed', '.ew-select-fixed .layui-form-select .layui-select-title', function () {
        let $this = $(this), $dl = $this.parent().children('dl'), tTop = $this.offset().top;
        let tWidth = $this.outerWidth(), tHeight = $this.outerHeight(), scrollT = $(document).scrollTop();
        let dWidth = $dl.outerWidth(), dHeight = $dl.outerHeight();
        let top = tTop + tHeight + 5 - scrollT, left = $this.offset().left;
        if (top + dHeight > admin.getPageHeight()) top = top - dHeight - tHeight - 10;
        if (left + dWidth > admin.getPageWidth()) left = left - dWidth + tWidth;
        $dl.css({'left': left, 'top': top, 'min-width': tWidth});
    });

    /** 用于滚动时关闭一些fixed的组件 */
    admin.hideFixedEl = function () {
        $('.ew-select-fixed .layui-form-select').removeClass('layui-form-selected layui-form-selectup');  // select
        $('body>.layui-laydate').remove();  // laydate
    };

    /** 垂直导航栏展开折叠增加过渡效果 */
    $(document).on('click', '.layui-nav-tree>.layui-nav-item a', function () {
        let $this = $(this), $child = $this.siblings('.layui-nav-child'), $parent = $this.parent();
        if ($child.length === 0) return;
        if ($parent.hasClass('admin-nav-hover')) return;
        if ($parent.hasClass('layui-nav-itemed')) {  // 因为layui会处理一遍所以这里状态是相反的
            $child.css('display', 'none').slideDown('fast', function () {
                $(this).css('display', '');
            });
        } else {
            $child.css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
        }
        if ($this.parents('.layui-nav').attr('lay-shrink') === '_all') {  // 手风琴效果
            let $siblings = $this.parent().siblings('.layui-nav-itemed');
            $siblings.children('.layui-nav-child').css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
            $siblings.removeClass('layui-nav-itemed');
        }
    });
    $('.layui-nav-tree[lay-shrink="all"]').attr('lay-shrink', '_all');  // 让layui不处理手风琴效果

    /** 折叠面板展开折叠增加过渡效果 */
    $(document).on('click', '.layui-collapse>.layui-colla-item>.layui-colla-title', function () {
        let $this = $(this), $content = $this.siblings('.layui-colla-content')
            , $collapse = $this.parent().parent(), isNone = $content.hasClass('layui-show');
        if (isNone) {  // 因为layui会处理一遍所以这里状态是相反的
            $content.removeClass('layui-show').slideDown('fast').addClass('layui-show');
        } else {
            $content.css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
        }
        $this.children('.layui-colla-icon').html('&#xe602;')
            .css({'transition': 'all .3s', 'transform': 'rotate(' + (isNone ? '90deg' : '0deg') + ')'});
        if ($collapse.attr('lay-shrink') === '_all') {  // 手风琴效果
            let $show = $collapse.children('.layui-colla-item').children('.layui-colla-content.layui-show').not($content);
            $show.css('display', 'block').slideUp('fast', function () {
                $(this).css('display', '');
            });
            $show.removeClass('layui-show');
            $show.siblings('.layui-colla-title').children('.layui-colla-icon').html('&#xe602;')
                .css({'transition': 'all .3s', 'transform': 'rotate(0deg)'});
        }
    });
    $('.layui-collapse[lay-accordion]').attr('lay-shrink', '_all').removeAttr('lay-accordion');  // 让layui不处理手风琴效果

    /** 表单验证tips提示样式修改 */
    layer.oldTips = layer.tips;
    layer.tips = function (content, follow, options) {
        let $fFip;  // 判断是否是表单验证调用的tips
        if ($(follow).length > 0 && $(follow).parents('.layui-form').length > 0) {
            if ($(follow).is('input') || $(follow).is('textarea')) {
                $fFip = $(follow);
            } else if ($(follow).hasClass('layui-form-select') || $(follow).hasClass('layui-form-radio')
                || $(follow).hasClass('layui-form-checkbox') || $(follow).hasClass('layui-form-switch')) {
                $fFip = $(follow).prev();
            }
        }
        if (!$fFip) return layer.oldTips(content, follow, options);
        options.tips = [$fFip.attr('lay-direction') || 3, $fFip.attr('lay-bg') || '#ff4c4c'];
        setTimeout(function () {
            options.success = function (layero) {
                $(layero).children('.layui-layer-content').css('padding', '6px 12px');
            };
            layer.oldTips(content, follow, options);
        }, 100);
    };

    /** 读取缓存的配置信息 */
    let cache = admin.getTempData(true);
    if (cache) {
        let keys = ['pageTabs', 'cacheTab', 'defaultTheme', 'navArrow', 'closeFooter', 'tabAutoRefresh'];
        for (let i = 0; i < keys.length; i++) if (cache[keys[i]] !== undefined) setter[keys[i]] = cache[keys[i]];
    }

    /** 选中行同时选中复选框 */
    admin.selectRowCheckBox = function (obj, selectTable) {
        let flag = 0;

        let checkDiv = obj.tr.find('.layui-form-checkbox');

        if(checkDiv.hasClass('layui-form-checked')) {
            checkDiv.removeClass('layui-form-checked');
            flag = 0;
        } else {
            checkDiv.addClass('layui-form-checked');
            flag = 1;
        }

        layui.each(table.cache[selectTable], function (i, l) {
            if (obj.tr.index() == l.LAY_TABLE_INDEX) {
                l.LAY_CHECKED = flag;
            }
        });

        // 全选
        let headerCheckDiv = obj.tr.parent().parent().parent().prev().find('tr').find('.layui-form-checkbox');
        let headerCheck = 1;
        layui.each(table.cache[selectTable], function (i, l) {
            if (!l.LAY_CHECKED) {
                headerCheck = 0;
                return;
            }
        });

        if(headerCheck) {
            headerCheckDiv.addClass('layui-form-checked');
        } else {
            headerCheckDiv.removeClass('layui-form-checked');
        }

        admin.clickRow(obj);
    };

    /** 选中行同时选中单选框 */
    admin.selectRowRedio = function (obj) {
        // 选中 radio 样式
        obj.tr.find('i[class="layui-anim layui-icon"]').trigger("click");

        admin.clickRow(obj);
    };

    /**
     * 选中行
     * @param obj
     */
    admin.clickRow = function(obj) {
        // 选中行样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    }

    /**
     * 获取选中的行
     * @param layId
     * @returns {jQuery}
     */
    admin.getSelectRow = function(layId) {
        return $('[lay-id="' + layId + '"]').find('.layui-table-click');
    }

    /** 获取url指定参数 */
    admin.getUrlParam = function(name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        let location = window.location.hash;
        let result = null;
        $.each(location.split('/'), function (index, item) {
            if(item) {
                let r = item.match(reg);
                if (r != null) {
                    result = unescape(r[2]);
                    return;
                }
            }
        });
        return result;
    }

    /**
     * 表格单元格tooltip
     * @param layId 表格ID
     * @param data
     * @param field 字段名
     * @param tip 数据中哪个字段显示为tip
     */
    admin.tableTooltip = function(layId, data, field, tip) {
        let tooltip = null;
        $.each(data, function (i, item) {
            let index= item['LAY_TABLE_INDEX'];
            $('[lay-id="' + layId + '"] tr[data-index=' + index + '] td[data-field="' + field + '"]').hover(
                function() {
                    tooltip = layer.tips(
                        data[index][tip],
                        this,
                        {tips : 1}
                    );
                }, function() {
                    layer.close(tooltip);
                });
        });
    }

    /**
     * 隐藏表格头
     * @param layId
     * @param delTitleHt 是否去除标题高度
     */
    admin.hideTableHeader = function(layId, delTitleHt) {
        $('[lay-id="' + layId + '"]').find('.layui-table-header').hide();
        if(delTitleHt) {
            let height = $('[lay-id="' + layId + '"]').height();
            $('[lay-id="' + layId + '"]').find('.layui-table-body').height(height + 'px');
        }
    }

    /**
     * 模拟form表单的提交
     * @param url url 跳转地址
     * @param target 在何处打开链接文档
     * 如需打开新窗口，form的target属性要设置为'_blank'
     * 如需打开本窗口，form的target属性要设置为'_self'
     * @param method 方式
     * @param params 参数
     */
    admin.openForm = function(url, target, method, obj) {
        // 创建form表单
        let tempForm = document.createElement("form");
        tempForm.action = url;
        // 如需打开新窗口，form的target属性要设置为'_blank'
        // 如需打开本窗口，form的target属性要设置为'_self'
        tempForm.target = target;
        tempForm.method = method;
        tempForm.style.display = "none";
        // 添加参数
        for (let key in obj) {
            let opt = document.createElement("input");
            opt.name = key;
            opt.value = obj[key];
            tempForm.appendChild(opt);
        }

        document.body.appendChild(tempForm);
        // 提交数据
        tempForm.submit();
    }

    /**
     * 设置token
     * @param token
     */
    admin.putToken = function(token) {
        setter.putToken(token);
    }

    /**
     * 获取token
     * @returns {*}
     */
    admin.getToken = function() {
        return setter.getToken();
    }

    /**
     * 设置登陆信息
     * @param user
     */
    admin.putUser = function(user) {
        setter.putUser(user);
    }

    /**
     * 获取登陆用户信息
     */
    admin.getUser = function() {
        return setter.getUser();
    }

    /**
     * 获取当前所属项目
     * @param isMsg 未获取到项目信息是否提示
     * @returns {*}
     */
    admin.getProj = function(isMsg) {
        let projId = admin.getUser().projId;

        if(!projId) {
            if(isMsg) {
                layer.msg('未获取到项目信息！！！', {icon: 5});
            } else {
                console.error('未获取到项目信息！！！');
            }
        }

        return projId;
    }

    /**
     * 临时存储区域ID
     * @param areaId
     */
    admin.putArea = function(areaId) {
        admin.putTempData('area', areaId);
    }

    /**
     * 获取临时区域ID
     */
    admin.getArea = function(isMsg) {
        let areaId = admin.getTempData('area');
        if(!areaId) {
            if(isMsg) {
                layer.msg('未获取到区域信息！！！', {icon: 5});
            } else {
                console.error('未获取到区域信息！！！');
            }
        }

        return areaId;
    }

    admin.getHeaders = function() {
        return setter.getHeaders();
    }

    admin.getBaseServer = function() {
        return setter.baseServer;
    }

    admin.loginType = {
        SYS_ADMIN: 'SYS_ADMIN',
        IOT_CORP: 'IOT_CORP',
        IOT_PROJ: 'IOT_PROJ',
        IOT_USR_WEB: 'IOT_USR_WEB',
        IOT_AREA: 'IOT_AREA'
    }

    admin.recoverState();  // 恢复本地配置
    admin.renderTpl();  // 渲染动态模板
    exports('admin', admin);
});
