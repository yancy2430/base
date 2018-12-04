<title>后台管理员</title>
<div class="layui-card layadmin-header">
    <div class="layui-breadcrumb" lay-filter="breadcrumb">
        <a lay-href="">主页</a>
        <a><cite>用户</cite></a>
        <a><cite>后台管理员</cite></a>
    </div>
</div>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto" lay-filter="layadmin-useradmin-formlist">
            <div class="layui-form-item">
                <#list attrs as attr>
                    <#if attr.find==true>
                        <#if attr.kvs??>
                        <div class="layui-inline">
                            <label class="layui-form-label">${attr.remarks}</label>
                            <div class="layui-input-block">
                                <select name="${attr.propertiesName}">
                                <#list attr.kvs as kv>
                                    <option value="${kv.k}">${kv.t}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <#else>
                        <div class="layui-inline">
                            <label class="layui-form-label">${attr.remarks}</label>
                            <div class="layui-input-block">
                                    <#if attr.like==true>
                                        <input type="text" name="${attr.propertiesName}" data-like="1" placeholder="请输入"
                                               autocomplete="off"
                                               class="layui-input">
                                    <#else>
                                        <input type="text" name="${attr.propertiesName}" data-like="0" placeholder="请输入"
                                               autocomplete="off"
                                               class="layui-input">
                                    </#if>
                            </div>
                        </div>
                        </#if>
                    </#if>
                </#list>
                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="LAY-user-back-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="layui-card-body">
            <div style="padding-bottom: 10px;">
                <button class="layui-btn layuiadmin-btn-admin" data-type="batchdel">删除</button>
                <button class="layui-btn layuiadmin-btn-admin" data-type="add">添加</button>
            </div>

            <table id="LAY-user-back-manage" lay-filter="LAY-user-back-manage"></table>

            <#list attrs as attr>
                <#if attr.kvs??>
                    <script type="text/html" id="buttonTpl">
                            <#list attr.kvs as kv>
                                    {{#  if(d.${attr.propertiesName} == ${kv.k}){ }}
                                            <button class="layui-btn layui-btn-xs">${kv.t}</button>
                                    {{#  } }}
                            </#list>

                    </script>
                </#if>
            </#list>

            <script type="text/html" id="table-useradmin-admin">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                {{#  if(d.role == '超级管理员'){ }}
                <a class="layui-btn layui-btn-disabled layui-btn-xs"><i class="layui-icon layui-icon-delete"></i>删除</a>
                {{#  } else { }}
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
                {{#  } }}
            </script>
        </div>
    </div>
</div>

<script>
    layui.use(['admin', 'table', 'view', 'form'], function () {
        var $ = layui.$
                , admin = layui.admin
                , view = layui.view
                , table = layui.table
                , form = layui.form;
        form.render(null, 'layadmin-useradmin-formlist');
        //监听搜索
        form.on('submit(LAY-user-back-search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('LAY-user-back-manage', {
                where: field
            });
        });

        //管理员管理
        table.render({
            elem: '#LAY-user-back-manage'
            , url: '/web/${className}/list' //模拟接口
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                <#list attrs as attr>
                    <#if attr.kvs??>
                    ,{field: '${attr.propertiesName}', title: '${attr.remarks}', templet: '#${attr.propertiesName}Tpl', align: 'center'}
                    <#else>
                    ,{field: '${attr.propertiesName}',  title: '${attr.remarks}'}
                    </#if>
                </#list>
                , {title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
            ]]
            , text: '对不起，加载出现异常！'
        });
        //事件
        var active = {
            batchdel: function () {
                var checkStatus = table.checkStatus('LAY-user-back-manage')
                        , checkData = checkStatus.data; //得到选中的数据

                if (checkData.length === 0) {
                    return layer.msg('请选择数据');
                }

                layer.prompt({
                    formType: 1
                    , title: '敏感操作，请验证口令'
                }, function (value, index) {
                    layer.close(index);

                    layer.confirm('确定删除吗？', function (index) {

                        //执行 Ajax 后重载
                        /*
                        admin.req({
                          url: 'xxx'
                          //,……
                        });
                        */
                        table.reload('LAY-user-back-manage');
                        layer.msg('已删除');
                    });
                });
            }
            , add: function () {
                admin.popup({
                    title: '添加管理员'
                    , area: ['420px', '450px']
                    , id: 'LAY-popup-useradmin-add'
                    , success: function (layero, index) {
                        view(this.id).render('member/user/add').done(function () {
                            form.render(null, 'layuiadmin-form-admin');

                            //监听提交
                            form.on('submit(LAY-user-back-submit)', function (data) {
                                var field = data.field; //获取提交的字段

                                //提交 Ajax 成功后，关闭当前弹层并重载表格
                                //$.ajax({});
                                layui.table.reload('LAY-user-back-manage'); //重载表格
                                layer.close(index); //执行关闭
                            });
                        });
                    }
                });
            }
        }
        $('.layui-btn.layuiadmin-btn-admin').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>