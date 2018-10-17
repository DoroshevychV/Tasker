$(document).ready(function () {
    $('#registration-button').click(function () {


        var firstName = $('#registration-first-name').val();
        var email = $('#registration-email').val();
        var password = $('#registration-password').val();

        alert(firstName+' '+email+" "+password);
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
            'success': function (data) {
                alert("Success");
            },
            'headers': {
                // 'A-Token': localStorage.getItem('A-Token'),
                'Content-Type': 'application/json;charset=utf-8'
            },
            'error': function (error) {
                console.log(error);
            }
        });
    });
});
