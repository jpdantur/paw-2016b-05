'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/FavouritesService',
	'services/IdService',
	'directives/star-rating'
], function(siglasApp) {

	siglasApp.controller('ItemCtrl', function($scope, $rootScope, $location, $route, $q, toastr, ItemService, FavouritesService, IdService) {

		console.log('ItemCtrl');

		console.log($route);

		$scope._ = _;
		$scope.$location = $location;

		var id = $route.current.pathParams.id;

		var self = this;

		self.isFavourite = null;
		self.isPublished = null;
		self.isPurchased = null;
		self.related = null;
		self.comment = {
			rating: 2.5
		};
		self.owner = {
			sellerRating: 2.5
		};

		self.postComment = postComment;

		self.addFavourite = addFavourite;

		// ///////

		$q.all({
			item: ItemService.findById(id),
			favourites: $rootScope.loggedUser ? FavouritesService.all() : Promise.resolve([]),
			published: $rootScope.loggedUser ? ItemService.allPublished() : Promise.resolve([]),
			purchased: $rootScope.loggedUser ? ItemService.pendingPurchases() : Promise.resolve([]),
			comments: ItemService.comments(id),
			related: ItemService.related(id, {
				pageNumber: 0,
				pageSize: 4
			})
		}).then(function (results) {
			self.storeItem = results.item;
			// self.favs = results.favourites;
			
			console.log(results);

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

			self.related = results.related.results;

			console.log(self.isFavourite);
			console.log(self.isPublished);
			console.log(self.isPurchased);
			console.log(self.related);

			IdService.profile(self.storeItem.owner.username).then(function (result) {
				console.log(result);
				self.owner = result;
			}, function (err) {
				console.error(err);
			});
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
				console.log(self.comment);
				ItemService.addComment(self.storeItem.id, self.comment).then(function (result) {
					console.log(result);
					toastr.success('Your review has been submitted');
					self.storeItem.comments.unshift(result);
				}, function (err) {
					toastr.error('An error ocurred posting your review');
					console.error(err);
				});
			}
		}

		function addFavourite(id) {
			FavouritesService.add(id).then(function (result) {

				if (!$rootScope.loggedUser.favourites.hasMore) {
					$rootScope.loggedUser.favourites.push(result);
				}

				self.isFavourite = result;

				toastr.success('Favourite added succesfully');
			}, function (err) {
				toastr.error('Couldn\'t add favourite');
			});
		}

		$scope.$on('fav.remove', function (e, id) {
			if (self.isFavourite && self.isFavourite.id === id) {
				self.isFavourite = null;
			}
		});

	});
});
