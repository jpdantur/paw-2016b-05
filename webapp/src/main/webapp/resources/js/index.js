$(document).ready(function(){
  var $window, $navbar;
  $window = $(window);
  $navbar = $('#navbar-content');
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