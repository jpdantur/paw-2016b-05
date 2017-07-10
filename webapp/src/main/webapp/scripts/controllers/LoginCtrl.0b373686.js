define([ "siglasApp", "services/AuthService", "services/FavouritesService" ], function(siglasApp) {
    siglasApp.controller("LoginCtrl", [ "$scope", "$rootScope", "$location", "toastr", "AuthService", "FavouritesService", "$filter", function($scope, $rootScope, $location, toastr, AuthService, FavouritesService, $filter) {
        function signIn() {
            AuthService.logIn(self.username, self.password).then(function(data) {
                return console.log(data), AuthService.fetchProfile();
            }).then(function(profile) {
                $rootScope.loggedUser = profile, $rootScope.loggedUser.favourites = [], FavouritesService.mine({
                    pageNumber: 0,
                    pageSize: 8,
                    sortOrder: "DESC",
                    sortField: "CREATED"
                }).then(function(result) {
                    $rootScope.loggedUser.favourites = result.results, $rootScope.loggedUser.favourites.hasMore = result.numberOfTotalResults > result.numberOfAvailableResults;
                }, function(error) {
                    console.error(error);
                }), toastr.success($filter("translate")("ng.messages.loginSuccessful")), $location.path($location.$$search.next || "/");
            }, function(err) {
                toastr.error($filter("translate")("ng.messages.loginError")), console.error(err);
            });
        }
        console.log("LoginCtrl");
        var self = this;
        if (self.password = "", self.username = "", self.signIn = signIn, $rootScope.loggedUser) return $location.path("/");
    } ]);
});