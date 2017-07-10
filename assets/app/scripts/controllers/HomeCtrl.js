'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/CategoryService'
], function(siglasApp) {

	siglasApp.controller('HomeCtrl', function($scope, $rootScope, $location, ItemService, CategoryService) {

		var self = this;

		self.items = [];

		// ///////

		ItemService.mostSold().then(function (items) {
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
		}, function (err) {
			console.error(err);
		});

		CategoryService.tree().then(function (categories) {
			self.categories = categories;
		}, function (err) {
			console.error(err);
		});

		// ///////

	});
});
