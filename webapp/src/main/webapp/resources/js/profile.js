$(document).ready(function(){
  var hash;
  $('#myTab a').click(function(e){
    e.preventDefault();
    $(this).tab('show');
  });
  $('ul.nav-tabs > li > a').on('shown.bs.tab', function(e){
    var id;
    id = $(e.target).attr('href').substr(1);
    window.location.hash = id;
  });
  $('form').submit(function(e){
    var action;
    action = $(this).attr('action');
    $(this).attr('action', action + window.location.hash);
  });
  hash = window.location.hash;
  $("#myTab a[href='" + hash + "']").tab('show');
  $(window).on('hashchange', function(){
    var hash;
    hash = window.location.hash;
    $("#myTab a[href='" + hash + "']").tab('show');
  });
});