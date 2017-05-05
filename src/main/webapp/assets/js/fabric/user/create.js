/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */
$().ready(
    function () {
        $("#registerButton").click(function () {
            console.debug(JSON.parse($("#userInfoJsonStringInput").val()));
            //提交后台
            $.ajax({
                type: "POST",
                url: contextPath + "/fabric/user/rest/register",
                data: JSON.parse($("#userInfoJsonStringInput").val()),
                complete: function (jqXHR ,  textStatus ) {
                    console.debug("调用完成!");
                    console.debug(jqXHR);
                    console.debug(textStatus);
                }
            })
        });
    });

