'use strict';
define([
	'siglasApp',
	'services/CategoryService',
	'services/ItemService'
], function(siglasApp) {

	siglasApp.controller('ImagesCtrl', function ($scope, $rootScope, $route, $location, $q, toastr, CategoryService, ItemService) {
		
		console.log('ImagesCtrl');

		$scope._ = _;

		var self = this;

		console.log($route);

		var id = $route.current.pathParams.id;

		self.remove = remove;
		self.submit = submit;


		// //////////
	
		function _base64ToArrayBuffer(base64) {
			var binaryString = window.atob(base64);
			var len = binaryString.length;
			var bytes = new Uint8Array(len);
			for (var i = 0; i < len; i++) {
				bytes[i] = binaryString.charCodeAt(i);
			}
			return bytes;
		}

		function dataURItoBlob(dataURI, mimeString) {
			// convert base64/URLEncoded data component to raw binary data held in a string
			var byteString = byteString = atob(dataURI);
			// if (dataURI.split(',')[0].indexOf('base64') >= 0) {
				
			// } else {
   //      byteString = unescape(dataURI.split(',')[1]);
			// }

			// separate out the mime component
			// var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

			// write the bytes of the string to a typed array
			var ia = new Uint8Array(byteString.length);
			for (var i = 0; i < byteString.length; i++) {
				ia[i] = byteString.charCodeAt(i);
			}

			return new Blob([ia], {type:mimeString});
		}

		ItemService.findById(id).then(function (item) {
			console.log(item);
			self.item = item;
			self.item.images = _.map(self.item.images, function (image) {
				return dataURItoBlob(image.content, image.mimeType);
				console.log(dataURItoBlob(image.content, image.mimeType));
				return new File(dataURItoBlob(image.content, image.mimeType), Date.now().toString(), {
					type: image.mimeType
				});
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
				toastr.success('Images have uploaded successfully');
				// $location.path('/store/item/')
			}, function (err) {
				console.error(err);
			});

		}

	});
});