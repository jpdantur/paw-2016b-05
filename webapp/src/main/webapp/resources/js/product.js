$(document).ready(function(){
  $('.rating.static').rate({
    max_value: 5,
    step_size: 0.5,
    readonly: true
  });
  $('#score').rate({
    max_value: 5,
    step_size: 0.5,
    update_input_field_name: $('#score-input')
  });
  $('.add-favourite').click(function(e){
    var $self, item, favId, isRemoving, url;
    e.preventDefault();
    $self = $(this);
    if ($self.hasClass('disabled')) {
      return;
    }
    item = $self.data('item');
    favId = $self.data('favid');
    isRemoving = $self.hasClass('text-danger');
    url = baseUrl + ("/favourites/" + (isRemoving ? 'remove' : 'add') + "/" + (isRemoving ? favId : item));
    $self.addClass('disabled');
    $.ajax({
      url: url,
      type: 'POST',
      success: function(data){
        console.log(data);
        $self.removeClass('disabled');
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
          if (!isRemoving) {
            $self.data('favid', data.favid);
          }
        }
      }
    });
  });
  $('#purchase[data-buyer-email]').click(_.once(function(e){
    var $self, item;
    e.preventDefault();
    $self = $(this);
    if ($self.hasClass('disabled')) {
      return;
    }
    item = $self.data('item');
    $self.addClass('disabled');
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
            $p = $("<div class='alert alert-info'><p>" + messages.purchaseInRevision + "</p><a href='" + baseUrl + "/profile/purchases' class='btn btn-success btn-sm'>" + messages.seeStatus + "</a></div>").hide();
            $self.replaceWith($p);
            return $p.fadeIn("slow");
          });
        }
      }
    });
  }));
  $('#comment-form').submit(function(e){
    console.log('hola');
    $('#submit-comment').prop('disabled', 'disabled');
  });
});