'use strict';
define([
	'siglasApp',
	'services/AuthService'
], function(siglasApp) {

	siglasApp.controller('ForgotPassCtrl', function($scope, $rootScope, $location, $filter, toastr, AuthService) {

		var self = this;

		self.submit = submit;

		self.email = '';


		// ///////

		if ($rootScope.loggedUser) {
			return $location.path('/');
		}

		// ///////

		function submit(valid) {
			if (valid) {
				self.loading = true;
				AuthService.forgotPass(self.email).then(function (result) {
					toastr.success($filter('translate')('auth.forgotpass.success.message'), $filter('translate')('auth.forgotpass.success.title'), {
						tapToDismiss: true,
						autoDismiss: false
					});
					self.loading = false;
				}, function (err) {
					self.loading = false;
					toastr.error($filter('translate')('login.error.wrongData'), $filter('translate')('login.error.error'));
				});
			}
		}

	});
});
