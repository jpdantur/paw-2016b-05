'use strict';
define([
	'siglasApp',
	'services/AuthService'
], function(siglasApp) {

	siglasApp.controller('LoginCtrl', function($scope, $rootScope, $location, AuthService) {

		console.log('LoginCtrl');

		var self = this;

		self.password = '';
		self.username = '';

		self.signIn = signIn;

		// ///////

		// ///////
		
		function signIn() {
			console.log({
				password: self.password,
				username: self.username
			});

			AuthService.logIn(self.username, self.password).then(function (data) {
				console.log(data);

				return AuthService.fetchProfile();
			}, function (err) {
				console.error(err);
			}).then(function (profile) {
				$rootScope.loggedUser = profile;
			}, function (err) {
				console.error(err);
			});
		}

	});
});
