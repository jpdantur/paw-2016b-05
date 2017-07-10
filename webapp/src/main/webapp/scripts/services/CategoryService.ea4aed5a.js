define([ "siglasApp" ], function(siglasApp) {
    siglasApp.service("CategoryService", [ "$http", "$q", "localStorageService", "jwtHelper", "HOST", function($http, $q, localStorageService, jwtHelper, HOST) {
        function tree() {
            var defer = $q.defer();
            return $http({
                method: "GET",
                url: api("/api/store/category-tree")
            }).then(function(response) {
                return response.status >= 400 ? (console.log(response.status), response.status, 
                defer.reject(response.data)) : void defer.resolve(response.data.categories);
            }, function(error) {
                console.log(error), defer.reject(error.data);
            }), defer.promise;
        }
        var Category = {};
        Category.tree = tree;
        var api = function(path) {
            return "http://" + HOST + path;
        };
        return Category;
    } ]);
});