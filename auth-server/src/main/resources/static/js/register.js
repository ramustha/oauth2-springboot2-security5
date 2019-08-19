jQuery(function ($) {
  var roles = $('#roles');

  roles.change(function () {
    var roles = $(this).val();

    var html = $.map(roles, function (role) {
      var permissions = JSON.parse(role).permissions;

      return $.map(permissions, function (permission) {
        return '<option value="' + permission + '">' + permission.value + '</option>'
      }).join('');
    });

    $('#permissions').html(html);
  })
});