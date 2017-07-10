'use strict';
define([
	'siglasApp',
	'services/ItemService',
	'services/CategoryService'
], function(siglasApp) {

	siglasApp.controller('EditCtrl', function($scope, $rootScope, $location, $q, $route, ItemService, CategoryService, toastr, $filter) {

		var self = this;

		var id = $route.current.pathParams.id;

		$scope._ = _;

		self.selectedTab = $route.current.params.tab || 'details';

		self.switchToTab = switchToTab;
		self.item = null;

		self.categoryPath = [];
		self.highlighted = [];
		self.selectedCategory = null;

		self.selectCategory = selectCategory;

		self.submit = submit;

		self.remove = remove;
		self.submit2 = submit2;

		// ///////

		function dataURItoBlob(dataURI, mimeString) {
			// convert base64/URLEncoded data component to raw binary data held in a string
			var byteString = atob(dataURI);
			var ia = new Uint8Array(byteString.length);
			for (var i = 0; i < byteString.length; i++) {
				ia[i] = byteString.charCodeAt(i);
			}

			return new Blob([ia], {type: mimeString});
		}

		$q.all({
			item: ItemService.findById(id),
			category: CategoryService.tree()
		}).then(function (result) {
			self.item = result.item;
			self.item.images = _.map(self.item.images, function (image) {
				return dataURItoBlob(image.content, image.mimeType);
			});

			self.categories = result.category;
			self.categoryPath.push(self.categories);

			itemCategoryLookup(self.categories, 1);

		}, function (err) {
			console.error(err);
		});
		// ///////

		var stop = false;

		function itemCategoryLookup(categories, depth) {
			_.each(categories, function (category) {
				if (stop) {
					return;
				}
				if (category.id === self.item.category.id) {
					stop = true;
					return selectCategory(category, depth);
				}
				selectCategory(category, depth);
				itemCategoryLookup(category.children, depth + 1);
			});
		}

		function switchToTab(tab) {
			$location.search({tab: tab});
			self.selectedTab = tab;
		}

		function selectCategory(category, depth) {
			if (category.children.length) {
				self.selectedCategory = null;
				self.categoryPath.splice(depth, self.categoryPath.length - depth, category.children);
			} else {
				self.selectedCategory = category;
				self.categoryPath.splice(depth, self.categoryPath.length - depth);
			}
			self.highlighted = _.map(self.categoryPath, function (group) {
				return group[0].parent;
			});
		}

		function submit(valid) {
			if (valid) {
				_.extend(self.item, {category: self.selectedCategory.id});
				ItemService.update(self.item).then(function (result) {
					toastr.success($filter('translate')('ng.messages.editSuccess'));
					// $location.path('/store/sell/images').search({id: result.id});
				}, function (err) {
					console.error(err);
					toastr.error($filter('translate')('ng.messages.editError'));
					// toastr.error('An error ocurred. Please try again');
				});
			}
		}

		function remove(index) {
			self.item.images.splice(index, 1);
		}

		function submit2() {

			ItemService.uploadImage(id, self.item.images).then(function (results) {
				toastr.success($filter('translate')('ng.messages.imageSuccess'));
				// $location.path('/store/items/' + id);
			}, function (err) {
				toastr.error($filter('translate')('ng.messages.imageError'));
				console.error($filter('translate')('ng.messages.imageError'));
			});

		}

	});
});
