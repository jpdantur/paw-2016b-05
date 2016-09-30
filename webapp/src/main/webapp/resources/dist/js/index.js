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
  $('#simple-search-form').submit(function(e){
    if ($.trim($('#simple-search-input').val()) === '') {
      e.preventDefault();
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
});