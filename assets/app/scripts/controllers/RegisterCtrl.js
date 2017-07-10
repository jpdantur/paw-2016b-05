'use strict';
define([
	'siglasApp',
	'services/AuthService',
	'directives/password-check'
], function(siglasApp) {

	siglasApp.controller('RegisterCtrl', function($scope, $rootScope, $location, $timeout, toastr, AuthService, $filter) {

		console.log('RegisterCtrl');

		var self = this;

		self.user = {};
		self.loading = false;

		self.submit = submit;

		if ($rootScope.loggedUser) {
			return $location.path('/');
		}

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
					toastr.error($filter('translate')('mg.messages.registerError'));
				}).then(function (profile) {
					$rootScope.loggedUser = profile;

					// console.log($location);

					$location.path($location.$$search.next);

					self.loading = false;

					toastr.success($filter('translate')('mg.messages.registerSuccess'));

				}, function (err) {
					console.error(err);
					toastr.error($filter('translate')('mg.messages.registerError'));
				});
			}
		}

	});
});
