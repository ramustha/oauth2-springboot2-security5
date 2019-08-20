jQuery(function ($) {
  var path = location.pathname;

  if (/clientapi/.test(path)) {
    $('li').removeClass('active');
    $('li .clientapi').addClass('active');
  } else if (/user/.test(path)) {
    $('li').removeClass('active');
    $('li .user').addClass('active');
  } else {
    $('li').removeClass('active');
    $('li .home').addClass('active');
  }
});