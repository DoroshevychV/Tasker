$(document).ready(function () {
    $('#logout-btn').click(function () {

        $.ajax({
            'url': 'http://localhost:8080/login?logout',
            'type': "POST",
            'contentType': 'application/json',
            'dataType': 'json',
            'success': function (data) {

            },
            'error': function (error) {

            }
        });
    });
});
