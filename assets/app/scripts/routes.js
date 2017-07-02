'use strict';

define([], function() {
	return {
		defaultRoutePath: '/404',
		routes: {
			'/': {
				templateUrl: 'views/HomeCtrl.html',
				controller: 'HomeCtrl',
				controllerAs: 'home'
			},
			'/login': {
				templateUrl: 'views/login/LoginCtrl.html',
				controller: 'LoginCtrl',
				controllerAs: 'login'
			},
			'/403': {
				templateUrl: 'views/403.html',
				controller: 'ForbiddenCtrl',
				controllerAs: 'forbiddenCtrl'
			},
			'/404': {
				templateUrl: 'views/404.html',
				controller: 'NotFoundCtrl',
				controllerAs: 'notFoundCtrl'
			}
			/* ===== yeoman hook ===== */
			/* Do not remove these commented lines! Needed for auto-generation */
		}
	};
});
