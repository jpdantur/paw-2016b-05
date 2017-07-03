'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('AuthService', function($http, $q, localStorageService, jwtHelper) {

		var Auth = {};

		Auth.logIn = logIn;
		Auth.register = register;
		Auth.syncWithLocalStorage = syncWithLocalStorage;
		Auth.fetchProfile = fetchProfile;
		Auth.logout = logout;

		Auth.isTokenValid = isTokenValid;


		var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return 'http://' + HOST + path;
		};

		// ///////

		function setDefaultAuthorizationHeader(token) {
			$http.defaults.headers.common.Authorization = 'Bearer ' + token;
			$http.defaults.headers.common['X-Token'] = 'Bearer ' + token;
		}

		function removeDefaultAuthorizationHeader() {
			delete $http.defaults.headers.common.Authorization;
		}

		function logIn(username, password) {

			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/auth/login'),
				data: {
					username: username,
					password: password
				}
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}

				console.log(response.data);

				setDefaultAuthorizationHeader(response.data.idToken);

				console.log($http.defaults.headers.common.Authorization);

				console.log('setting local storage');
				localStorageService.set('tokens', response.data);

				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function register(user) {

			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/auth/register'),
				data: JSON.stringify(user)
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}

				console.log(response.data);

				setDefaultAuthorizationHeader(response.data.idToken);

				console.log($http.defaults.headers.common.Authorization);

				console.log('setting local storage');
				localStorageService.set('tokens', response.data);

				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function renewToken(tokens) {

			var defer = $q.defer();

			console.log('renewing token');

			$http({
				method: 'POST',
				url: api('/v1/auth/renew'),
				data: angular.extend({}, tokens, {
					appVersion: window.appVersion
				})
			})
			.then(function (response) {
				console.log('renew response', response);

				if (response.status >= 400) {
					console.log(response.status);

					// kickout user
					localStorageService.remove('tokens');
					// removeDefaultAuthorizationHeader();

					return logout()
						.then(function () {
							removeDefaultAuthorizationHeader();
							defer.reject(error);
						}, function (err) {
							removeDefaultAuthorizationHeader();
							defer.reject(err);
						});


					// if (response.status >= 500) {
					// 	return defer.reject(response.data);
					// }
					// return defer.reject(response.data);
				}

				setDefaultAuthorizationHeader(response.data.tokens.accessToken);
				
				console.log($http.defaults.headers.common.Authorization);

				console.log('setting local storage');
				localStorageService.set('tokens', response.data.tokens);

				defer.resolve(response.data.user);
			}, function (error) {
				console.log(error);

				// kickout user
				localStorageService.remove('tokens');
				// removeDefaultAuthorizationHeader();

				return logout()
					.then(function () {
						removeDefaultAuthorizationHeader();
						defer.reject(error);
					}, function (err) {
						removeDefaultAuthorizationHeader();
						defer.reject(err);
					});

				// defer.reject(error.data);
			});

			return defer.promise;
		}

		function fetchProfile() {

			var defer = $q.defer();

			console.log('fetching profile');

			console.log($http.defaults.headers.common.Authorization);

			$http({
				method: 'GET',
				url: api('/api/auth/profile')
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}
				console.log('got profile', response.data);
				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function syncWithLocalStorage() {

			var defer = $q.defer();

			console.log('syncWithLocalStorage');

			var tokens = localStorageService.get('tokens');

			console.log('current token is', tokens);

			if (tokens) {
				if (jwtHelper.isTokenExpired(tokens.idToken)) {
					console.log('token is expired');
					return renewToken(tokens);
				}

				console.log('token is ok');
				console.log('setting default $http Authorization header to ' + tokens.idToken);

				setDefaultAuthorizationHeader(tokens.idToken);

				return fetchProfile();
			}

			defer.resolve();

			return defer.promise;
		}

		function isTokenValid() {

			console.log('isTokenValid');

			var tokens = localStorageService.get('tokens');

			console.log('current token is', tokens);

			if (tokens) {
				return !jwtHelper.isTokenExpired(tokens.accessToken);
			}

			return false;

		}

		function logout() {

			var defer = $q.defer();

			console.log('logout');

			removeDefaultAuthorizationHeader();
			localStorageService.remove('tokens');

			defer.resolve();

			return defer.promise;

			// $http({
			// 	method: 'POST',
			// 	url: api('/v1/auth/logout')
			// })
			// .then(function (response) {
			// 	if (response.status >= 400) {
			// 		console.log(response.status);
			// 		if (response.status >= 500) {
			// 			return defer.reject(response.data);
			// 		}
			// 		return defer.reject(response.data);
			// 	}

				

			// 	defer.resolve();
			// }, function (error) {
			// 	console.log(error);
			// 	defer.reject(error.data);
			// });

			// return defer.promise;
		}

		return Auth;
	});

});