define([ "siglasApp", "services/AuthService", "directives/password-check" ], function(siglasApp) {
    siglasApp.controller("ResetPassCtrl", [ "$scope", "$rootScope", "$location", "toastr", "AuthService", "$filter", function($scope, $rootScope, $location, toastr, AuthService, $filter) {
        function submit(valid) {
            valid && (self.loading = !0, AuthService.resetPassword(params.username, self.pass).then(function() {
                toastr.success($filter("translate")("ng.messages.passwordSuccess")), $location.path("/auth/login");
            }, function(err) {
                self.loading = !1, toastr.success($filter("translate")("ng.messages.passwordError"));
            }));
        }
        console.log("ResetPassCtrl");
        var self = this;
        self.submit = submit;
        var params = $location.search();
        return self.status = null, $rootScope.loggedUser ? $location.path("/") : void AuthService.isPasswordTokenValid(params.username, params.token).then(function(result) {
            self.status = "OK";
        }, function(err) {
            console.error(err), _.isString(err) && (self.status = err);
        });
    } ]);
});