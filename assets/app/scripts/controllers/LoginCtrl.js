'use strict';
define([
	'siglasApp',
	'services/AuthService',
	'services/FavouritesService'
], function(siglasApp) {

	siglasApp.controller('LoginCtrl', function($scope, $rootScope, $location, toastr, AuthService, FavouritesService) {

		console.log('LoginCtrl');

		var self = this;

		self.password = '';
		self.username = '';

		self.signIn = signIn;

		if ($rootScope.loggedUser) {
			return $location.path('/');
		}

		// ///////

		// ///////
		
		function signIn() {

			AuthService.logIn(self.username, self.password).then(function (data) {
				console.log(data);

				return AuthService.fetchProfile();
			}).then(function (profile) {
				$rootScope.loggedUser = profile;
				$rootScope.loggedUser.favourites = [];

				FavouritesService.mine({
					pageNumber: 0,
					pageSize: 8,
					sortOrder: 'DESC',
					sortField: 'CREATED'
				}).then(function (result) {
					$rootScope.loggedUser.favourites = result.results;
					$rootScope.loggedUser.favourites.hasMore = result.numberOfTotalResults > result.numberOfAvailableResults;
				}, function (error) {
					console.error(error);
				});

				toastr.success('Successfully logged in');

				$location.path($location.$$search.next || '/');
			}, function (err) {
				toastr.error('Incorrect username or password');
				console.error(err);
			});
		}

	});
});
