'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('IdService', function($http, $q, localStorageService, jwtHelper) {

		var Id = {};

		Id.mostSold = mostSold;

		Id.published = published;
		Id.allPublished = allPublished;

		Id.findById = findById;
		Id.update = update;
		Id.create = create;

		Id.uploadImage = uploadImage;


		var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return 'http://' + HOST + path;
		};

		// ///////

		function mostSold(limit) {

			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/store/most-sold'),
				params: {
					limit: limit
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
				console.log(response);
				defer.resolve(response.data.items);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		return Id;
	});

});