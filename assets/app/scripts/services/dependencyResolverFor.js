'use strict';
/*
 * With this method we enable lazy loading of AngularJS dependencies using RequireJS.
 * We do it by returning a promise to the dependencies passed as parameter, to be
 * used as routes in the routeProvider. They will be resolved when needed.
 */
 define([], function() {

 	return function(dependencies) {
 		console.log('dependencyResolverFor', dependencies);
 		var allDependencies = _.map(['ActivityCtrl', 'DashboardCtrl', 'GenresCtrl', 'HomeGenresCtrl', 'MediaPlayerCtrl', 'NotFoundCtrl', 'ChannelsCtrl', 'ForbiddenCtrl', 'HistoryCtrl', 'IndexCtrl', 'NewChannelsCtrl', 'SidebarCtrl', 'ClientsCtrl', 'GenreCtrl', 'HomeCtrl', 'LoginCtrl'], function (c) {
 			return 'controllers/' + c;
 		});
 		var definition = {
 			resolver: ['$q', '$rootScope', 'LOCAL_FILE', function($q, $rootScope, LOCAL_FILE) {
 				console.log('LOCAL_FILE', LOCAL_FILE);
 				var deferred = $q.defer();
 				dependencies = LOCAL_FILE ? allDependencies : dependencies;
 				// dependencies = allDependencies;
 				require(dependencies, function() {
 					console.log('resolving', dependencies);
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
