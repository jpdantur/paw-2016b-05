define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("dependencyPreloader", [ "$q", function($q) {
        this.load = function(dependency) {
            console.log("dependency preloader", dependency);
            var deferred = $q.defer();
            return require([ "controllers/" + dependency ], function() {
                console.log("resolving", dependency), deferred.resolve();
            }), deferred.promise;
        };
    } ]);
});