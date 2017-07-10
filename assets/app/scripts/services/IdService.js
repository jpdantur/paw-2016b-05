'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('IdService', function($http, $q, localStorageService, jwtHelper, HOST) {

		var Id = {};

		Id.byUsername = byUsername;
		Id.profile = profile;
		Id.published = published;

		// var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return HOST + path;
		};

		// ///////

		function byUsername(username) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/id/' + username)
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

		function profile(username) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/id/' + username + '/profile')
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

		function published(username, query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/id/' + username + '/published'),
				params: query
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

		return Id;
	});

});