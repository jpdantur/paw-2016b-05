define([ "siglasApp", "services/AuthService", "directives/password-check" ], function(siglasApp) {
    siglasApp.controller("RegisterCtrl", [ "$scope", "$rootScope", "$location", "$timeout", "toastr", "AuthService", "$filter", function($scope, $rootScope, $location, $timeout, toastr, AuthService, $filter) {
        function submit(isValid) {
            self.loading = !0, isValid && AuthService.register(self.user).then(function(data) {
                return console.log(data), AuthService.fetchProfile();
            }, function(err) {
                console.error(err), toastr.error($filter("translate")("mg.messages.registerError"));
            }).then(function(profile) {
                $rootScope.loggedUser = profile, $location.path($location.$$search.next), self.loading = !1, 
                toastr.success($filter("translate")("mg.messages.registerSuccess"));
            }, function(err) {
                console.error(err), toastr.error($filter("translate")("mg.messages.registerError"));
            });
        }
        console.log("RegisterCtrl");
        var self = this;
        if (self.user = {}, self.loading = !1, self.submit = submit, $rootScope.loggedUser) return $location.path("/");
    } ]);
});