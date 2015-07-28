$(document).ready(function() {
    $("#datePicker").datetimepicker({
        sideBySide: true,
        format: "MM-DD-YYYY hh:mma"
    });
    $("#selectTimeZone").chosen();
});