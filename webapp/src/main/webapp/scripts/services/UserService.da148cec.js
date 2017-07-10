define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("UserService", [ "$http", "$q", "localStorageService", "jwtHelper", "HOST", function($http, $q, localStorageService, jwtHelper, HOST) {
        function update(delta) {
            var defer = $q.defer();
            return $http({
                method: "PUT",
                url: api("/api/me"),
                data: JSON.stringify(delta)
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status, 
                defer.reject(response.data)) : void defer.resolve(response.data);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        function changePassword(passwords) {
            var defer = $q.defer();
            return $http({
                method: "PUT",
                url: api("/api/me/password"),
                data: JSON.stringify(passwords)
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status, 
                defer.reject(response.data)) : void defer.resolve(response);
            }, function(error) {
                console.log(error), defer.reject(error);
            }), defer.promise;
        }
        var User = {};
        User.update = update, User.changePassword = changePassword;
        var api = function(path) {
            return "http://" + HOST + path;
        };
        return User;
    } ]);
});