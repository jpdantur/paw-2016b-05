$(document).ready(function(){
  var onHashChange;
  $('#myTab a, #itemsTab a, #salesTab a').click(function(e){
    e.preventDefault();
    $(this).tab('show');
  });
  $('ul.nav-tabs > li > a').on('shown.bs.tab', function(e){
    var id;
    id = $(e.target).attr('href').substr(1);
    window.location.hash = id;
  });
  $('form').submit(function(e){
    var action;
    action = $(this).attr('action');
    $(this).attr('action', action + window.location.hash);
  });
  onHashChange = function(hash){
    var parts, mainHash;
    parts = hash.split('-');
    mainHash = parts[0];
    $("#myTab a[href='" + mainHash + "']").tab('show');
    if (parts.length > 1) {
      $("#itemsTab a[href='" + hash + "']").tab('show');
      $("#salesTab a[href='" + hash + "']").tab('show');
    }
  };
  onHashChange(window.location.hash);
  $(window).on('hashchange', function(){
    onHashChange(window.location.hash);
  });
  $('.toggle-item-state').click(function(e){
    var $self, $row, isActive;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    isActive = $self.hasClass('btn-default');
    bootbox.confirm("Esta seguro que desea " + (isActive ? 'pausar' : 'reanudar') + " esta publicación", function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/item/" + $row.data('id') + "/" + (isActive ? 'pause' : 'resume'),
          type: 'POST',
          success: function(data){
            if (data.err) {
              $.notify({
                message: 'Mensaje de error que le falta i18n'
              }, {
                type: 'danger'
              });
            } else {
              $.notify({
                message: 'Mensaje de exito que le falta i18n'
              }, {
                type: 'success'
              });
              $self.toggleClass('btn-success btn-default');
              $self.text(isActive ? 'Reanudar Publicación' : 'Pausar Publicación');
            }
          }
        });
      }
    });
  });
});