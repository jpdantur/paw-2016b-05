'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('FavouritesService', function($http, $q, localStorageService, jwtHelper, HOST) {

		var Favourites = {};

		Favourites.mine = mine;
		Favourites.all = all;
		Favourites.remove = remove;
		Favourites.add = add;

		// var HOST = 'localhost:8081/webapp';

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
				defer.reject(error);
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
				defer.reject(error);
			});

			return defer.promise;
		}

		function add(id) {
			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/me/favourites/'),
				data: {
					item: id
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
				defer.reject(error);
			});

			return defer.promise;
		}

		function remove(id) {
			var defer = $q.defer();

			$http({
				method: 'DELETE',
				url: api('/api/me/favourites/' + id)
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
				defer.reject(error);
			});

			return defer.promise;
		}

		return Favourites;
	});

});