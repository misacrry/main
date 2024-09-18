/**
 * 表单扩展模块
 * date:2020-05-04   License By http://easyweb.vip
 */
layui.define(['form'], function (exports) {
    let $ = layui.jquery;
    let form = layui.form;
    let verifyText = {
        phoneX: '请输入正确的手机号',
        emailX: '邮箱格式不正确',
        urlX: '链接格式不正确',
        numberX: '只能填写数字',
        dateX: '日期格式不正确',
        identityX: '请输入正确的身份证号',
        psw: '密码必须5到12位，且不能出现空格',
        equalTo: '两次输入不一致',
        digits: '只能输入整数',
        digitsP: '只能输入正整数',
        digitsN: '只能输入负整数',
        digitsPZ: '只能输入正整数和0',
        digitsNZ: '只能输入负整数和0',
        minlength: '最少输入{minlength}个字符',
        maxlength: '最多输入{maxlength}个字符',
        min: '值不能小于{min}',
        max: '值不能大于{max}',
        fileName: '文件名不能以数字开头，不能有特殊字符'
    };

    /** 扩展验证规则 */
    let verifyList = {
        /* 手机号 */
        phoneX: function (value, item) {
            let reg = /^1\d{10}$/;
            if (value && !reg.test(value)) {
                return verifyText.phoneX;
            }
        },
        /* 邮箱 */
        emailX: function (value, item) {
            let reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if (value && !reg.test(value)) {
                return verifyText.emailX;
            }
        },
        /* 网址 */
        urlX: function (value, item) {
            let reg = /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/;
            if (value && !reg.test(value)) {
                return verifyText.urlX;
            }
        },
        /* 数字 */
        numberX: function (value, item) {
            if (value && isNaN(value)) {
                return verifyText.numberX;
            }
        },
        /* 日期 */
        dateX: function (value, item) {
            let reg = /^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/;
            if (value && !reg.test(value)) {
                return verifyText.dateX;
            }
        },
        /* 身份证 */
        identityX: function (value, item) {
            let reg = /(^\d{15}$)|(^\d{17}(x|X|\d)$)/;
            if (value && !reg.test(value)) {
                return verifyText.identityX;
            }
        },
        /* 密码 */
        psw: function (value, item) {
            if (value && !/^[\S]{5,12}$/.test(value)) {
                return verifyText.psw
            }
        },
        /* 重复 */
        equalTo: function (value, item) {
            if (value != $($(item).attr('lay-equalTo')).val()) {
                let text = $(item).attr('lay-equalToText');
                return text ? text : verifyText.equalTo;
            }
        },
        /* 整数 */
        digits: function (value, item) {
            let reg = /^-?\d+$/;
            if (value && !reg.test(value)) {
                return verifyText.digits;
            }
        },
        /* 正整数 */
        digitsP: function (value, item) {
            let reg = /^[1-9]\d*$/;
            if (value && !reg.test(value)) {
                return verifyText.digitsP;
            }
        },
        /* 负整数 */
        digitsN: function (value, item) {
            let reg = /^-[1-9]\d*$/;
            if (value && !reg.test(value)) {
                return verifyText.digitsN;
            }
        },
        /* 非负整数 */
        digitsPZ: function (value, item) {
            let reg = /^\d+$/;
            if (value && !reg.test(value)) {
                return verifyText.digitsPZ;
            }
        },
        /* 非正整数 */
        digitsNZ: function (value, item) {
            let reg = /^-[1-9]\d*|0/;
            if (value && !reg.test(value)) {
                return verifyText.digitsNZ;
            }
        },
        /* h5 */
        h5: function (value, item) {
            if (value) {
                let minlength = $(item).attr('minlength');
                let maxlength = $(item).attr('maxlength');
                let min = $(item).attr('min');
                let max = $(item).attr('max');
                if (minlength && value.length < minlength) {
                    return verifyText.minlength.replace(/{minlength}/g, minlength);
                }
                if (maxlength && value.length > maxlength) {
                    return verifyText.maxlength.replace(/{maxlength}/g, maxlength);
                }
                if (min && value * 1 < min * 1) {
                    return verifyText.min.replace(/{min}/g, min);
                }
                if (max && value * 1 > max * 1) {
                    return verifyText.max.replace(/{max}/g, max);
                }
            }
        },
        fileName: function (value, item) {
            let reg = /^[^0-9][\u4E00-\u9FA5A-Za-z0-9_]+$/;
            if (value && !reg.test(value)) {
                return verifyText.fileName;
            }
        }
    };

    let formX = {
        init: function () {
            form.verify(verifyList);
        },
        /* 赋值表单，解决top.layui.form.val无效的问题 */
        formVal: function (filter, object) {
            formX.val(filter, object);
        },
        /* 赋值表单，解决top.layui.form.val无效的问题 */
        val: function (filter, object) {
            $('.layui-form[lay-filter="' + filter + '"]').each(function () {
                let $item = $(this);
                for (let f in object) {
                    if (!object.hasOwnProperty(f)) continue;
                    let $elem = $item.find('[name="' + f + '"]');
                    if ($elem.length > 0) {
                        let type = $elem[0].type;
                        if (type === 'checkbox') {  // 如果为复选框
                            $elem[0].checked = object[f];
                        } else if (type === 'radio') { // 如果为单选框
                            $elem.each(function () {
                                if (this.value == object[f]) {
                                    this.checked = true;
                                }
                            });
                        } else { // 其它类型的表单
                            $elem.val(object[f]);
                        }
                    }
                }
            });
            form.render(null, filter);
        },
        /* 渲染select */
        renderSelect: function (param) {
            let defaultOption = {
                elem: undefined,
                data: [],
                name: undefined,
                value: undefined,
                hint: '请选择',
                initValue: undefined,
                method: 'get',
                where: undefined,
                headers: undefined,
                async: true,
                done: undefined,
                error: undefined
            };
            param = $.extend(defaultOption, param);
            if (typeof param.data === 'string') {
                $.ajax({
                    url: param.data,
                    type: param.method,
                    data: param.where,
                    dataType: 'json',
                    headers: param.header || param.headers,
                    async: param.async,
                    success: function (result, status, xhr) {
                        if (result.data) {
                            param.data = result.data;
                            formX.renderSelect(param);
                        } else {
                            param.error && param.error(xhr, result);
                        }
                    },
                    error: param.error
                });
            } else {
                let html = param.hint ? ('<option value="">' + param.hint + '</option>') : '';
                for (let i = 0; i < param.data.length; i++) {
                    if (param.name && param.value) {
                        html += ('<option value="' + param.data[i][param.value] + '"' + (param.data[i][param.value] == param.initValue ? ' selected' : '') + '>' + param.data[i][param.name] + '</option>');
                    } else {
                        html += ('<option value="' + param.data[i] + '"' + (param.data[i] == param.initValue ? ' selected' : '') + '>' + param.data[i] + '</option>');
                    }
                }
                $(param.elem).html(html);
                let $form = $(param.elem).parent('.layui-form');
                if ($form.length === 0) {
                    $form = $(param.elem).parentsUntil('.layui-form').last().parent();
                }
                form.render('select', $form.attr('lay-filter'));
                param.done && param.done(param.data);
            }
        },
        /* 验证码倒计时 */
        startTimer: function (elem, time, format) {
            if (!time) time = 60;
            if (!format) {
                format = function (t) {
                    return t + 's';
                }
            }
            if (formX.timers[elem]) clearInterval(formX.timers[elem]);
            let orgHtml = $(elem).html();
            $(elem).html(format(time));
            $(elem).prop('disabled', true);
            $(elem).addClass('layui-btn-disabled');
            let timer = setInterval(function () {
                time--;
                if (time <= 0) {
                    clearInterval(timer);
                    $(elem).html(orgHtml);
                    $(elem).removeProp('disabled');
                    $(elem).removeClass('layui-btn-disabled');
                } else {
                    $(elem).html(format(time));
                }
            }, 1000);
            formX.timers[elem] = timer;
        },
        timers: {},
        /* 获取表单修改过的数据 */
        formUpdatedField: function (field, oldField) {
            if (typeof field == 'string') field = form.val(field);
            for (let key in field) {
                if (!field.hasOwnProperty(key)) continue;
                if (field.hasOwnProperty(key) && oldField.hasOwnProperty(key)) {
                    if(looseEqual(oldField[key], field[key])) {
                        delete field[key];
                    }
                }
            }
            if (Object.keys(field).length > 0) return field;
        }
    };

    function looseEqual(a, b) {
        if (a === b) { return true }

        let isObject = function (obj) {
            return obj !== null && typeof obj === 'object'
        };

        let isObjectA = isObject(a),
            isObjectB = isObject(b);

        if (isObjectA || isObjectB) {
            try {
                let isArrayA = Array.isArray(a),
                    isArrayB = Array.isArray(b);
                if (isArrayA && isArrayB) {
                    return a.length === b.length && a.every(function (e, i) {
                        return looseEqual(e, b[i])
                    })
                } else if (!isArrayA && !isArrayB) {
                    let keysA = Object.keys(a),
                        keysB = Object.keys(b);
                    return keysA.length === keysB.length && keysA.every(function (key) {
                        return looseEqual(a[key], b[key])
                    })
                }  else if (isArrayA && !isArrayB) {
                    let keysA = Object.keys(a),
                        keysB = Object.keys(b);
                    return keysA.length === keysB.length && keysA.every(function (key) {
                        return looseEqual(a[key], b[key])
                    })
                } else {
                    return false
                }
            } catch (e) {
                return false
            }
        } else if (!isObjectA && !isObjectB) {
            if (typeof a === 'number' || typeof b === 'number') {
                return Number(a) === Number(b)
            }
            else if (typeof a === 'boolean' || typeof b === 'boolean') {
                return Boolean(Number(a)) === Boolean(Number(b))
            }
            else {
                return String(a) === String(b)
            }

        } else {
            return false;
        }
    }

    formX.init();
    exports('formX', formX);
});
