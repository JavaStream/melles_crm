$(document).ready(function () {
    $("#search_2").keyup(function () {
        _this = this;
        $.each($("#user option"), function () {
            if ($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    });
});
