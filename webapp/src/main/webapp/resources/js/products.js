$(document).ready(function(){
  var defaultFilterValues, filters, buildFilters, resetPageNumber;
  defaultFilterValues = {
    minPrice: null,
    maxPrice: null,
    categories: [],
    pageSize: 20,
    orderBy: 'price',
    sortOrder: 'asc',
    query: '',
    pageNumber: 0
  };
  filters = $.extend({}, {
    minPrice: null,
    maxPrice: null,
    categories: [],
    pageSize: 20,
    orderBy: 'price',
    sortOrder: 'asc',
    query: '',
    pageNumber: 0
  });
  buildFilters = function(){
    var minPrice, maxPrice, $filterOption, filterOption, $selectedCheckboxes, $categoryLabels;
    minPrice = parseInt($('#filter-price-min').val() || -1);
    maxPrice = parseInt($('#filter-price-max').val() || -1);
    if (minPrice !== -1) {
      filters.minPrice = minPrice;
    }
    if (maxPrice !== -1) {
      filters.maxPrice = maxPrice;
    }
    filters.pageSize = parseInt($('#modifier-page-size').find('.modifier-option.selected').text());
    $filterOption = $('#modifier-order-input').find('option:selected');
    filterOption = $filterOption.val().split('-');
    filters.orderBy = filterOption[0];
    filters.sortOrder = filterOption[1];
    filters.query = $('#navbar-query-input').val();
    filters.pageNumber = parseInt($('#filter-page').data('page'));
    $selectedCheckboxes = $('#category-filter input:checked');
    if ($selectedCheckboxes.length) {
      filters.categories = $selectedCheckboxes.map(function(){
        return $(this).val();
      }).toArray();
    } else {
      $categoryLabels = $('#category-filter label.parent-label');
      if ($categoryLabels.length > 0) {
        filters.categories = $categoryLabels.map(function(){
          return $(this).data('id');
        }).toArray();
      } else {
        filters.categories = $('.applied-filter[data-category-id]').map(function(){
          return $(this).data('category-id');
        }).toArray();
      }
    }
    console.log(filters);
  };
  buildFilters();
  resetPageNumber = function(){
    filters.pageNumber = defaultFilterValues.pageNumber;
  };
  $('#modifier-order-input').change(function(e){
    e.preventDefault();
    buildFilters();
    resetPageNumber();
    window.location.search = $.param(filters, true);
  });
  $('#filter-price-set').click(function(e){
    e.preventDefault();
    buildFilters();
    window.location.search = $.param(filters, true);
  });
  $('#filter-price-min, #filter-price-max').keydown(function(e){
    console.log(e.keyCode);
    if (e.keyCode === 13) {
      $('#filter-price-set').click();
    }
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
    buildFilters();
    resetPageNumber();
    window.location.search = $.param(filters, true);
  });
  $('#navbar-search').on('submit', function(e){
    if ($.trim($('#navbar-query-input').val()) === '') {
      e.preventDefault();
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
      filters.pageNumber = 0;
    }
    if ($self.parent().hasClass('pre')) {
      filters.pageNumber -= 1;
    }
    if ($self.parent().hasClass('ne')) {
      filters.pageNumber += 1;
    }
    if ($self.parent().hasClass('next')) {
      filters.pageNumber = parseInt($('#filter-page').data('last-page'));
    }
    window.location.search = $.param(filters, true);
  });
  $('#category-filter-button').click(function(e){
    e.preventDefault();
    buildFilters();
    window.location.search = $.param(filters, true);
  });
  $('.applied-filter .close').click(function(e){
    var $self, targets, i$, len$, target, index;
    e.preventDefault();
    $self = $(this).parent();
    if ($self.data('target')) {
      targets = $self.data('target').split(',');
      for (i$ = 0, len$ = targets.length; i$ < len$; ++i$) {
        target = targets[i$];
        filters[target] = defaultFilterValues[target];
      }
    } else {
      index = filters[$self.data('target-complex')].indexOf($self.data('category-id'));
      filters[$self.data('target-complex')].splice(index, 1);
    }
    console.log(filters);
    window.location.search = $.param(filters, true);
  });
});