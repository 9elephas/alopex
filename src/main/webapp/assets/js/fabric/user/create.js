/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */
$().ready(
    function () {
        $("#registerButton").click(function () {
            //提交后台
            $.ajax({
                type: "POST",
                url: contextPath + "/rest/dispatcher/invoke.json",
                contentType: 'application/json',
                data: $("#userInfoJsonStringInput").val(),
                success: function (data, textStatus, jqXHR) {
                    $("#result").val(jqXHR.responseJSON.result);
                    Messenger().post({
                        message: '注册成功',
                        type: 'success',
                        showCloseButton: true
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    Messenger().post({
                        message: jqXHR.responseText,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            })
        });
    });

