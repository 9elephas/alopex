/**
 * Created by roamer on 2017/4/27.
 */
var config_items_table;

$().ready(
    function () {
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
                "columns": [{
                    "data": "id"
                }, {
                    "data": "key"
                }, {
                    "data": "value"
                }],
                "columnDefs": [{
                    orderable: false,
                    targets: [0]
                }, {
                    "render": function (data, type, row) {
                        return '<a href="javascript:void(0)" onClick="fun_edit_property_for_modal(\'' + contextPath + "/properties/"
                            + row.id + '/edit\')">' + data + "</a>"
                    },
                    "targets": [1]
                }]
            });
    }
)
