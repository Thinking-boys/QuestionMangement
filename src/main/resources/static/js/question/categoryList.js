/**
 * 问题列表
 */
var pageCurr;
$(function () {
    layui.use('table', function () {
        var table = layui.table
            , form = layui.form;

        tableIns = table.render({
            elem: '#questionList'
            , url: '/category/getCategorys'
            , method: 'post' //默认：get请求
            , cellMinWidth: 80
            , page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                , limitName: 'limit'//每页数据量的参数名，默认：limit
            }, response: {
                statusName: 'code' //数据状态的字段名称，默认：code
                , statusCode: 200 //成功的状态码，默认：0
                , countName: 'totals' //数据总数的字段名称，默认：count
                , dataName: 'list' //数据列表的字段名称，默认：data
            }
            , cols: [[
                {type: 'numbers'}
                , {field: 'id', title: 'ID', width: 80, unresize: true, sort: true}
                , {field: 'categoryName', title: '类别名称'}
                , {field: 'questionCount', title: '问题数量'}
                // , {fixed: 'right', title: '操作', width: 140, align: 'center', toolbar: '#optBar'}
            ]]
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr = curr;
            }
        });
        //监听提交
        form.on('submit(userSubmit)', function (data) {
            // TODO 校验
            formSubmit(data);
            return false;
        });

    });

    //搜索框
    layui.use(['form', 'laydate'], function () {
        var form = layui.form, layer = layui.layer
            , laydate = layui.laydate;
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function (data) {
            //重新加载table
            load(data);
            return false;
        });
    });

    //操作
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(permSubmit)', function(data){
            //校验 TODO
            $.ajax({
                type: "POST",
                data: $("#permForm").serialize(),
                url: "/category/addCategory",
                success: function (data) {
                    if (data == "ok") {
                        layer.alert("操作成功",function(){
                            layer.closeAll();
                        });
                    } else {
                        layer.alert(data);
                    }
                },
                error: function (data) {
                    layer.alert("操作请求错误，请您稍后再试");
                }
            });
            return false;
        });
        form.render();
    });
});

//设置问题是否删除
function setisDelete(obj, id, nameVersion, checked) {
//	var version = obj.data.version;
//     var name = nameVersion.substring(0, nameVersion.indexOf("_"));
//     var version = nameVersion.substring(nameVersion.indexOf("_") + 1);
    //console.log("name:"+name);
    //console.log("version:"+version);
    var isDelete = checked ? 1 : 0;
    var userIsDelete = checked ? "删除" : "未删除";
    //是否离职
    layer.confirm('您确定要把问题设置为' + userIsDelete + '状态吗？', {
        btn: ['确认', '返回'] //按钮
    }, function () {
        $.post("/question/setDeleteQuestion", {"id": id, "isDelete": isDelete}, function (data) {
            if (isLogin(data)) {
                if (data == "ok") {
                    //回调弹框
                    layer.alert("操作成功！", function () {
                        layer.closeAll();
                        //加载load方法
                        load(obj);
                    });
                } else {
                    layer.alert(data, function () {
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            }
        });
    }, function () {
        layer.closeAll();
        //加载load方法
        load(obj);
    });
}

//置顶状态
function setisTop(obj, id, nameVersion, checked) {
//	var version = obj.data.version;
//     var name = nameVersion.substring(0, nameVersion.indexOf("_"));
//     var version = nameVersion.substring(nameVersion.indexOf("_") + 1);
    //console.log("name:"+name);
    //console.log("version:"+version);
    var isTop = checked ? 1 : 0;
    var userisTop = checked ? "置顶" : "未置顶";
    //是否离职
    layer.confirm('您确定要把问题设置为' + userisTop + '状态吗？', {
        btn: ['确认', '返回'] //按钮
    }, function () {
        $.post("/question/setTopQuestion", {"id": id, "isTop": isTop}, function (data) {
            if (isLogin(data)) {
                if (data == "ok") {
                    //回调弹框
                    layer.alert("操作成功！", function () {
                        layer.closeAll();
                        //加载load方法
                        load(obj);
                    });
                } else {
                    layer.alert(data, function () {
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            }
        });
    }, function () {
        layer.closeAll();
        //加载load方法
        load(obj);
    });
}

//提交表单
function formSubmit(obj) {
    var currentUser = $("#currentUser").html();
    if (checkRole()) {
        if ($("#id").val() == currentUser) {
            layer.confirm('更新自己的信息后，需要您重新登录才能生效；您确定要更新么？', {
                btn: ['返回', '确认'] //按钮
            }, function () {
                layer.closeAll();
            }, function () {
                layer.closeAll();//关闭所有弹框
                submitAjax(obj, currentUser);
            });
        } else {
            submitAjax(obj, currentUser);
        }
    }
}

function submitAjax(obj, currentUser) {
    $.ajax({
        type: "POST",
        data: $("#userForm").serialize(),
        url: "/user/setUser",
        success: function (data) {
            if (isLogin(data)) {
                if (data == "ok") {
                    layer.alert("操作成功", function () {
                        if ($("#id").val() == currentUser) {
                            //如果是自己，直接重新登录
                            parent.location.reload();
                        } else {
                            layer.closeAll();
                            cleanUser();
                            //$("#id").val("");
                            //加载页面
                            load(obj);
                        }
                    });
                } else {
                    layer.alert(data, function () {
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试", function () {
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

function cleanUser() {
    $("#username").val("");
    $("#mobile").val("");
    $("#email").val("");
    $("#password").val("");
}

function checkRole() {
    //选中的角色
    var array = new Array();
    var roleCheckd = $(".layui-form-checked");
    //获取选中的权限id
    for (var i = 0; i < roleCheckd.length; i++) {
        array.push($(roleCheckd.get(i)).prev().val());
    }
    //校验是否授权
    var roleIds = array.join(",");
    if (roleIds == null || roleIds == '') {
        layer.alert("请您给该用户添加对应的角色！")
        return false;
    }
    $("#roleIds").val(roleIds);
    return true;
}

function openUser(id, title) {
    if (id == null || id == "") {
        $("#id").val("");
    }
    layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['550px'],
        content: $('#setUser'),
        end: function () {
            cleanUser();
        }
    });
}

//新增类别
function addCategory() {
    layer.open({
        type: 1,
        title: "新增类别",
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['500px', '580px'],
        content: $('#updatePerm'),  //页面自定义的div，样式自定义
        end: function () {
            location.reload();
        }
    });

}

function load(obj) {
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

