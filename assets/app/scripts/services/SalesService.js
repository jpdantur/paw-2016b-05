'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('SalesService', function($http, $q, localStorageService, jwtHelper) {

		var Sales = {};

		Sales.mine = mine;

		Sales.approve = approve;
		Sales.decline = decline;

		Sales.findById = findById;

		Sales.rateWithRole = rateWithRole;

		var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return 'http://' + HOST + path;
		};

		// ///////

		function mine(query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/sales'),
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
				console.log(response);
				defer.resolve(response.data);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		function approve(id) {
			var defer = $q.defer();

			$http({
				method: 'PUT',
				url: api('/api/store/sales/' + id + '/approve')
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

		function decline(id) {
			var defer = $q.defer();

			$http({
				method: 'PUT',
				url: api('/api/store/sales/' + id + '/decline')
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

		function findById(id) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/store/sales/' + id)
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

		function rateWithRole(id, role, rating) {
			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/store/sales/' + id + '/' + role + '-review'),
				data: rating
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



		return Sales;
	});

});