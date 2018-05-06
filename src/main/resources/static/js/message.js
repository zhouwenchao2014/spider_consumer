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
            pageIndex:params.offset,
            siteName:$("siteName_search").val(),
            title:$("title_search").val(),
            sources:$("sources_search").val(),
            url:$("url_search").val()
        }
    }
    $('#mytable').bootstrapTable({
        url:"/pageList",//要请求数据的文件路径
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
        rowStyle: function (row, index) {
          var sources = row.sources;
          var style = "";
          if(sources=="[]"){
            style='danger';
          }
          return { classes: style }
        },
        columns:[
            {
                title:'ID',
                field:'ID',
                width:"5%",
                visible:false
            },
            {
                title:'标题',
                field:'title',
                width:"15%",
                sortable:true
            },
            {
                title:'内容',
                field:'content',
                width:"15%",
                sortable:true
            },
            {
                title:'url',
                width:"10%",
                field:'url',
                formatter:operateFormatter
            },
            {
                title:'项目标识',
                width:"10%",
                field:'siteName'
            },
            {
                title:'创建时间',
                field:'createTime',
                width:"10%",
                sortable:true
            },
            {
                title:'图片',
                field:'pic',
                width:"20%",
                align:'center',
                //列数据格式化
                formatter:operateFormatter
            },
            {
                title:'资源',
                field:'sources',
                width:"25%",
                align:'center',
                //列数据格式化
                formatter:operateFormatter
            }
        ],
        locale:'zh-CN',//中文支持,
        responseHandler:function(res){
        //在ajax获取到数据，渲染表格之前，修改数据源
            return res;
        }
    });

    function operateFormatter(value,row,index){
        if(value==undefined){
            return "";
        }
      var length = value.length;
      var val = value.substr(0,40);
      var title = row.title;
      if(length>=40){
          return '<span class="showSources">'+val+'</span><span style="display:none" class="source">'+value+'</span><span style="display:none" class="title">'+title+'</span>'
      }else{
          return value;
      }
    }

    $("html").on("click",".showSources",function(origin){
      var value = $(".source",$(origin.currentTarget.parentElement))[0].textContent;
      var title = $(".title",$(origin.currentTarget.parentElement))[0].textContent;
      var val=eval("(" + value + ")");
      $("#sourceView .modal-body").empty();
      var length = val.length;
      for(var i=0;i<length;i++){
        $("#sourceView .modal-body").append("<a href='"+val[i]+"'>"+title+"</a><tr/>");
      }
      $("#sourceView").modal('show')
    });
});
