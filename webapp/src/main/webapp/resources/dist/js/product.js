$(document).ready(function(){
  return $('[data-buyer-email]').click(function(e){
    return $(this).fadeOut("slow", function(){
      var $p;
      $p = $("<pre><p class='lead' id='contact-email'>" + $(this).data('buyer-email') + "</p></pre>").hide();
      $(this).replaceWith($p);
      return $p.fadeIn("slow");
    });
  });
});