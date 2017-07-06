'use strict';
define([
	'siglasApp',
	'services/UserService',
	'services/ItemService',
	'services/SalesService',
	'services/PurchasesService',
	'services/FavouritesService',
	'directives/password-check',
	'ngBootbox'
], function(siglasApp) {

	siglasApp.controller('ProfileCtrl', function($scope, $rootScope, $route, $location, $ngBootbox, toastr, UserService, ItemService, SalesService, PurchasesService, FavouritesService) {

		console.log('ProfileCtrl');

		$scope.Math = window.Math;

		var self = this;

		console.log($route);

		self.selectedTab = $route.current.params.tab || 'account';

		self.switchToTab = switchToTab;

		// email tab
		self.user = {
			firstName: $rootScope.loggedUser.firstName,
			lastName: $rootScope.loggedUser.lastName,
			email: $rootScope.loggedUser.email
		};

		// password tab
		self.pass = {};

		// items tab

		self.itemsQuery = {
			pageNumber: 0,
			pageSize: 20,
			query: '',
			status: 'ANY',
			sortOrder: 'ASC',
			sortField: 'PRICE'
		};

		self.itemsLoading = false;
		self.itemResult = {};

		self.itemsOrder = 'PRICE-ASC';

		// sales tab
		
		self.salesQuery = {
			pageNumber: 0,
			pageSize: 20,
			query: '',
			status: 'PENDING',
			sortOrder: 'ASC',
			sortField: 'PRICE'
		};

		self.salesLoading = false;
		self.salesResult = {};

		self.salesOrder = 'PRICE-ASC';

		self.approveSale = approveSale;
		self.declineSale = declineSale;

		self.showScoresAsSeller = showScoresAsSeller;

		// purchases tab

		self.purchasesQuery = {
			pageNumber: 0,
			pageSize: 20,
			query: '',
			status: 'PENDING',
			sortOrder: 'ASC',
			sortField: 'PRICE'
		};

		self.purchasesLoading = false;
		self.purchasesResult = {};

		self.purchasesOrder = 'PRICE-ASC';

		// favs tab

		self.favouritesQuery = {
			pageNumber: 0,
			pageSize: 20,
			query: '',
			sortOrder: 'ASC',
			sortField: 'PRICE'
		};

		self.favouritesLoading = false;
		self.favouritesResult = {};

		self.favouritesOrder = 'PRICE-ASC';

		self.removeFavourite = removeFavourite;

		// methods
		
		self.changeEmail = changeEmail;
		self.changePassword = changePassword;
		self.publishedItems = publishedItems;

		self.updateItemsQuery = _.debounce(updateItemsQuery, 400);
		self.onItemsOrderChange = onItemsOrderChange;

		self.setPublicationStatus = setPublicationStatus;



		self.updateSalesQuery = _.debounce(updateSalesQuery, 400);
		self.onSalesOrderChange = onSalesOrderChange;



		self.updatePurchasesQuery = _.debounce(updatePurchasesQuery, 400);
		self.onPurchasesOrderChange = onPurchasesOrderChange;

		self.updateFavouritesQuery = _.debounce(updateFavouritesQuery, 400);
		self.onFavouritesOrderChange = onFavouritesOrderChange;

		// ///////

		publishedItems();
		sales();
		purchases();
		favourites();

		// ///////

		function switchToTab(tab) {
			$location.search({tab: tab});
			self.selectedTab = tab;
		}

		function changeEmail(isValid) {

			console.log(self.user);
			console.log($rootScope.loggedUser.email);

			if (isValid) {
				UserService.update(self.user).then(function (user) {
					console.log(user);
					$rootScope.loggedUser.email = user.email;
					toastr.success('Email was successfully updated');
				}, function (err) {
					toastr.error('There was an error updating your email. Please try again');
				});
			}
		}

		function changePassword(isValid) {

			// return console.log(self.pass);

			if (isValid) {
				UserService.changePassword(self.pass).then(function () {
					// console.log(user);
					// $rootScope.loggedUser.email = user.email;
					toastr.success('Password was successfully updated');
				}, function (err) {
					toastr.error('There was an error changing your password. Please try again');
				});
			}

		}

		// items

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
			ItemService.published(self.itemsQuery).then(function (result) {
				console.log(result);
				self.itemResult = result;
				self.itemsLoading = false;
			}, function (err) {
				console.error(err);
			});
		}

		function setPublicationStatus(originalItem, status) {
			var i = _.extend({}, originalItem, {
				category: originalItem.category.id,
				owner: originalItem.owner.id,
				status: status
			});
			console.log(i);
			ItemService.update(i).then(function (item) {
				console.log(item);
				angular.extend(originalItem, item);
				toastr.success('Successfully updated publication');
			}, function (err) {
				console.error(err);
			});
		}



		// sales
	
		function updateSalesQuery(delta) {
			_.extend(self.salesQuery, delta);
			sales();
		}

		function onSalesOrderChange() {
			var parts = self.salesOrder.split('-');
			updateSalesQuery({pageNumber: 0, sortOrder: parts[1], sortField: parts[0]});
		}

		function sales() {
			self.salesLoading = true;
			SalesService.mine(self.salesQuery).then(function (result) {
				console.log(result);
				self.salesResult = result;
				self.salesLoading = false;
			}, function (err) {
				console.error(err);
			});
		}

		function approveSale(item) {
			SalesService.approve(item.id).then(function (result) {
				console.log(result);
				item.status = 'APPROVED';
				toastr.success('Purchase successfully approved');
			}, function (err) {
				console.error(err);
				toastr.error('There was an error approving your sale');
			});
		}

		function declineSale(item) {
			SalesService.decline(item.id).then(function (result) {
				console.log(result);
				item.status = 'DECLINED';
				toastr.success('Purchase successfully declined');
			}, function (err) {
				console.error(err);
				toastr.error('There was an error declining your sale');
			});
		}

		function showScoresAsSeller(transaction) {
			$location.search(angular.extend({}, $route.current.params, {txId: transaction.id, role: 'seller'}));
		}

		// purchases

		function updatePurchasesQuery(delta) {
			_.extend(self.purchasesQuery, delta);
			purchases();
		}

		function onPurchasesOrderChange() {
			var parts = self.purchasesOrder.split('-');
			updatePurchasesQuery({pageNumber: 0, sortOrder: parts[1], sortField: parts[0]});
		}

		function purchases() {
			self.purchasesLoading = true;
			PurchasesService.mine(self.purchasesQuery).then(function (result) {
				console.log(result);
				self.purchasesResult = result;
				self.purchasesLoading = false;
			}, function (err) {
				console.error(err);
			});
		}

		// favourites
		
		function updateFavouritesQuery(delta) {
			_.extend(self.favouritesQuery, delta);
			favourites();
		}

		function onFavouritesOrderChange() {
			var parts = self.favouritesOrder.split('-');
			updateFavouritesQuery({pageNumber: 0, sortOrder: parts[1], sortField: parts[0]});
		}

		function favourites() {
			self.favouritesLoading = true;
			FavouritesService.mine(self.favouritesQuery).then(function (result) {
				console.log(result);
				self.favouritesResult = result;
				self.favouritesLoading = false;
			}, function (err) {
				console.error(err);
			});
		}

		function removeFavourite(favId) {
			FavouritesService.remove(favId).then(function (result) {
				self.favouritesResult.results = _.filter(self.favouritesResult.results, function (fav) {
					return fav.id !== favId;
				});
				$rootScope.loggedUser.favourites = _.filter($rootScope.loggedUser.favourites, function (fav) {
					return fav.id !== favId;
				});
				$rootScope.loggedUser.favourites.hasMore = $rootScope.loggedUser.favourites.length > 8;
				toastr.success('Favourite successfully removed');
			}, function (err) {
				console.error(err);
				toastr.error('Couldn\'t remove favourite');
			});
		}

		$scope.$on('fav.remove', function (e, id) {
			console.log('fav.remove');
			self.favouritesResult.results = _.filter(self.favouritesResult.results, function (fav) {
				return fav.id !== id;
			});
		});


	});
});
