'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('PurchasesService', function($http, $q, localStorageService, jwtHelper, HOST) {

		var Purchase = {};

		Purchase.mine = mine;

		// var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return HOST + path;
		};

		// ///////

		function mine(query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/purchases'),
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

		return Purchase;
	});

});