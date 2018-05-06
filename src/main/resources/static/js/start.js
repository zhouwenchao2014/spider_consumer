$(function(){
    function tableHeight(){
        //可以根据自己页面情况进行调整
        return $(window).height() -280;
    }
    //请求服务数据时所传参数
    function queryParams(params){
        return{
            //每页多少条数据
            pageSize: params.limit,
            //请求第几页
            pageIndex:params.offset
        }
    }
    $('#mytable').bootstrapTable({
        url:"/spiderProjectList",//要请求数据的文件路径
        height:tableHeight(),//高度调整
        toolbar: '#toolbar',//指定工具栏
        striped: true, //是否显示行间隔色
        // dataField: "res",//bootstrap table 可以前端分页也可以后端分页，这里
        //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
        //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        pageNumber: 1, //初始化加载第一页，默认第一页
        pagination:true,//是否分页
        queryParamsType:'limit',//查询参数组织方式
        queryParams:queryParams,//请求服务器时所传的参数
        sidePagination:'server',//指定服务器端分页
        pageSize:10,//单页记录数
        pageList:[5,10,20,30],//分页步进值
        showRefresh:true,//刷新按钮
        showColumns:true,
        clickToSelect: true,//是否启用点击选中行
        toolbarAlign:'right',//工具栏对齐方式
        buttonsAlign:'right',//按钮对齐方式
        columns:[
            {
                title:'ID',
                field:'id',
                width:"5%"
            },
            {
                title:'项目标识',
                field:'name',
                width:"10%",
                sortable:true
            },
            {
                title:'项目中文名称',
                field:'displayName',
                width:"10%",
                sortable:true
            },
            {
                title:'根地址',
                width:"10%",
                field:'rootUrl'
            },
            {
                title:'线程配置',
                width:"10%",
                field:'sourceConfig',
                formatter:operateFormatter
            },
            {
                title:'爬取配置',
                field:'spiderConfig',
                width:"20%",
                sortable:true,
                formatter:operateFormatter
            },
            {
                title:'创建者',
                field:'createdBy',
                width:"10%",
                align:'center'
            },
            {
                title:'修改者',
                field:'modifiedBy',
                width:"10%",
                align:'center'
            },
            {
                title:'创建时间',
                field:'createdTime',
                width:"10%",
                align:'center'
            },
            {
                title:'修改时间',
                field:'modifiedTime',
                width:"10%",
                align:'center'
            },
            {
                title:'是否爬取',
                field:'used',
                width:"20%",
                align:'center',
                //列数据格式化
                formatter:formaterOpertation
            }
        ],
        locale:'zh-CN',//中文支持,
        responseHandler:function(res){
            //在ajax获取到数据，渲染表格之前，修改数据源
            return res;
        },
        onLoadSuccess:function(value){
            $("html [name='my-checkbox']").bootstrapSwitch({
                onText:"启动",
                offText:"停止",
                onColor:"success",
                offColor:"danger",
                size:"small",
                onSwitchChange:function(event,state){
                    var used=0;
                    if(state==true){
                        $(this).val("1");
                    }else{
                        $(this).val("2");
                        var used=1;
                    }
                    var id=$(event.currentTarget.parentElement.parentElement.parentElement.parentElement.parentElement).find("td").eq(0)[0].textContent;

                    $.ajax({
                        url:"startOrStopWork",
                        data:{
                            used:used,
                            id:id
                        },
                        success:function(data){
                            if(data.success){
                                $(".alert-success").empty();
                                $(".alert-success").append(data.message)
                                $(".alert-success").show();
                                setTimeout(function(){
                                    $(".alert-success").hide();
                                },1000)
                            }else{
                                $(".alert-danger").empty();
                                $(".alert-danger").append(data.message)
                                $(".alert-danger").show();
                                setTimeout(function(){
                                    $(".alert-danger").hide();
                                },1000)
                            }
                        }
                    })
                }
            });
            setTimeout(function(){
                value.rows.forEach(function(data,index){
                    var name = data.name;
                    var used = data.used;
                    $("html #"+name+"_switch").bootstrapSwitch('state',used==0)
                });
            },100);
        }
    });


    function formaterOpertation(value,row,index){
        html='<div class="switch" data-animated="false"><input id="'+row.name+'_switch" name="my-checkbox" type="checkbox" checked /> </div>'
        return html;
    }

    function operateFormatter(value,row,index){
        var length = value.length;
        var val = value.substr(0,40);
        var displayName = row.displayName;
        if(length>=40){
            return '<span class="showSources">'+val+'</span><span style="display:none" class="source">'+value+'</span><span style="display:none" class="title">'+displayName+'</span>'
        }else{
            return value;
        }
    }

    $("html").on("click",".showSources",function(origin){
        var value = $(".source",$(origin.currentTarget.parentElement))[0].textContent;
        var title = $(".title",$(origin.currentTarget.parentElement))[0].textContent;
        $("#sourceView .modal-body").empty();
        $("#sourceView .modal-body").append("<pre>"+value+"</pre>");
        $("#sourceView").modal('show')
    });
});