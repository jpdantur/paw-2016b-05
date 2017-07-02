'use strict';
define([
	'siglasApp'
], function(siglasApp) {

	siglasApp.controller('NotFoundCtrl', function($scope, $rootScope) {
		var self = this;

		if ($rootScope.loggedUser) {
			self.path = '/dashboard';
		} else {
			self.path = '/';
		}
	});
});
