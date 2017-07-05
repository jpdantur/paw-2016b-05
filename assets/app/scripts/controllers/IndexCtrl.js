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

		self.query = null;

		self.performQuery = performQuery;

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
				$location.path('/store/items/all').search({query:self.query});
			}
		}

	});
});
