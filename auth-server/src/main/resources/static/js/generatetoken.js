jQuery(function ($) {
  $('#generateId').submit(function (event) {
    // stop being refreshing
    event.preventDefault();

    var values = jQuery.param({
      client_id: $('#currentclientId').val(),
      client_secret: $('#currentclientSecret').val(),
      username: document.getElementById('username').innerText,
      password: $('#password').val(),
      grant_type: 'password'
    });

    jQuery.ajax({
      type: 'POST',
      url: '/oauth/token',
      data: values,
      success: function (response) {
        var token = response.access_token;
        $('#token').html(token);
        $('#generateId').trigger('reset');
        console.log(token);
      },
      error: function (xhr, status, errorThrown) {
        //Here the status code can be retrieved like;
        xhr.status;

        //The message added to Response object in Controller can be retrieved as following.
        alert(JSON.parse(xhr.responseText).error_description)
      }
    })
  });
});