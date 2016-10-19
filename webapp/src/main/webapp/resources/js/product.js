$(document).ready(function(){
  $('.add-favourite').click(function(e){
    var $self, item, remove, isRemoving;
    $self = $(this);
    item = $self.data('item');
    remove = '/remove';
    isRemoving = true;
    if ($self.hasClass('text-muted')) {
      remove = '/';
      isRemoving = false;
    }
    $.ajax({
      url: baseUrl + '/store/item/' + item + '/favourite' + remove,
      type: 'POST',
      success: function(data){
        console.log(data);
        if (data.err) {
          $.notify({
            message: isRemoving
              ? messages.removeError
              : messages.addError
          }, {
            type: 'danger'
          });
        } else {
          $.notify({
            message: isRemoving
              ? messages.removeSuccess
              : messages.addSuccess
          }, {
            type: 'success'
          });
          $self.toggleClass('text-muted text-danger');
        }
      }
    });
  });
  $('[data-buyer-email]').click(function(e){
    e.preventDefault();
  });
  $('#purchase').click(function(e){
    var $self, item;
    e.preventDefault();
    $self = $(this);
    item = $self.data('item');
    $.ajax({
      url: baseUrl + '/store/item/' + item + '/purchase',
      type: 'POST',
      success: function(data){
        if (data.err) {
          $.notify({
            message: messages.buyError
          }, {
            type: 'danger'
          });
        } else {
          $.notify({
            message: messages.buySuccess
          }, {
            type: 'success'
          });
          $self.fadeOut("slow", function(){
            var $p;
            $p = $("<pre><p class='lead' id='contact-email'>" + $self.data('buyer-email') + "</p></pre>").hide();
            $self.replaceWith($p);
            return $p.fadeIn("slow");
          });
        }
      }
    });
  });
});