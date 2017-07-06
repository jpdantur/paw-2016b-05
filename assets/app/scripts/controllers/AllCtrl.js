'use strict';
define([
	'siglasApp',
	'services/StoreService'
], function(siglasApp) {

	siglasApp.controller('AllCtrl', function($scope, $rootScope, $location, $route, toastr, $translate, StoreService) {

		console.log('AllCtrl');

		$scope._ = _;

		var self = this;

		self.itemsQuery = _.extend({
			pageNumber: 0,
			pageSize: 20,
			query: '',
			sortOrder: 'ASC',
			sortField: 'PRICE',
			minPrice: null,
			maxPrice: null,
			categories: []
		}, _.mapValues($location.search(), function (val) {
			if (!val) {
				return val;
			}
			if (isFinite(val)) {
				return parseInt(val, 10);
			}
			return val;
		}));

		self.displayedCategories = [];
		self.selectedCategories = [];

		console.log(self.itemsQuery);

		self.itemsLoading = false;
		self.itemResult = {};

		self.itemsOrder = 'PRICE-ASC';

		self.updateItemsQuery = _.debounce(updateItemsQuery, 800);
		self.onItemsOrderChange = onItemsOrderChange;

		self.toggleCategory = toggleCategory;
		self.applyCategory = applyCategory;

		// /////////////////////////////

		$scope.$on('query.update', function (e, query) {
			console.log('query.updated', query);
			updateItemsQuery({pageNumber: 0, query: query});
		});

		publishedItems();

		// /////////////////////////////
		
		function updateItemsQuery(delta) {
			_.extend(self.itemsQuery, delta);
			publishedItems();
		}

		function onItemsOrderChange() {
			var parts = self.itemsOrder.split('-');
			updateItemsQuery({pageNumber: 0, sortOrder: parts[1], sortField: parts[0]});
		}

		function publishedItems() {
			self.itemsLoading = true;
			console.log(self.itemsQuery);
			StoreService.search(self.itemsQuery).then(function (result) {
				console.log(result);
				self.itemResult = result;
				self.itemsLoading = false;

				if (self.itemResult.results.length === 1) {
					return $location.search({}).path('/store/items/' + self.itemResult.results[0].id);
				}

				self.displayedCategories = self.itemResult.selectedCategories;

				$location.search(self.itemsQuery);
			}, function (err) {
				console.error(err);
			});
		}

		function toggleCategory(category) {
			var idx = _.findIndex(self.selectedCategories, {id: category.id});
			if (~idx) {
				self.selectedCategories.splice(idx, 1);
			} else {
				self.selectedCategories.push(category);
			}
		}

		function applyCategory() {
			updateItemsQuery({
				pageNumber: 0,
				categories: _.map(self.selectedCategories, 'id')
			});
			// var d = self.displayedCategories;
			// d.splice.apply(d, [0, d.length].concat(self.selectedCategories));
			// self.displayedCategories = self.selectedCategories;
			self.selectedCategories = [];
			// console.log(self.displayedCategories);
			// console.log(self.selectedCategories);
		}

	});
});
