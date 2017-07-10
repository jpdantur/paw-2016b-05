'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/FavouritesService',
	'services/IdService',
	'services/StoreService',
	'directives/star-rating'
], function(siglasApp) {

	siglasApp.controller('ItemCtrl', function($scope, $rootScope, $location, $route, $q, toastr, ItemService, FavouritesService, IdService, StoreService, $filter) {

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

		self.purchase = purchase;

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

			if (_.includes(['UNPUBLISHED', 'PAUSED'], self.storeItem.status)) {
				if ($rootScope.loggedUser.id !== self.storeItem.owner.id) {
					return $location.path('/403');
				}
			}

			if (self.storeItem.status === 'UNPUBLISHED') {

				toastr.warning($filter('translate')('item.notifications.draft.message'), $filter('translate')('item.notifications.draft.title'), {
					onTap: function (toast) {
						$location.path('/store/item/' + self.storeItem.id + '/details');
					},
					tapToDismiss: true
				});
			}

			if (self.storeItem.status === 'PAUSED') {
				toastr.warning($filter('translate')('item.notifications.paused.alternative'), {
					onTap: function (toast) {
						$location.path('/store/item/' + self.storeItem.id + '/details');
					},
					tapToDismiss: true
				});
				toastr.warning($filter('translate')('item.notifications.paused.message'), $filter('translate')('item.notifications.paused.title'), {
					onTap: function (toast) {
						$location.path('/profile/details').search({tab: 'items'});
					},
					tapToDismiss: true
				});

			}

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

			IdService.profile(self.storeItem.owner.username).then(function (result) {
				self.owner = result;
			}, function (err) {
				console.error(err);
			});
		}, function (err) {
			console.error(err);
			if (err.status === 401) {
				$location.path('/403');
			}
			if (err.status === 403) {
				$location.path('/403');
			}
			if (err.status === 404) {
				$location.path('/404');
			}
		});


		// ///////
		
		function postComment(valid) {
			if (valid) {
				ItemService.addComment(self.storeItem.id, self.comment).then(function (result) {
					toastr.success($filter('translate')('ng.messages.reviewSuccess'));
					self.storeItem.comments.unshift(result);
				}, function (err) {
					toastr.error($filter('translate')('ng.messages.reviewError'));
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

				toastr.success($filter('translate')('successMessages.toggleFavourite.addSuccess'));
			}, function (err) {
				toastr.error($filter('translate')('successMessages.toggleFavourite.addError'));
			});
		}

		$scope.$on('fav.remove', function (e, id) {
			if (self.isFavourite && self.isFavourite.id === id) {
				self.isFavourite = null;
			}
		});

		function purchase(item) {
			self.purchasing = true;
			StoreService.purchase(item).then(function (result) {
				toastr.success($filter('translate')('successMessages.buyItem.success'));
				self.isPurchased = result;
			}, function (err) {
				self.purchasing = false;
				toastr.error($filter('translate')('successMessages.buyItem.error'));
				console.error(err);
			});
		}

	});
});
