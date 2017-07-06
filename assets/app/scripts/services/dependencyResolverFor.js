'use strict';
/*
 * With this method we enable lazy loading of AngularJS dependencies using RequireJS.
 * We do it by returning a promise to the dependencies passed as parameter, to be
 * used as routes in the routeProvider. They will be resolved when needed.
 */
 define([], function() {

 	return function(dependencies) {
 		console.log('dependencyResolverFor', dependencies);
 		var definition = {
 			resolver: ['$q', '$rootScope', function($q, $rootScope) {
 				var deferred = $q.defer();
 				// dependencies = allDependencies;
 				require(dependencies, function() {
 					console.log('resolved', dependencies);
					deferred.resolve();
 					$rootScope.$apply(function() {
 						
 					});
 				}, function (err) {
 					console.log(err);
 					console.log(err.requireModules);
 					_.each(err.requireModules, requirejs.undef);
 				});

 				return deferred.promise;
 			}]
 		};

 		return definition;
 	};
 });
