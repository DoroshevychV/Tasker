$(document).ready(function () {

    $.ajax({
        'url': 'http://localhost:8080/user/details',
        'type': "GET",
        'contentType': 'application/json',
        'dataType': 'json',
        'success': function (data) {
            $('#name-place').prepend('<a href="/user/logout"><i class="Logout-btn fas fa-sign-out-alt fa-lg"><span>Logout</span></i></a>\n' +
                '            <a href="/user"><i class="User-Name fas fa-user fa-lg">'+data.name+'</i></a>');

            $('#user-details').prepend('<h3 class="Title">Your details</h3>'+'<p class="UDField text-center">\n' +
                '                        <b>Name:</b>\n' +
                '                    </p>\n' +
                '                    <p class="UDValue text-center">\n' +
                '                        <span>'+data.name+'</span>\n' +
                '                    </p>\n' +
                '                    <p class="UDField text-center">\n' +
                '                        <b>Email:</b>\n' +
                '                    </p>\n' +
                '                    <p class="UDValue text-center">\n' +
                '                        <span>'+data.email+'</span>\n' +
                '                    </p>')

        },
        'error': function (error) {
            console.log(JSON.parse(error.responseText).message);
        }
    });

    getUsersTasks();


    function getUsersTasks(){
        // console.log("GetUSersTAsk");
        $.ajax({
            'url': 'http://localhost:8080/task/all',
            'type': "GET",
            'contentType': 'application/json',
            'dataType': 'json',
            'success': function (data) {
                $('.Tasks-List').empty();
                $('#send-select-task').empty();
                if (data.length > 0) {



                    for (var i = 0; i < data.length; i++) {
                        $('.Tasks-List').prepend('' +
                            '<li id="' + data[i].id + '" class="Tasks-List-Li list-group-item">' + data[i].title + '</li>');
                        $('#send-select-task').prepend('<option id="'+data[i].id+'">'+data[i].title+'</option>');
                    }
                }else{
                    $('.Tasks-List').append('' +
                        '<li class="list-group-item">' + 'You do not have any tasks' + '</li>');
                }

            },
            'error': function (error) {
                console.log(JSON.parse(error.responseText).message);
            }
        });
    }

    $(document).on('click','.Tasks-List-Li', function (e) {

        var id = $(this).attr("id");
        console.log('http://localhost:8080/task/' + id + '');
        $.ajax({
            'url': 'http://localhost:8080/task/' + id + '',
            'type': "GET",
            'contentType': 'application/json',
            'dataType': 'json',
            'success': function (data) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title">Edit task</h3>\n' +
                    '\n' +
                    '                <p class="text-center">\n' +
                    '                    <b>Id:</b>\n' +
                    '                </p>\n' +
                    '                <p class="text-center">\n' +
                    '                    <span id="delete-id">'+data.id+'</span>\n' +
                    '                </p>\n' +
                    '\n' +
                    '                <form>\n' +
                    '                    <div class="form-group">\n' +
                    '                        <label class="Medium-Title" for="edit-title-field">Title</label>\n' +
                    '                        <input type="text" class="form-control" id="edit-title-field"\n' +
                    '                               placeholder="Task name(2-30 symbols)" value="'+data.title+'">\n' +
                    '                    </div>\n' +
                    '\n' +
                    '                    <div class="form-group">\n' +
                    '                        <label class="Medium-Title" for="edit-description-field">Description</label>\n' +
                    '                        <textarea class="form-control" id="edit-description-field" rows="3"\n' +
                    '                                  placeholder="Description(10-300 symbols)">'+data.description+'</textarea>\n' +
                    '                    </div>\n' +
                    '                </form>\n' +
                    '\n' +
                    '      <label class="Medium-Title" for="persons-list">Users</label>           <ul id="persons-list" class="list-group"></ul>\n' +
                    '                <button id="delete-btn" type="button" class="btn btn-default" style="float: left">Delete</button>\n' +
                    '                <button id="edit-btn" type="button" class="btn btn-default" style="float: right">Edit</button>\n');


                for (var i = 0;i < data.persons.length;i++) {
                        $('#persons-list').append('<li class="Tasks-List-Li list-group-item">'+data.persons[i].email+'</li>');
                    }
            },
            'error': function (error) {

            }
        });
    });
    $('#create-btn').on('click', function (e) {

        $('.Action-Block').empty();
        $('.Action-Block').append('<h3 class="Title">Create new task</h3>\n' +
            '                <form>\n' +
            '                    <div class="form-group">\n' +
            '                        <label class="Medium-Title" for="create-title-field">Title</label>\n' +
            '                        <input type="text" class="form-control" id="create-title-field"\n' +
            '                               placeholder="Task name(2-30 symbols)">\n' +
            '                    </div>\n' +
            '\n' +
            '                    <div class="form-group">\n' +
            '                        <label class="Medium-Title" for="create-description-field">Description</label>\n' +
            '                        <textarea class="form-control" id="create-description-field" rows="3"\n' +
            '                                  placeholder="Description(10-200 symbols)"></textarea>\n' +
            '                    </div>\n' +
            '\n' +
            '                    <button id="save-new-task-btn" type="button" class="Button btn btn-default">Save</button>\n' +
            '                </form>');

    });

    $(document).on('click', '#save-new-task-btn', function () {

        var title = $('#create-title-field').val();
        var description = $('#create-description-field').val();

        var task = {
            'title': title,
            'description': description
        };
        console.log(task);

        $.ajax({
            'url': 'http://localhost:8080/task/create',
            'type': "POST",
            'contentType': 'application/json',
            'data': JSON.stringify(task),
            'success': function (data) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: green">Task successfully created</h3>');
                getUsersTasks();
            },
            'error': function (error) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: red">ERROR: '+JSON.parse(error.responseText).message+'</h3>');
            }
        });
    });

    $(document).on('click','#edit-btn', function (e) {

        var id = $('#delete-id').html();
        var title = $('#edit-title-field').val();
        var description = $('#edit-description-field').val();

        var task = {
            'id': id,
            'title': title,
            'description': description
        };


        $.ajax({
            'url': 'http://localhost:8080/task/edit',
            'type': "PUT",
            'contentType': 'application/json',
            'data': JSON.stringify(task),
            'success': function (data) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: green">Task successfully edited</h3>');
                getUsersTasks();
            },
            'error': function (error) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: red">ERROR: '+JSON.parse(error.responseText).message+'</h3>');
            }
        });



    });

    $(document).on('click','#delete-btn', function (e) {
        var id = $('#delete-id').html();
        console.log("id = "+id);
        $.ajax({
            'url': 'http://localhost:8080/task/delete',
            'type': "DELETE",
            'contentType': 'application/json',
            'data': JSON.stringify(id),
            'success': function (data) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: green">Task successfully deleted</h3>');
                getUsersTasks();
            },
            'error': function (error) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: red">ERROR: '+JSON.parse(error.responseText).message+'</h3>');
            }
        });


    });


    $(document).on('click','#send-btn', function (e) {
        var id = $('#send-select-task').children(":selected").attr("id");
        var email = $('#send-email-recipient').val();

        var sendTaskDTO = {
            'id': id,
            'email': email
        };


        $.ajax({
            'url': 'http://localhost:8080/task/send',
            'type': "PUT",
            'contentType': 'application/json',
            'data': JSON.stringify(sendTaskDTO),
            'success': function (data) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: green">Task successfully send</h3>');
                getUsersTasks();
            },
            'error': function (error) {
                $('.Action-Block').empty();
                $('.Action-Block').append('<h3 class="Title" style="color: red">ERROR: '+JSON.parse(error.responseText).message+'</h3>');
            }
        });


    });

});
