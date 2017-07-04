'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('UserService', function($http, $q, localStorageService, jwtHelper) {

		var User = {};

		User.update = update;
		User.changePassword = changePassword;


		var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return 'http://' + HOST + path;
		};

		// ///////

		function update(delta) {

			var defer = $q.defer();

			$http({
				method: 'PUT',
				url: api('/api/me'),
				data: JSON.stringify(delta)
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}
				console.log(response);
				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function changePassword(passwords) {

			var defer = $q.defer();

			$http({
				method: 'PUT',
				url: api('/api/me/password'),
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
				console.log(response);
				defer.resolve(response);
			}, function (error) {
				console.log(error);
				defer.reject(error);
			});

			return defer.promise;
		}

		return User;
	});

});