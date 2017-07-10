'use strict';

define([
	'siglasApp',
	'services/IdService'
], function (siglasApp) {

	siglasApp.service('AuthService', function($http, $q, localStorageService, jwtHelper, IdService, md5, HOST) {

		var Auth = {};

		Auth.logIn = logIn;
		Auth.register = register;
		Auth.syncWithLocalStorage = syncWithLocalStorage;
		Auth.fetchProfile = fetchProfile;
		Auth.logout = logout;
		Auth.forgotPass = forgotPass;

		Auth.resetPassword = resetPassword;

		Auth.isPasswordTokenValid = isPasswordTokenValid;

		Auth.isTokenValid = isTokenValid;


		// var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return HOST + path;
		};

		// ///////

		function setDefaultAuthorizationHeader(token) {
			$http.defaults.headers.common.Authorization = 'Bearer ' + token;
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



			$http({
				method: 'POST',
				url: api('/v1/auth/renew'),
				data: angular.extend({}, tokens, {
					appVersion: window.appVersion
				})
			})
			.then(function (response) {


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

		function forgotPass(email) {
			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/auth/forgot-pass'),
				data: {
					email: email
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
				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function fetchProfile() {

			var defer = $q.defer();



			console.log($http.defaults.headers.common.Authorization);

			$http({
				method: 'GET',
				url: api('/api/me/profile')
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}

				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function syncWithLocalStorage() {

			var defer = $q.defer();



			var tokens = localStorageService.get('tokens');



			if (tokens) {
				if (jwtHelper.isTokenExpired(tokens.idToken)) {

					return renewToken(tokens);
				}




				setDefaultAuthorizationHeader(tokens.idToken);

				return fetchProfile();
			}

			defer.resolve();

			return defer.promise;
		}

		function isTokenValid() {



			var tokens = localStorageService.get('tokens');



			if (tokens) {
				return !jwtHelper.isTokenExpired(tokens.accessToken);
			}

			return false;

		}

		function logout() {

			var defer = $q.defer();



			removeDefaultAuthorizationHeader();
			localStorageService.remove('tokens');

			defer.resolve();

			return defer.promise;
		}

		var MAX_EXPIRY = 3 * 60 * 60 * 1000;

		function isPasswordTokenValid(username, token) {
			var defer = $q.defer();

			IdService.profile(username).then(function (result) {

				var timestamp = Date.now();
				var hexTimestamp = timestamp.toString(16);
				var usernameHash = md5.createHash(username);
				var generatedToken = hexTimestamp + usernameHash;

				if (generatedToken.length !== token.length) {
					return defer.reject('INVALID');
				}

				var tokenTimestamp = parseInt(token.substr(0, hexTimestamp.length), 16);

				if (tokenTimestamp - timestamp > 0) {
					return defer.reject('INVALID');
				}

				if (timestamp - tokenTimestamp > MAX_EXPIRY) {
					return defer.reject('EXPIRED');
				}

				var tokenUsername = generatedToken.substr(hexTimestamp.length);

				if (tokenUsername === token.substr(hexTimestamp.length)) {
					return defer.resolve();
				}

				return defer.reject('INVALID');

			}, function (err) {
				defer.reject(err);
			});

			return defer.promise;
		}

		function resetPassword(username, passwords) {

			var defer = $q.defer();

			$http({
				method: 'PUT',
				url: api('/api/id/' + username + '/reset-password'),
				data: JSON.stringify(passwords)
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}
				defer.resolve(response);
			}, function (error) {
				console.log(error);
				defer.reject(error);
			});

			return defer.promise;
		}

		// http://pawserver.it.itba.edu.ar/paw-2016b-05/auth/reset-pass?token=15d165ca3f152902899b410eb0e3ccd55686ba63b21&username=mgoffan

		return Auth;
	});

});