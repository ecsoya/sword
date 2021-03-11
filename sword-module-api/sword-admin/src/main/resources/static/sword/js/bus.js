
function uploadImg(obj){
    var formData = new FormData();
    var files = obj.files[0];
    formData.append("code", $("#rid").val());
    formData.append("file", files);
    formData.append("type", "img");
    $.ajax({
        url: ctx + 'common/uploadCloud',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false, 
        dataType: 'json',
        success:function (res) {
        	$($(obj).data("id")).val(res.url);
        	$($(obj).data("id") + "-preview").attr('src', res.url);
        },error:function (res) {
        	console.log(res)
        }
    });
}