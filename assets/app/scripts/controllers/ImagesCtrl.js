'use strict';
define([
	'siglasApp',
	'services/CategoryService',
	'services/ItemService'
], function(siglasApp) {

	siglasApp.controller('ImagesCtrl', function ($scope, $rootScope, $route, $location, $q, toastr, CategoryService, ItemService, $filter) {
		
		console.log('ImagesCtrl');

		$scope._ = _;

		var self = this;

		console.log($route);

		var id = $route.current.pathParams.id;

		self.remove = remove;
		self.submit = submit;


		// //////////

		function dataURItoBlob(dataURI, mimeString) {
			// convert base64/URLEncoded data component to raw binary data held in a string
			var byteString = atob(dataURI);
			var ia = new Uint8Array(byteString.length);
			for (var i = 0; i < byteString.length; i++) {
				ia[i] = byteString.charCodeAt(i);
			}

			return new Blob([ia], {type: mimeString});
		}

		ItemService.findById(id).then(function (item) {
			console.log(item);
			self.item = item;
			self.item.images = _.map(self.item.images, function (image) {
				return dataURItoBlob(image.content, image.mimeType);
			});
		}, function (err) {
			console.error(err);
		});

		// /////////

		function remove(index) {
			self.item.images.splice(index, 1);
		}

		function submit() {

			ItemService.uploadImage(id, self.item.images).then(function (results) {
				console.log(results);
				toastr.success($filter('translate')('ng.messages.imageSuccess'));
				// $location.path('/store/item/')
			}, function (err) {
				console.error($filter('translate')('ng.messages.imageError'));
			});

		}

	});
});