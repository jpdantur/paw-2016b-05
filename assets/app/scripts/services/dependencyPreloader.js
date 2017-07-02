'use strict';
define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('dependencyPreloader', function ($q) {

		this.load = function (dependency) {
			console.log('dependency preloader', dependency);
			var deferred = $q.defer();
			require(['controllers/' + dependency], function() {
				console.log('resolving', dependency);
				deferred.resolve();
			});

			return deferred.promise;
		};
	});
});
