$(document).ready(function(){
  $('.rating.static').rate({
    max_value: 5,
    step_size: 0.1,
    readonly: true
  });
  $('.rating.static').each(function(){
    var $self, rateValue;
    $self = $(this);
    rateValue = $self.rate('getValue');
    if (rateValue > 4) {
      $self.addClass('great');
    } else if (rateValue > 3) {
      $self.addClass('good');
    } else if (rateValue > 2) {
      $self.addClass('ok');
    } else if (rateValue > 1) {
      $self.addClass('bad');
    } else {
      $self.addClass('worst');
    }
  });
  $('.remove-fav').click(function(e){
    var $self, item;
    e.preventDefault();
    $self = $(this);
    if ($self.hasClass('disabled')) {
      return;
    }
    item = $self.data('id');
    $self.addClass('disabled');
    $.ajax({
      url: baseUrl + '/favourites/remove/' + item,
      type: 'POST',
      success: function(data){
        $self.removeClass('disabled');
        console.log(data);
        if (data.err) {
          $.notify({
            message: globalMessages.removeError
          }, {
            type: 'danger'
          });
        } else {
          $.notify({
            message: globalMessages.removeSuccess
          }, {
            type: 'success'
          });
          $self.closest('li,tr').remove();
        }
      }
    });
  });
});