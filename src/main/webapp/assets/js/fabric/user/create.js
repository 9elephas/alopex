/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */
$().ready(
    function () {
        $("#registerButton").click(function () {
            var userJsonObj = {"userName": $("#user").val(), "password": $("#passwd").val()};
            var data = JSON.stringify(userJsonObj);

            //提交后台
            $.ajax({
                type: "POST",
                url: contextPath + "/fabric/user/rest/register",
                data: {userName: $("#user").val(), userPasswd: $("#passwd").val()},
                complete: function (jqXHR ,  textStatus ) {
                    console.debug("调用完成!");
                    console.debug(jqXHR);
                    console.debug(textStatus);
                }
            })
        });
    });

