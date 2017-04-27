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
                    "data": "key"
                }, {
                    "data": "value"
                }],
                "columnDefs": [{
                    orderable: false,
                    targets: [0,1]
                }]
            });
    }
)
