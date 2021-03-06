$(document).ready(function(){
  $('.toggle-default').click(function(e){
    var $self, $row;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    if ($self.hasClass('text-success')) {
      return;
    }
    $.ajax({
      url: baseUrl + '/admin/roles/default/' + $row.data('id'),
      type: 'POST',
      success: function(data){
        if (data.err) {
          $.notify({
            message: messages.fail
          }, {
            type: 'danger'
          });
        } else {
          $.notify({
            message: messages.success
          }, {
            type: 'success'
          });
          $row.closest('table').find('.default-role').find('.text-success').toggleClass('text-success text-muted').find('i').toggleClass('fa-check fa-minus');
          $self.toggleClass('text-success text-muted').find('i').toggleClass('fa-check fa-minus');
        }
      }
    });
  });
});