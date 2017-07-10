'use strict';
define([
	'siglasApp',
	'services/SalesService',
	'directives/star-rating'
], function(siglasApp) {

	siglasApp.controller('ScoreCtrl', function ($scope, $rootScope, $route, toastr, SalesService, $filter) {
		
		console.log('ScoreCtrl');

		var self = this;

		var txId = $route.current.params.txId;

		self.role = $route.current.params.role;
		self.tx = null;

		self.rateSale = rateSale;

		// //////////
	

		SalesService.findById(txId).then(function (tx) {
			var prop = self.role + 'Review';
			if (!tx[prop]) {
				console.log('setting');
				_.set(tx, prop, {rating: 2.5});
			}
			self.tx = tx;
			console.log(self.tx);
			console.log(tx);
		}, function (error) {
			console.error(error);
		});

		// /////////

		function rateSale(valid) {
			if (valid) {
				SalesService.rateWithRole(self.tx.id, self.role, {
					rating: self.role === 'buyer' ? self.tx.buyerReview.rating : self.tx.sellerReview.rating,
					comment: self.comment
				}).then(function (result) {
					console.log(result);
					toastr.success($filter('translate')('ng.messages.reviewSuccess'));
					self.tx[self.role + 'Review'].id = -1;
				}, function (err) {
					toastr.error($filter('translate')('ng.messages.reviewError'));
				});
			}
		}

	});
});