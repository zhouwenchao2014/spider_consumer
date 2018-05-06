$(function(){
    setInterval(function(){
        $.ajax({
            url:"statusMessage",
            success:function(data){
                if(data.success){
                    var length = data.keys.length;
                    for(var i=0;i<length;i++){
                        var key = data.keys[i];
                        var onePre = data.onePre[i]*100;
                        var twoPre = data.twoPre[i]*100;
                        $("div[role='progressbarpage']").css("width",onePre+"%");
                        $("div[role='progressbarpage']").empty()
                        $("div[role='progressbarpage']").append("爬取进度"+onePre+"%")
                        $("div[role='progressbar']").css("width",twoPre+"%");
                        $("div[role='progressbar']").empty()
                        $("div[role='progressbar']").append("保存进度"+twoPre+"%")
                    }
                }
            }
        })
    },1000);
});