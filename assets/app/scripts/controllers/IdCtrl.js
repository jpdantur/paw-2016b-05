'use strict';
define([
	'siglasApp',
	'services/IdService'
], function(siglasApp) {

	siglasApp.controller('IdCtrl', function($scope, $rootScope, $location, $route, toastr, IdService) {

		console.log('IdCtrl');

		var self = this;

		self.user = null;
		self.params = $route.current.pathParams;

		self.itemsQuery = {
			pageNumber: 0,
			pageSize: 20,
			query: '',
			sortOrder: 'ASC',
			sortField: 'PRICE'
		};

		self.itemsLoading = false;
		self.itemResult = {};

		self.itemsOrder = 'PRICE-ASC';

		self.updateItemsQuery = _.debounce(updateItemsQuery, 400);
		self.onItemsOrderChange = onItemsOrderChange;

		// ///////

		IdService.profile(self.params.username).then(function (user) {

			self.user = user;
			self.user.totalTransactions = user.approvedTransactions + user.declinedTransactions;
			self.user.totalPurchases = user.approvedPurchases + user.declinedPurchases;
		}, function (err) {
			console.error(err);
		});

		publishedItems();

		// ///////

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
			IdService.published(self.params.username, self.itemsQuery).then(function (result) {
				console.log(result);
				self.itemResult = result;
				self.itemsLoading = false;
			}, function (err) {
				console.error(err);
			});
		}

	});
});
