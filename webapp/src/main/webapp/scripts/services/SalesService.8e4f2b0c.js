define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("SalesService", [ "$http", "$q", "localStorageService", "jwtHelper", "HOST", function($http, $q, localStorageService, jwtHelper, HOST) {
        function mine(query) {
            var defer = $q.defer();
            return $http({
                method: "GET",
                url: api("/api/me/sales"),
                params: query
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status >= 500 ? defer.reject(response.data) : defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        function approve(id) {
            var defer = $q.defer();
            return $http({
                method: "PUT",
                url: api("/api/store/sales/" + id + "/approve")
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status >= 500 ? defer.reject(response.data) : defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        function decline(id) {
            var defer = $q.defer();
            return $http({
                method: "PUT",
                url: api("/api/store/sales/" + id + "/decline")
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status >= 500 ? defer.reject(response.data) : defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        function findById(id) {
            var defer = $q.defer();
            return $http({
                method: "GET",
                url: api("/api/store/sales/" + id)
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status >= 500 ? defer.reject(response.data) : defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        function rateWithRole(id, role, rating) {
            var defer = $q.defer();
            return $http({
                method: "POST",
                url: api("/api/store/sales/" + id + "/" + role + "-review"),
                data: rating
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status >= 500 ? defer.reject(response.data) : defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        var Sales = {};
        Sales.mine = mine, Sales.approve = approve, Sales.decline = decline, Sales.findById = findById, 
        Sales.rateWithRole = rateWithRole;
        var api = function(path) {
            return "http://" + HOST + path;
        };
        return Sales;
    } ]);
});