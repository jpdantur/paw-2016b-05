'use strict';
define([
	'siglasApp',
	'services/AuthService',
	'directives/password-check'
], function(siglasApp) {

	siglasApp.controller('RegisterCtrl', function($scope, $rootScope, $location, $timeout, toastr, AuthService) {

		console.log('RegisterCtrl');

		var self = this;

		self.user = {};
		self.loading = false;

		self.submit = submit;

		// ///////

		// ///////
		
		function submit(isValid) {

			self.loading = true;

			if (isValid) {


				AuthService.register(self.user).then(function (data) {
					console.log(data);

					return AuthService.fetchProfile();

				}, function (err) {
					console.error(err);
					toastr.error('There was an error during registration');
				}).then(function (profile) {
					$rootScope.loggedUser = profile;

					// console.log($location);

					$location.path($location.$$search.next);

					self.loading = false;

					toastr.success('Successfully registered');

				}, function (err) {
					console.error(err);
					toastr.error('There was an error during registration');
				});
			}
		}

	});
});
