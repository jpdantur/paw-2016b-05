$(document).ready(function(){
  $('.remove-fav').click(function(e){
    var $self, item;
    e.preventDefault();
    $self = $(this);
    item = $self.data('id');
    $.ajax({
      url: baseUrl + '/store/item/' + item + '/favourite/remove',
      type: 'POST',
      success: function(data){
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
          $self.closest('li').remove();
        }
      }
    });
  });
});