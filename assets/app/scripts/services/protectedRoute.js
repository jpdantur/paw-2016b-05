'use strict';
/*
 * With this method we enable lazy loading of AngularJS dependencies using RequireJS.
 * We do it by returning a promise to the dependencies passed as parameter, to be
 * used as routes in the routeProvider. They will be resolved when needed.
 */
 define([], function() {

 	return function(route) {
 		var definition = {
 			resolver: ['$q', '$rootScope', '$location', 'AuthService', function($q, $rootScope, $location, AuthService) {
 				var deferred = $q.defer();

 				if (!route.protected) {
 					console.log('route is not protected');
 					return deferred.resolve();
 				}

 				console.log('checking authentication state');

 				if (AuthService.isTokenValid()) {
 					return deferred.resolve();
 				}

 				AuthService
				.syncWithLocalStorage()
				.then(function (profile) {

					if (route.protected(profile)) {
						console.log('is authed');
						return deferred.resolve();
					}

					console.log('not authed');

					$location.path('/403');

					deferred.resolve();
				}, function (error) {
					console.log(error);
				});

 				return deferred.promise;
 			}]
 		};

 		return definition;
 	};
 });
