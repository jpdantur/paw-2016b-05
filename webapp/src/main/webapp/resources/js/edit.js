$(document).ready(function(){
  var $prevCard, $successCard, $categoryName, $categoryInput, redrawBrowser, markActiveTree, $activeCategory;
  $prevCard = [];
  $successCard = $('#success-card');
  $categoryName = $('#category-name');
  $categoryInput = $('#category-input');
  redrawBrowser = function(){
    var $visibleCards, $widths, widths, reducer, width;
    $visibleCards = $('#category-browser .category-card.visible');
    $widths = $visibleCards.map(function(){
      return $(this).width();
    });
    widths = $widths.get();
    reducer = function(memo, num){
      return memo + num;
    };
    width = _.reduce(widths, reducer, 0);
    $('#category-browser > div').css({
      width: width + 20 * widths.length
    });
  };
  $('.category-card a').click(function(e){
    var $self, $card, $nextCard;
    e.preventDefault();
    $self = $(this);
    $card = $self.closest('.category-card');
    $nextCard = $(".category-card[data-parent=" + $self.data('id') + "]");
    $card.nextAll('.category-card').removeClass('visible').find('.active').removeClass('active');
    $self.parent().siblings().removeClass('active');
    $self.parent().addClass('active');
    if ($nextCard.length === 0) {
      $successCard.addClass('visible');
      $categoryName.text($self.text());
      $categoryInput.val($self.data('id'));
    } else {
      $successCard.removeClass('visible');
      $nextCard.addClass('visible');
    }
    redrawBrowser();
  });
  $('form').submit(function(e){
    $('#submit-button').addClass('disabled');
    if (!$categoryInput.val() || $categoryInput.val() === '0') {
      $('#submit-button').removeClass('disabled');
      $.notify({
        message: messages.editPickCategory
      }, {
        type: 'warning'
      });
      e.preventDefault();
      return false;
    }
  });
  markActiveTree = function(){
    var $self, $card, $parentLink, $parentCard;
    $self = this;
    $self.addClass('active');
    $card = $self.closest('.category-card');
    $parentLink = $(".category-card a[data-id=" + $card.data('parent') + "]");
    $parentCard = $parentLink.parent().addClass('active').closest('.category-card');
    $card.addClass('visible');
    if ($card.data('parent') !== $parentCard.data('parent')) {
      markActiveTree.apply($parentLink);
    }
  };
  console.log(".category-card a[data-id=" + $categoryInput.val() + "]");
  $activeCategory = $(".category-card a[data-id=" + $categoryInput.val() + "]");
  $successCard.addClass('visible');
  $categoryName.text($activeCategory.text());
  markActiveTree.apply($activeCategory.parent());
});