$(document).ready(function(){
  $('#navbar-search').on('submit', function(e){
    if ($.trim($('#navbar-query-input').val()) === '') {
      e.preventDefault();
    }
  });
});