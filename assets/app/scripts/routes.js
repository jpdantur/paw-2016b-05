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
			'/auth/login': {
				templateUrl: 'views/LoginCtrl.html',
				controller: 'LoginCtrl',
				controllerAs: 'login'
			},
			'/auth/register': {
				templateUrl: 'views/RegisterCtrl.html',
				controller: 'RegisterCtrl',
				controllerAs: 'register'
			},
			'/auth/forgot-pass': {
				templateUrl: 'views/ForgotPassCtrl.html',
				controller: 'ForgotPassCtrl',
				controllerAs: 'pass'
			},
			'/auth/reset-pass': {
				templateUrl: 'views/ResetPassCtrl.html',
				controller: 'ResetPassCtrl',
				controllerAs: 'reset'
			},
			'/profile/details': {
				templateUrl: 'views/ProfileCtrl.html',
				controller: 'ProfileCtrl',
				controllerAs: 'profile',
				controllers: ['ScoreCtrl']
			},
			'/store/sell/details': {
				templateUrl: 'views/SellCtrl.html',
				controller: 'SellCtrl',
				controllerAs: 'sell'
			},
			'/store/sell/images/:id': {
				templateUrl: 'views/ImagesCtrl.html',
				controller: 'ImagesCtrl',
				controllerAs: 'images'
			},
			'/store/items/all': {
				templateUrl: 'views/AllCtrl.html',
				controller: 'AllCtrl',
				controllerAs: 'all'
			},
			'/store/items/:id': {
				templateUrl: 'views/ItemCtrl.html',
				controller: 'ItemCtrl',
				controllerAs: 'item'
			},
			'/store/item/:id/details': {
				templateUrl: 'views/EditCtrl.html',
				controller: 'EditCtrl',
				controllerAs: 'edit'
			},
			'/id/:username/:role': {
				templateUrl: 'views/IdCtrl.html',
				controller: 'IdCtrl',
				controllerAs: 'id'
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
