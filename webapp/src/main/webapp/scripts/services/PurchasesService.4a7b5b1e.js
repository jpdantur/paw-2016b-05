define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("PurchasesService", [ "$http", "$q", "localStorageService", "jwtHelper", "HOST", function($http, $q, localStorageService, jwtHelper, HOST) {
        function mine(query) {
            var defer = $q.defer();
            return $http({
                method: "GET",
                url: api("/api/me/purchases"),
                params: query
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status >= 500 ? defer.reject(response.data) : defer.reject(response.data)) : (console.log(response), 
                void defer.resolve(response.data));
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        var Purchase = {};
        Purchase.mine = mine;
        var api = function(path) {
            return "http://" + HOST + path;
        };
        return Purchase;
    } ]);
});