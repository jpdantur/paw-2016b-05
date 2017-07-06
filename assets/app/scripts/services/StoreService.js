'use strict';

define(['siglasApp'], function(siglasApp) {

		siglasApp.service('StoreService', function ($http, $q, HOST) {

			var StoreService = {};

			StoreService.search = search;
			StoreService.purchase = purchase;

			// var HOST = 'localhost:8081/webapp';

			var api = function api(path) {
				return 'http://' + HOST + path;
			};

			// //////

			function search(query) {
				var defer = $q.defer();

				$http({
					method: 'GET',
					url: api('/api/store/search'),
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

			function purchase(item) {
				var defer = $q.defer();

				$http({
					method: 'PUT',
					url: api('/api/store/item/' + item.id + '/purchase')
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


			return StoreService;
		});

});