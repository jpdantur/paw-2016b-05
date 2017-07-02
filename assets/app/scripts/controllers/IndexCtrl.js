'use strict';
define([
	'siglasApp',
	'services/AuthService'
], function(siglasApp) {

	siglasApp.controller('IndexCtrl', function($scope, $rootScope, $location, $route, AuthService, toastr, $translate) {

		console.log('IndexCtrl');

		var self = this;

		console.log($route);

		self.$route = $route;
		self.$location = $location;

		self.logout = logout;

		// /////////////////////////////
	
		// /////////////////////////////

		function logout() {
			AuthService.logout().then(function () {
				$rootScope.loggedUser = null;
				toastr.success('Logout successful');
			});
		}
	});
});
