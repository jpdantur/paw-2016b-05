$(document).ready(function(){
  $('.add-favourite').click(function(e){
    var $self, item, remove;
    $self = $(this);
    item = $self.data('item');
    remove = '/remove';
    if ($self.hasClass('text-muted')) {
      remove = '/';
    }
    $.ajax({
      url: baseUrl + '/store/item/' + item + '/favourite' + remove,
      type: 'POST',
      success: function(data){
        console.log(data);
        if (!data.err) {
          $self.toggleClass('text-muted text-danger');
        }
      }
    });
  });
  return $('[data-buyer-email]').click(function(e){
    return $(this).fadeOut("slow", function(){
      var $p;
      $p = $("<pre><p class='lead' id='contact-email'>" + $(this).data('buyer-email') + "</p></pre>").hide();
      $(this).replaceWith($p);
      return $p.fadeIn("slow");
    });
  });
});