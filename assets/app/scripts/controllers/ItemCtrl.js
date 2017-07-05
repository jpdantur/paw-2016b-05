'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/FavouritesService',
	'directives/star-rating'
], function(siglasApp) {

	siglasApp.controller('ItemCtrl', function($scope, $rootScope, $location, $route, $q, ItemService, FavouritesService) {

		console.log('ItemCtrl');

		console.log($route);

		$scope._ = _;
		$scope.$location = $location;

		var id = $route.current.pathParams.id;

		var self = this;

		self.isFavourite = null;
		self.isPublished = null;
		self.isPurchased = null;
		self.comment = {
			rating: 2.5
		};

		// ///////

		$q.all({
			item: ItemService.findById(id),
			favourites: $rootScope.loggedUser ? FavouritesService.all() : Promise.resolve([]),
			published: $rootScope.loggedUser ? ItemService.allPublished() : Promise.resolve([]),
			purchased: $rootScope.loggedUser ? ItemService.pendingPurchases() : Promise.resolve([]),
			comments: ItemService.comments(id)
		}).then(function (results) {
			self.storeItem = results.item;
			// self.favs = results.favourites;
			self.isFavourite = _.find(results.favourites, function (fav) {
				return fav.item.id === self.storeItem.id;
			});
			self.isPublished = _.find(results.published, function (i) {
				return i.id === self.storeItem.id;
			});
			self.isPurchased = _.find(results.purchased, function (p) {
				return p.id === self.storeItem.id;
			});

			self.storeItem.comments = results.comments;

			console.log(self.isFavourite);
			console.log(self.isPublished);
			console.log(self.isPurchased);
			// self.favouriteIds = _.map(results.favourites, function (fav) {
				// return {itemId: fav.item.id, favId: fav.id};
			// });
			// self.publishedIds = _.map(results.published, 'id');
		}, function (err) {
			console.error(err);
		});

		// ItemService.findById(id).then(function (result) {
			
		// }, function (err) {
		// 	console.error(err);
		// });

		// ///////
		
		function postComment(valid) {
			if (valid) {
				ItemService.addComment(self.storeItem.id, self.comment).then(function (result) {
					console.log(result);
				}, function (err) {
					console.error(err);
				})
			}
		}

	});
});
