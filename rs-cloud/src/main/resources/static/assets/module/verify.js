/**
 * 图形验证码
 */
layui.define(['jquery'], function (exports) {
    let $ = layui.jquery;
    let verify = {
        options: { // 默认options参数值
            id: "", // 容器Id
            size: 4,
            canvasId: "verifyCanvas", // canvas的ID
            width: "100", // 默认canvas宽度
            height: "30", // 默认canvas高度
            type: "letter", // 图形验证码默认类型blend:数字字母混合类型、number:纯数字、letter:纯字母
            code: "",
            numArr: getAllNumber(),
            letterArr: getAllLetter(),
            /**版本号**/
            version: '1.0.0',
        },
        /** 初始化方法 **/
        render: function (options) {
            if (Object.prototype.toString.call(options) == "[object Object]") {// 判断传入参数类型
                for (let i in options) { // 根据传入的参数，修改默认参数值
                    this.options[i] = options[i];
                }
            } else {
                this.options.id = options;
            }

            let con = document.getElementById(this.options.id);
            let canvas = document.createElement("canvas");
            this.options.width = con.offsetWidth > 0 ? con.offsetWidth : "100";
            this.options.height = con.offsetHeight > 0 ? con.offsetHeight : "30";
            canvas.id = this.options.canvasId;
            canvas.width = this.options.width;
            canvas.height = this.options.height;
            canvas.style.cursor = "pointer";
            canvas.innerHTML = "您的浏览器版本不支持canvas";
            con.appendChild(canvas);

            verify.refresh();

            let parent = this;
            canvas.onclick = function () {
                parent.refresh();
            }

            return this;
        },
        /** 生成验证码 **/
        refresh: function () {
            let ctx;
            this.options.code = "";
            let canvas = document.getElementById(this.options.canvasId);
            if (canvas.getContext) {
                ctx = canvas.getContext('2d');
            } else {
                return;
            }

            ctx.textBaseline = "middle";

            ctx.fillStyle = randomColor(180, 240);
            ctx.fillRect(0, 0, this.options.width, this.options.height);

            let txtArr = this.options.letterArr;
            if (this.options.type == "blend") { // 判断验证码类型
                txtArr = this.options.numArr.concat(this.options.letterArr);
            } else if (this.options.type == "number") {
                txtArr = this.options.numArr;
            }

            let letterArr = this.options.letterArr;
            let numArr = this.options.numArr;
            let codeArr = new Array();

            // 数字字母组合
            if (this.options.type == "blend") {
                for (let i = 1; i <= Math.floor(this.options.size / 2); i++) {
                    codeArr.push(numArr[randomNum(0, numArr.length)]);
                }

                for (let i = 1; i <= Math.ceil(this.options.size / 2); i++) {
                    codeArr.push(letterArr[randomNum(0, letterArr.length)]);
                }
            }
            // 纯数字
            else if (this.options.type == "number") {
                for (let i = 1; i <= this.options.size; i++) {
                    codeArr.push(numArr[randomNum(0, numArr.length)]);
                }
            }
            // 纯字母
            else if (this.options.type == "letter") {
                for (let i = 1; i <= this.options.size; i++) {
                    codeArr.push(letterArr[randomNum(0, letterArr.length)]);
                }
            }

            // 顺序打乱
            codeArr = shuffle(codeArr);

            for (let i = 1; i <= codeArr.length; i++) {
                let txt = codeArr[i - 1];
                this.options.code += txt;
                ctx.font = randomNum(this.options.height / 2, this.options.height) + 'px SimHei'; // 随机生成字体大小
                ctx.fillStyle = randomColor(50, 160); // 随机生成字体颜色
                ctx.shadowOffsetX = randomNum(-3, 3);
                ctx.shadowOffsetY = randomNum(-3, 3);
                ctx.shadowBlur = randomNum(-3, 3);
                ctx.shadowColor = "rgba(0, 0, 0, 0.3)";
                let x = this.options.width / (this.options.size + 1) * i;
                let y = this.options.height / 2;
                let deg = randomNum(-30, 30);
                /**设置旋转角度和坐标原点**/
                ctx.translate(x, y);
                ctx.rotate(deg * Math.PI / 180);
                ctx.fillText(txt, 0, 0);
                /**恢复旋转角度和坐标原点**/
                ctx.rotate(-deg * Math.PI / 180);
                ctx.translate(-x, -y);
            }
            /**绘制干扰线**/
            // for (let i = 0; i < 4; i++) {
            //     ctx.strokeStyle = randomColor(40, 180);
            //     ctx.beginPath();
            //     ctx.moveTo(randomNum(0, this.options.width), randomNum(0, this.options.height));
            //     ctx.lineTo(randomNum(0, this.options.width), randomNum(0, this.options.height));
            //     ctx.stroke();
            // }
            /**绘制干扰点**/
            // for (let i = 0; i < this.options.width / 4; i++) {
            //     ctx.fillStyle = randomColor(0, 255);
            //     ctx.beginPath();
            //     ctx.arc(randomNum(0, this.options.width), randomNum(0, this.options.height), 1, 0, 2 * Math.PI);
            //     ctx.fill();
            // }
        },
        /**验证验证码**/
        validate: function (code) {
            code = code.toLowerCase();
            let v_code = this.options.code.toLowerCase();
            if (code == v_code) {
                return true;
            } else {
                this.refresh();
                return false;
            }
        }
    };

    /**生成字母数组**/
    function getAllLetter() {
        let letterStr = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        return letterStr.split(",");
    }

    /**生成数字数组**/
    function getAllNumber() {
        let numStr = "0,1,2,3,4,5,6,7,8,9";
        return numStr.split(",");
    }

    /**生成一个随机数**/
    function randomNum(min, max) {
        return Math.floor(Math.random() * (max - min) + min);
    }

    /** 将数组随机打乱 **/
    function shuffle(arr) {
        let len = arr.length;
        for (let i = 0; i < len; i++) {
            let end = len - 1;
            let index = (Math.random() * (end + 1)) >> 0;
            let t = arr[end];
            arr[end] = arr[index];
            arr[index] = t;
        }
        return arr;
    }

    /**生成一个随机色**/
    function randomColor(min, max) {
        let r = randomNum(min, max);
        let g = randomNum(min, max);
        let b = randomNum(min, max);
        return "rgb(" + r + "," + g + "," + b + ")";
    }

    exports("verify", verify);
});
