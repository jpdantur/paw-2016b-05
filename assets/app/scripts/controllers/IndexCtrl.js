'use strict';
define([
	'siglasApp'
], function(siglasApp) {

	siglasApp.controller('IndexCtrl', function($scope, $rootScope, $location, $route) {

		console.log('IndexCtrl');

		var self = this;

		console.log($route);

		self.$route = $route;
		self.$location = $location;
	});
});
