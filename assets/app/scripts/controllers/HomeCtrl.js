'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/CategoryService'
], function(siglasApp) {

	siglasApp.controller('HomeCtrl', function($scope, $rootScope, $location, ItemService, CategoryService) {

		console.log('HomeCtrl');

		var self = this;

		self.items = [];

		// ///////

		ItemService.mostSold().then(function (items) {
			console.log(items);
			self.items = items;
			self.compartments = _.reduce(self.items, function (memo, val, i) {
				var key = Math.floor(i / 6);
				if (key in memo) {
					memo[key].push(val);
				} else {
					memo[key] = [val];
				}
				return memo;
			}, {});
			console.log(self.compartments);
		}, function (err) {
			console.error(err);
		});

		CategoryService.tree().then(function (categories) {
			self.categories = categories;
			console.log(self.categories);
		}, function (err) {
			console.error(err);
		});

		// ///////

	});
});
