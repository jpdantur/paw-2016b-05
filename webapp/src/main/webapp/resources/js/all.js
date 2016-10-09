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
        if (!data.err) {
          $self.closest('li').remove();
        }
      }
    });
  });
});