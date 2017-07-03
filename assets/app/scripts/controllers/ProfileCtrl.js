'use strict';
define([
	'siglasApp',
	'services/AuthService'
], function(siglasApp) {

	siglasApp.controller('ProfileCtrl', function($scope, $rootScope, $location, AuthService) {

		console.log('ProfileCtrl');

		var self = this;

		self.selectedTab = 'account';

		self.switchToTab = switchToTab;

		self.user = {
			email: $rootScope.loggedUser.email
		};

		self.changeEmail = changeEmail;

		// ///////

		// ///////
		
		function switchToTab(tab) {
			self.selectedTab = tab;
		}

		function changeEmail() {
		}
	});
});
