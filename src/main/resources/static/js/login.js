$(document).ready(function () {
    $('#login-button').click(function () {


        var email = $('#login-email').val();
        var password = $('#login-password').val();

        var person = {
            'email': email,
            'password': password
        };
        $.ajax({
            'url': 'http://localhost:8080/login',
            'type': "POST",
            'contentType': 'application/json',
            'dataType': 'json',
            'data': JSON.stringify(person),
            'headers': {
                // 'A-Token': localStorage.getItem('A-Token'),
                'Content-Type': 'application/json;charset=utf-8'
            },
            'success': function (data) {
                console.log(data.key + " = "+data.value);
                document.cookie = data.key + "=" + data.value+";path=/";
                window.location.replace("/user");
            },
            'error': function (error) {
                $('#login-error-field').empty();
                $('#login-error-field').text(JSON.parse(error.responseText).message);
                console.log(JSON.parse(error.responseText).message);
            }
        });
    });
});
