'use strict';
define([
	'siglasApp',
	'services/StoreService'
], function(siglasApp) {

	siglasApp.controller('AllCtrl', function($scope, $rootScope, $location, $route, toastr, $filter, StoreService) {

		$scope._ = _;
		$scope.Math = window.Math;

		var lock = true;

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
		self.displayedCategoriesG = {};
		self.selectedCategories = [];

		self.itemsLoading = false;
		self.itemResult = {};

		self.itemsOrder = 'PRICE-ASC';

		self.updateItemsQuery = _.debounce(updateItemsQuery, 500);
		self.onItemsOrderChange = onItemsOrderChange;

		self.toggleCategory = toggleCategory;
		self.applyCategory = applyCategory;
		self.removeCategory = removeCategory;

		// /////////////////////////////

		$scope.$on('query.update', function (e, query) {
			updateItemsQuery({pageNumber: 0, query: query});
		});

		$scope.$watch(function() {
			return $location.search();
		}, function() {
			if (!lock) {
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
				publishedItems();
			}
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
			lock = true;
			StoreService.search(self.itemsQuery).then(function (result) {
				self.itemResult = result;
				self.itemsLoading = false;

				if (self.itemResult.results.length === 1) {
					return $location.search({}).path('/store/items/' + self.itemResult.results[0].id);
				}

				self.displayedCategories = self.itemResult.selectedCategories;

				self.displayedCategoriesG = _.reduce(self.displayedCategories, function (memo, val) {
					memo[val.id] = val;
					return memo;
				}, {});

				$location.search(self.itemsQuery);
				setTimeout(function () {
					lock = false;
				}, 50);
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
			self.selectedCategories = [];
		}

		function removeCategory(category) {
			var idx = _.findIndex(self.displayedCategories, {id: category.id});

			self.displayedCategories.splice(idx, 1);

			updateItemsQuery({
				pageNumber: 0,
				categories: _.map(self.displayedCategories, 'id')
			});
			self.selectedCategories = [];
		}

	});
});
