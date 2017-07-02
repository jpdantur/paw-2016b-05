'use strict';
define([
	'siglasApp'
], function(siglasApp) {

	siglasApp.controller('HomeCtrl', function($scope, $rootScope, $location) {

		console.log('HomeCtrl');

		var self = this;

		self.hola = 'hola';
	});
});
