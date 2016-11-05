$(document).ready(function(){
  var onHashChange;
  $('#itemsTab a, #salesTab a, #purchaseTab a').click(function(e){});
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
      $("#purchaseTab a[href='" + hash + "']").tab('show');
    }
  };
  onHashChange(window.location.hash);
  $(window).on('hashchange', function(){
    onHashChange(window.location.hash);
  });
  $('.decide-transaction').click(function(e){
    var $self, $row, isApproving;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    isApproving = $self.hasClass('btn-success');
    bootbox.confirm("Esta seguro que desea " + (isApproving ? 'aprobar' : 'rechazar') + " esta venta", function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/sales/" + $row.data('id') + "/" + (isApproving ? 'approve' : 'decline'),
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
              if (isApproving) {
                $self.next().remove();
                $self.text('Venta Aprobada');
              } else {
                $self.prev().remove();
                $self.text('Venta Rechazada');
              }
              $self.removeClass('decide-transaction');
            }
          }
        });
      }
    });
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