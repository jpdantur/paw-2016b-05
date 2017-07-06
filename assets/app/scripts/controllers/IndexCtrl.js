'use strict';
define([
	'siglasApp',
	'services/AuthService',
	'services/FavouritesService'
], function(siglasApp) {

	siglasApp.controller('IndexCtrl', function($scope, $rootScope, $location, $route, AuthService, toastr, $translate, FavouritesService) {

		console.log('IndexCtrl');

		var self = this;

		self.$route = $route;
		self.$location = $location;

		self.logout = logout;

		self.query = $location.search().query;

		self.performQuery = performQuery;

		self.removeFavourite = removeFavourite;

		// /////////////////////////////
	
		// /////////////////////////////

		function logout() {
			AuthService.logout().then(function () {
				$rootScope.loggedUser = null;
				toastr.success('Logout successful');
				$location.path('/');
			});
		}


		function performQuery() {
			if ($location.path() === '/store/items/all') {
				$scope.$broadcast('query.update', self.query);
			} else {
				$location.path('/store/items/all').search({query: self.query});
			}
		}

		function removeFavourite(favId) {
			FavouritesService.remove(favId).then(function (result) {
				$rootScope.loggedUser.favourites = _.filter($rootScope.loggedUser.favourites, function (fav) {
					return fav.id !== favId;
				});
				$rootScope.loggedUser.favourites.hasMore = $rootScope.loggedUser.favourites.length > 8;

				$scope.$broadcast('fav.remove', favId);
				toastr.success('Favourite successfully removed');
			}, function (err) {
				console.error(err);
				toastr.error('Couldn\'t remove favourite');
			});
		}

	});
});
