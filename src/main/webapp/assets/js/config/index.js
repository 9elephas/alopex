/**
 * Created by roamer on 2017/4/27.
 */
var config_items_table;
var editor;

$().ready(
    function () {
        editor = new $.fn.dataTable.Editor({
            ajax: contextPath + "/config/getAllForDatatableContent",
            table: "#config_items_table",
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
                    button: "Modifier",
                    title: "Modifier entrée",
                    submit: "更新"
                }
            }
        });
        config_items_table = $("#config_items_table").DataTable(
            {
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
                select: {
                    style: 'os',
                    selector: 'td:first-child'
                }
            });

        // Activate the bubble editor on click of a table cell
        $('#config_items_table').on('click', 'tbody td:not(:first-child)', function (e) {
            editor.bubble(this);
        });
    }
)
