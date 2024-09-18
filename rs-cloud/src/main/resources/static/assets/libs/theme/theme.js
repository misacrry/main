layui.use("layer element form code util colorpicker".split(" "), function() {
    function b() {
        var c = {};
        c.logoColor = d("input[name=logo]").val();
        c.logoTvColor = "white" == getContrastYIQ(c.logoColor) ? "#eee" : "#333";
        c.sideColor = d("input[name=side]").val();
        c.sideTvColor = "white" == getContrastYIQ(c.sideColor) ? "#eee" : "#333";
        c.sideTvColor2 = getColor2(c.sideTvColor);
        c.headerColor = d("input[name=header]").val();
        var b = "white" == getContrastYIQ(c.headerColor);
        c.headerTvColor = b ? "#eee" : "#333";
        c.primaryColor = d("input[name=primary]").val();
        c.headerLineColor = b ? "#eee" : c.primaryColor;
        c.tabLineColor = b ? c.headerColor : c.primaryColor;
        c.tabPostion = b ? "38px" : "0px";
        return c
    }

    function e() {
        var c = b(),
            d = document.getElementById("preCanvas1"),
            a = d.getContext("2d");
        a.clearRect(0, 0, d.width, d.height);
        a.fillStyle = c.logoColor;
        a.beginPath();
        a.fillRect(0, 0, 80, 30);
        a.closePath();
        a.fill();
        a.font = "12px Arial";
        a.fillStyle = c.logoTvColor;
        a.fillText("rs-iot", 16, 18);
        a.fillStyle = c.sideColor;
        a.beginPath();
        a.fillRect(0, 30, 80, 170);
        a.closePath();
        a.fill();
        a.fillStyle = c.sideTvColor;
        a.fillText("side", 30, 100);
        a.fillStyle = c.headerColor;
        a.beginPath();
        a.fillRect(80, 0, 240, 30);
        a.closePath();
        a.fill();
        a.fillStyle = c.headerTvColor;
        a.fillText("header", 270, 18);
        a.fillStyle = c.primaryColor;
        a.beginPath();
        a.fillRect(180, 95, 40, 20);
        a.closePath();
        a.fill();
        a.font = "11px Arial";
        a.fillStyle = "#fff";
        a.fillText("button", 185, 108);
        d = document.getElementById("preCanvas2");
        a = d.getContext("2d");
        a.clearRect(0, 0, d.width, d.height);
        a.fillStyle = c.logoColor;
        a.beginPath();
        a.fillRect(0, 0, 20, 10);
        a.closePath();
        a.fill();
        a.fillStyle = c.sideColor;
        a.beginPath();
        a.fillRect(0, 10, 20, 40);
        a.closePath();
        a.fill();
        a.fillStyle = c.headerColor;
        a.beginPath();
        a.fillRect(20, 0, 60, 10);
        a.closePath();
        a.fill()
    }
    var d = layui.jquery,
        f = layui.layer,
        l = layui.colorpicker;
    d("#tv-jc").text('<link rel="stylesheet" href="assets/libs/layui/layui.css"/>\n\x3c!-- \u5f15\u5165\u4e0b\u8f7d\u7684css --\x3e\n<link rel="stylesheet" href="assets/css/theme.css"/>');
    layui.code({
        about: !1
    });
    var h;
    f.load(2);
    d.get("assets/libs/theme/theme.tpl", function(b) {
        h = b;
        f.closeAll("loading")
    });
    for (var g =
        1; 5 > g; g++) {
        var k = "#colorPicker" + g,
            m = d(k).prev().val();
        l.render({
            elem: k,
            color: m,
            predefine: !0,
            done: function(b) {
                var c = d(this)[0].elem;
                d(c).prev().val(b)
            },
            change: function(b) {
                var c = d(this)[0].elem;
                d(c).prev().val(b);
                e()
            }
        })
    }
    d("#btn-down").click(function() {
        var c = b(),
            d = h,
            a;
        for (a in c) {
            var e = c[a];
            if (!e) return f.msg(a + "\u4e0d\u80fd\u4e3a\u7a7a", {
                icon: 2
            }), !1;
            d = d.replace(new RegExp("{{" + a + "}}", "gm"), e)
        }
        c = new File([d], "theme.css", {
            type: "text/plain;charset=utf-8"
        });
        saveAs(c);
        f.msg("\u751f\u6210\u5b8c\u6210", {
            icon: 1
        })
    });
    e()
});

function getContrastYIQ(b) {
    0 == b.indexOf("#") && (b = b.substr(1, b.length - 1));
    3 == b.length && (b = b[0] + b[0] + b[1] + b[1] + b[2] + b[2]);
    var e = parseInt(b.substr(0, 2), 16),
        d = parseInt(b.substr(2, 2), 16);
    b = parseInt(b.substr(4, 2), 16);
    return 128 <= (299 * e + 587 * d + 114 * b) / 1E3 ? "black" : "white"
}

function getColor2(b) {
    0 == b.indexOf("#") && (b = b.substr(1, b.length - 1));
    3 == b.length && (b = b[0] + b[0] + b[1] + b[1] + b[2] + b[2]);
    var e = parseInt(b.substr(0, 2), 16),
        d = parseInt(b.substr(2, 2), 16);
    b = parseInt(b.substr(4, 2), 16);
    return "rgba(" + e + "," + d + "," + b + ",.7)"
};