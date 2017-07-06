'use strict';

define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('ItemService', function($http, $q, localStorageService, jwtHelper, HOST) {

		var Item = {};

		Item.mostSold = mostSold;

		Item.published = published;
		Item.allPublished = allPublished;
		Item.pendingPurchases = pendingPurchases;

		Item.related = related;

		Item.findById = findById;
		Item.update = update;
		Item.create = create;

		Item.uploadImage = uploadImage;

		Item.comments = comments;
		Item.addComment = addComment;

		// var HOST = 'localhost:8081/webapp';

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
				defer.resolve(response.data.items);
			}, function (error) {
				console.log(error);
				defer.reject(error);
			});

			return defer.promise;
		}

		function published(query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/published/'),
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

		function allPublished(query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/published/all')
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

		function pendingPurchases(query) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/me/purchased/pending')
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

		function findById(id) {

			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/store/item/' + id)
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

		function related(id, query) {

			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/store/item/' + id + '/related'),
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

		function update(item) {

			var defer = $q.defer();

			$http({
				method: 'PUT',
				url: api('/api/store/item/' + item.id),
				data: item
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
				defer.reject(error);
			});

			return defer.promise;

		}

		function create(item) {

			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/store/item'),
				data: item
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
				defer.reject(error);
			});

			return defer.promise;

		}

		function uploadImage(id, files) {
			var defer = $q.defer();
			var formData = new FormData();
			_.each(files, function (file) {
				formData.append('images', file);
			});
			$http({
				method: 'PUT',
				url: api('/api/store/item/' + id + '/images'),
				data: formData,
				headers: {
					'Content-Type': undefined
				},
				transformRequest: angular.identity
			})
			.then(function (response) {
				console.log(response);
				defer.resolve(response.data);
			}, function (error) {
				defer.reject(error);
			});

			return defer.promise;
		}

		function comments(id) {
			var defer = $q.defer();

			$http({
				method: 'GET',
				url: api('/api/store/item/' + id + '/comments')
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

		function addComment(id, comment) {
			var defer = $q.defer();

			$http({
				method: 'POST',
				url: api('/api/store/item/' + id + '/comments'),
				data: comment
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

		return Item;
	});

});