$(document).ready(function(){
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