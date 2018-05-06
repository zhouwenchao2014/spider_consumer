$(function(){
    $.ajax({
        url:"/spiderProjectList",
        success:function(data){
            if(data!=null&&data!=undefined){
                var obj = eval('(' + data + ')');
                var length = obj.length;
                for(var i = 0;i<length;i++){
                    $("table tbody").append("<tr><td>"+obj[i].id+"</td><td>"+obj[i].name+"</td><td>"+obj[i].displayName+"</td><td>"+obj[i].rootUrl+"</td><td><code>"+obj[i].sourceConfig+"</code></td><td><code>"+obj[i].spiderConfig+"</code></td><td>"+obj[i].createdBy+"</td><td>"+obj[i].modifiedBy+"</td><td>"+obj[i].createdTime+"</td><td>"+obj[i].modifiedTime+"</td><td>"+obj[i].used+"</td></tr>")
                }

            }

        }
    });
});