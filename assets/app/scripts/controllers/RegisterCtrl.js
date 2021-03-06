'use strict';
define([
	'siglasApp',
	'services/AuthService',
	'directives/password-check'
], function(siglasApp) {

	siglasApp.controller('RegisterCtrl', function($scope, $rootScope, $location, $timeout, toastr, AuthService, $filter) {

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

					AuthService.fetchProfile().then(function (profile) {
						$rootScope.loggedUser = profile;

						$location.path($location.$$search.next);

						self.loading = false;

						toastr.success($filter('translate')('mg.messages.registerSuccess'));

					}, function (err) {
						console.error(err);
						toastr.error($filter('translate')('mg.messages.registerError'));
					});

				}, function (err) {

					self.loading = false;

					if (err.status === 409) {			

						return toastr.error($filter('translate')('UsernameExists.RegisterForm.username'));
					}

					console.error(err);
					toastr.error($filter('translate')('mg.messages.registerError'));
				});
			}
		}

	});
});
