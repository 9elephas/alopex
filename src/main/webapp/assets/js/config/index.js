/**
 * Created by roamer on 2017/4/27.
 */
var config_items_table;
var editor;

$().ready(
    function () {
        editor = new $.fn.dataTable.Editor({
            ajax: "/config/getAllForDatatableContent",
            table: "#config_items_table",
            display: 'bootstrap',
            idSrc: "id",
            fields: [{
                label: "属性 key",
                name: "key"
            }, {
                label: "属性值:",
                name: "value"
            }],
            i18n: {
                edit: {
                    button: "修改",
                    title: "修改配置项",
                    submit: "更新"
                },
                create:{
                    button: "增加",
                    title: "增加配置项",
                    submit: "更新"
                },
                remove:{
                    button: "删除",
                    title: "删除配置项",
                    submit: "确认"
                }
            }
        });
        config_items_table = $("#config_items_table").DataTable(
            {
                dom: "Bfrtip",
                "processing": true,
                // "serverSide": true,
                "ajax": {
                    url: contextPath + "/config/getAllForDatatableContent",
                    type: 'get'
                },
                "language": {
                    "url": contextPath + "/assets/js/lib//DataTables-1.10.15/chinese.lang.json"
                },
                "columns": [
                    {"data": "key"},
                    {"data": "value"}
                ],
                "columnDefs": [{
                    orderable: false,
                    targets: [0, 1]
                }],
                select: true,
                buttons: [
                    { extend: "create", editor: editor },
                    { extend: "edit",   editor: editor },
                    { extend: "remove", editor: editor }
                ]
            });

        // // Activate the bubble editor on click of a table cell
        // $('#config_items_table').on('click', 'tbody td:not(:first-child)', function (e) {
        //     editor.bubble(this,{
        //         submit: 'allIfChanged'
        //     });
        // });
    }
)
