$(document).ready(function(){
  $('#treegrid').treegrid({
    initialState: 'collapsed'
  });
  $('.create-subcategory').click(function(e){
    var $self, $row, id, parent, $iframe;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    id = $row.data('id');
    parent = $row.data('parent');
    $iframe = $('<iframe>', {
      style: 'width:100%;min-height:400px',
      src: baseUrl + "/admin/categories/create?parent=" + id + "&name=-"
    });
    $iframe.on('load', function(){
      if ($(this).contents().find('#success').length > 0) {
        setTimeout(function(){
          bootbox.hideAll();
          window.location.reload(true);
        }, 1500);
      }
    });
    bootbox.dialog({
      message: $iframe,
      buttons: {
        cancel: {
          label: messages.cancel,
          callback: function(){}
        }
      }
    });
  });
  $('.rename-category').click(function(e){
    var $self, $row, id, parent, $iframe;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    id = $row.data('id');
    parent = $row.data('parent');
    $iframe = $('<iframe>', {
      style: 'width:100%;min-height:400px',
      src: baseUrl + "/admin/categories/" + id + "/edit"
    });
    $iframe.on('load', function(){
      if ($(this).contents().find('#success').length > 0) {
        setTimeout(function(){
          bootbox.hideAll();
          window.location.reload(true);
        }, 1500);
      }
    });
    bootbox.dialog({
      message: $iframe,
      buttons: {
        cancel: {
          label: messages.cancel,
          callback: function(){}
        }
      }
    });
  });
});