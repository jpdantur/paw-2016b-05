$(document).ready(function(){
  var $window, $navbar;
  $window = $(window);
  $navbar = $('#navbar-content');
  $window.scroll(function(e){
    if (!$navbar.hasClass('scrolled') && $navbar.offset().top > 50) {
      $navbar.addClass('scrolled');
      $('.navbar-brand').animate({
        'font-size': '1em'
      });
    } else if ($navbar.hasClass('scrolled') && $navbar.offset().top <= 50) {
      $navbar.removeClass('scrolled');
      $('.navbar-brand').animate({
        'font-size': '2em'
      });
    }
  });
  $('#link-busqueda-compleja').click(function(e){
    var $target;
    e.preventDefault();
    $target = $('#busqueda-compleja');
    $("html,body").animate({
      scrollTop: $target.offset().top
    }, 800, 'easeOutQuint');
  });
  $('input').keydown(function(e){
    e.preventDefault();
    return false;
  });
  $('input').bind('cut copy pase', function(e){
    e.preventDefault();
  });
  $('input').on('contextmenu', function(e){
    return false;
  });
});