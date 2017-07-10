'use strict';
define([
	'siglasApp'
], function (siglasApp) {

	siglasApp.service('dependencyPreloader', function ($q) {

		this.load = function (dependency) {

			var deferred = $q.defer();
			require(['controllers/' + dependency], function() {

				deferred.resolve();
			});

			return deferred.promise;
		};
	});
});
