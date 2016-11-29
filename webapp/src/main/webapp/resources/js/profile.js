$(document).ready(function(){
  var onHashChange, reviewBuyer, query, buildQuery, submit, resetPageNumber;
  $('[data-toggle="tooltip"]').tooltip();
  $('#score').rate({
    max_value: 5,
    step_size: 0.5,
    update_input_field_name: $('#score-input')
  });
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
  reviewBuyer = function($self, r){
    var $modal, $row, isSale;
    if (r) {
      $modal = $('#review-modal');
      $row = $self.closest('tr');
      isSale = $row.hasClass('p');
      if (isSale) {
        $modal.find('.what').text(messages.scorebuyer);
      } else {
        $modal.find('.what').text(messages.scoreseller);
      }
      $modal.modal('show').data('target', $self);
    }
  };
  $('.show-scores').click(function(e){
    var $self, $row, isSale, buyerRating, sellerRating, $modal, idx;
    e.preventDefault();
    $self = $(this);
    $row = $self.closest('tr');
    isSale = $row.hasClass('p');
    buyerRating = $self.data('b-rating') || {
      rating: 0
    };
    sellerRating = $self.data('s-rating') || {
      rating: 0
    };
    $modal = $('#review-modal-readonly');
    $modal.find('.what').text(isSale
      ? messages.scoreBuyer
      : messages.scoreSeller);
    $modal.find(".r1, .r2").hide();
    $modal.find(".r1.l,.r2.l").text(messages.scoreStillNoScore);
    idx = isSale ? 1 : 2;
    if (buyerRating.id) {
      $modal.find(".r" + idx + ".rating, .r" + idx + ".l").show();
      $modal.find(".r" + idx + ".l").text(buyerRating.comment);
    } else {
      $modal.find(".r" + idx + ".help-block, .r" + idx + ".l").show();
    }
    idx = isSale ? 2 : 1;
    if (sellerRating.id) {
      $modal.find(".r" + idx + ".rating, .r" + idx + ".l").show();
      $modal.find(".r" + idx + ".l").text(sellerRating.comment);
    } else {
      $modal.find(".r" + idx + ".help-block, .r" + idx + ".l").show();
    }
    idx = isSale ? 1 : 2;
    $modal.find('.rating').rate('destroy').each(function(){
      var $rater, rateValue;
      $rater = $(this);
      rateValue = $rater.hasClass("r" + idx)
        ? buyerRating.rating
        : sellerRating.rating;
      $rater.rate({
        max_value: 5,
        step_size: 0.1,
        readonly: true,
        initial_value: rateValue
      });
      if (rateValue > 4) {
        $rater.addClass('great');
      } else if (rateValue > 3) {
        $rater.addClass('good');
      } else if (rateValue > 2) {
        $rater.addClass('ok');
      } else if (rateValue > 1) {
        $rater.addClass('bad');
      } else {
        $rater.addClass('worst');
      }
    });
    $modal.find('.rate-button').click(function(e){
      e.preventDefault();
      reviewBuyer($self, true);
    });
    $modal.modal('show');
  });
  $('#rate-action').click(function(e){
    var $self, $content, $modal, $target, $row, isSale;
    e.preventDefault();
    $self = $(this);
    $content = $('#content');
    $modal = $('#review-modal');
    $target = $modal.data('target');
    $row = $target.closest('tr');
    isSale = $row.hasClass('p');
    if (!$content.val()) {
      $content.closest('.form-group').addClass('has-error');
      $content.after('<span class="help-block">' + messages.scoreVoidContent + '</span>');
      return;
    }
    $self.addClass('disabled');
    console.log($('#comment-form').serialize());
    $.ajax({
      url: baseUrl + "/store/sales/" + $row.data('id') + "/" + (isSale ? 'seller' : 'buyer') + "/review",
      data: $('#comment-form').serialize(),
      type: 'POST',
      success: function(data){
        $self.removeClass('disabled');
        if (data.err) {
          $.notify({
            message: messages.scoreError
          }, {
            type: 'danger',
            z_index: 1300
          });
        } else {
          $modal.modal('hide');
          $.notify({
            message: messages.scoreSuccess
          }, {
            type: 'success',
            z_index: 1300
          });
        }
      }
    });
  });
  $('.decide-transaction').click(function(e){
    var $self, $row, isApproving, actionDesc;
    e.preventDefault();
    $self = $(this);
    if ($self.hasClass('disabled')) {
      return;
    }
    $row = $self.closest('tr');
    isApproving = $self.hasClass('btn-success');
    $self.addClass('disabled');
    actionDesc = isApproving
      ? messages.sellApprove
      : messages.sellReject;
    bootbox.confirm(messages.sellConfirmation + actionDesc + messages.sellConfirmation2, function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/sales/" + $row.data('id') + "/" + (isApproving ? 'approve' : 'decline'),
          type: 'POST',
          success: function(data){
            $self.removeClass('disabled');
            if (data.err) {
              $.notify({
                message: messages.sellError + (isApproving
                  ? messages.approving
                  : messages.rejecting)
              }, {
                type: 'danger',
                z_index: 1300
              });
            } else {
              $.notify({
                message: messages.sellSuccess
              }, {
                type: 'success',
                z_index: 1300
              });
              if (isApproving) {
                $self.next().remove();
                $self.text(messages.sellApproved);
              } else {
                $self.prev().remove();
                $self.text(messages.sellRejected);
              }
              bootbox.confirm(messages.scoreScoreBuyer, function(r){
                reviewBuyer($self, r);
              });
            }
          }
        });
      } else {
        $self.removeClass('disabled');
      }
    });
  });
  $(document.body).on('click', '.toggle-item-state', function(e){
    var $self, $row, isActive, actionDesc;
    e.preventDefault();
    $self = $(this);
    if ($self.hasClass('disabled')) {
      return;
    }
    $row = $self.closest('tr');
    isActive = $self.hasClass('btn-default');
    $self.addClass('disabled');
    actionDesc = isActive
      ? messages.sellPause
      : messages.sellResume;
    bootbox.confirm(messages.sellConfirmation + actionDesc + messages.sellConfirmation2, function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/item/" + $row.data('id') + "/" + (isActive ? 'pause' : 'resume'),
          type: 'POST',
          success: function(data){
            $self.removeClass('disabled');
            if (data.err) {
              $.notify({
                message: messages.sellError + (isActive
                  ? messages.pausing
                  : messages.resuming)
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
      } else {
        $self.removeClass('disabled');
      }
    });
  });
  $(document.body).on('click', '.publish', function(e){
    var $self, $row, isActive;
    e.preventDefault();
    $self = $(this);
    if ($self.hasClass('disabled')) {
      return;
    }
    $row = $self.closest('tr');
    $self.addClass('disabled');
    isActive = $self.hasClass('btn-default');
    bootbox.confirm(messages.sellPublishConfirm, function(r){
      if (r) {
        $.ajax({
          url: baseUrl + "/store/item/" + $row.data('id') + "/publish",
          type: 'POST',
          success: function(data){
            $self.removeClass('disabled');
            if (data.err) {
              $.notify({
                message: messages.sellError + messages.publishing
              }, {
                type: 'danger'
              });
            } else {
              $.notify({
                message: messages.sellSuccess
              }, {
                type: 'success'
              });
              $self.toggleClass('publish btn-success btn-default toggle-item-state').text(messages.sellPauseBtn);
            }
          }
        });
      } else {
        $self.removeClass('disabled');
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
  resetPageNumber = function(){
    query.pageNumber = 0;
  };
  if ($('#query-input').length) {
    buildQuery();
  }
  $('#query-input').keyup(function(e){
    if (e.keyCode === 13) {
      e.preventDefault();
      buildQuery();
      resetPageNumber();
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
    resetPageNumber();
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
    resetPageNumber();
    submit();
  });
});