$(document).ready(function () {

    $.ajax({
        'url': 'http://localhost:8080/user/name',
        'type': "GET",
        'contentType': 'application/json',
        // 'async': false,
        'dataType': 'json',
        'success': function (data) {
            $('#name-place').prepend('<a href="/user/logout"><i class="Logout-btn fas fa-sign-out-alt fa-lg"><span>Logout</span></i></a>\n' +
                '            <a href="/user"><i class="User-Name fas fa-user fa-lg">'+data+'</i></a>');
            // console.log(data);
        },
        'error': function (error) {
            console.log(JSON.parse(error.responseText).message);
        }
    });
});