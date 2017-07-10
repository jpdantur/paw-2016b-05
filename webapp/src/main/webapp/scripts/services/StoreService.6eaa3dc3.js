define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("StoreService", [ "$http", "$q", "HOST", function($http, $q, HOST) {
        function search(query) {
            var defer = $q.defer();
            return $http({
                method: "GET",
                url: api("/api/store/search"),
                params: query
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status, 
                defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        function purchase(item) {
            var defer = $q.defer();
            return $http({
                method: "PUT",
                url: api("/api/store/item/" + item.id + "/purchase")
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status, 
                defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        var StoreService = {};
        StoreService.search = search, StoreService.purchase = purchase;
        var api = function(path) {
            return "http://" + HOST + path;
        };
        return StoreService;
    } ]);
});