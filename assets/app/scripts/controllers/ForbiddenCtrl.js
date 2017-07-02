'use strict';
define([
	'pimixApp'
], function(siglasApp) {

	siglasApp.controller('ForbiddenCtrl', function($scope, $rootScope) {
		var self = this;

		if ($rootScope.loggedUser) {
			self.path = '/dashboard';
		} else {
			self.path = '/';
		}
	});
});
