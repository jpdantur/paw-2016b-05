'use strict';
/*
 * With this method we enable lazy loading of AngularJS dependencies using RequireJS.
 * We do it by returning a promise to the dependencies passed as parameter, to be
 * used as routes in the routeProvider. They will be resolved when needed.
 */
 define([], function() {

 	return function(route) {
 		var definition = {
 			resolver: ['$q', '$rootScope', '$location', 'AuthService', 'ItemService', function($q, $rootScope, $location, AuthService, ItemService) {
 				var deferred = $q.defer();



 				if (!route.protected) {

 					return deferred.resolve();
 				}



 				if (AuthService.isTokenValid()) {
 					return deferred.resolve();
 				}

 				AuthService
				.syncWithLocalStorage()
				.then(function (profile) {

					$q.when(route.protected.call({
						$q: $q,
						$rootScope: $rootScope,
						$location: $location,
						AuthService: AuthService,
						ItemService: ItemService
					}, profile)).then(function (path) {

						console.log(path);

						if (isFinite(path)) {
							$location.path('/' + path);
						} else if (_.isString(path)) {
							$location.path(path);
						}

						console.log($location.path());

						return deferred.resolve();
					}, function (err) {

						return deferred.resolve();
					});
				}, function (error) {
					console.log(error);
				});

 				return deferred.promise;
 			}]
 		};

 		return definition;
 	};
 });
