'use strict';
define([
	'siglasApp'
], function(siglasApp) {

	siglasApp.controller('IndexCtrl', function($scope, $rootScope, $location) {

		console.log('IndexCtrl');

		var self = this;

		self.hola = 'hola';
	});
});
