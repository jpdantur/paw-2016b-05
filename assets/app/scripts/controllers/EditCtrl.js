'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/CategoryService'
], function(siglasApp) {

	siglasApp.controller('EditCtrl', function($scope, $rootScope, $location, $q, $route, ItemService, CategoryService) {

		console.log('EditCtrl');

		var self = this;

		$scope._ = _;

		self.selectedTab = $route.current.params.tab || 'details';

		self.switchToTab = switchToTab;
		self.item = null;

		self.categoryPath = [];
		self.highlighted = [];
		self.selectedCategory = null;

		self.selectCategory = selectCategory;

		// ///////

		$q.all({
			item: ItemService.findById($route.current.pathParams.id),
			category: CategoryService.tree()
		}).then(function (result) {
			self.item = result.item;

			self.categories = result.category;
			self.categoryPath.push(self.categories);

			itemCategoryLookup(self.categories, 1, function (category) {
				console.log(category);
			});

		}, function (err) {
			console.error(err);
		});
		// ///////

		function itemCategoryLookup(categories, depth, cb) {
			var stop = false;
			_.each(categories, function (category) {
				if (stop) {
					return;
				}
				if (category.id === self.item.category.id) {
					stop = true;
					selectCategory(category, depth);
					return cb(category);
				}
				selectCategory(category, depth);
				itemCategoryLookup(category.children, depth + 1, cb);
			});
		}

		function switchToTab(tab) {
			$location.search({tab: tab});
			self.selectedTab = tab;
		}

		function selectCategory(category, depth) {
			if (category.children.length) {
				self.selectedCategory = null;
				self.categoryPath.splice(depth, self.categoryPath.length - depth, category.children);
			} else {
				self.selectedCategory = category;
				self.categoryPath.splice(depth, self.categoryPath.length - depth);
			}
			self.highlighted = _.map(self.categoryPath, function (group) {
				return group[0].parent;
			});
		}

	});
});
