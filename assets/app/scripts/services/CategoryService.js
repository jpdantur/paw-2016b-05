'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('CategoryService', function($http, $q, localStorageService, jwtHelper) {

		var Category = {};

		Category.tree = tree;


		var HOST = 'localhost:8081/webapp';

		var api = function api(path) {
			return 'http://' + HOST + path;
		};

		// ///////

		function tree() {

			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/store/category-tree')
			})
			.then(function (response) {
				if (response.status >= 400) {
					console.log(response.status);
					if (response.status >= 500) {
						return defer.reject(response.data);
					}
					return defer.reject(response.data);
				}

				defer.resolve(response.data.categories);
			}, function (error) {
				console.log(error);
				defer.reject(error.data);
			});

			return defer.promise;
		}

		return Category;
	});

});