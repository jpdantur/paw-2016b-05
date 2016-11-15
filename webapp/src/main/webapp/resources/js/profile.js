$(document).ready(function(){
  var onHashChange, query, buildQuery, submit;
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
    var $self, $row, isApproving, actionDesc;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    isApproving = $self.hasClass('btn-success');
    actionDesc = isApproving
      ? messages.sellApprove
      : messages.sellReject;
    bootbox.confirm(messages.sellConfirmation + actionDesc + messages.sellConfirmation2, function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/sales/" + $row.data('id') + "/" + (isApproving ? 'approve' : 'decline'),
          type: 'POST',
          success: function(data){
            if (data.err) {
              $.notify({
                message: messages.sellError
              }, {
                type: 'danger'
              });
            } else {
              $.notify({
                message: messages.sellSuccess
              }, {
                type: 'success'
              });
              if (isApproving) {
                $self.next().remove();
                $self.text(messages.sellApproved);
              } else {
                $self.prev().remove();
                $self.text(messages.sellRejected);
              }
              $self.removeClass('decide-transaction');
            }
          }
        });
      }
    });
  });
  $('.toggle-item-state').click(function(e){
    var $self, $row, isActive, actionDesc;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    isActive = $self.hasClass('btn-default');
    actionDesc = isActive
      ? messages.sellPause
      : messages.sellResume;
    bootbox.confirm(messages.sellConfirmation + actionDesc + messages.sellConfirmation2, function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/item/" + $row.data('id') + "/" + (isActive ? 'pause' : 'resume'),
          type: 'POST',
          success: function(data){
            if (data.err) {
              $.notify({
                message: messages.sellError
              }, {
                type: 'danger'
              });
            } else {
              $.notify({
                message: messages.sellSuccess
              }, {
                type: 'success'
              });
              $self.toggleClass('btn-success btn-default');
              $self.text(isActive
                ? messages.sellResumeBtn
                : messages.sellPauseBtn);
            }
          }
        });
      }
    });
  });
  $('.publish').click(function(e){
    var $self, $row, isActive;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    isActive = $self.hasClass('btn-default');
    bootbox.confirm(messages.sellPublishConfirm, function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/item/" + $row.data('id') + "/publish",
          type: 'POST',
          success: function(data){
            if (data.err) {
              $.notify({
                message: messages.sellError
              }, {
                type: 'danger'
              });
            } else {
              $.notify({
                message: messages.sellSuccess
              }, {
                type: 'success'
              });
              $self.toggleClass('publish').prop('disabled', 'disabled').text(messages.sellPublished);
            }
          }
        });
      }
    });
  });
  query = {};
  buildQuery = function(){
    var $filterOption, filterOption;
    query.pageNumber = parseInt($('#filter-page').data('page'));
    query.pageSize = parseInt($('#modifier-page-size').find('.modifier-option.selected').text());
    query.query = $('#query-input').val();
    $filterOption = $('#modifier-order-input').find('option:selected');
    filterOption = $filterOption.val().split('-');
    query.orderBy = filterOption[0].toUpperCase();
    query.sortOrder = filterOption[1].toUpperCase();
  };
  submit = function(){
    console.log(query);
    window.location.search = $.param(query, true);
  };
  if ($('#query-input').length) {
    buildQuery();
  }
  $('#query-input').keyup(function(e){
    if (e.keyCode === 13) {
      e.preventDefault();
      buildQuery();
      query.pageNumber = 0;
      submit();
    }
  });
  $('.filter-page-action').click(function(e){
    var $self;
    e.preventDefault();
    $self = $(this);
    if ($self.parent().hasClass('disabled')) {
      return;
    }
    if ($self.parent().hasClass('previous')) {
      query.pageNumber = 0;
    }
    if ($self.parent().hasClass('pre')) {
      query.pageNumber -= 1;
    }
    if ($self.parent().hasClass('ne')) {
      query.pageNumber += 1;
    }
    if ($self.parent().hasClass('next')) {
      query.pageNumber = parseInt($('#filter-page').data('last-page'));
    }
    submit();
  });
  $('#modifier-order-input').change(function(e){
    e.preventDefault();
    buildQuery();
    submit();
  });
  $('.modifier-option').click(function(e){
    var $self, $row;
    e.preventDefault();
    $self = $(this);
    $row = $self.parent();
    if ($self.hasClass('selected')) {
      return;
    }
    $row.find('.selected').toggleClass('selected');
    $self.toggleClass('selected');
    buildQuery();
    submit();
  });
});