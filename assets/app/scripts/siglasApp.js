'use strict';
define([
	'routes',
	'services/dependencyResolverFor',
	'services/protectedRoute',
	'i18n/i18nLoader!',
	'angular',
	'angular-route',
	'bootstrap',
	'angular-translate',
	'angular-local-storage',
	'angular-animate',
	'angular-toastr',
	'angular-jwt',
	'angular-bootstrap',
	'lodash',
	'async',
	'angular-moment',
	'angular-sanitize',
	'ngBootbox',
	'ng-file-upload'
], function(config, dependencyResolverFor, protectedRoute, i18n) {
		var siglasApp = angular.module('siglasApp', [
			'ngRoute',
			'ngSanitize',
			'pascalprecht.translate',
			'LocalStorageModule',
			'ngAnimate',
			'toastr',
			'angular-jwt',
			'ui.bootstrap',
			'angularMoment',
			'ngBootbox',
			'ngFileUpload'
		]);
		siglasApp
		.config([
			'$routeProvider',
			'$controllerProvider',
			'$compileProvider',
			'$filterProvider',
			'$provide',
			'$translateProvider',
			'$httpProvider',
			'localStorageServiceProvider',
			function($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, $httpProvider, localStorageServiceProvider) {

				siglasApp.controller = $controllerProvider.register;
				siglasApp.directive = $compileProvider.directive;
				siglasApp.filter = $filterProvider.register;
				siglasApp.factory = $provide.factory;
				siglasApp.service = $provide.service;

				if (config.routes !== undefined) {
					angular.forEach(config.routes, function(route, path) {
						var controllers = _.map([route.controller].concat(route.controllers || []), function (controller) {
							return 'controllers/' + controller;
						});
						$routeProvider.when(path, {
							templateUrl: route.templateUrl,
							resolve: {
								controllers: dependencyResolverFor(controllers).resolver,
								protected: protectedRoute(route).resolver
							},
							controller: route.controller,
							controllerAs: route.controllerAs,
							reloadOnSearch: false
						});
					});
				}
				if (config.defaultRoutePath !== undefined) {
					$routeProvider.otherwise({
						redirectTo: config.defaultRoutePath
					});
				}

				$translateProvider.translations('preferredLanguage', i18n);
				$translateProvider.preferredLanguage('preferredLanguage');
				$translateProvider.useSanitizeValueStrategy('sanitize');

				$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';

				localStorageServiceProvider.setPrefix('siglas');

				// taken from https://github.com/showpad/angular-q-spread/blob/master/src/q-spread.js
				$provide.decorator('$q', ['$delegate', function ($delegate) {

					var originalDefer = $delegate.defer;

					$delegate.defer = function () {
						// Get the prototype of the promise
						var promiseProto = originalDefer().promise.constructor.prototype;

						// Add the spread method
						Object.defineProperty(promiseProto, 'spread', {
							value: function (resolve, reject) {
								function spread (data) {
									return resolve.apply(null, data);
								}

								return this.then(spread, reject);
							},
							writable: true,
							enumerable: false
						});

						return originalDefer();
					};

					return $delegate;
				}]);
			}
		])
		.run(function ($rootScope, $location, $route, $q, $http, $window, AuthService, FavouritesService, amMoment) {

			amMoment.changeLocale('es');

			$rootScope.$on('$routeChangeStart', function (event, next, current) {
				console.log('$routeChangeStart ' + (current || {loadedTemplateUrl: 'none'}).loadedTemplateUrl + ' -> ' + next.loadedTemplateUrl);
			});
			$rootScope.$on('$routeChangeSuccess', function (event, next, current) {
				console.log('$routeChangeSuccess ' + (current || {loadedTemplateUrl: 'none'}).loadedTemplateUrl + ' -> ' + next.loadedTemplateUrl);
			});

			function onSync(profile) {
				console.log('profile', profile);
				if (profile) {
					$rootScope.loggedUser = profile;
					$rootScope.loggedUser.favourites = [];
				}

				FavouritesService.mine({
					pageNumber: 0,
					pageSize: 8,
					sortOrder: 'DESC',
					sortField: 'CREATED'
				}).then(function (result) {
					$rootScope.loggedUser.favourites = result.results;
					$rootScope.loggedUser.favourites.hasMore = result.numberOfTotalResults > result.numberOfAvailableResults;
				}, function (error) {
					console.error(error);
				});

				// if (profile && $location.path() === '/') {
				// 	$location.path('/dashboard');
				// } else if (!profile) {
				// 	$location.path('/');
				// }

				$rootScope.loading = false;

				$rootScope.$broadcast('siglas.login', $rootScope.loggedUser);
			}

			$rootScope.loading = true;

			AuthService
			.syncWithLocalStorage()
			.then(onSync, function (error) {
				console.log(error);
				$rootScope.loading = false;
				$location.path('/');
				$rootScope.loggedUser = null;
			});
		});


		return siglasApp;
	}
);
