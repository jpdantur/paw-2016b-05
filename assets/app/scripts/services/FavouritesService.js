'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('FavouritesService', function($http, $q, localStorageService, jwtHelper) {

		var Favourites = {};

		Favourites.mine = mine;
		Favourites.all = all;

		var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return 'http://' + HOST + path;
		};

		// ///////

		function mine(query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/favourites'),
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

		function all() {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/favourites/all')
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

		return Favourites;
	});

});