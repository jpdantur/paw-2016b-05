$(document).ready(function(){
  var $prevCard, $successCard, $categoryName, $categoryInput, redrawBrowser;
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
    console.log('form-submit');
    console.log($categoryInput.val());
    console.log(!$categoryInput.val());
    if (!$categoryInput.val()) {
      console.log('preventing');
      e.preventDefault();
      return false;
    }
  });
});