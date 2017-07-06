'use strict';
define([
	'siglasApp',
	'services/AuthService',
	'directives/password-check'
], function(siglasApp) {

	siglasApp.controller('ResetPassCtrl', function($scope, $rootScope, $location, $filter, toastr, AuthService) {

		console.log('ResetPassCtrl');

		var self = this;

		self.submit = submit;

		var params = $location.search();

		self.status = null;

		// ///////

		if ($rootScope.loggedUser) {
			return $location.path('/');
		}

		AuthService.isPasswordTokenValid(params.username, params.token).then(function (result) {
			self.status = 'OK';
		}, function (err) {
			console.error(err);
			if (_.isString(err)) {
				self.status = err;
			}
		});

		// ///////

		function submit(valid) {
			if (valid) {
				self.loading = true;
				AuthService.resetPassword(params.username, self.pass).then(function () {
					// console.log(user);
					// $rootScope.loggedUser.email = user.email;
					toastr.success('Password was successfully updated');
					$location.path('/auth/login');
				}, function (err) {
					self.loading = false;
					toastr.error('There was an error changing your password. Please try again');
				});
			}
		}

	});
});
