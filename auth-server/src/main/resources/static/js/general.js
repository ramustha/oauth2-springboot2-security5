jQuery(function ($) {
  if (/clientapi/.test(location.pathname)) {
    $('li').removeClass('active');
    $('li .clientapi').addClass('active');
  } else if (/users/.test(location.pathname)) {
    $('li').removeClass('active');
    $('li .users').addClass('active');
  } else {
    $('li').removeClass('active');
    $('li .home').addClass('active');
  }
});