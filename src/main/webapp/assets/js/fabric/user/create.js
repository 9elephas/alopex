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
                // data: test,
                complete: function (jqXHR, textStatus) {
                    console.debug("调用完成!");
                    console.debug(jqXHR.responseJSON);
                    console.debug(textStatus);
                    $("#result").val(jqXHR.responseJSON.response);
                }
            })
        });
    });

