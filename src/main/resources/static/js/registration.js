$(document).ready(function () {
    $('#registration-button').click(function () {


        var firstName = $('#registration-first-name').val();
        var email = $('#registration-email').val();
        var password = $('#registration-password').val();

        var person = {
            'firstName': firstName,
            'email': email,
            'password': password
        };
        $.ajax({
            'url': 'http://localhost:8080/registration',
            'type': "POST",
            'contentType': 'application/json',
            'dataType': 'json',
            'data': JSON.stringify(person),
            'headers': {
                'Content-Type': 'application/json;charset=utf-8'
            },
            'success': function (data) {
                document.cookie = data.key + "=" + data.value;
                window.location.replace("/user");
            },
            'error': function (error) {
                $('#registration-error-field').empty();
                $('#registration-error-field').text(JSON.parse(error.responseText).message);
                console.log(JSON.parse(error.responseText).message);
            }
        });
    });
});
